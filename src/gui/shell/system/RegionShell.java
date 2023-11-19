package gui.shell.system;

import gui.Vocab;
import gui.shell.ObjectShell;
import gui.widgets.IDList;
import lwt.container.LFrame;
import lwt.container.LPanel;
import lwt.dialog.LShell;
import lwt.widget.LLabel;
import lwt.widget.LText;

import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;

import project.Project;

import data.config.Region;

import org.eclipse.wb.swt.SWTResourceManager;

public class RegionShell extends ObjectShell<Region> {
	
	public RegionShell(LShell parent) {
		super(parent);
		
		contentEditor.setLayout(new GridLayout(2, false));
		
		new LLabel(contentEditor, Vocab.instance.NAME);
		
		LText txtName = new LText(contentEditor);
		addControl(txtName, "name");
		
		new LLabel(contentEditor, Vocab.instance.COLOR);
		
		LPanel color = new LPanel(contentEditor, 2, false);
		color.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, false, false, 1, 1));
		
		LPanel imgColor = new LPanel(color);
		imgColor.setBackground(SWTResourceManager.getColor(SWT.COLOR_WHITE));
		imgColor.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1));

		// TODO
		//ColorButton btnColor = new ColorButton(color, SWT.NONE);
		//btnColor.setColorWidget(imgColor);
		//addControl(btnColor, "rgb");
		
		LFrame grpTroops = new LFrame(contentEditor, Vocab.instance.TROOPS, true, true);
		GridData gd_grpBattler = new GridData(SWT.FILL, SWT.FILL, false, true, 2, 1);
		gd_grpBattler.widthHint = 155;
		gd_grpBattler.heightHint = 98;
		grpTroops.setLayoutData(gd_grpBattler);
		IDList lstTroops = new IDList(grpTroops);
		lstTroops.dataTree = Project.current.troops.getTree();
		addChild(lstTroops, "troops");

		pack();
	}

}
