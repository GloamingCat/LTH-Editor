package gui.shell;

import gui.Vocab;

import java.util.ArrayList;

import org.eclipse.swt.widgets.Shell;

import lwt.dialog.LObjectShell;
import lwt.editor.LComboView;

import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;

public abstract class IDShell extends LObjectShell<Integer> {
	
	private LComboView cmbID;

	public IDShell(Shell parent) {
		super(parent);
		
		content.setLayout(new GridLayout(2, false));
		
		Label lblID = new Label(content, SWT.NONE);
		lblID.setText(Vocab.instance.ID);
		
		IDShell self = this;
		cmbID = new LComboView(content, SWT.NONE) {
			@Override
			protected ArrayList<?> getArray() {
				return self.getArray();
			}
		};
		cmbID.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1));
		cmbID.setOptional(false);
		
		pack();
	}
	
	public void open(Integer initial) {
		super.open(initial);
		cmbID.getControl().setValue(initial);
	}

	@Override
	protected Integer createResult(Integer initial) {
		if (cmbID.getControl().getValue().equals(initial)) {
			return null;
		} else {
			return (Integer) cmbID.getControl().getValue();
		}
	}
	
	protected void checkSubclass() { }
	
	protected ArrayList<?> getArray() { return null; }
	
}
