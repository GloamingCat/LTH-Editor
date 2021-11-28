package gui.shell.database;

import gui.Vocab;
import gui.shell.ObjectShell;

import org.eclipse.swt.widgets.Shell;

import data.subcontent.Element;
import lwt.widget.LCombo;
import lwt.widget.LLabel;
import lwt.widget.LSpinner;

import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.layout.GridData;

import project.Project;

public class ElementShell extends ObjectShell<Element> {

	public ElementShell(Shell parent) {
		super(parent, 270, 100);
		contentEditor.setLayout(new GridLayout(2, false));
		
		new LLabel(contentEditor, Vocab.instance.ELEMENT);
				
		LCombo cmbID = new LCombo(contentEditor, SWT.NONE);
		cmbID.setOptional(false);
		cmbID.setIncludeID(true);
		cmbID.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, false, false, 1, 1));
		cmbID.setItems(Project.current.elements.getList());
		addControl(cmbID, "id");
		
		new LLabel(contentEditor, Vocab.instance.VALUE);
		
		LSpinner spnValue = new LSpinner(contentEditor, SWT.NONE);
		spnValue.setMinimum(-10000);
		spnValue.setMaximum(10000);
		spnValue.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1));
		addControl(spnValue, "value");
		
		new LLabel(contentEditor, Vocab.instance.TYPE);
		
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
