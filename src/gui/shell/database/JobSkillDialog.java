package gui.shell.database;

import gui.Tooltip;
import gui.Vocab;
import gui.shell.ObjectEditorDialog;

import data.Job;
import lui.dialog.LWindow;
import lui.widget.LLabel;
import lui.widget.LNodeSelector;
import lui.widget.LSpinner;
import project.Project;

public class JobSkillDialog extends ObjectEditorDialog<Job.Skill> {

	public JobSkillDialog(LWindow parent) {
		super(parent, Vocab.instance.SKILLSHELL);
		setRequiredSize(300, 200);
		setSize(300, 200);
	}
	
	@Override
	protected void createContent(int style) {
		super.createContent(style);
		contentEditor.setGridLayout(2);

		LNodeSelector<Object> tree = new LNodeSelector<Object>(contentEditor, false);
		tree.getCellData().setExpand(true, true);
		tree.getCellData().setSpread(2, 1);
		tree.setCollection(Project.current.skills.getTree());
		addControl(tree, "id");
		
		new LLabel(contentEditor, Vocab.instance.MINLEVEL, Tooltip.instance.SKILLLEVEL);
		LSpinner spnLevel = new LSpinner(contentEditor);
		spnLevel.getCellData().setExpand(true, false);
		spnLevel.setMinimum(0);
		spnLevel.setMaximum(9999);
		addControl(spnLevel, "level");
	}
	
}
