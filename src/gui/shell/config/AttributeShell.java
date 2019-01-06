package gui.shell.config;

import gui.Vocab;
import gui.shell.ObjectShell;

import org.eclipse.swt.widgets.Shell;

import data.config.Attribute;
import lwt.widget.LText;
import lwt.widget.LTextBox;

import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.layout.GridData;

public class AttributeShell extends ObjectShell<Attribute> {
	
	public AttributeShell(Shell parent) {
		super(parent);
		
		contentEditor.setLayout(new GridLayout(2, false));
		
		Label lblName = new Label(contentEditor, SWT.NONE);
		lblName.setText(Vocab.instance.NAME);
		
		LText txtName = new LText(contentEditor, SWT.NONE);
		txtName.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, true, 1, 1));
		addControl(txtName, "name");
		
		Label lblShortName = new Label(contentEditor, SWT.NONE);
		lblShortName.setText(Vocab.instance.SHORTNAME);
		
		LText txtShortName = new LText(contentEditor, SWT.NONE);
		txtShortName.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true, 1, 1));
		addControl(txtShortName, "shortName");
		
		Label lblScript = new Label(contentEditor, SWT.NONE);
		lblScript.setLayoutData(new GridData(SWT.LEFT, SWT.FILL, false, false, 1, 1));
		lblScript.setText(Vocab.instance.SCRIPT);
		
		LTextBox txtScript = new LTextBox(contentEditor, SWT.NONE);
		GridData gd_txtScript = new GridData(SWT.FILL, SWT.FILL, true, true, 1, 1);
		gd_txtScript.minimumWidth = 192;
		gd_txtScript.minimumHeight = 48;
		txtScript.setLayoutData(gd_txtScript);
		addControl(txtScript, "script");
		
		pack();
	}
	
}
