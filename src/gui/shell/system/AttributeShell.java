package gui.shell.system;

import gui.Tooltip;
import gui.Vocab;
import gui.shell.ObjectShell;

import data.config.Attribute;
import lwt.dialog.LWindow;
import lwt.widget.LCombo;
import lwt.widget.LLabel;
import lwt.widget.LText;
import lwt.widget.LTextBox;

public class AttributeShell extends ObjectShell<Attribute> {
	
	public AttributeShell(LWindow parent) {
		super(parent, Vocab.instance.ATTRIBUTESHELL);
	}
	
	@Override
	protected void createContent(int style) {
		super.createContent(style);
		
		contentEditor.setGridLayout(2);
		
		new LLabel(contentEditor, Vocab.instance.KEY, Tooltip.instance.KEY);
		
		LText txtKey = new LText(contentEditor);
		addControl(txtKey, "key");
		
		new LLabel(contentEditor, Vocab.instance.NAME, Tooltip.instance.NAME);
		
		LText txtName = new LText(contentEditor);
		addControl(txtName, "name");
		
		new LLabel(contentEditor, Vocab.instance.SHORTNAME, Tooltip.instance.SHORTNAME);
		
		LText txtShortName = new LText(contentEditor);
		addControl(txtShortName, "shortName");

		new LLabel(contentEditor, Vocab.instance.VISIBILITY, Tooltip.instance.VISIBILITY);
		
		LCombo cmbVisibliy = new LCombo(contentEditor, true);
		cmbVisibliy.setOptional(false);
		cmbVisibliy.setIncludeID(false);
		cmbVisibliy.setItems(new String [] {
			Vocab.instance.NOTVISIBLE,
			Vocab.instance.PRIMARY,
			Vocab.instance.SECONDARY
		});
		addControl(cmbVisibliy, "visibility");
		
		new LLabel(contentEditor, Vocab.instance.FORMULA, Tooltip.instance.FORMULA);
		//lblScript.setLayoutData(new GridData(SWT.LEFT, SWT.FILL, false, false, 1, 1));
		
		LTextBox txtScript = new LTextBox(contentEditor);
		txtScript.getCellData().setMinimumSize(200, 48);
		addControl(txtScript, "script");
		
		pack();
	}
	
}
