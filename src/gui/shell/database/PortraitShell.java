package gui.shell.database;

import gui.Tooltip;
import gui.Vocab;
import lui.base.LFlags;
import lui.base.event.listener.LControlListener;
import lui.base.event.listener.LMouseListener;
import lui.graphics.LColor;
import lui.graphics.LPainter;
import lui.container.LImage;
import lui.container.LFlexPanel;
import lui.container.LScrollPanel;
import lui.base.data.LDataTree;
import lui.dialog.LObjectWindow;
import lui.dialog.LWindow;
import lui.base.event.LControlEvent;
import lui.base.event.LMouseEvent;
import lui.widget.LLabel;
import lui.widget.LNodeSelector;
import lui.widget.LText;

import data.Animation;
import data.GameCharacter.Portrait;

import project.Project;

public class PortraitShell extends LObjectWindow<Portrait> {
	
	protected LNodeSelector<Object> tree;
	protected LImage image;
	protected int col, row;
	private LScrollPanel scroll;
	private LText txtName;
	
	public PortraitShell(LWindow parent) {
		super(parent, Vocab.instance.PORTRAITSHELL);
		setMinimumSize(600, 400);
	}
	
	@Override
	protected void createContent(int style) {
		super.createContent(style);

		content.setGridLayout(2);
		
		new LLabel(content, Vocab.instance.NAME, Tooltip.instance.KEY);
		txtName = new LText(content);

		LFlexPanel sashForm = new LFlexPanel(content, true);
		sashForm.getCellData().setSpread(2, 1);
		sashForm.getCellData().setExpand(true, true);
		
		tree = new LNodeSelector<Object>(sashForm, true);
		tree.setCollection(getTree());
		tree.addModifyListener(new LControlListener<Integer>() {
			@Override
			public void onModify(LControlEvent<Integer> event) {
				Animation anim = (Animation) tree.getSelectedObject();
				setImage(anim);
			}
		});
		
		scroll = new LScrollPanel(sashForm);
		
		image = new LImage(scroll);
		image.setBackground(new LColor(127, 127, 127));
		image.getCellData().setAlignment(LFlags.TOP | LFlags.LEFT);
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
		scroll.setContentSize(anim.quad.width, anim.quad.height);
		image.redraw();
	}
	
	public void open(Portrait initial) {
		super.open(initial);
		txtName.setValue(initial.name);
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
	protected Portrait createResult(Portrait initial) {
		Portrait icon = new Portrait();
		icon.name = txtName.getValue();
		icon.id = tree.getValue();
		icon.col = col;
		icon.row = row;
		return icon;
	}
	
	protected LDataTree<Object> getTree() {
		return Project.current.animations.getTree();
	}
	
}
