package gui.shell.system;

import gui.Vocab;
import gui.shell.ObjectShell;
import gui.widgets.IDList;
import lwt.LFlags;
import lwt.container.LFrame;
import lwt.container.LImage;
import lwt.container.LPanel;
import lwt.dialog.LShell;
import lwt.graphics.LColor;
import lwt.widget.LLabel;
import lwt.widget.LText;

import project.Project;

import data.config.Region;

public class RegionShell extends ObjectShell<Region> {
	
	public RegionShell(LShell parent) {
		super(parent);
		
		contentEditor.setGridLayout(2, false);
		
		new LLabel(contentEditor, Vocab.instance.NAME);
		
		LText txtName = new LText(contentEditor);
		addControl(txtName, "name");
		
		new LLabel(contentEditor, Vocab.instance.COLOR);
		
		LPanel color = new LPanel(contentEditor, 2, false);
		color.setAlignment(LFlags.CENTER);
		
		LImage imgColor = new LImage(color);
		imgColor.setBackground(new LColor(255, 255, 255));
		imgColor.setExpand(true, false);
		imgColor.setAlignment(LFlags.CENTER);

		// TODO
		//ColorButton btnColor = new ColorButton(color, SWT.NONE);
		//btnColor.setColorWidget(imgColor);
		//addControl(btnColor, "rgb");
		
		LFrame grpTroops = new LFrame(contentEditor, Vocab.instance.TROOPS, true, true);
		grpTroops.setSpread(2, 1);
		grpTroops.setExpand(true, true);
		grpTroops.setMinimumWidth(150);
		grpTroops.setMinimumHeight(100);
		IDList lstTroops = new IDList(grpTroops);
		lstTroops.dataTree = Project.current.troops.getTree();
		addChild(lstTroops, "troops");

		pack();
	}

}
