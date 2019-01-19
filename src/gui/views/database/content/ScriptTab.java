package gui.views.database.content;

import lwt.widget.LCheckButton;
import lwt.widget.LTextBox;
import gson.project.GObjectTreeSerializer;
import gui.Vocab;
import gui.views.database.DatabaseTab;
import gui.widgets.ConstantList;

import org.eclipse.swt.widgets.Composite;

import project.Project;

import org.eclipse.swt.widgets.Group;
import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.widgets.Label;

public class ScriptTab extends DatabaseTab {

	/**
	 * Create the composite.
	 * @param parent
	 * @param style
	 */
	public ScriptTab(Composite parent, int style) {
		super(parent, style);
		
		Label lblGlobal = new Label(grpGeneral, SWT.NONE);
		lblGlobal.setText("Global");
		
		LCheckButton btnGlobal = new LCheckButton(grpGeneral, SWT.NONE);
		addControl(btnGlobal, "global");
		
		Group grpConstants = new Group(contentEditor, SWT.NONE);
		grpConstants.setLayout(new FillLayout(SWT.HORIZONTAL));
		GridData gd_grpConstants = new GridData(SWT.FILL, SWT.FILL, false, true, 1, 2);
		gd_grpConstants.widthHint = 175;
		grpConstants.setLayoutData(gd_grpConstants);
		grpConstants.setText(Vocab.instance.CONSTANTS);
		
		ConstantList lstConstants = new ConstantList(grpConstants, SWT.NONE);
		addChild(lstConstants, "constants");
		
		Group grpCommands = new Group(contentEditor, SWT.NONE);
		grpCommands.setLayout(new FillLayout(SWT.HORIZONTAL));
		grpCommands.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true, 1, 1));
		grpCommands.setText(Vocab.instance.COMMANDS);
		
		LTextBox txtCommands = new LTextBox(grpCommands, SWT.NONE);
		addControl(txtCommands, "commands");
		
	}

	@Override
	protected GObjectTreeSerializer getSerializer() {
		return Project.current.scripts;
	}

}
