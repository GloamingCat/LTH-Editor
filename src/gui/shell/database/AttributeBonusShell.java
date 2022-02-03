package gui.shell.database;

import lwt.widget.LLabel;
import lwt.widget.LSpinner;
import lwt.widget.LText;
import gui.Vocab;
import gui.shell.ObjectShell;

import org.eclipse.swt.widgets.Shell;

import data.Item.Attribute;

import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.layout.GridData;

public class AttributeBonusShell extends ObjectShell<Attribute> {

	public AttributeBonusShell(Shell parent) {
		super(parent, 400, 100);
		contentEditor.setLayout(new GridLayout(2, false));
		
		new LLabel(contentEditor, Vocab.instance.KEY);
		
		LText txtKey = new LText(contentEditor);
		GridData gd_txtKey = new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1);
		gd_txtKey.widthHint = 136;
		txtKey.setLayoutData(gd_txtKey);
		addControl(txtKey, "key");
		
		new LLabel(contentEditor, Vocab.instance.ATTADD);
		
		LSpinner spnAdd = new LSpinner(contentEditor, SWT.NONE);
		spnAdd.setMaximum(999999);
		spnAdd.setMinimum(-999999);
		spnAdd.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, false, false, 1, 1));
		addControl(spnAdd, "add");
		
		new LLabel(contentEditor, Vocab.instance.ATTMUL);
		
		LSpinner spnMul = new LSpinner(contentEditor, SWT.NONE);
		spnMul.setMaximum(999999);
		spnMul.setMinimum(-999999);
		spnMul.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, false, false, 1, 1));
		addControl(spnMul, "mul");
		
		pack();
	}
	
}
