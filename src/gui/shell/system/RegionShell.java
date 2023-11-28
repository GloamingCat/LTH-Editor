package gui.shell.system;

import gui.Vocab;
import gui.shell.ObjectShell;
import gui.widgets.IDList;
import lwt.LFlags;
import lwt.container.LFrame;
import lwt.container.LPanel;
import lwt.dialog.LShell;
import lwt.widget.LLabel;
import lwt.widget.LText;

import org.eclipse.swt.SWT;

import project.Project;

import data.config.Region;

import org.eclipse.wb.swt.SWTResourceManager;

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
		
		LPanel imgColor = new LPanel(color);
		imgColor.setBackground(SWTResourceManager.getColor(SWT.COLOR_WHITE));
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
