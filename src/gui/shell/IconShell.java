package gui.shell;

import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.Image;

import project.Project;
import data.Animation;
import data.subcontent.Icon;
import lwt.container.LSashPanel;
import lwt.container.LScrollPanel;
import lwt.dataestructure.LDataTree;
import lwt.dialog.LObjectShell;
import lwt.dialog.LShell;
import lwt.event.LControlEvent;
import lwt.event.listener.LControlListener;
import lwt.widget.LImage;
import lwt.widget.LNodeSelector;

import org.eclipse.swt.events.MouseAdapter;
import org.eclipse.swt.events.MouseEvent;
import org.eclipse.swt.events.PaintListener;
import org.eclipse.swt.events.PaintEvent;
import org.eclipse.wb.swt.SWTResourceManager;

public class IconShell extends LObjectShell<Icon> {
	
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
		image.setBackground(SWTResourceManager.getColor(SWT.COLOR_DARK_GRAY));
		image.addPaintListener(new PaintListener() {
			public void paintControl(PaintEvent e) {
				Animation anim = (Animation) tree.getSelectedObject();
				if (anim != null && anim.cols > 0 && anim.rows > 0) {
					int w = anim.quad.width / anim.cols;
					int h = anim.quad.height / anim.rows;
					e.gc.drawRectangle(anim.quad.x + w * col, anim.quad.y + h * row, w, h);
				}
			}
		});
		image.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseUp(MouseEvent arg0) {
				Animation anim = (Animation) tree.getSelectedObject();
				if (anim != null) {
					col = (int) (arg0.x - anim.quad.x) / (anim.quad.width / anim.cols);
					row = (int) (arg0.y - anim.quad.y) / (anim.quad.height / anim.rows);
					image.redraw();
				}
			}
		});
		scroll.setContent(image);
		
		sashForm.setWeights(new int[] {1, 2});
		
		pack();
	}
	
	private void setImage(Animation anim) {
		if (anim == null)
			return;
		Image img = anim.quad.getImage();
		image.setImage(img);
		scroll.setMinSize(anim.quad.width, anim.quad.height);
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
