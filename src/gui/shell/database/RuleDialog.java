package gui.shell.database;

import gui.Tooltip;
import gui.Vocab;
import lui.gson.GObjectDialog;
import gui.views.database.subcontent.TagList;
import lui.container.LFrame;
import lui.container.LFlexPanel;
import lui.dialog.LWindow;
import lui.widget.LFileSelector;
import lui.widget.LLabel;
import lui.widget.LText;

import data.subcontent.Rule;
import project.Project;

public class RuleDialog extends GObjectDialog<Rule> {
	
	private LFileSelector selFile;
	
	public RuleDialog(LWindow parent) {
		super(parent, 600, 350, Vocab.instance.RULESHELL);
	}
	
	@Override
	protected void createContent(int style) {
		super.createContent(style);
		contentEditor.setFillLayout(true);

		LFlexPanel form = new LFlexPanel(contentEditor, true);
		selFile = new LFileSelector(form, false);
		selFile.addFileRestriction(f -> f.getName().endsWith(".lua"));
		selFile.setFolder(Project.current.rulePath());
		
		LFrame opts = new LFrame(form, Vocab.instance.OPTIONS);
		opts.setGridLayout(2);

		new LLabel(opts, Vocab.instance.CONDITION, Tooltip.instance.CONDITION);
		LText txtCondition = new LText(opts);
		txtCondition.getCellData().setExpand(true, false);
		addControl(txtCondition, "condition");

		new LLabel(opts, Vocab.instance.PARAM, Tooltip.instance.PARAM);
		TagList lstParam = new TagList(opts);
		lstParam.getCellData().setExpand(true, true);
		addChild(lstParam, "tags");

		form.setWeights(1, 1);
	}
	
	public void open(Rule initial) {
		super.open(initial);
		selFile.setSelectedFile(initial.name);
	}
	
	@Override
	protected Rule createResult(Rule initial) {
		Rule script = contentEditor.getObject();
		script.name = selFile.getSelectedFile();
		if (script.name == null)
			script.name = "";
		return super.createResult(initial);
	}
}
