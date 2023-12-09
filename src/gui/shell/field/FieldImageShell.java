package gui.shell.field;

import gui.Vocab;
import lwt.LFlags;
import lwt.container.LImage;
import lwt.container.LPanel;
import lwt.container.LSashPanel;
import lwt.container.LScrollPanel;
import lwt.dataestructure.LDataTree;
import lwt.dialog.LObjectShell;
import lwt.dialog.LShell;
import lwt.event.LControlEvent;
import lwt.event.LMouseEvent;
import lwt.event.listener.LControlListener;
import lwt.event.listener.LMouseListener;
import lwt.graphics.LColor;
import lwt.graphics.LPainter;
import lwt.widget.LCheckBox;
import lwt.widget.LLabel;
import lwt.widget.LNodeSelector;
import lwt.widget.LText;

import data.Animation;
import data.field.FieldImage;

import project.Project;


public class FieldImageShell extends LObjectShell<FieldImage> {
	
	protected LNodeSelector<Object> tree;
	protected LImage image;
	protected int col, row;
	private LScrollPanel scroll;
	private LText txtName;
	private LCheckBox btnVisible;
	private LCheckBox btnForeground;
	private LCheckBox btnGlued;
	
	public FieldImageShell(LShell parent) {
		super(parent);
		setMinimumSize(400, 300);

		content.setGridLayout(2, false);
		
		new LLabel(content, Vocab.instance.NAME);
		
		txtName = new LText(content);

		LSashPanel sashForm = new LSashPanel(content, true);
		sashForm.setExpand(true, true);
		sashForm.setSpread(2, 1);
		
		tree = new LNodeSelector<Object>(sashForm, false);
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
		image.setBackground(new LColor(100, 100, 100));
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
		sashForm.setWeights(new int[] {1, 2});

		LPanel options = new LPanel(content, 3, false);
		options.setExpand(true, false);
		options.setSpread(2, 1);
		
		btnVisible = new LCheckBox(options);
		btnVisible.setText(Vocab.instance.VISIBLE);
		
		btnForeground = new LCheckBox(options);
		btnForeground.setText(Vocab.instance.FOREGROUND);
		
		btnGlued = new LCheckBox(options);
		btnGlued.setText(Vocab.instance.GLUED);
		
		pack();
	}
	
	private void setImage(Animation anim) {
		if (anim == null)
			return;
		image.setImage(anim.quad.fullPath());
		scroll.refreshSize(anim.quad.width, anim.quad.height);
		image.redraw();
	}
	
	public void open(FieldImage initial) {
		super.open(initial);
		txtName.setValue(initial.name);
		btnVisible.setValue(initial.visible);
		btnForeground.setValue(initial.foreground);
		btnGlued.setValue(initial.glued);
		tree.setValue(-1);
		if (initial.id >= 0) {
			col = initial.col;
			row = initial.row;
			LDataTree<Object> node = getTree().findNode(initial.id);
			if (node != null) {
				tree.setValue(node.id);
				setImage((Animation) node.data);
			}
		} else {
			col = row = 0;
		}
	}
	
	@Override
	protected FieldImage createResult(FieldImage initial) {
		FieldImage icon = new FieldImage();
		icon.name = txtName.getValue();
		icon.id = tree.getValue();
		icon.col = col;
		icon.row = row;
		icon.visible = btnVisible.getValue();
		icon.foreground = btnForeground.getValue();
		icon.glued = btnGlued.getValue();
		return icon;
	}
	
	protected LDataTree<Object> getTree() {
		return Project.current.animations.getTree();
	}
	
}
