package gui.widgets;

import lui.LovelyTheme;
import lui.base.LFlags;
import lui.container.LContainer;
import lui.container.LPanel;

import lui.container.LImage;
import lui.editor.LObjectEditor;
import lui.widget.LLabel;

public class IconButtonPanel extends LPanel {

    public IconButtonPanel(LContainer parent, LLabel lblIcon, LObjectEditor<?> editor, String attName) {
        super(parent);
		setGridLayout(2);
		getCellData().setExpand(true, false);
		getCellData().setTargetSize(-1, 48);
		getCellData().setRequiredSize(-1, 48);

		LImage imgIcon = new LImage(this);
		imgIcon.setBackground(LovelyTheme.LIGHT);
		imgIcon.getCellData().setExpand(true, true);
		imgIcon.setAlignment(LFlags.LEFT | LFlags.TOP);

		IconButton btnGraphics = new IconButton(this, true);
		btnGraphics.setImageWidget(imgIcon);
		btnGraphics.addMenu(lblIcon);
		btnGraphics.addMenu(imgIcon);
		editor.addControl(btnGraphics, attName);

    }
}
