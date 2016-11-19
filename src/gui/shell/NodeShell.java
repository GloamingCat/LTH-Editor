package gui.shell;

import gui.Vocab;

import java.util.ArrayList;

import org.eclipse.swt.widgets.Shell;

import data.Node;
import lwt.dialog.LObjectShell;
import lwt.editor.LComboView;
import lwt.widget.LText;

import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.layout.GridData;

public abstract class NodeShell extends LObjectShell<Node> {
	
	private LComboView cmbID;
	private LText txtName;

	public NodeShell(Shell parent) {
		super(parent);
		content.setLayout(new GridLayout(2, false));
		
		Label lblName = new Label(content, SWT.NONE);
		lblName.setLayoutData(new GridData(SWT.LEFT, SWT.FILL, false, false, 1, 1));
		lblName.setText(Vocab.instance.NAME);
		
		txtName = new LText(content, SWT.NONE);
		GridData gd_txtValue = new GridData(SWT.FILL, SWT.FILL, true, true, 1, 1);
		gd_txtValue.widthHint = 170;
		gd_txtValue.heightHint = 75;
		txtName.setLayoutData(gd_txtValue);
		
		Label lblID = new Label(content, SWT.NONE);
		lblID.setText(Vocab.instance.ID);
		
		NodeShell self = this;
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
	
	public void open(Node initial) {
		super.open(initial);
		cmbID.getControl().setValue(initial.id);
		txtName.setValue(initial.name);
	}

	@Override
	protected Node createResult(Node initial) {
		if (cmbID.getControl().getValue().equals(initial.id) && txtName.getValue().equals(initial.name)) {
			return null;
		} else {
			Node bonus = new Node();
			bonus.id = (Integer) cmbID.getControl().getValue();
			bonus.name = (String) txtName.getValue();
			return bonus;
		}
	}
	
	protected void checkSubclass() { }
	
	protected ArrayList<?> getArray() { return null; }
	
}
