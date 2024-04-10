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

public class JobStatusDialog extends ObjectEditorDialog<Job.Status> {

	public JobStatusDialog(LWindow parent) {
		super(parent, Vocab.instance.STATUSSHELL);
		setMinimumSize(300, 100);
	}
	
	@Override
	protected void createContent(int style) {
		super.createContent(style);
		contentEditor.setGridLayout(2);

		LNodeSelector<Object> tree = new LNodeSelector<Object>(contentEditor, false);
		tree.getCellData().setExpand(true, true);
		tree.getCellData().setSpread(2, 1);
		tree.setCollection(Project.current.status.getTree());
		addControl(tree, "id");
		
		new LLabel(contentEditor, Vocab.instance.MINLEVEL, Tooltip.instance.STATUSLEVEL);
		LSpinner spnLevel = new LSpinner(contentEditor);
		addControl(spnLevel, "level");

		pack();
	}
	
}
