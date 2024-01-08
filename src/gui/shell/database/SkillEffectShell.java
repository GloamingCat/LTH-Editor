package gui.shell.database;

import gui.Tooltip;
import gui.Vocab;
import gui.shell.ObjectShell;

import data.Skill.Effect;
import lwt.container.LFrame;
import lwt.dialog.LShell;
import lwt.widget.LCheckBox;
import lwt.widget.LLabel;
import lwt.widget.LNodeSelector;
import lwt.widget.LText;
import project.Project;

public class SkillEffectShell extends ObjectShell<Effect> {

	public SkillEffectShell(LShell parent) {
		super(parent, Vocab.instance.EFFECTSHELL);
		setMinimumSize(300, 100);
	}
	
	@Override
	protected void createContent(int style) {
		super.createContent(style);
		contentEditor.setGridLayout(2);
		
		new LLabel(contentEditor, Vocab.instance.KEY, Tooltip.instance.KEY);
		
		LText txtKey = new LText(contentEditor);
		addControl(txtKey, "key");
		
		new LLabel(contentEditor, Vocab.instance.BASICRESULT, Tooltip.instance.BASICRESULT);
		
		LText txtBasicResult = new LText(contentEditor);
		addControl(txtBasicResult, "basicResult");
		
		new LLabel(contentEditor, Vocab.instance.SUCCESSRATE, Tooltip.instance.SUCCESSRATE);

		LText txtSuccessRate = new LText(contentEditor);
		addControl(txtSuccessRate, "successRate");
		
		LCheckBox btnHeal = new LCheckBox(contentEditor);
		btnHeal.setText(Vocab.instance.HEAL);
		btnHeal.setHoverText(Tooltip.instance.HEAL);
		addControl(btnHeal, "heal");
		
		LCheckBox btnAbsorb = new LCheckBox(contentEditor);
		btnAbsorb.setText(Vocab.instance.ABSORB);
		btnAbsorb.setHoverText(Tooltip.instance.ABSORB);
		addControl(btnAbsorb, "absorb");
		LFrame frame = new LFrame(contentEditor, (String) Vocab.instance.STATUS);
		frame.setFillLayout(true);
		
		LFrame grpStatus = frame;
		grpStatus.setHoverText(Tooltip.instance.EFFECTSTATUS);
		grpStatus.setExpand(true, true);
		grpStatus.setSpread(2, 1);
		LNodeSelector<Object> tree = new LNodeSelector<Object>(grpStatus, true);
		tree.setCollection(Project.current.status.getTree());
		addControl(tree, "statusID");
		
		pack();
	}
	
}
