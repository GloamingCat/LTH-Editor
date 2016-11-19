package gui.shell;

import gui.Vocab;

import java.util.ArrayList;

import org.eclipse.swt.widgets.Shell;

import data.Bonus;
import lwt.dialog.LObjectShell;
import lwt.editor.LComboView;
import lwt.widget.LSpinner;

import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.layout.GridData;

public abstract class BonusShell extends LObjectShell<Bonus> {
	
	private LComboView cmbID;
	private LSpinner spnValue;

	public BonusShell(Shell parent) {
		super(parent);
		content.setLayout(new GridLayout(2, false));
		
		Label lblID = new Label(content, SWT.NONE);
		lblID.setText(Vocab.instance.ID);
		
		BonusShell self = this;
		cmbID = new LComboView(content, SWT.NONE) {
			@Override
			protected ArrayList<?> getArray() {
				return self.getArray();
			}
		};
		cmbID.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1));
		cmbID.setOptional(false);
		
		Label lblValue = new Label(content, SWT.NONE);
		lblValue.setLayoutData(new GridData(SWT.LEFT, SWT.FILL, false, false, 1, 1));
		lblValue.setText(Vocab.instance.VALUE);
		
		spnValue = new LSpinner(content, SWT.NONE);
		GridData gd_txtValue = new GridData(SWT.FILL, SWT.FILL, true, true, 1, 1);
		gd_txtValue.widthHint = 170;
		gd_txtValue.heightHint = 75;
		spnValue.setLayoutData(gd_txtValue);
		
		pack();
	}
	
	public void open(Bonus initial) {
		super.open(initial);
		cmbID.getControl().setValue(initial.id);
		spnValue.setValue(initial.value);
	}

	@Override
	protected Bonus createResult(Bonus initial) {
		if (cmbID.getControl().getValue().equals(initial.id) && spnValue.getValue().equals(initial.value)) {
			return null;
		} else {
			Bonus bonus = new Bonus();
			bonus.id = (Integer) cmbID.getControl().getValue();
			bonus.value = (Integer) spnValue.getValue();
			return bonus;
		}
	}
	
	protected void checkSubclass() { }
	
	protected ArrayList<?> getArray() { return null; }
	
}
