package gui.shell.field;

import gui.Tooltip;
import gui.Vocab;
import lui.base.LFlags;
import lui.container.LImage;
import lui.container.LPanel;
import lui.container.LFlexPanel;
import lui.container.LScrollPanel;
import lui.base.data.LDataTree;
import lui.dialog.LObjectDialog;
import lui.dialog.LWindow;
import lui.graphics.LColor;
import lui.graphics.LPainter;
import lui.widget.LCheckBox;
import lui.widget.LLabel;
import lui.widget.LNodeSelector;
import lui.widget.LText;

import data.Animation;
import data.field.FieldImage;

import project.Project;


public class FieldImageShell extends LObjectDialog<FieldImage> {
	
	protected LNodeSelector<Object> tree;
	protected LImage image;
	protected int col, row;
	private LScrollPanel scroll;
	private LText txtName;
	private LCheckBox btnVisible;
	private LCheckBox btnForeground;
	private LCheckBox btnGlued;
	
	public FieldImageShell(LWindow parent) {
		super(parent, Vocab.instance.FIELDIMGSHELL);
		setRequiredSize(400, 300);
		setSize(600, 400);
	}
	
	@Override
	protected void createContent(int style) {
		super.createContent(style);
		content.setGridLayout(2);
		
		new LLabel(content, Vocab.instance.NAME, Tooltip.instance.KEY);
		txtName = new LText(content);
		txtName.getCellData().setExpand(true, false);

		LFlexPanel sashForm = new LFlexPanel(content, true);
		sashForm.getCellData().setExpand(true, true);
		sashForm.getCellData().setSpread(2, 1);
		
		tree = new LNodeSelector<>(sashForm, false);
		tree.setCollection(getTree());
		tree.addModifyListener(event -> {
            Animation anim = (Animation) tree.getSelectedObject();
            setImage(anim);
        });
		
		scroll = new LScrollPanel(sashForm);
		
		image = new LImage(scroll);
		image.setBackground(new LColor(100, 100, 100));
		image.setAlignment(LFlags.TOP | LFlags.LEFT);
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
		image.addMouseListener(e -> {
            if (e.button == LFlags.LEFT && e.type == LFlags.PRESS) {
                Animation anim = (Animation) tree.getSelectedObject();
                if (anim != null) {
                    col = (e.x - anim.quad.x) / (anim.quad.width / anim.cols);
                    row = (e.y - anim.quad.y) / (anim.quad.height / anim.rows);
                    image.redraw();
                }
            }
        });
		sashForm.setWeights(1, 2);

		LPanel options = new LPanel(content);
		options.setGridLayout(3);
		options.getCellData().setExpand(true, false);
		options.getCellData().setSpread(2, 1);
		
		btnVisible = new LCheckBox(options);
		btnVisible.setText(Vocab.instance.VISIBLE);
		btnVisible.setHoverText(Tooltip.instance.IMGVISIBLE);
		
		btnForeground = new LCheckBox(options);
		btnForeground.setText(Vocab.instance.FOREGROUND);
		btnForeground.setHoverText(Tooltip.instance.FOREGROUND);
		
		btnGlued = new LCheckBox(options);
		btnGlued.setText(Vocab.instance.GLUED);
		btnGlued.setHoverText(Tooltip.instance.GLUED);
	}
	
	private void setImage(Animation anim) {
		if (anim == null)
			return;
		image.setImage(anim.quad.fullPath());
		scroll.setContentSize(anim.quad.width, anim.quad.height);
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
