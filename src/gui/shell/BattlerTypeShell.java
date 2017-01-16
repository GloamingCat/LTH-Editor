package gui.shell;

import gui.Vocab;

import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Text;

import lwt.dialog.LObjectShell;

import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;

import data.BattlerType;

public class BattlerTypeShell extends LObjectShell<BattlerType> {
	
	private Text txtName;
	private Text txtCode;

	public BattlerTypeShell(Shell parent) {
		super(parent);
		
		content.setLayout(new GridLayout(2, false));
		
		Label lblName = new Label(content, SWT.NONE);
		lblName.setText(Vocab.instance.NAME);
		
		txtName = new Text(content, SWT.BORDER);
		txtName.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1));
		
		Label lblCode = new Label(content, SWT.NONE);
		lblCode.setText(Vocab.instance.CODE);
		
		txtCode = new Text(content, SWT.BORDER);
		txtCode.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1));
		
		pack();
	}
	
	public void open(BattlerType initial) {
		super.open(initial);
		txtName.setText(initial.name);
		txtCode.setText(initial.code);
	}

	@Override
	protected BattlerType createResult(BattlerType initial) {
		if (txtName.getText().equals(initial.name) && 
				txtCode.getText().equals(initial.code)) {
			return null;
		} else {
			BattlerType t = new BattlerType();
			t.name = txtName.getText();
			t.code = txtCode.getText();
			return t;
		}
	}

}
