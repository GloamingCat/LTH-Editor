package gui.widgets;

import data.Animation;
import data.subcontent.Icon;
import lui.base.LFlags;
import lui.container.LContainer;
import lui.graphics.LPainter;

public class IconSelector extends AnimationSelector {

    private int col, row;

    public IconSelector(LContainer parent) {
        super(parent);
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
    }

    public void setIcon(Icon icon) {
        if (icon != null && icon.id >= 0) {
            col = icon.col;
            row = icon.row;
            setAnimation(icon.id);
        } else {
            col = row = 0;
            setAnimation(null);
        }
    }

    public int getSelectedCol() {
        return col;
    }

    public int getSelectedRow() {
        return row;
    }

}
