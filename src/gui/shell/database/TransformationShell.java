package gui.shell.database;

import gui.Tooltip;
import gui.Vocab;
import gui.shell.ObjectShell;

import data.subcontent.Transformation;
import lui.dialog.LWindow;
import lui.widget.LCheckBox;
import lui.widget.LCombo;
import lui.widget.LLabel;
import lui.widget.LSpinner;

public class TransformationShell extends ObjectShell<Transformation> {

	public TransformationShell(LWindow parent) {
		super(parent, Vocab.instance.TRANSFORMSHELL);
		setMinimumSize(270, 100);
	}
	
	@Override
	protected void createContent(int style) {
		super.createContent(style);
		
		contentEditor.setGridLayout(2);
		
		new LLabel(contentEditor, Vocab.instance.TYPE, Tooltip.instance.TRANSFORMTYPE);
		
		LCombo cmbType = new LCombo(contentEditor, true);
		cmbType.setOptional(false);
		cmbType.setIncludeID(false);
		cmbType.setItems(Transformation.types);
		addControl(cmbType, "type");
		
		new LLabel(contentEditor, Vocab.instance.VALUE, Tooltip.instance.TRANSFORMVALUE);
		
		LSpinner spnValue = new LSpinner(contentEditor);
		spnValue.setMinimum(-10000);
		spnValue.setMaximum(10000);
		addControl(spnValue, "value");
		
		LCheckBox btnOverride = new LCheckBox(contentEditor, 2);
		btnOverride.setText(Vocab.instance.OVERRIDETRANSFORM);
		btnOverride.setHoverText(Tooltip.instance.OVERRIDETRANSFORM);
		addControl(btnOverride, "override");
		
		pack();
	}
	
}
