package gui.shell;

import project.Project;
import data.Animation;
import data.subcontent.Icon;
import lwt.graphics.LColor;
import lwt.graphics.LPainter;
import lwt.LFlags;
import lwt.container.LImage;
import lwt.container.LSashPanel;
import lwt.container.LScrollPanel;
import lwt.dataestructure.LDataTree;
import lwt.dialog.LObjectShell;
import lwt.dialog.LShell;
import lwt.event.LControlEvent;
import lwt.event.LMouseEvent;
import lwt.event.listener.LControlListener;
import lwt.event.listener.LMouseListener;
import lwt.widget.LNodeSelector;

public class IconShell extends LObjectShell<Icon> {
	
	protected static LColor bg = new LColor(127, 127, 127);
	protected LNodeSelector<Object> tree;
	protected LImage image;
	protected int col, row;
	private LScrollPanel scroll;
	
	public IconShell(LShell parent, boolean optional) {
		super(parent);
		setMinimumSize(600, 400);
		setSize(800, 800);
		
		LSashPanel sashForm = new LSashPanel(content, true);
		
		tree = new LNodeSelector<Object>(sashForm, optional);
		tree.setCollection(getTree());
		tree.addModifyListener(new LControlListener<Integer>() {
			@Override
			public void onModify(LControlEvent<Integer> event) {
				Animation anim = (Animation) tree.getSelectedObject();
				setImage(anim);
			}
		});
		
		scroll = new LScrollPanel(sashForm, true);
		image = new LImage(scroll);
		image.setBackground(bg);
		image.setAlignment(LFlags.TOP & LFlags.LEFT);
		image.addPainter(new LPainter() {
			public void paint() {
				Animation anim = (Animation) tree.getSelectedObject();
				if (anim != null && anim.cols > 0 && anim.rows > 0) {
					int w = anim.quad.width / anim.cols;
					int h = anim.quad.height / anim.rows;
					drawRect(anim.quad.x + w * col, anim.quad.y + h * row, w, h);
				}
			}
		});
		image.addMouseListener(new LMouseListener() {
			@Override
			public void onMouseChange(LMouseEvent e) {
				if (e.button == LFlags.LEFT && e.type == LFlags.PRESS) {
					Animation anim = (Animation) tree.getSelectedObject();
					if (anim != null) {
						col = (int) (e.x - anim.quad.x) / (anim.quad.width / anim.cols);
						row = (int) (e.y - anim.quad.y) / (anim.quad.height / anim.rows);
						image.redraw();
					}
				}
			}
		});
		
		sashForm.setWeights(1, 2);
		
		pack();
	}
	
	private void setImage(Animation anim) {
		if (anim == null)
			return;
		image.setImage(anim.quad.fullPath());
		scroll.refreshSize(anim.quad.width, anim.quad.height);
		image.redraw();
	}
	
	public void open(Icon initial) {
		super.open(initial);
		if (initial.id >= 0) {
			col = initial.col;
			row = initial.row;
			LDataTree<Object> node = getTree().findNode(initial.id);
			if (node != null) {
				tree.setValue(node.id);
				setImage((Animation) node.data);
				return;
			}
		} else {
			col = row = 0;
		}
		tree.setValue(-1);
	}
	
	@Override
	protected Icon createResult(Icon initial) {
		Icon icon = new Icon();
		icon.id = tree.getValue();
		icon.col = col;
		icon.row = row;
		return icon;
	}
	
	protected LDataTree<Object> getTree() {
		return Project.current.animations.getTree();
	}
	
}
