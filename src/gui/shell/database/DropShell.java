package gui.shell.database;

import gui.Vocab;
import gui.shell.ObjectShell;

import lwt.dialog.LShell;
import lwt.widget.LLabel;
import lwt.widget.LNodeSelector;
import lwt.widget.LSpinner;

import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.layout.GridData;

import project.Project;
import data.Battler.Drop;

public class DropShell extends ObjectShell<Drop> {

	public DropShell(LShell parent) {
		super(parent);
		setMinimumSize(400, 200);
		contentEditor.setLayout(new GridLayout(2, false));
		
		new LLabel(contentEditor, Vocab.instance.CHANCE);
		LSpinner spnChance = new LSpinner(contentEditor);
		addControl(spnChance, "value");
		
		new LLabel(contentEditor, Vocab.instance.COUNT);
		LSpinner spnCount = new LSpinner(contentEditor);
		spnCount.setMaximum(999999999);
		spnCount.setMinimum(1);
		addControl(spnCount, "count");
		
		LNodeSelector<Object> tree = new LNodeSelector<>(contentEditor, false);
		tree.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true, 2, 1));
		tree.setCollection(Project.current.items.getTree());
		addControl(tree, "id");
		
		pack();
	}
	
}
