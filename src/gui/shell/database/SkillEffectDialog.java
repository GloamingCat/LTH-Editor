package gui.shell.database;

import gui.Tooltip;
import gui.Vocab;
import gui.widgets.CheckBoxPanel;
import lui.container.LPanel;
import lui.gson.GObjectDialog;

import data.Skill.Effect;
import lui.container.LFrame;
import lui.dialog.LWindow;
import lui.widget.LCheckBox;
import lui.widget.LLabel;
import lui.widget.LNodeSelector;
import lui.widget.LText;
import project.Project;

public class SkillEffectDialog extends GObjectDialog<Effect> {

	public SkillEffectDialog(LWindow parent) {
		super(parent, 400, 400, Vocab.instance.EFFECTSHELL);
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

		LPanel check = new CheckBoxPanel(contentEditor);
		check.getCellData().setSpread(2, 1);

		LCheckBox btnHeal = new LCheckBox(check);
		btnHeal.setText(Vocab.instance.HEAL);
		btnHeal.setHoverText(Tooltip.instance.HEAL);
		addControl(btnHeal, "heal");
		
		LCheckBox btnAbsorb = new LCheckBox(check);
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
