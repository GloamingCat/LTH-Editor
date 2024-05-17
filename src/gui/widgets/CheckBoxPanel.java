package gui.widgets;

import lui.base.LFlags;
import lui.base.LPrefs;
import lui.container.LContainer;
import lui.container.LPanel;

public class CheckBoxPanel extends LPanel {

    public CheckBoxPanel(LContainer parent) {
        super(parent);
        setSequentialLayout(true);
        getCellData().setExpand(true, false);
        getCellData().setAlignment(LFlags.FILL);
        setSpacing(LPrefs.GRIDSPACING, 0);
        setMargins(LPrefs.GRIDSPACING, 0);
    }

}
