package gui.shell.database;

import gui.Vocab;
import gui.shell.ObjectShell;

import data.subcontent.Transformation;
import lwt.dialog.LShell;
import lwt.widget.LCheckBox;
import lwt.widget.LCombo;
import lwt.widget.LLabel;
import lwt.widget.LSpinner;

import org.eclipse.swt.layout.GridLayout;

public class TransformationShell extends ObjectShell<Transformation> {

	public TransformationShell(LShell parent) {
		super(parent, 270, 100);
		contentEditor.setLayout(new GridLayout(2, false));
		
		new LLabel(contentEditor, Vocab.instance.TYPE);
		
		LCombo cmbType = new LCombo(contentEditor);
		cmbType.setOptional(false);
		cmbType.setIncludeID(false);
		cmbType.setItems(Transformation.types);
		addControl(cmbType, "type");
		
		new LLabel(contentEditor, Vocab.instance.VALUE);
		
		LSpinner spnValue = new LSpinner(contentEditor);
		spnValue.setMinimum(-10000);
		spnValue.setMaximum(10000);
		addControl(spnValue, "value");
		
		LCheckBox btnOverride = new LCheckBox(contentEditor, 2);
		btnOverride.setText(Vocab.instance.OVERRIDETRANSFORM);
		addControl(btnOverride, "override");
		
		pack();
	}
	
}
