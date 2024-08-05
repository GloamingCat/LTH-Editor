package gui.shell.system;

import data.config.UIConfig;
import gui.Tooltip;
import gui.Vocab;
import lui.base.LFlags;
import lui.container.LImage;
import lui.dialog.LWindow;
import lui.gson.GObjectDialog;
import lui.widget.LColorButton;
import lui.widget.LLabel;
import lui.widget.LText;

public class BaseColorDialog extends GObjectDialog<UIConfig.BaseColor> {

	public BaseColorDialog(LWindow parent) {
		super(parent, 0, Vocab.instance.REGIONSHELL);
	}
	
	@Override
	protected void createContent(int style) {
		super.createContent(style);

		contentEditor.setGridLayout(3);

		new LLabel(contentEditor, Vocab.instance.NAME, Tooltip.instance.KEY);
		LText txtName = new LText(contentEditor);
		txtName.getCellData().setExpand(true, false);
		txtName.getCellData().setSpread(2, 1);
		addControl(txtName, "name");

		new LLabel(contentEditor, Vocab.instance.COLOR, Tooltip.instance.COLOR);
		LImage imgColor = new LImage(contentEditor);
		imgColor.getCellData().setExpand(true, false);
		imgColor.getCellData().setAlignment(LFlags.FILL);
		LColorButton btnColor = new LColorButton(contentEditor);
		btnColor.setImageWidget(imgColor);
		addControl(btnColor, "color");

	}
}
