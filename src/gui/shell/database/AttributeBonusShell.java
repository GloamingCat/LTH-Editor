package gui.shell.database;

import lwt.widget.LSpinner;
import lwt.widget.LText;
import gui.Vocab;
import gui.shell.ObjectShell;

import org.eclipse.swt.widgets.Shell;

import data.Item.Attribute;

import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.layout.GridData;

public class AttributeBonusShell extends ObjectShell<Attribute> {

	public AttributeBonusShell(Shell parent) {
		super(parent);
		setMinimumSize(new Point(200, 100));
		contentEditor.setLayout(new GridLayout(2, false));
		
		Label lblName = new Label(contentEditor, SWT.NONE);
		lblName.setText(Vocab.instance.KEY);
		
		LText txtKey = new LText(contentEditor, SWT.BORDER);
		GridData gd_txtKey = new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1);
		gd_txtKey.widthHint = 136;
		txtKey.setLayoutData(gd_txtKey);
		addControl(txtKey, "key");
		
		Label lblAdd = new Label(contentEditor, SWT.NONE);
		lblAdd.setText(Vocab.instance.ATTADD);
		
		LSpinner spnAdd = new LSpinner(contentEditor, SWT.BORDER);
		spnAdd.setMaximum(999999);
		spnAdd.setMinimum(-999999);
		spnAdd.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, false, false, 1, 1));
		addControl(spnAdd, "add");
		
		Label lblMul = new Label(contentEditor, SWT.NONE);
		lblMul.setText(Vocab.instance.ATTMUL);
		
		LSpinner spnMul = new LSpinner(contentEditor, SWT.BORDER);
		spnMul.setMaximum(999999);
		spnMul.setMinimum(-999999);
		spnMul.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, false, false, 1, 1));
		addControl(spnMul, "mul");
		
		pack();
	}
	
}
