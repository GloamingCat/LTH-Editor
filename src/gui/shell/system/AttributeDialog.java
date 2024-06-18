package gui.shell.system;

import gui.Tooltip;
import gui.Vocab;
import lui.gson.GObjectDialog;

import data.config.Attribute;
import lui.dialog.LWindow;
import lui.widget.LCombo;
import lui.widget.LLabel;
import lui.widget.LText;
import lui.widget.LTextBox;

public class AttributeDialog extends GObjectDialog<Attribute> {
	
	public AttributeDialog(LWindow parent) {
		super(parent, 300, 300, Vocab.instance.ATTRIBUTESHELL);
	}
	
	@Override
	protected void createContent(int style) {
		super.createContent(style);
		contentEditor.setGridLayout(2);
		
		new LLabel(contentEditor, Vocab.instance.KEY, Tooltip.instance.KEY);
		LText txtKey = new LText(contentEditor);
		txtKey.getCellData().setExpand(true, false);
		addControl(txtKey, "key");

		new LLabel(contentEditor, Vocab.instance.NAME, Tooltip.instance.NAME);
		LText txtName = new LText(contentEditor);
		txtName.getCellData().setExpand(true, false);
		addControl(txtName, "name");

		new LLabel(contentEditor, Vocab.instance.SHORTNAME, Tooltip.instance.SHORTNAME);
		LText txtShortName = new LText(contentEditor);
		txtShortName.getCellData().setExpand(true, false);
		addControl(txtShortName, "shortName");

		new LLabel(contentEditor, Vocab.instance.VISIBILITY, Tooltip.instance.ATTVISIBLE);
		LCombo cmbVisibility = new LCombo(contentEditor, LCombo.READONLY);
		cmbVisibility.getCellData().setExpand(true, false);
		cmbVisibility.setItems(new String [] {
			Vocab.instance.NOTVISIBLE,
			Vocab.instance.PRIMARY,
			Vocab.instance.SECONDARY
		});
		addControl(cmbVisibility, "visibility");

		new LLabel(contentEditor, Vocab.instance.FORMULA, Tooltip.instance.FORMULA);
		LTextBox txtScript = new LTextBox(contentEditor);
		txtScript.getCellData().setExpand(true, true);
		txtScript.getCellData().setRequiredSize(200, 48);
		addControl(txtScript, "script");

	}
	
}
