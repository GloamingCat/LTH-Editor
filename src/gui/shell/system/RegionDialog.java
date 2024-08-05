package gui.shell.system;

import gui.Tooltip;
import gui.Vocab;
import lui.gson.GObjectDialog;
import gui.widgets.IDList;
import lui.base.LFlags;
import lui.container.LFrame;
import lui.container.LImage;
import lui.dialog.LWindow;
import lui.graphics.LColor;
import lui.widget.LColorButton;
import lui.widget.LLabel;
import lui.widget.LText;

import project.Project;

import data.config.Region;

public class RegionDialog extends GObjectDialog<Region> {
	
	public RegionDialog(LWindow parent) {
		super(parent, 250, 250, Vocab.instance.REGIONSHELL);
	}
	
	@Override
	protected void createContent(int style) {
		super.createContent(style);
		contentEditor.setGridLayout(3);
		
		new LLabel(contentEditor, Vocab.instance.NAME, Tooltip.instance.DISPLAYNAME);
		LText txtName = new LText(contentEditor);
		txtName.getCellData().setExpand(true, false);
		txtName.getCellData().setSpread(2, 1);
		addControl(txtName, "name");

		new LLabel(contentEditor, Vocab.instance.COLOR, Tooltip.instance.COLOR);

		LImage imgColor = new LImage(contentEditor);
		imgColor.setBackground(new LColor(255, 255, 255));
		imgColor.getCellData().setExpand(true, false);
		imgColor.getCellData().setAlignment(LFlags.FILL);
		LColorButton btnColor = new LColorButton(contentEditor);
		btnColor.setImageWidget(imgColor);
		addControl(btnColor, "color");

		LFrame grpFields = new LFrame(contentEditor, Vocab.instance.BATTLEFIELDS);
		grpFields.setFillLayout(true);
		grpFields.setHoverText(Tooltip.instance.BATTLEFIELDS);
		grpFields.getCellData().setExpand(true, true);
		grpFields.getCellData().setSpread(3, 1);
		grpFields.getCellData().setRequiredSize(150, 100);
		IDList lstFields = new IDList(grpFields, Vocab.instance.TROOPSHELL);
		lstFields.dataTree = Project.current.fieldTree.getData().toObjectTree();
		addChild(lstFields, "battleFields");
	}

}
