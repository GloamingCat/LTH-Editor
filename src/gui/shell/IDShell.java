package gui.shell;

import gui.Vocab;

import java.util.ArrayList;

import org.eclipse.swt.widgets.Combo;
import org.eclipse.swt.widgets.Shell;

import lwt.dialog.LObjectShell;

import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.graphics.Point;

public abstract class IDShell extends LObjectShell<Integer> {
	
	private Combo cmbID;

	public IDShell(Shell parent) {
		super(parent);
		setMinimumSize(new Point(224, 39));
		
		content.setLayout(new GridLayout(2, false));
		
		Label lblID = new Label(content, SWT.NONE);
		lblID.setText(Vocab.instance.ID);
		
		cmbID = new Combo(content, SWT.BORDER | SWT.READ_ONLY);
		cmbID.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1));
		
		pack();
	}
	
	public void open(Integer initial) {
		super.open(initial);
		cmbID.setItems(getItems(getArray()));
		cmbID.select(initial);
	}

	@Override
	protected Integer createResult(Integer initial) {
		if (cmbID.getSelectionIndex() == initial) {
			return null;
		} else {
			return cmbID.getSelectionIndex();
		}
	}
	
	protected void checkSubclass() { }
	
	protected ArrayList<?> getArray() { return null; }
	
}
