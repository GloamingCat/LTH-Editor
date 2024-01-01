package gui.shell.database;

import gui.Tooltip;
import gui.Vocab;
import gui.shell.ObjectShell;
import gui.views.database.subcontent.TagList;
import lwt.container.LFrame;
import lwt.container.LPanel;
import lwt.container.LSashPanel;
import lwt.dialog.LShell;
import lwt.widget.LFileSelector;
import lwt.widget.LLabel;
import lwt.widget.LText;

import data.subcontent.Rule;
import project.Project;

public class RuleShell extends ObjectShell<Rule> {
	
	private LFileSelector selFile;
	
	public RuleShell(LShell parent) {
		super(parent);
		contentEditor.setFillLayout(true);
		LSashPanel form = new LSashPanel(contentEditor, true);
		selFile = new LFileSelector(form, false);
		selFile.addFileRestriction( (f) -> { return f.getName().endsWith(".lua"); } );
		selFile.setFolder(Project.current.rulePath());
		
		LPanel composite = new LPanel(form, 2, false);
		
		LFrame grpParameters = new LFrame(composite, Vocab.instance.PARAM, true, true);
		grpParameters.setHoverText(Tooltip.instance.PARAM);
		grpParameters.setExpand(true, true);
		grpParameters.setSpread(2, 1);
		TagList lstParam = new TagList(grpParameters);
		addChild(lstParam, "tags");
		
		new LLabel(composite, Vocab.instance.CONDITION, Tooltip.instance.CONDITION);
		
		LText txtCondition = new LText(composite);
		addControl(txtCondition, "condition");
		
		form.setWeights(new int[] {1, 1});
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
