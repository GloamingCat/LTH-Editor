package gui.shell.database;

import gui.Tooltip;
import gui.Vocab;
import lui.gson.GObjectDialog;

import data.Job;
import lui.dialog.LWindow;
import lui.widget.LLabel;
import lui.widget.LNodeSelector;
import lui.widget.LSpinner;
import project.Project;

public class JobStatusDialog extends GObjectDialog<Job.Status> {

	public JobStatusDialog(LWindow parent) {
		super(parent, 300, 200, Vocab.instance.STATUSSHELL);
	}
	
	@Override
	protected void createContent(int style) {
		super.createContent(style);
		contentEditor.setGridLayout(2);

		LNodeSelector<Object> tree = new LNodeSelector<Object>(contentEditor, 0);
		tree.getCellData().setExpand(true, true);
		tree.getCellData().setSpread(2, 1);
		tree.setCollection(Project.current.status.getTree());
		addControl(tree, "id");
		
		new LLabel(contentEditor, Vocab.instance.MINLEVEL, Tooltip.instance.STATUSLEVEL);
		LSpinner spnLevel = new LSpinner(contentEditor);
		spnLevel.getCellData().setExpand(true, false);
		addControl(spnLevel, "level");
	}
	
}
