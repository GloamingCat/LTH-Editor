package gui.views.database.subcontent;

import java.lang.reflect.Type;

import data.Skill.AnimInfo;
import gui.Tooltip;
import gui.Vocab;
import gui.widgets.CheckBoxPanel;
import gui.widgets.ImageButton;
import lui.base.LPrefs;
import lui.container.LContainer;
import lui.container.LPanel;
import lui.container.LViewFolder;
import lui.gson.GDefaultObjectEditor;
import lui.widget.LCheckBox;
import lui.widget.LLabel;
import lui.widget.LSpinner;
import lui.widget.LText;

public class AnimInfoEditor extends GDefaultObjectEditor<AnimInfo> {
	
	public AnimInfoEditor(LContainer parent) {
		super(parent, false);
	}

	protected void createContent(int style) {
		setFillLayout(true);
		
		LViewFolder tab = new LViewFolder(this, false);

		// Battle

		LPanel battle = new LPanel(tab);
		battle.setGridLayout(3);
		battle.setMargins(LPrefs.FRAMEMARGIN, LPrefs.FRAMEMARGIN);
		tab.addTab(Vocab.instance.BATTLE, battle);
		
		LLabel lblLoad = new LLabel(battle, Vocab.instance.LOAD, Tooltip.instance.LOAD);
		lblLoad.getCellData().setRequiredSize(LPrefs.LABELWIDTH, 0);
		LText txtLoad = new LText(battle, true);
		txtLoad.getCellData().setExpand(true, false);
		ImageButton btnLoad = new ImageButton(battle, true);
		btnLoad.setNameWidget(txtLoad);
		btnLoad.addMenu(lblLoad);
		btnLoad.addMenu(txtLoad);
		addControl(btnLoad, "loadID");
		
		LLabel lblCast = new LLabel(battle, Vocab.instance.CAST, Tooltip.instance.CAST);
		LText txtCast = new LText(battle, true);
		txtCast.getCellData().setExpand(true, false);
		ImageButton btnCast = new ImageButton(battle, true);
		btnCast.setNameWidget(txtCast);
		btnCast.addMenu(lblCast);
		btnCast.addMenu(txtCast);
		addControl(btnCast, "castID");
		
		LLabel lblInd = new LLabel(battle, Vocab.instance.INDIVIDUAL, Tooltip.instance.INDIVIDUAL);
		LText txtInd = new LText(battle, true);
		txtInd.getCellData().setExpand(true, false);
		ImageButton btnInd = new ImageButton(battle, true);
		btnInd.setNameWidget(txtInd);
		btnInd.addMenu(lblInd);
		btnInd.addMenu(txtInd);
		addControl(btnInd, "individualID");

		LPanel battleCheck = new CheckBoxPanel(battle);
		battleCheck.getCellData().setSpread(3, 1);

		LCheckBox btnMirror = new LCheckBox(battleCheck);
		btnMirror.setText(Vocab.instance.MIRROR);
		btnMirror.setHoverText(Tooltip.instance.MIRROR);
		btnMirror.addMenu();
		addControl(btnMirror, "mirror");
		
		// User
		
		LPanel user = new LPanel(tab);
		user.setGridLayout(2);
		user.setMargins(LPrefs.FRAMEMARGIN, LPrefs.FRAMEMARGIN);
		user.getCellData().setExpand(true, false);
		tab.addTab(Vocab.instance.USER, user);
		
		LLabel lblUserLoad = new LLabel(user, Vocab.instance.LOAD, Tooltip.instance.USERLOAD);
		lblUserLoad.getCellData().setRequiredSize(LPrefs.LABELWIDTH, 0);
		LText txtUserLoad = new LText(user);
		txtUserLoad.getCellData().setExpand(true, false);
		txtUserLoad.addMenu(lblUserLoad);
		addControl(txtUserLoad, "userLoad");
		
		LLabel lblUserCast = new LLabel(user, Vocab.instance.CAST, Tooltip.instance.USERCAST);
		LText txtUserCast = new LText(user);
		txtUserCast.getCellData().setExpand(true, false);
		txtUserCast.addMenu(lblUserCast);
		addControl(txtUserCast, "userCast");

		new LLabel(user, 2, 1).getCellData().setExpand(false, true);

		LPanel userCheck = new CheckBoxPanel(user);
		userCheck.getCellData().setSpread(2, 1);

		LCheckBox btnStep = new LCheckBox(userCheck);
		btnStep.setText(Vocab.instance.STEPONCAST);
		btnStep.setHoverText(Tooltip.instance.STEPONCAST);
		btnStep.addMenu();
		addControl(btnStep, "stepOnCast");

		// Options

		LPanel animOptions = new LPanel(tab);
		animOptions.setGridLayout(4);
		animOptions.setMargins(LPrefs.FRAMEMARGIN, LPrefs.FRAMEMARGIN);
		tab.addTab(Vocab.instance.OPTIONS, animOptions);

		LLabel lblIntroTime = new LLabel(animOptions, Vocab.instance.INTROTIME, Tooltip.instance.INTROTIME);
		lblIntroTime.getCellData().setRequiredSize(LPrefs.LABELWIDTH, 0);
		LSpinner spnIntroTime = new LSpinner(animOptions);
		spnIntroTime.getCellData().setExpand(true, false);
		spnIntroTime.addMenu(lblIntroTime);
		spnIntroTime.setMinimum(-1);
		spnIntroTime.setMaximum(3600);
		addControl(spnIntroTime, "introTime");

		LLabel lblCastTime = new LLabel(animOptions, Vocab.instance.CASTTIME, Tooltip.instance.CASTTIME);
		lblCastTime.getCellData().setRequiredSize(LPrefs.LABELWIDTH, 0);
		LSpinner spnCastTime = new LSpinner(animOptions);
		spnCastTime.getCellData().setExpand(true, false);
		spnCastTime.addMenu(lblCastTime);
		spnCastTime.setMinimum(-1);
		spnCastTime.setMaximum(3600);
		addControl(spnCastTime, "castTime");

		LLabel lblCenterTime = new LLabel(animOptions, Vocab.instance.CENTERTIME, Tooltip.instance.CENTERTIME);
		LSpinner spnCenterTime = new LSpinner(animOptions);
		spnCenterTime.getCellData().setExpand(true, false);
		spnCenterTime.addMenu(lblCenterTime);
		spnCenterTime.setMinimum(-1);
		spnCenterTime.setMaximum(3600);
		addControl(spnCenterTime, "centerTime");

		LLabel lblTargetTime = new LLabel(animOptions, Vocab.instance.TARGETTIME, Tooltip.instance.TARGETTIME);
		LSpinner spnTargetTime = new LSpinner(animOptions);
		spnTargetTime.getCellData().setExpand(true, false);
		spnTargetTime.addMenu(lblTargetTime);
		spnTargetTime.setMinimum(-1);
		spnTargetTime.setMaximum(3600);
		addControl(spnTargetTime, "targetTime");

		LLabel lblFinishTime = new LLabel(animOptions, Vocab.instance.FINISHTIME, Tooltip.instance.FINISHTIME);
		LSpinner spnFinishTime = new LSpinner(animOptions);
		spnFinishTime.getCellData().setExpand(true, false);
		spnFinishTime.addMenu(lblFinishTime);
		spnFinishTime.setMinimum(-1);
		spnFinishTime.setMaximum(3600);
		addControl(spnFinishTime, "finishTime");

		new LLabel(animOptions,2, 1);

		LPanel otherCheck = new CheckBoxPanel(animOptions);
		otherCheck.getCellData().setSpread(4, 1);

		LCheckBox btnDamage = new LCheckBox(otherCheck);
		btnDamage.setText(Vocab.instance.DAMAGEANIM);
		btnDamage.setHoverText(Tooltip.instance.DAMAGEANIM);
		btnDamage.addMenu();
		addControl(btnDamage, "damageAnim");
		
	}

	@Override
	public Type getType() {
		return AnimInfo.class;
	}
	
}
