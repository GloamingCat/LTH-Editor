package gui.shell.database;

import gui.Vocab;

import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Spinner;

import data.Item.Attribute;
import lwt.dialog.LObjectShell;

import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Text;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.layout.GridData;

public class AttributeBonusShell extends LObjectShell<Attribute> {
	
	private Text txtKey;
	private Spinner spnAdd;
	private Spinner spnMul;

	public AttributeBonusShell(Shell parent) {
		super(parent);
		setMinimumSize(new Point(200, 100));
		content.setLayout(new GridLayout(2, false));
		
		Label lblName = new Label(content, SWT.NONE);
		lblName.setText(Vocab.instance.KEY);
		
		txtKey = new Text(content, SWT.BORDER);
		GridData gd_txtKey = new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1);
		gd_txtKey.widthHint = 136;
		txtKey.setLayoutData(gd_txtKey);
		
		Label lblAdd = new Label(content, SWT.NONE);
		lblAdd.setText(Vocab.instance.ATTADD);
		
		spnAdd = new Spinner(content, SWT.BORDER);
		spnAdd.setMaximum(999999);
		spnAdd.setMinimum(-999999);
		spnAdd.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, false, false, 1, 1));
		
		Label lblMul = new Label(content, SWT.NONE);
		lblMul.setText(Vocab.instance.ATTMUL);
		
		spnMul = new Spinner(content, SWT.BORDER);
		spnMul.setMaximum(999999);
		spnMul.setMinimum(-999999);
		spnMul.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, false, false, 1, 1));
		
		pack();
	}
	
	public void open(Attribute initial) {
		super.open(initial);
		txtKey.setText(initial.key);
		spnAdd.setSelection(initial.add);
		spnMul.setSelection(initial.mul);
	}

	@Override
	protected Attribute createResult(Attribute initial) {
		if (txtKey.getText().equals(initial.key) &&
				spnAdd.getSelection() == initial.add &&
				spnMul.getSelection() == initial.mul) {
			return null;
		} else {
			Attribute att = new Attribute();
			att.key = txtKey.getText();
			att.add = spnAdd.getSelection();
			att.mul = spnMul.getSelection();
			return att;
		}
	}
	
}
