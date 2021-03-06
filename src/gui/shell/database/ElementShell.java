package gui.shell.database;

import gui.Vocab;
import gui.shell.ObjectShell;

import org.eclipse.swt.widgets.Shell;

import data.subcontent.Element;
import lwt.widget.LCombo;
import lwt.widget.LSpinner;

import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.graphics.Point;

import project.Project;

public class ElementShell extends ObjectShell<Element> {

	public ElementShell(Shell parent) {
		super(parent);
		setMinimumSize(new Point(272, 100));
		contentEditor.setLayout(new GridLayout(2, false));
		
		Label lblID = new Label(contentEditor, SWT.NONE);
		lblID.setText(Vocab.instance.ELEMENT);
				
		LCombo cmbID = new LCombo(contentEditor, SWT.NONE);
		cmbID.setOptional(false);
		cmbID.setIncludeID(true);
		cmbID.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, false, false, 1, 1));
		cmbID.setItems(Project.current.elements.getList());
		addControl(cmbID, "id");
		
		Label lblValue = new Label(contentEditor, SWT.NONE);
		lblValue.setText(Vocab.instance.VALUE);
		
		LSpinner spnValue = new LSpinner(contentEditor, SWT.NONE);
		spnValue.setMinimum(-10000);
		spnValue.setMaximum(10000);
		spnValue.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1));
		addControl(spnValue, "value");
		
		Label lblType = new Label(contentEditor, SWT.NONE);
		lblType.setText(Vocab.instance.TYPE);
		
		LCombo cmbType = new LCombo(contentEditor, SWT.NONE);
		cmbType.setOptional(false);
		cmbType.setIncludeID(false);
		cmbType.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, false, false, 1, 1));
		cmbType.setItems(new String[] {
				Vocab.instance.ELEMENTDEF,
				Vocab.instance.ELEMENTATK,
				Vocab.instance.ELEMENTBUFF
		});
		addControl(cmbType, "type");
		
		pack();
	}
	
}
