package gui.shell;

import gui.Vocab;

import java.util.ArrayList;

import org.eclipse.swt.widgets.Combo;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Spinner;

import data.Bonus;
import lwt.dialog.LObjectShell;

import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.layout.GridData;

public abstract class BonusShell extends LObjectShell<Bonus> {
	
	private Combo cmbID;
	private Spinner spnValue;

	public BonusShell(Shell parent) {
		super(parent);
		content.setLayout(new GridLayout(2, false));
		
		Label lblID = new Label(content, SWT.NONE);
		lblID.setText(Vocab.instance.ID);
		
		cmbID = new Combo(content, SWT.BORDER | SWT.READ_ONLY);
		cmbID.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1));
		
		Label lblValue = new Label(content, SWT.NONE);
		lblValue.setLayoutData(new GridData(SWT.LEFT, SWT.FILL, false, false, 1, 1));
		lblValue.setText(Vocab.instance.VALUE);
		
		spnValue = new Spinner(content, SWT.NONE);
		GridData gd_txtValue = new GridData(SWT.FILL, SWT.FILL, true, true, 1, 1);
		gd_txtValue.widthHint = 170;
		gd_txtValue.heightHint = 75;
		spnValue.setLayoutData(gd_txtValue);
		
		pack();
	}
	
	public void open(Bonus initial) {
		super.open(initial);
		cmbID.setItems(getItems(getArray()));
		cmbID.select(initial.id);
		spnValue.setSelection(initial.value);
	}

	@Override
	protected Bonus createResult(Bonus initial) {
		if (cmbID.getSelectionIndex() == initial.id && spnValue.getSelection() == initial.value) {
			return null;
		} else {
			Bonus bonus = new Bonus();
			bonus.id = cmbID.getSelectionIndex();
			bonus.value = spnValue.getSelection();
			return bonus;
		}
	}
	
	protected void checkSubclass() { }
	
	protected ArrayList<?> getArray() { return null; }
	
}
