package gui.shell.system;

import gui.Vocab;
import gui.shell.ObjectShell;

import org.eclipse.swt.widgets.Shell;

import data.config.Attribute;
import lwt.widget.LCombo;
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
		
		Label lblKey = new Label(contentEditor, SWT.NONE);
		lblKey.setText(Vocab.instance.KEY);
		
		LText txtKey = new LText(contentEditor, SWT.NONE);
		txtKey.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true, 1, 1));
		addControl(txtKey, "key");
		
		Label lblVisibility = new Label(contentEditor, SWT.NONE);
		lblVisibility.setText(Vocab.instance.VISIBILITY);
		
		LCombo cmbVisibliy = new LCombo(contentEditor);
		cmbVisibliy.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, false, false, 1, 1));
		cmbVisibliy.setOptional(false);
		cmbVisibliy.setIncludeID(false);
		cmbVisibliy.setItems(new String [] {
			Vocab.instance.NOTVISIBLE,
			Vocab.instance.PRIMARY,
			Vocab.instance.SECONDARY
		});
		addControl(cmbVisibliy, "visibility");
		
		Label lblScript = new Label(contentEditor, SWT.NONE);
		lblScript.setLayoutData(new GridData(SWT.LEFT, SWT.FILL, false, false, 1, 1));
		lblScript.setText(Vocab.instance.SCRIPT);
		
		LTextBox txtScript = new LTextBox(contentEditor, SWT.NONE);
		GridData gd_txtScript = new GridData(SWT.FILL, SWT.FILL, true, true, 1, 1);
		gd_txtScript.widthHint = 266;
		gd_txtScript.minimumWidth = 192;
		gd_txtScript.minimumHeight = 48;
		txtScript.setLayoutData(gd_txtScript);
		addControl(txtScript, "script");
		
		pack();
	}
	
}
