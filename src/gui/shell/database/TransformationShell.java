package gui.shell.database;

import gui.Vocab;
import gui.shell.ObjectShell;

import org.eclipse.swt.widgets.Shell;

import data.subcontent.Transformation;
import lwt.widget.LCheckBox;
import lwt.widget.LCombo;
import lwt.widget.LLabel;
import lwt.widget.LSpinner;

import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.layout.GridData;

public class TransformationShell extends ObjectShell<Transformation> {

	public TransformationShell(Shell parent) {
		super(parent, 270, 100);
		contentEditor.setLayout(new GridLayout(2, false));
		
		new LLabel(contentEditor, Vocab.instance.TYPE);
		
		LCombo cmbType = new LCombo(contentEditor);
		cmbType.setOptional(false);
		cmbType.setIncludeID(false);
		cmbType.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, false, false, 1, 1));
		cmbType.setItems(Transformation.types);
		addControl(cmbType, "type");
		
		new LLabel(contentEditor, Vocab.instance.VALUE);
		
		LSpinner spnValue = new LSpinner(contentEditor);
		spnValue.setMinimum(-10000);
		spnValue.setMaximum(10000);
		spnValue.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1));
		addControl(spnValue, "value");
		
		LCheckBox btnOverride = new LCheckBox(contentEditor);
		btnOverride.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, false, false, 2, 1));
		btnOverride.setText(Vocab.instance.OVERRIDETRANSFORM);
		addControl(btnOverride, "override");
		
		pack();
	}
	
}
