package gui.shell.database;

import gui.Tooltip;
import gui.Vocab;
import gui.shell.ObjectShell;
import gui.views.database.subcontent.TagList;
import lui.container.LFrame;
import lui.container.LPanel;
import lui.container.LFlexPanel;
import lui.dialog.LWindow;
import lui.widget.LFileSelector;
import lui.widget.LLabel;
import lui.widget.LText;

import data.subcontent.Rule;
import project.Project;

public class RuleShell extends ObjectShell<Rule> {
	
	private LFileSelector selFile;
	
	public RuleShell(LWindow parent) {
		super(parent, Vocab.instance.RULESHELL);
	}
	
	@Override
	protected void createContent(int style) {
		super.createContent(style);
		contentEditor.setFillLayout(true);
		LFlexPanel form = new LFlexPanel(contentEditor, true);
		selFile = new LFileSelector(form, false);
		selFile.addFileRestriction( (f) -> { return f.getName().endsWith(".lua"); } );
		selFile.setFolder(Project.current.rulePath());
		
		LPanel composite = new LPanel(form);
		composite.setGridLayout(2);
		LFrame frame = new LFrame(composite, (String) Vocab.instance.PARAM);
		frame.setFillLayout(true);
		
		LFrame grpParameters = frame;
		grpParameters.setHoverText(Tooltip.instance.PARAM);
		grpParameters.getCellData().setExpand(true, true);
		grpParameters.getCellData().setSpread(2, 1);
		TagList lstParam = new TagList(grpParameters);
		addChild(lstParam, "tags");
		
		new LLabel(composite, Vocab.instance.CONDITION, Tooltip.instance.CONDITION);
		
		LText txtCondition = new LText(composite);
		addControl(txtCondition, "condition");
		
		form.setWeights(1, 1);
	}
	
	public void open(Rule initial) {
		super.open(initial);
		selFile.setSelectedFile(initial.name);
	}
	
	@Override
	protected Rule createResult(Rule initial) {
		Rule script = (Rule) contentEditor.getObject();
		script.name = selFile.getSelectedFile();
		if (script.name == null)
			script.name = "";
		return super.createResult(initial);
	}
}
