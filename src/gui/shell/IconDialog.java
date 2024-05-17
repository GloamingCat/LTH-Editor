package gui.shell;

import project.Project;
import data.Animation;
import data.subcontent.Icon;
import gui.Vocab;
import lui.base.LFlags;
import lui.graphics.LColor;
import lui.graphics.LPainter;
import lui.container.LImage;
import lui.container.LFlexPanel;
import lui.container.LScrollPanel;
import lui.base.data.LDataTree;
import lui.dialog.LObjectDialog;
import lui.dialog.LWindow;
import lui.widget.LNodeSelector;

public class IconDialog extends LObjectDialog<Icon> {
	
	protected static LColor bg = new LColor(127, 127, 127);
	protected LNodeSelector<Object> tree;
	protected LImage image;
	protected int col, row;
	private LScrollPanel scroll;
	
	public static final int OPTIONAL = 0x01;
	
	/**
	 * @wbp.parser.constructor
	 */
	public IconDialog(LWindow parent, int style) {
		super(parent, 600, 400, style, Vocab.instance.ICONSHELL);
		setCurrentSize(800, 800);
	}
	
	@Override
	protected void createContent(int style) {
		super.createContent(style);
		content.setFillLayout(true);
		LFlexPanel sashForm = new LFlexPanel(content, true);
		tree = new LNodeSelector<>(sashForm, (style & OPTIONAL) > 0);
		tree.setCollection(getTree());
		tree.addModifyListener(event -> {
            Animation anim = (Animation) tree.getSelectedObject();
            setImage(anim);
        });

		scroll = new LScrollPanel(sashForm);
		image = new LImage(scroll);
		image.setBackground(bg);
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
					col = Math.max(0, Math.min(col, anim.cols - 1));
					row = Math.max(0, Math.min(row, anim.rows - 1));
                    image.repaint();
                }
            }
        });
		
		sashForm.setWeights(1, 2);
	}
	
	private void setImage(Animation anim) {
		if (anim == null)
			return;
		image.setImage(anim.quad.fullPath());
		scroll.setContentSize(anim.quad.width, anim.quad.height);
		image.repaint();
	}
	
	public void open(Icon initial) {
		setIcon(initial);
		super.open(initial);
	}

	protected void setIcon(Icon icon) {
		if (icon.id >= 0) {
			col = icon.col;
			row = icon.row;
			LDataTree<Object> node = getTree().findNode(icon.id);
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
