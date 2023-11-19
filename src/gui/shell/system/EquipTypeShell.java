package gui.shell.system;

import gui.Vocab;
import gui.shell.ObjectShell;

import data.config.EquipType;
import lwt.dialog.LShell;
import lwt.widget.LCombo;
import lwt.widget.LLabel;
import lwt.widget.LSpinner;
import lwt.widget.LText;

import org.eclipse.swt.layout.GridLayout;

public class EquipTypeShell extends ObjectShell<EquipType> {

	public EquipTypeShell(LShell parent) {
		super(parent);
		
		setText(Vocab.instance.EQUIP);
		contentEditor.setLayout(new GridLayout(2, false));
		
		new LLabel(contentEditor, Vocab.instance.NAME);

		LText txtName = new LText(contentEditor);
		addControl(txtName, "name");
		
		new LLabel(contentEditor, Vocab.instance.KEY);

		LText txtKey = new LText(contentEditor);
		addControl(txtKey, "key");
		
		new LLabel(contentEditor, Vocab.instance.STATE);
		
		LCombo cmbState = new LCombo(contentEditor);
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
		spnCount.setMinimum(1);
		spnCount.setMaximum(99);
		addControl(spnCount, "count");
		
		pack();
	}
	
}
