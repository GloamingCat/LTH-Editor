package gui.shell.database;

import gui.Vocab;
import gui.shell.ObjectShell;

import data.Job;
import lwt.dialog.LShell;
import lwt.widget.LLabel;
import lwt.widget.LNodeSelector;
import lwt.widget.LSpinner;
import project.Project;

import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.layout.GridData;

public class JobStatusShell extends ObjectShell<Job.Status> {

	public JobStatusShell(LShell parent) {
		super(parent);
		setMinimumSize(300, 100);
		contentEditor.setLayout(new GridLayout(2, false));

		LNodeSelector<Object> tree = new LNodeSelector<Object>(contentEditor, false);
		tree.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, true, 2, 1));
		tree.setCollection(Project.current.status.getTree());
		addControl(tree, "id");
		
		new LLabel(contentEditor, Vocab.instance.LEVEL);
		LSpinner spnLevel = new LSpinner(contentEditor);
		addControl(spnLevel, "level");

		pack();
	}
	
}
