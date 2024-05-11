package gui.shell.system;

import gui.Tooltip;
import gui.Vocab;
import gui.shell.ObjectEditorDialog;
import gui.widgets.IDList;
import lui.base.LFlags;
import lui.container.LFrame;
import lui.container.LImage;
import lui.container.LPanel;
import lui.dialog.LWindow;
import lui.graphics.LColor;
import lui.widget.LLabel;
import lui.widget.LText;

import project.Project;

import data.config.Region;

public class RegionDialog extends ObjectEditorDialog<Region> {
	
	public RegionDialog(LWindow parent) {
		super(parent, Vocab.instance.REGIONSHELL);
		setRequiredSize(250, 250);
		setSize(250, 250);
	}
	
	@Override
	protected void createContent(int style) {
		super.createContent(style);
		
		contentEditor.setGridLayout(2);
		
		new LLabel(contentEditor, Vocab.instance.NAME, Tooltip.instance.NAME);
		
		LText txtName = new LText(contentEditor);
		txtName.getCellData().setExpand(true, false);
		addControl(txtName, "name");
		
		new LLabel(contentEditor, Vocab.instance.COLOR, Tooltip.instance.COLOR);
		
		LPanel color = new LPanel(contentEditor);
		color.setGridLayout(2);
		color.getCellData().setAlignment(LFlags.CENTER);
		LImage imgColor = new LImage(color);
		imgColor.setBackground(new LColor(255, 255, 255));
		imgColor.getCellData().setExpand(true, false);
		imgColor.getCellData().setAlignment(LFlags.CENTER);
		// TODO
		//ColorButton btnColor = new ColorButton(color, SWT.NONE);
		//btnColor.setColorWidget(imgColor);
		//addControl(btnColor, "rgb");

		LFrame grpTroops = new LFrame(contentEditor, Vocab.instance.BATTLEFIELDS);
		grpTroops.setFillLayout(true);
		grpTroops.setHoverText(Tooltip.instance.BATTLEFIELDS);
		grpTroops.getCellData().setSpread(2, 1);
		grpTroops.getCellData().setExpand(true, true);
		grpTroops.getCellData().setRequiredSize(150, 100);
		IDList lstTroops = new IDList(grpTroops, Vocab.instance.TROOPSHELL);
		lstTroops.dataTree = Project.current.fieldTree.getData().toObjectTree();
		addChild(lstTroops, "troops");
	}

}
