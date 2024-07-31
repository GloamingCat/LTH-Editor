package gui.views.system;

import data.config.Config;
import gui.Tooltip;
import gui.Vocab;
import gui.widgets.PositionButton;
import gui.widgets.ScriptButton;
import lui.container.LContainer;
import lui.container.LFrame;
import lui.container.LPanel;
import lui.gson.GDefaultObjectEditor;
import lui.widget.LLabel;
import lui.widget.LSpinner;
import lui.widget.LText;

import java.lang.reflect.Type;

public class PlayerEditor extends GDefaultObjectEditor<Config.Player> {
    public PlayerEditor(LContainer parent, boolean doubleBuffered) {
        super(parent, doubleBuffered);
    }

    @Override
    protected void createContent(int style) {
		setGridLayout(3);

		new LLabel(this, Vocab.instance.STARTPOS, Tooltip.instance.STARTPOS);
		LPanel pos = new LPanel(this);
		pos.setGridLayout(2);
		pos.getCellData().setExpand(true, false);
		pos.getCellData().setSpread(2, 1);
		LText txtPos = new LText(pos, true);
		txtPos.getCellData().setExpand(true, false);
		PositionButton btnStartPos = new PositionButton(pos, -1);
		btnStartPos.setTextWidget(txtPos);
		addControl(btnStartPos, "startPos");

		new LLabel(this, Vocab.instance.LOADSCRIPT, Tooltip.instance.LOADSCRIPT);
		LPanel script = new LPanel(this);
		script.setGridLayout(2);
		script.getCellData().setExpand(true, false);
		script.getCellData().setSpread(2, 1);
		LText txtScript = new LText(script, true);
		txtScript.getCellData().setExpand(true, false);
		ScriptButton btnScript = new ScriptButton(script, false, true, false);
		btnScript.setPathWidget(txtScript);
		addControl(btnScript, "loadScript");

		new LLabel(this, Vocab.instance.WALKSPEED, Tooltip.instance.WALKSPEED);
		LSpinner spnWalkSpeed = new LSpinner(this);
		spnWalkSpeed.getCellData().setExpand(true, false);
		spnWalkSpeed.getCellData().setSpread(2, 1);
		addControl(spnWalkSpeed, "walkSpeed");

		new LLabel(this, Vocab.instance.DASHSPEED, Tooltip.instance.DASHSPEED);
		LSpinner spnDashSpeed = new LSpinner(this);
		spnDashSpeed.getCellData().setExpand(true, false);
		addControl(spnDashSpeed, "dashSpeed");
		new LLabel(this, "%");

		new LLabel(this, Vocab.instance.GRAVITY, Tooltip.instance.GRAVITY);
		LSpinner spnGravity = new LSpinner(this);
		spnGravity.getCellData().setExpand(true, false);
		spnGravity.getCellData().setSpread(2, 1);
		addControl(spnGravity, "gravity");

		new LLabel(this, Vocab.instance.DIAGTHRESHOLD, Tooltip.instance.DIAGTHRESHOLD);
		LSpinner spnDiagThreshold = new LSpinner(this);
		spnDiagThreshold.getCellData().setExpand(true, false);
		addControl(spnDiagThreshold, "diagThreshold");
		new LLabel(this, "%");

		new LLabel(this, Vocab.instance.WALKDELAY, Tooltip.instance.WALKDELAY);
		LSpinner spnWalkDelay = new LSpinner(this);
		spnWalkDelay.getCellData().setExpand(true, false);
		spnWalkDelay.getCellData().setSpread(2, 1);
		addControl(spnWalkDelay, "walkDelay");

		new LLabel(this, Vocab.instance.PATHLIMIT, Tooltip.instance.PATHLIMIT);
		LSpinner spnPathLen = new LSpinner(this);
		spnPathLen.getCellData().setExpand(true, false);
		spnPathLen.getCellData().setSpread(2, 1);
		addControl(spnPathLen, "pathLength");

		LFrame grpSound = new LFrame(this, Vocab.instance.STEPSOUND, Tooltip.instance.STEPSOUND);
		grpSound.getCellData().setSpread(3, 1);
		grpSound.getCellData().setExpand(true, false);
		grpSound.setGridLayout(6);

		new LLabel(grpSound, Vocab.instance.STEPFREQ, Tooltip.instance.STEPFREQ);
		LSpinner spnStepFreq = new LSpinner(grpSound);
		spnStepFreq.getCellData().setExpand(true, false);
		spnStepFreq.getCellData().setSpread(2, 1);
		addControl(spnStepFreq, "stepFreq");

		new LLabel(grpSound, Vocab.instance.VARFREQ, Tooltip.instance.STEPVARFREQ);
		LSpinner spnStepVarFreq = new LSpinner(grpSound);
		spnStepVarFreq.getCellData().setExpand(true, false);
		addControl(spnStepVarFreq, "stepVarFreq");
		new LLabel(grpSound, "%");

		new LLabel(grpSound, Vocab.instance.VARPITCH, Tooltip.instance.STEPVARPITCH);
		LSpinner spnStepVarPitch = new LSpinner(grpSound);
		spnStepVarPitch.getCellData().setExpand(true, false);
		addControl(spnStepVarPitch, "stepVarPitch");
		new LLabel(grpSound, "%");

		new LLabel(grpSound, Vocab.instance.VARVOLUME, Tooltip.instance.STEPVARVOLUME);
		LSpinner spnStepVarVolume = new LSpinner(grpSound);
		spnStepVarVolume.getCellData().setExpand(true, false);
		addControl(spnStepVarVolume, "stepVarVolume");
		new LLabel(grpSound, "%");

		new LLabel(this, 3, 1).getCellData().setExpand(false, true);
    }

    @Override
    public Type getType() {
        return Config.Player.class;
    }
}
