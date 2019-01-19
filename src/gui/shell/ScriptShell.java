package gui.shell;

import gui.Vocab;
import gui.widgets.ConstantList;
import lwt.widget.LCheckButton;
import lwt.widget.LTextBox;

import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Group;
import org.eclipse.swt.widgets.Shell;

import data.Script;

public class ScriptShell extends ObjectShell<Script> {

	/**
	 * Create the shell.
	 * @param display
	 */
	public ScriptShell(Shell parent) {
		super(parent);
		
		GridLayout gridLayout = new GridLayout(2, false);
		gridLayout.verticalSpacing = 0;
		contentEditor.setLayout(gridLayout);
		
		Group grpCommands = new Group(contentEditor, SWT.NONE);
		grpCommands.setLayout(new FillLayout(SWT.HORIZONTAL));
		grpCommands.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true, 1, 1));
		grpCommands.setText(Vocab.instance.COMMANDS);
		
		LTextBox txtCommands = new LTextBox(grpCommands, SWT.NONE);
		addControl(txtCommands, "commands");
		
		Group grpConstants = new Group(contentEditor, SWT.NONE);
		grpConstants.setLayout(new FillLayout(SWT.HORIZONTAL));
		GridData gd_grpConstants = new GridData(SWT.FILL, SWT.FILL, false, true, 1, 2);
		gd_grpConstants.widthHint = 175;
		grpConstants.setLayoutData(gd_grpConstants);
		grpConstants.setText(Vocab.instance.CONSTANTS);
		
		ConstantList lstConstants = new ConstantList(grpConstants, SWT.NONE);
		addChild(lstConstants, "constants");

		LCheckButton btnGlobal = new LCheckButton(contentEditor, SWT.NONE);
		btnGlobal.setLayoutData(new GridData(SWT.FILL, SWT.FILL, false, false, 1, 1));
		btnGlobal.setText(Vocab.instance.GLOBAL);
		addControl(btnGlobal, "global");
		
	}

}
