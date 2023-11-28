package gui.shell.system;

import gui.Vocab;
import gui.shell.ObjectShell;

import data.config.Attribute;
import lwt.dialog.LShell;
import lwt.widget.LCombo;
import lwt.widget.LLabel;
import lwt.widget.LText;
import lwt.widget.LTextBox;

public class AttributeShell extends ObjectShell<Attribute> {
	
	public AttributeShell(LShell parent) {
		super(parent);
		
		contentEditor.setGridLayout(2, false);
		
		new LLabel(contentEditor, Vocab.instance.NAME);
		
		LText txtName = new LText(contentEditor);
		addControl(txtName, "name");
		
		new LLabel(contentEditor, Vocab.instance.SHORTNAME);
		
		LText txtShortName = new LText(contentEditor);
		addControl(txtShortName, "shortName");
		
		new LLabel(contentEditor, Vocab.instance.KEY);
		
		LText txtKey = new LText(contentEditor);
		addControl(txtKey, "key");
		
		new LLabel(contentEditor, Vocab.instance.VISIBILITY);
		
		LCombo cmbVisibliy = new LCombo(contentEditor);
		cmbVisibliy.setOptional(false);
		cmbVisibliy.setIncludeID(false);
		cmbVisibliy.setItems(new String [] {
			Vocab.instance.NOTVISIBLE,
			Vocab.instance.PRIMARY,
			Vocab.instance.SECONDARY
		});
		addControl(cmbVisibliy, "visibility");
		
		new LLabel(contentEditor, Vocab.instance.SCRIPT);
		//lblScript.setLayoutData(new GridData(SWT.LEFT, SWT.FILL, false, false, 1, 1));
		
		LTextBox txtScript = new LTextBox(contentEditor, 1, 1);
		txtScript.setMinimumWidth(200);
		txtScript.setMinimumHeight(48);
		addControl(txtScript, "script");
		
		pack();
	}
	
}
