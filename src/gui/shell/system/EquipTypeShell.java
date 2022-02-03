package gui.shell.system;

import gui.Vocab;
import gui.shell.ObjectShell;

import org.eclipse.swt.widgets.Shell;

import data.config.EquipType;
import lwt.widget.LCombo;
import lwt.widget.LLabel;
import lwt.widget.LSpinner;
import lwt.widget.LText;

import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.GridData;

public class EquipTypeShell extends ObjectShell<EquipType> {

	public EquipTypeShell(Shell parent) {
		super(parent);
		
		setText(Vocab.instance.EQUIP);
		contentEditor.setLayout(new GridLayout(2, false));
		
		new LLabel(contentEditor, Vocab.instance.NAME);

		LText txtName = new LText(contentEditor);
		txtName.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, false, false, 1, 1));
		addControl(txtName, "name");
		
		new LLabel(contentEditor, Vocab.instance.KEY);

		LText txtKey = new LText(contentEditor);
		txtKey.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, false, false, 1, 1));
		addControl(txtKey, "key");
		
		new LLabel(contentEditor, Vocab.instance.STATE);
		
		LCombo cmbState = new LCombo(contentEditor, SWT.NONE);
		cmbState.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, false, false, 1, 1));
		cmbState.setIncludeID(false);
		cmbState.setOptional(false);
		cmbState.setItems(new String[] {
				Vocab.instance.FREE, Vocab.instance.NOTEMPTY,
				Vocab.instance.ALLEQUIPED, Vocab.instance.UNCHANGABLE,
				Vocab.instance.INVISIBLE
		});
		addControl(cmbState, "state");
		
		new LLabel(contentEditor, Vocab.instance.COUNT);
		
		LSpinner spnCount = new LSpinner(contentEditor);
		spnCount.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, false, false, 1, 1));
		spnCount.setMinimum(1);
		spnCount.setMaximum(99);
		addControl(spnCount, "count");
		
		pack();
	}
	
}
