package gui.shell;

import lwt.dialog.LObjectShell;

import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Shell;

import data.Troop.Unit;

public class UnitShell extends LObjectShell<Unit> {

	/**
	 * Create the shell.
	 * @param display
	 */
	public UnitShell(Shell parent) {
		super(parent);
		
		content.setLayout(new GridLayout(2, false));
	}

	/**
	 * Create contents of the shell.
	 */
	protected void createContents() {
		setText("SWT Application");
		setSize(450, 300);

	}

	@Override
	protected Unit createResult(Unit initial) {
		// TODO Auto-generated method stub
		return null;
	}
	
	@Override
	protected void checkSubclass() {
		// Disable the check that prevents subclassing of SWT components
	}

}
