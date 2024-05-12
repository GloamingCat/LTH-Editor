package gui.widgets;

import lui.container.LContainer;
import lui.widget.LTextBox;

public class DescriptionField extends LTextBox {
    public DescriptionField(LContainer parent) {
        super(parent, false);
        getCellData().setExpand(true, true);
		getCellData().setRequiredSize(0, 60);
    }


}
