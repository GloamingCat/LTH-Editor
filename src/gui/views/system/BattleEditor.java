package gui.views.system;

import data.config.Config;
import gui.Tooltip;
import gui.Vocab;
import gui.widgets.CheckBoxPanel;
import lui.base.LPrefs;
import lui.container.LContainer;
import lui.container.LPanel;
import lui.gson.GDefaultObjectEditor;
import lui.widget.*;

import java.lang.reflect.Type;

public class BattleEditor extends GDefaultObjectEditor<Config.Battle> {
    public BattleEditor(LContainer parent, boolean doubleBuffered) {
        super(parent, doubleBuffered);
    }

    @Override
    protected void createContent(int style) {
        setGridLayout(5);

		new LLabel(this, Vocab.instance.FINALLEVEL, Tooltip.instance.FINALLEVEL)
				.getCellData().setRequiredSize(LPrefs.LABELWIDTH, LPrefs.WIDGETHEIGHT);
		LSpinner spnMaxLevel = new LSpinner(this);
		spnMaxLevel.getCellData().setExpand(true, false);
		addControl(spnMaxLevel, "maxLevel");
		new LLabel(this, 1, 1);

		new LLabel(this, Vocab.instance.EXPINCREASE, Tooltip.instance.EXPINCREASE)
				.getCellData().setRequiredSize(LPrefs.LABELWIDTH, LPrefs.WIDGETHEIGHT);
		LSpinner spnExpIncrease = new LSpinner(this);
		spnExpIncrease.getCellData().setExpand(true, false);
		addControl(spnExpIncrease, "expIncrease");

		new LLabel(this, Vocab.instance.CHARSPEED, Tooltip.instance.CHARSPEED);
		LSpinner spnCharSpeed = new LSpinner(this);
		spnCharSpeed.getCellData().setExpand(true, false);
		addControl(spnCharSpeed, "charSpeed");
		new LLabel(this, "%");
		new LLabel(this, 2, 1).getCellData();

		LSeparator grpAtt = new LSeparator(this, Vocab.instance.ATTRIBUTES, Tooltip.instance.ATTCONFIG);
		grpAtt.getCellData().setSpread(5, 1);
		grpAtt.getCellData().setExpand(true, false);

		new LLabel(this, Vocab.instance.ATTHP, Tooltip.instance.ATTHP);
		LText txtAttHP = new LText(this);
		txtAttHP.getCellData().setExpand(true, false);
		addControl(txtAttHP, "attHP");
		new LLabel(this, 1, 1);

		new LLabel(this, Vocab.instance.ATTSP, Tooltip.instance.ATTSP);
		LText txtAttSP = new LText(this);
		txtAttSP.getCellData().setExpand(true, false);
		addControl(txtAttSP, "attSP");

		new LLabel(this, Vocab.instance.ATTSTEP, Tooltip.instance.ATTSTEP);
		LText txtAttStep = new LText(this);
		txtAttStep.getCellData().setExpand(true, false);
		addControl(txtAttStep, "attStep");
		new LLabel(this, 1, 1);

		new LLabel(this, Vocab.instance.ATTJUMP, Tooltip.instance.ATTJUMP);
		LText txtAttJump = new LText(this);
		txtAttJump.getCellData().setExpand(true, false);
		addControl(txtAttJump, "attJump");

		new LSeparator(this, true).getCellData().setSpread(5, 1);

		LPanel checkBattle = new CheckBoxPanel(this);
		checkBattle.getCellData().setSpread(5, 1);

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
