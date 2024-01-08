package gui.shell.database;

import gui.Tooltip;
import gui.Vocab;
import gui.shell.ObjectShell;

import data.Job;
import lwt.dialog.LShell;
import lwt.widget.LLabel;
import lwt.widget.LNodeSelector;
import lwt.widget.LSpinner;
import project.Project;

public class JobSkillShell extends ObjectShell<Job.Skill> {

	public JobSkillShell(LShell parent) {
		super(parent, Vocab.instance.SKILLSHELL);
		setMinimumSize(300, 100);
	}
	
	@Override
	protected void createContent(int style) {
		super.createContent(style);
		contentEditor.setGridLayout(2);

		LNodeSelector<Object> tree = new LNodeSelector<Object>(contentEditor, false);
		tree.setExpand(true, true);
		tree.setSpread(2, 1);
		tree.setCollection(Project.current.skills.getTree());
		addControl(tree, "id");
		
		new LLabel(contentEditor, Vocab.instance.MINLEVEL, Tooltip.instance.SKILLLEVEL);
		LSpinner spnLevel = new LSpinner(contentEditor);
		addControl(spnLevel, "level");

		pack();
	}
	
}
