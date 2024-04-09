package gui.shell.system;

import gui.Tooltip;
import gui.Vocab;
import gui.shell.ObjectShell;
import gui.widgets.IDList;
import lbase.LFlags;
import lwt.container.LFrame;
import lwt.container.LImage;
import lwt.container.LPanel;
import lwt.dialog.LWindow;
import lwt.graphics.LColor;
import lwt.widget.LLabel;
import lwt.widget.LText;

import project.Project;

import data.config.Region;

public class RegionShell extends ObjectShell<Region> {
	
	public RegionShell(LWindow parent) {
		super(parent, Vocab.instance.REGIONSHELL);
	}
	
	@Override
	protected void createContent(int style) {
		super.createContent(style);
		
		contentEditor.setGridLayout(2);
		
		new LLabel(contentEditor, Vocab.instance.NAME, Tooltip.instance.NAME);
		
		LText txtName = new LText(contentEditor);
		addControl(txtName, "name");
		
		new LLabel(contentEditor, Vocab.instance.COLOR, Tooltip.instance.COLOR);
		
		LPanel color = new LPanel(contentEditor);
		color.setGridLayout(2);
		color.getCellData().setAlignment(LFlags.CENTER);
		
		LImage imgColor = new LImage(color);
		imgColor.setBackground(new LColor(255, 255, 255));
		imgColor.getCellData().setExpand(true, false);
		imgColor.getCellData().setAlignment(LFlags.CENTER);
		LFrame frame = new LFrame(contentEditor, (String) Vocab.instance.BATTLEFIELDS);
		frame.setFillLayout(true);

		// TODO
		//ColorButton btnColor = new ColorButton(color, SWT.NONE);
		//btnColor.setColorWidget(imgColor);
		//addControl(btnColor, "rgb");
		
		LFrame grpTroops = frame;
		grpTroops.setHoverText(Tooltip.instance.BATTLEFIELDS);
		grpTroops.getCellData().setSpread(2, 1);
		grpTroops.getCellData().setExpand(true, true);
		grpTroops.getCellData().setMinimumSize(150, 100);
		IDList lstTroops = new IDList(grpTroops, Vocab.instance.TROOPSHELL);
		lstTroops.dataTree = Project.current.fieldTree.getData().toObjectTree();
		addChild(lstTroops, "troops");

		pack();
	}

}
