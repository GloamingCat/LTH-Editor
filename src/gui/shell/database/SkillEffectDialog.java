package gui.shell.database;

import gui.Tooltip;
import gui.Vocab;
import gui.shell.ObjectEditorDialog;

import data.Skill.Effect;
import lui.container.LFrame;
import lui.dialog.LWindow;
import lui.widget.LCheckBox;
import lui.widget.LLabel;
import lui.widget.LNodeSelector;
import lui.widget.LText;
import project.Project;

public class SkillEffectDialog extends ObjectEditorDialog<Effect> {

	public SkillEffectDialog(LWindow parent) {
		super(parent, Vocab.instance.EFFECTSHELL);
		setSize(400, 400);
		setRequiredSize(400, 400);
	}
	
	@Override
	protected void createContent(int style) {
		super.createContent(style);
		contentEditor.setGridLayout(2);
		
		new LLabel(contentEditor, Vocab.instance.KEY, Tooltip.instance.KEY);
		LText txtKey = new LText(contentEditor);
		txtKey.getCellData().setExpand(true, false);
		addControl(txtKey, "key");

		new LLabel(contentEditor, Vocab.instance.BASICRESULT, Tooltip.instance.BASICRESULT);
		LText txtBasicResult = new LText(contentEditor);
		txtBasicResult.getCellData().setExpand(true, false);
		addControl(txtBasicResult, "basicResult");

		new LLabel(contentEditor, Vocab.instance.SUCCESSRATE, Tooltip.instance.SUCCESSRATE);
		LText txtSuccessRate = new LText(contentEditor);
		txtSuccessRate.getCellData().setExpand(true, false);
		addControl(txtSuccessRate, "successRate");
		
		LCheckBox btnHeal = new LCheckBox(contentEditor);
		btnHeal.setText(Vocab.instance.HEAL);
		btnHeal.setHoverText(Tooltip.instance.HEAL);
		addControl(btnHeal, "heal");
		
		LCheckBox btnAbsorb = new LCheckBox(contentEditor);
		btnAbsorb.setText(Vocab.instance.ABSORB);
		btnAbsorb.setHoverText(Tooltip.instance.ABSORB);
		addControl(btnAbsorb, "absorb");

		LFrame grpStatus = new LFrame(contentEditor, Vocab.instance.STATUS);
		grpStatus.setFillLayout(true);
		grpStatus.setHoverText(Tooltip.instance.EFFECTSTATUS);
		grpStatus.getCellData().setExpand(true, true);
		grpStatus.getCellData().setSpread(2, 1);
		LNodeSelector<Object> tree = new LNodeSelector<Object>(grpStatus, true);
		tree.setCollection(Project.current.status.getTree());
		addControl(tree, "statusID");
	}
	
}
