package gui.views.system;

import data.config.Config;
import gui.Tooltip;
import gui.Vocab;
import gui.widgets.PositionButton;
import gui.widgets.ScriptButton;
import lui.container.LContainer;
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
		new LLabel(this, Vocab.instance.DIAGTHRESHOLD, Tooltip.instance.DIAGTHRESHOLD);

		LSpinner spnDiagThreshold = new LSpinner(this);
		spnDiagThreshold.getCellData().setExpand(true, false);
		addControl(spnDiagThreshold, "diagThreshold");

		new LLabel(this, "%");

		new LLabel(this, Vocab.instance.STARTPOS, Tooltip.instance.STARTPOS);
		LText txtPos = new LText(this, true);
		txtPos.getCellData().setExpand(true, false);
		PositionButton btnStartPos = new PositionButton(this, -1);
		btnStartPos.setTextWidget(txtPos);
		addControl(btnStartPos, "startPos");

		new LLabel(this, Vocab.instance.LOADSCRIPT, Tooltip.instance.LOADSCRIPT);
		LText txtScript = new LText(this, true);
		txtScript.getCellData().setExpand(true, false);
		ScriptButton btnScript = new ScriptButton(this, false, true, false);
		btnScript.setPathWidget(txtScript);
		addControl(btnScript, "loadScript");

		new LLabel(this, 3, 1).getCellData().setExpand(false, true);
    }

    @Override
    public Type getType() {
        return Config.Player.class;
    }
}
