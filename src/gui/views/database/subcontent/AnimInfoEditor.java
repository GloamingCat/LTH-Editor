package gui.views.database.subcontent;

import java.lang.reflect.Type;

import data.Skill.AnimInfo;
import gson.editor.GDefaultObjectEditor;
import gui.Vocab;
import gui.widgets.IDButton;
import lwt.container.LContainer;
import lwt.container.LPanel;
import lwt.container.LViewFolder;
import lwt.widget.LCheckBox;
import lwt.widget.LLabel;
import lwt.widget.LSpinner;
import lwt.widget.LText;
import project.Project;

public class AnimInfoEditor extends GDefaultObjectEditor<AnimInfo> {

	private IDButton btnLoad;
	private IDButton btnCast;
	private IDButton btnInd;
	
	public AnimInfoEditor(LContainer parent) {
		super(parent, true, false);
		
		LViewFolder tab = new LViewFolder(this, false);
		
		// Battle
		
		LPanel battle = new LPanel(tab, 3, false);
		tab.addTab(Vocab.instance.BATTLE, battle);
		
		LLabel lblLoad = new LLabel(battle, Vocab.instance.LOAD);
		LText txtLoad = new LText(battle, true);
		btnLoad = new IDButton(battle, true);
		btnLoad.setNameWidget(txtLoad);
		btnLoad.addMenu(lblLoad);
		btnLoad.addMenu(txtLoad);
		addControl(btnLoad, "loadID");
		
		LLabel lblCast = new LLabel(battle, Vocab.instance.CAST);
		LText txtCast = new LText(battle, true);
		btnCast = new IDButton(battle, true);
		btnCast.setNameWidget(txtCast);
		btnCast.addMenu(lblCast);
		btnCast.addMenu(txtCast);
		addControl(btnCast, "castID");
		
		LLabel lblInd = new LLabel(battle, Vocab.instance.INDIVIDUAL);
		LText txtInd = new LText(battle, true);
		btnInd = new IDButton(battle, true);
		btnInd.setNameWidget(txtInd);
		btnInd.addMenu(lblInd);
		btnInd.addMenu(txtInd);
		addControl(btnInd, "individualID");
		
		LCheckBox btnMirror = new LCheckBox(battle);
		btnMirror.setSpread(3, 1);
		btnMirror.setExpand(false, false);
		btnMirror.setText(Vocab.instance.MIRROR);
		btnMirror.addMenu();
		addControl(btnMirror, "mirror");
		
		// User
		
		LPanel user = new LPanel(tab, 2, false);
		tab.addTab(Vocab.instance.USER, user);
		
		LLabel lblUserLoad = new LLabel(user, Vocab.instance.LOAD);
		LText txtUserLoad = new LText(user);
		txtUserLoad.addMenu(lblUserLoad);
		addControl(txtUserLoad, "userLoad");
		
		LLabel lblUserCast = new LLabel(user, Vocab.instance.CAST);
		LText txtUserCast = new LText(user);
		txtUserCast.addMenu(lblUserCast);
		addControl(txtUserCast, "userCast");
		
		LCheckBox btnStep = new LCheckBox(user, 2);
		btnStep.setText(Vocab.instance.STEPONCAST);
		btnStep.addMenu();
		addControl(btnStep, "stepOnCast");
		
		// Options
		
		LPanel animOptions = new LPanel(tab, 4, false);
		tab.addTab(Vocab.instance.OPTIONS, animOptions);
		
		LLabel lblIntroTime = new LLabel(animOptions, Vocab.instance.INTROTIME);
		LSpinner spnIntroTime = new LSpinner(animOptions);
		spnIntroTime.addMenu(lblIntroTime);
		spnIntroTime.setMinimum(-1);
		spnIntroTime.setMaximum(3600);
		addControl(spnIntroTime, "introTime");
		
		LLabel lblCastTime = new LLabel(animOptions, Vocab.instance.CASTTIME);
		LSpinner spnCastTime = new LSpinner(animOptions);
		spnCastTime.addMenu(lblCastTime);
		spnCastTime.setMinimum(-1);
		spnCastTime.setMaximum(3600);
		addControl(spnCastTime, "castTime");
		
		LLabel lblCenterTime = new LLabel(animOptions, Vocab.instance.CENTERTIME);
		LSpinner spnCenterTime = new LSpinner(animOptions);
		spnCenterTime.addMenu(lblCenterTime);
		spnCenterTime.setMinimum(-1);
		spnCenterTime.setMaximum(3600);
		addControl(spnCenterTime, "centerTime");
		
		LLabel lblTargetTime = new LLabel(animOptions, Vocab.instance.TARGETTIME);
		LSpinner spnTargetTime = new LSpinner(animOptions);
		spnTargetTime.addMenu(lblTargetTime);
		spnTargetTime.setMinimum(-1);
		spnTargetTime.setMaximum(3600);
		addControl(spnTargetTime, "targetTime");
		
		LLabel lblFinishTime = new LLabel(animOptions, Vocab.instance.FINISHTIME);
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
	
	public void onVisible() {
		btnCast.dataTree = Project.current.animations.getTree();
		btnInd.dataTree = Project.current.animations.getTree();
		btnLoad.dataTree = Project.current.animations.getTree();
		super.onVisible();
	}

	@Override
	public Type getType() {
		return AnimInfo.class;
	}
	
}
