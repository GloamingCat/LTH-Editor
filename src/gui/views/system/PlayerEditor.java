package gui.views.system;

import data.config.Config;
import gui.Tooltip;
import gui.Vocab;
import gui.widgets.PositionButton;
import gui.widgets.ScriptButton;
import lui.base.LPrefs;
import lui.container.LContainer;
import lui.container.LFrame;
import lui.container.LPanel;
import lui.gson.GDefaultObjectEditor;
import lui.widget.LLabel;
import lui.widget.LSeparator;
import lui.widget.LSpinner;
import lui.widget.LText;

import javax.swing.*;
import javax.swing.border.LineBorder;
import java.awt.*;
import java.lang.reflect.Type;

public class PlayerEditor extends GDefaultObjectEditor<Config.Player> {
    public PlayerEditor(LContainer parent, boolean doubleBuffered) {
        super(parent, doubleBuffered);
    }

    @Override
    protected void createContent(int style) {
		setGridLayout(6);

		new LLabel(this, Vocab.instance.STARTPOS, Tooltip.instance.STARTPOS)
				.getCellData().setRequiredSize(LPrefs.LABELWIDTH, LPrefs.WIDGETHEIGHT);
		LPanel pos = new LPanel(this);
		pos.setGridLayout(2);
		pos.getCellData().setExpand(true, false);
		pos.getCellData().setSpread(5, 1);
		LText txtPos = new LText(pos, true);
		txtPos.getCellData().setExpand(true, false);
		PositionButton btnStartPos = new PositionButton(pos, -1);
		btnStartPos.setTextWidget(txtPos);
		addControl(btnStartPos, "startPos");

		new LLabel(this, Vocab.instance.LOADSCRIPT, Tooltip.instance.LOADSCRIPT);
		LPanel script = new LPanel(this);
		script.setGridLayout(2);
		script.getCellData().setExpand(true, false);
		script.getCellData().setSpread(5, 1);
		LText txtScript = new LText(script, true);
		txtScript.getCellData().setExpand(true, false);
		ScriptButton btnScript = new ScriptButton(script, false, true, false);
		btnScript.setPathWidget(txtScript);
		addControl(btnScript, "loadScript");

		new LLabel(this, Vocab.instance.WALKSPEED, Tooltip.instance.WALKSPEED);
		LSpinner spnWalkSpeed = new LSpinner(this);
		spnWalkSpeed.getCellData().setExpand(true, false);
		addControl(spnWalkSpeed, "walkSpeed");
		new LLabel(this, 1, 1);

		new LLabel(this, Vocab.instance.DASHSPEED, Tooltip.instance.DASHSPEED)
				.getCellData().setRequiredSize(LPrefs.LABELWIDTH, LPrefs.WIDGETHEIGHT);
		LSpinner spnDashSpeed = new LSpinner(this);
		spnDashSpeed.getCellData().setExpand(true, false);
		addControl(spnDashSpeed, "dashSpeed");
		new LLabel(this, "%");

		new LLabel(this, Vocab.instance.WALKDELAY, Tooltip.instance.WALKDELAY);
		LSpinner spnWalkDelay = new LSpinner(this);
		spnWalkDelay.getCellData().setExpand(true, false);
		addControl(spnWalkDelay, "walkDelay");
		new LLabel(this, 1, 1);

		new LLabel(this, Vocab.instance.DIAGTHRESHOLD, Tooltip.instance.DIAGTHRESHOLD);
		LSpinner spnDiagThreshold = new LSpinner(this);
		spnDiagThreshold.getCellData().setExpand(true, false);
		addControl(spnDiagThreshold, "diagThreshold");
		new LLabel(this, "%");

		new LLabel(this, Vocab.instance.GRAVITY, Tooltip.instance.GRAVITY);
		LSpinner spnGravity = new LSpinner(this);
		spnGravity.getCellData().setExpand(true, false);
		addControl(spnGravity, "gravity");
		new LLabel(this, 1, 1);

		new LLabel(this, Vocab.instance.PATHLIMIT, Tooltip.instance.PATHLIMIT);
		LSpinner spnPathLen = new LSpinner(this);
		spnPathLen.getCellData().setExpand(true, false);
		addControl(spnPathLen, "pathLength");
		new LLabel(this, 1, 1);

		LSeparator s = new LSeparator(this, Vocab.instance.STEPSOUND);
		s.getCellData().setExpand(true, false);
		s.getCellData().setSpread(6, 1);

		new LLabel(this, Vocab.instance.STEPFREQ, Tooltip.instance.STEPFREQ);
		LSpinner spnStepFreq = new LSpinner(this);
		spnStepFreq.getCellData().setExpand(true, false);
		addControl(spnStepFreq, "stepFreq");
		new LLabel(this, 1, 1);

		new LLabel(this, Vocab.instance.VARFREQ, Tooltip.instance.STEPVARFREQ);
		LSpinner spnStepVarFreq = new LSpinner(this);
		spnStepVarFreq.getCellData().setExpand(true, false);
		addControl(spnStepVarFreq, "stepVarFreq");
		new LLabel(this, "%");

		new LLabel(this, Vocab.instance.VARPITCH, Tooltip.instance.STEPVARPITCH);
		LSpinner spnStepVarPitch = new LSpinner(this);
		spnStepVarPitch.getCellData().setExpand(true, false);
		addControl(spnStepVarPitch, "stepVarPitch");
		new LLabel(this, "%");

		new LLabel(this, Vocab.instance.VARVOLUME, Tooltip.instance.STEPVARVOLUME);
		LSpinner spnStepVarVolume = new LSpinner(this);
		spnStepVarVolume.getCellData().setExpand(true, false);
		addControl(spnStepVarVolume, "stepVarVolume");
		new LLabel(this, "%");

		new LLabel(this, 5, 1).getCellData().setExpand(false, true);
    }

    @Override
    public Type getType() {
        return Config.Player.class;
    }
}
