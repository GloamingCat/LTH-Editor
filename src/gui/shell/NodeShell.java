package gui.shell;

import gui.Vocab;

import java.util.ArrayList;

import org.eclipse.swt.widgets.Combo;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Text;

import data.subcontent.Node;
import lwt.dialog.LObjectShell;

import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.layout.GridData;

public abstract class NodeShell extends LObjectShell<Node> {
	
	private Combo cmbID;
	private Text txtName;

	public NodeShell(Shell parent) {
		super(parent);
		content.setLayout(new GridLayout(2, false));
		
		Label lblName = new Label(content, SWT.NONE);
		lblName.setLayoutData(new GridData(SWT.LEFT, SWT.FILL, false, false, 1, 1));
		lblName.setText(Vocab.instance.NAME);
		
		txtName = new Text(content, SWT.BORDER);
		GridData gd_txtValue = new GridData(SWT.FILL, SWT.FILL, true, true, 1, 1);
		gd_txtValue.widthHint = 170;
		gd_txtValue.heightHint = 75;
		txtName.setLayoutData(gd_txtValue);
		
		Label lblID = new Label(content, SWT.NONE);
		lblID.setText(Vocab.instance.ID);
		
		cmbID = new Combo(content, SWT.BORDER | SWT.READ_ONLY);
		cmbID.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1));
		
		pack();
	}
	
	public void open(Node initial) {
		super.open(initial);
		cmbID.setItems(getItems(getArray()));
		cmbID.select(initial.id);
		txtName.setText(initial.name);
	}

	@Override
	protected Node createResult(Node initial) {
		if (cmbID.getSelectionIndex() == initial.id && txtName.getText().equals(initial.name)) {
			return null;
		} else {
			Node bonus = new Node();
			bonus.id = cmbID.getSelectionIndex();
			bonus.name = txtName.getText();
			return bonus;
		}
	}
	
	protected void checkSubclass() { }
	
	protected ArrayList<?> getArray() { return null; }
	
}
