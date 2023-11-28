package gui.shell.database;

import gui.Vocab;
import gui.shell.ObjectShell;

import data.Job;
import lwt.dialog.LShell;
import lwt.widget.LLabel;
import lwt.widget.LNodeSelector;
import lwt.widget.LSpinner;
import project.Project;

public class JobStatusShell extends ObjectShell<Job.Status> {

	public JobStatusShell(LShell parent) {
		super(parent);
		setMinimumSize(300, 100);
		contentEditor.setGridLayout(2, false);

		LNodeSelector<Object> tree = new LNodeSelector<Object>(contentEditor, false);
		tree.setExpand(true, true);
		tree.setSpread(2, 1);
		tree.setCollection(Project.current.status.getTree());
		addControl(tree, "id");
		
		new LLabel(contentEditor, Vocab.instance.LEVEL);
		LSpinner spnLevel = new LSpinner(contentEditor);
		addControl(spnLevel, "level");

		pack();
	}
	
}
