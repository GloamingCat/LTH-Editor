package gui.shell.system;

import gui.Vocab;
import gui.shell.ObjectShell;
import gui.widgets.ColorButton;
import gui.widgets.IDList;

import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Group;
import org.eclipse.swt.widgets.Shell;

import lwt.dataestructure.LDataTree;
import lwt.widget.LLabel;
import lwt.widget.LText;

import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.layout.FillLayout;

import project.Project;

import data.config.Region;

import org.eclipse.wb.swt.SWTResourceManager;

public class RegionShell extends ObjectShell<Region> {
	
	public RegionShell(Shell parent) {
		super(parent);
		
		contentEditor.setLayout(new GridLayout(2, false));
		
		new LLabel(contentEditor, Vocab.instance.NAME);
		
		LText txtName = new LText(contentEditor, SWT.NONE);
		txtName.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1));
		addControl(txtName, "name");
		
		new LLabel(contentEditor, Vocab.instance.COLOR);
		
		Composite color = new Composite(contentEditor, SWT.NONE);
		color.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, false, false, 1, 1));
		GridLayout gl_color = new GridLayout(2, false);
		gl_color.marginHeight = 0;
		gl_color.marginWidth = 0;
		color.setLayout(gl_color);
		
		Composite imgColor = new Composite(color, SWT.NONE);
		imgColor.setBackground(SWTResourceManager.getColor(SWT.COLOR_WHITE));
		imgColor.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1));

		ColorButton btnColor = new ColorButton(color, SWT.NONE);
		btnColor.setColorWidget(imgColor);
		addControl(btnColor, "rgb");
		
		Group grpTroops = new Group(contentEditor, SWT.NONE);
		grpTroops.setLayout(new FillLayout(SWT.HORIZONTAL));
		GridData gd_grpBattler = new GridData(SWT.FILL, SWT.FILL, false, true, 2, 1);
		gd_grpBattler.widthHint = 155;
		gd_grpBattler.heightHint = 98;
		grpTroops.setLayoutData(gd_grpBattler);
		grpTroops.setText(Vocab.instance.TROOPS);
		
		IDList lstTroops = new IDList(grpTroops, SWT.NONE) {
			public LDataTree<Object> getDataTree() {
				return Project.current.troops.getTree();
			}
		};
		addChild(lstTroops, "troops");

		pack();
	}

}
