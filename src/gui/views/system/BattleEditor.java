package gui.views.system;

import data.config.Config;
import gui.Tooltip;
import gui.Vocab;
import gui.widgets.CheckBoxPanel;
import lui.container.LContainer;
import lui.container.LPanel;
import lui.gson.GDefaultObjectEditor;
import lui.widget.LCheckBox;
import lui.widget.LLabel;
import lui.widget.LSpinner;
import lui.widget.LText;

import java.lang.reflect.Type;

public class BattleEditor extends GDefaultObjectEditor<Config.Battle> {
    public BattleEditor(LContainer parent, boolean doubleBuffered) {
        super(parent, doubleBuffered);
    }

    @Override
    protected void createContent(int style) {
        setGridLayout(3);

		new LLabel(this, Vocab.instance.FINALLEVEL, Tooltip.instance.FINALLEVEL);

		LSpinner spnMaxLevel = new LSpinner(this);
		spnMaxLevel.getCellData().setExpand(true, false);
		spnMaxLevel.getCellData().setSpread(2, 1);
		addControl(spnMaxLevel, "maxLevel");

		new LLabel(this, Vocab.instance.ATTHP, Tooltip.instance.ATTHP);

		LText txtAttHP = new LText(this);
		txtAttHP.getCellData().setExpand(true, false);
		txtAttHP.getCellData().setSpread(2, 1);
		addControl(txtAttHP, "attHP");

		new LLabel(this, Vocab.instance.ATTSP, Tooltip.instance.ATTSP);

		LText txtAttSP = new LText(this);
		txtAttSP.getCellData().setExpand(true, false);
		txtAttSP.getCellData().setSpread(2, 1);
		addControl(txtAttSP, "attSP");

		new LLabel(this, Vocab.instance.ATTSTEP, Tooltip.instance.ATTSTEP);

		LText txtAttStep = new LText(this);
		txtAttStep.getCellData().setExpand(true, false);
		txtAttStep.getCellData().setSpread(2, 1);
		addControl(txtAttStep, "attStep");

		new LLabel(this, Vocab.instance.ATTJUMP, Tooltip.instance.ATTJUMP);

		LText txtAttJump = new LText(this);
		txtAttJump.getCellData().setExpand(true, false);
		txtAttJump.getCellData().setSpread(2, 1);
		addControl(txtAttJump, "attJump");

		new LLabel(this, Vocab.instance.CHARSPEED, Tooltip.instance.CHARSPEED);

		LSpinner spnCharSpeed = new LSpinner(this);
		spnCharSpeed.getCellData().setExpand(true, false);
		addControl(spnCharSpeed, "charSpeed");

		new LLabel(this, "%");

		LPanel checkBattle = new CheckBoxPanel(this);
		checkBattle.getCellData().setSpread(2, 1);

		LCheckBox btnRevive = new LCheckBox(checkBattle);
		btnRevive.setText(Vocab.instance.BATTLEENDREVIVE);
		addControl(btnRevive, "battleEndRevive");

		LCheckBox btnKeepParties = new LCheckBox(checkBattle);
		btnKeepParties.setText(Vocab.instance.KEEPPARTIES);
		addControl(btnKeepParties, "keepParties");

		new LLabel(this, 3, 1).getCellData().setExpand(false, true);

    }

    @Override
    public Type getType() {
        return Config.Battle.class;
    }
}
