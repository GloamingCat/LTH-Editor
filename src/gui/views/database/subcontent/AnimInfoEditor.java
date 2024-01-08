package gui.views.database.subcontent;

import java.lang.reflect.Type;

import data.Skill.AnimInfo;
import gson.editor.GDefaultObjectEditor;
import gui.Tooltip;
import gui.Vocab;
import gui.widgets.ImageButton;
import lwt.container.LContainer;
import lwt.container.LPanel;
import lwt.container.LViewFolder;
import lwt.widget.LCheckBox;
import lwt.widget.LLabel;
import lwt.widget.LSpinner;
import lwt.widget.LText;

public class AnimInfoEditor extends GDefaultObjectEditor<AnimInfo> {
	
	public AnimInfoEditor(LContainer parent) {
		super(parent, false);
		setFillLayout(true);
		
		LViewFolder tab = new LViewFolder(this, false);
		
		// Battle
		
		LPanel battle = new LPanel(tab);
		battle.setGridLayout(3);
		battle.setMargins(5, 5);
		tab.addTab(Vocab.instance.BATTLE, battle);
		
		LLabel lblLoad = new LLabel(battle, Vocab.instance.LOAD, Tooltip.instance.LOAD);
		LText txtLoad = new LText(battle, true);
		ImageButton btnLoad = new ImageButton(battle, true);
		btnLoad.setNameWidget(txtLoad);
		btnLoad.addMenu(lblLoad);
		btnLoad.addMenu(txtLoad);
		addControl(btnLoad, "loadID");
		
		LLabel lblCast = new LLabel(battle, Vocab.instance.CAST, Tooltip.instance.CAST);
		LText txtCast = new LText(battle, true);
		ImageButton btnCast = new ImageButton(battle, true);
		btnCast.setNameWidget(txtCast);
		btnCast.addMenu(lblCast);
		btnCast.addMenu(txtCast);
		addControl(btnCast, "castID");
		
		LLabel lblInd = new LLabel(battle, Vocab.instance.INDIVIDUAL, Tooltip.instance.INDIVIDUAL);
		LText txtInd = new LText(battle, true);
		ImageButton btnInd = new ImageButton(battle, true);
		btnInd.setNameWidget(txtInd);
		btnInd.addMenu(lblInd);
		btnInd.addMenu(txtInd);
		addControl(btnInd, "individualID");
		
		LCheckBox btnMirror = new LCheckBox(battle);
		btnMirror.setText(Vocab.instance.MIRROR);
		btnMirror.setHoverText(Tooltip.instance.MIRROR);
		btnMirror.setSpread(3, 1);
		btnMirror.setExpand(false, false);
		btnMirror.addMenu();
		addControl(btnMirror, "mirror");
		
		// User
		
		LPanel user = new LPanel(tab);
		user.setGridLayout(2);
		user.setMargins(5, 5);
		tab.addTab(Vocab.instance.USER, user);
		
		LLabel lblUserLoad = new LLabel(user, Vocab.instance.LOAD, Tooltip.instance.USERLOAD);
		LText txtUserLoad = new LText(user);
		txtUserLoad.addMenu(lblUserLoad);
		addControl(txtUserLoad, "userLoad");
		
		LLabel lblUserCast = new LLabel(user, Vocab.instance.CAST, Tooltip.instance.USERCAST);
		LText txtUserCast = new LText(user);
		txtUserCast.addMenu(lblUserCast);
		addControl(txtUserCast, "userCast");
		
		LCheckBox btnStep = new LCheckBox(user, 2);
		btnStep.setText(Vocab.instance.STEPONCAST);
		btnStep.addMenu();
		addControl(btnStep, "stepOnCast");
		
		// Options
		
		LPanel animOptions = new LPanel(tab);
		animOptions.setGridLayout(4);
		animOptions.setMargins(5, 5);
		tab.addTab(Vocab.instance.OPTIONS, animOptions);
		
		LLabel lblIntroTime = new LLabel(animOptions, Vocab.instance.INTROTIME, Tooltip.instance.INTROTIME);
		LSpinner spnIntroTime = new LSpinner(animOptions);
		spnIntroTime.addMenu(lblIntroTime);
		spnIntroTime.setMinimum(-1);
		spnIntroTime.setMaximum(3600);
		addControl(spnIntroTime, "introTime");
		
		LLabel lblCastTime = new LLabel(animOptions, Vocab.instance.CASTTIME, Tooltip.instance.CASTTIME);
		LSpinner spnCastTime = new LSpinner(animOptions);
		spnCastTime.addMenu(lblCastTime);
		spnCastTime.setMinimum(-1);
		spnCastTime.setMaximum(3600);
		addControl(spnCastTime, "castTime");
		
		LLabel lblCenterTime = new LLabel(animOptions, Vocab.instance.CENTERTIME, Tooltip.instance.CENTERTIME);
		LSpinner spnCenterTime = new LSpinner(animOptions);
		spnCenterTime.addMenu(lblCenterTime);
		spnCenterTime.setMinimum(-1);
		spnCenterTime.setMaximum(3600);
		addControl(spnCenterTime, "centerTime");
		
		LLabel lblTargetTime = new LLabel(animOptions, Vocab.instance.TARGETTIME, Tooltip.instance.TARGETTIME);
		LSpinner spnTargetTime = new LSpinner(animOptions);
		spnTargetTime.addMenu(lblTargetTime);
		spnTargetTime.setMinimum(-1);
		spnTargetTime.setMaximum(3600);
		addControl(spnTargetTime, "targetTime");
		
		LLabel lblFinishTime = new LLabel(animOptions, Vocab.instance.FINISHTIME, Tooltip.instance.FINISHTIME);
		LSpinner spnFinishTime = new LSpinner(animOptions);
		spnFinishTime.addMenu(lblFinishTime);
		spnFinishTime.setMinimum(-1);
		spnFinishTime.setMaximum(3600);
		addControl(spnFinishTime, "finishTime");
		
		LCheckBox btnDamage = new LCheckBox(animOptions, 2);
		btnDamage.setText(Vocab.instance.DAMAGEANIM);
		btnDamage.addMenu();
		addControl(btnDamage, "damageAnim");
		
	}

	@Override
	public Type getType() {
		return AnimInfo.class;
	}
	
}
