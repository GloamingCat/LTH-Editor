package gui.widgets;

import lui.base.LFlags;
import lui.base.data.LDataTree;
import lui.container.LContainer;
import lui.container.LFlexPanel;
import lui.container.LScrollPanel;
import lui.graphics.LColor;
import lui.widget.LNodeSelector;

public class AnimationSelector extends LFlexPanel {

    protected static LColor bg = new LColor(127, 127, 127);

    protected final LNodeSelector<Object> tree;
    protected final TransformedImage image;
    private final LScrollPanel scroll;

    public AnimationSelector(LContainer parent) {
        super(parent, true);
		tree = new LNodeSelector<>(this, true);
		scroll = new LScrollPanel(this);
		image = new TransformedImage(scroll);
		image.setBackground(bg);
		image.getCellData().setAlignment(LFlags.TOP | LFlags.LEFT);
        tree.addModifyListener(event -> setAnimation(event.newValue));
    }

    public void setCollection(LDataTree<Object> collection) {
        tree.setCollection(collection);
    }

    public int getSelectedId() {
        return tree.getValue() == null ? -1 : tree.getValue();
    }

    public void setAnimation(Integer id) {
        if (id == null) {
            tree.setValue(-1);
            image.updateImage(-1);
        } else {
            image.updateImage(id);
        }
        scroll.setContentSize(image.getTargetSize());
        image.repaint();
    }

}
