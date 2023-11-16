package gui.shell.database;

import gui.Vocab;
import gui.shell.ObjectShell;

import org.eclipse.swt.widgets.Shell;

import data.Job;
import lwt.widget.LLabel;
import lwt.widget.LNodeSelector;
import lwt.widget.LSpinner;
import project.Project;

import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.layout.GridData;

public class JobStatusShell extends ObjectShell<Job.Status> {

	public JobStatusShell(Shell parent) {
		super(parent);
		setMinimumSize(300, 100);
		contentEditor.setLayout(new GridLayout(2, false));

		LNodeSelector<Object> tree = new LNodeSelector<Object>(contentEditor, 0);
		tree.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, true, 2, 1));
		tree.setCollection(Project.current.status.getTree());
		addControl(tree, "id");
		
		new LLabel(contentEditor, Vocab.instance.LEVEL);
		
		LSpinner spnLevel = new LSpinner(contentEditor);
		spnLevel.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1));
		addControl(spnLevel, "level");

		pack();
	}
	
}
