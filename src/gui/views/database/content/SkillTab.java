package gui.views.database.content;

import gson.project.GObjectTreeSerializer;
import gui.Vocab;
import gui.views.database.DatabaseTab;
import gui.views.database.subcontent.BonusList;
import gui.views.database.subcontent.EffectList;
import gui.views.database.subcontent.RangeEditor;
import gui.views.database.subcontent.TagList;
import gui.widgets.IDButton;
import gui.widgets.IconButton;
import gui.widgets.ScriptButton;

import java.util.ArrayList;

import lwt.dataestructure.LDataTree;
import lwt.editor.LComboView;
import lwt.widget.LCheckButton;
import lwt.widget.LImage;
import lwt.widget.LTextBox;
import lwt.widget.LText;

import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Group;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.TabFolder;
import org.eclipse.swt.widgets.Text;

import data.config.Config;
import project.Project;

import org.eclipse.swt.widgets.TabItem;

public class SkillTab extends DatabaseTab {

	/**
	 * Create the composite.
	 * @param parent
	 * @param style
	 */
	public SkillTab(Composite parent, int style) {
		super(parent, style);

		Label lblDescription = new Label(grpGeneral, SWT.NONE);
		lblDescription.setLayoutData(new GridData(SWT.LEFT, SWT.FILL, false, false, 1, 1));
		lblDescription.setText(Vocab.instance.DESCRIPTION);
		
		LTextBox txtDescription = new LTextBox(grpGeneral, SWT.NONE);
		txtDescription.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true, 1, 1));
		addControl(txtDescription, "description");
		
		// Icon
		
		Label lblIcon = new Label(grpGeneral, SWT.NONE);
		lblIcon.setText(Vocab.instance.ICON);
		
		Composite compositeIcon = new Composite(grpGeneral, SWT.NONE);
		GridLayout gl_compositeIcon = new GridLayout(2, false);
		gl_compositeIcon.marginWidth = 0;
		gl_compositeIcon.marginHeight = 0;
		compositeIcon.setLayout(gl_compositeIcon);
		compositeIcon.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, false, false, 1, 1));
		
		LImage imgIcon = new LImage(compositeIcon, SWT.NONE);
		imgIcon.setImage("/javax/swing/plaf/basic/icons/image-delayed.png");
		GridData gd_imgIcon = new GridData(SWT.FILL, SWT.FILL, true, true, 1, 1);
		gd_imgIcon.widthHint = 48;
		gd_imgIcon.heightHint = 48;
		imgIcon.setVerticalAlign(SWT.CENTER);
		imgIcon.setLayoutData(gd_imgIcon);
		
		IconButton btnGraphics = new IconButton(compositeIcon, SWT.NONE);
		btnGraphics.optional = false;
		btnGraphics.setImage(imgIcon);
		addControl(btnGraphics, "icon");

		// Script
		
		Label lblScript = new Label(grpGeneral,  SWT.NONE);
		lblScript.setText(Vocab.instance.SCRIPT);
		
		Composite script = new Composite(grpGeneral,  SWT.NONE);
		script.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, false, false, 1, 1));
		GridLayout gl_script = new GridLayout(2, false);
		gl_script.marginWidth = 0;
		gl_script.marginHeight = 0;
		script.setLayout(gl_script);
		
		Text txtScript = new Text(script, SWT.BORDER | SWT.READ_ONLY);
		txtScript.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1));
		
		ScriptButton btnScript = new ScriptButton(script, SWT.NONE);
		btnScript.optional = true;
		btnScript.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, false, false, 1, 1));
		addScriptButton(btnScript, txtScript, "skill", "script");
		
		// Type
		
		Label lblType = new Label(grpGeneral, SWT.NONE);
		lblType.setText(Vocab.instance.TYPE);
		
		ArrayList<String> types = new ArrayList<String>();
		types.add(Vocab.instance.GENERAL);
		types.add(Vocab.instance.ATTACK);
		types.add(Vocab.instance.SUPPORT);
		
		LComboView cmbType = new LComboView(grpGeneral, SWT.NONE) {
			public ArrayList<?> getArray() {
				return types;
			}
		};
		cmbType.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1));
		cmbType.setIncludeID(false);
		cmbType.setOptional(false);
		addControl(cmbType, "type");
		
		// Target Type
		
		Label lblTarget = new Label(grpGeneral, SWT.NONE);
		lblTarget.setText(Vocab.instance.TARGETTYPE);
		
		ArrayList<String> targets = new ArrayList<String>();
		targets.add(Vocab.instance.ANYTILE);
		targets.add(Vocab.instance.ANYCHARACTER);
		targets.add(Vocab.instance.LIVINGONLY);
		targets.add(Vocab.instance.DEADONLY);
		
		LComboView cmbTargets = new LComboView(grpGeneral, SWT.NONE) {
			public ArrayList<?> getArray() {
				return targets;
			}
		};
		cmbTargets.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1));
		cmbTargets.setIncludeID(false);
		cmbTargets.setOptional(false);
		addControl(cmbTargets, "targetType");
		
		// Restrictions
		
		ArrayList<String> restrictions = new ArrayList<String>();
		restrictions.add(Vocab.instance.ALWAYS);
		restrictions.add(Vocab.instance.BATTLEONLY);
		restrictions.add(Vocab.instance.FIELDONLY);
		
		Label lblRestrictions = new Label(grpGeneral, SWT.NONE);
		lblRestrictions.setText(Vocab.instance.RESTRICTIONS);
		
		LComboView cmbRestrictions = new LComboView(grpGeneral, SWT.NONE) {
			public ArrayList<?> getArray() {
				return restrictions;
			}
		};
		cmbRestrictions.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1));
		cmbRestrictions.setIncludeID(false);
		cmbRestrictions.setOptional(false);
		addControl(cmbRestrictions, "restriction");
		
		// Costs
		
		Label lblCosts = new Label(grpGeneral, SWT.NONE);
		lblCosts.setLayoutData(new GridData(SWT.LEFT, SWT.TOP, false, false, 1, 1));
		lblCosts.setText(Vocab.instance.COSTS);
		
		TagList lstCost = new TagList(grpGeneral, SWT.NONE);
		GridData gd_lstCost = new GridData(SWT.FILL, SWT.CENTER, false, false, 1, 1);
		gd_lstCost.heightHint = 60;
		lstCost.setLayoutData(gd_lstCost);
		lstCost.attributeName = "costs";
		addChild(lstCost);
		
		// Animations
		
		Composite right = new Composite(contentEditor, SWT.NONE);
		GridLayout gl_right = new GridLayout(1, false);
		gl_right.verticalSpacing = 0;
		gl_right.marginWidth = 0;
		gl_right.marginHeight = 0;
		right.setLayout(gl_right);
		GridData gd_right = new GridData(SWT.FILL, SWT.FILL, true, false, 1, 1);
		gd_right.widthHint = 300;
		right.setLayoutData(gd_right);
		
		Group grpAnimations = new Group(right, SWT.NONE);
		GridLayout gl_grpAnimations = new GridLayout(1, false);
		gl_grpAnimations.marginTop = 5;
		gl_grpAnimations.marginHeight = 0;
		gl_grpAnimations.marginWidth = 0;
		grpAnimations.setLayout(gl_grpAnimations);
		grpAnimations.setLayoutData(new GridData(SWT.FILL, SWT.FILL, false, false, 1, 1));
		grpAnimations.setText(Vocab.instance.ANIMATIONS);
		TabFolder tabAnim = new TabFolder(grpAnimations, SWT.NONE);
		tabAnim.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1));
		
		// Battle Animations
		
		TabItem tabBattleAnim = new TabItem(tabAnim, SWT.NONE);
		tabBattleAnim.setText(Vocab.instance.BATTLE);
		Composite battleAnim = new Composite(tabAnim, SWT.NONE);
		tabBattleAnim.setControl(battleAnim);
		battleAnim.setLayout(new GridLayout(3, false));
		
		Label lblLoadAnimation = new Label(battleAnim, SWT.NONE);
		lblLoadAnimation.setText(Vocab.instance.LOAD);
		
		Text txtLoadAnim = new Text(battleAnim, SWT.BORDER | SWT.READ_ONLY);
		txtLoadAnim.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, false, 1, 1));
		
		IDButton btnLoadAnim = new IDButton(battleAnim, SWT.NONE) {
			@Override
			public LDataTree<Object> getDataTree() {
				return Project.current.animations.getTree();
			}
		};
		btnLoadAnim.optional = true;
		btnLoadAnim.setNameText(txtLoadAnim);
		addControl(btnLoadAnim, "loadAnimID");
		
		Label lblCastAnimation = new Label(battleAnim, SWT.NONE);
		lblCastAnimation.setText(Vocab.instance.CAST);
		
		Text txtCastAnim = new Text(battleAnim, SWT.BORDER | SWT.READ_ONLY);
		txtCastAnim.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, false, 1, 1));
		
		IDButton btnCastAnim = new IDButton(battleAnim, SWT.NONE) {
			@Override
			public LDataTree<Object> getDataTree() {
				return Project.current.animations.getTree();
			}
		};
		btnCastAnim.optional = true;
		btnCastAnim.setNameText(txtCastAnim);
		addControl(btnCastAnim, "castAnimID");
		
		Label lblIndividualAnimation = new Label(battleAnim, SWT.NONE);
		lblIndividualAnimation.setText(Vocab.instance.INDIVIDUAL);

		Text txtIndAnim = new Text(battleAnim, SWT.BORDER | SWT.READ_ONLY);
		txtIndAnim.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, false, 1, 1));
		
		IDButton btnIndAnim = new IDButton(battleAnim, SWT.NONE) {
			@Override
			public LDataTree<Object> getDataTree() {
				return Project.current.animations.getTree();
			}
		};
		btnIndAnim.optional = true;
		btnIndAnim.setNameText(txtIndAnim);
		addControl(btnIndAnim, "individualAnimID");
		
		LCheckButton btnMirror = new LCheckButton(battleAnim, SWT.NONE);
		btnMirror.setLayoutData(new GridData(SWT.FILL, SWT.FILL, false, false, 3, 1));
		btnMirror.setText(Vocab.instance.MIRROR);
		addControl(btnMirror, "mirror");
		
		// User Animations
		
		TabItem tabUserAnim = new TabItem(tabAnim, SWT.NONE);
		tabUserAnim.setText(Vocab.instance.USER);
		Composite userAnim = new Composite(tabAnim, SWT.NONE);
		tabUserAnim.setControl(userAnim);
		userAnim.setLayout(new GridLayout(2, false));
		
		Label lblUserLoadAnim = new Label(userAnim, SWT.NONE);
		lblUserLoadAnim.setBounds(0, 0, 55, 15);
		lblUserLoadAnim.setText(Vocab.instance.LOAD);
		
		LText txtUserLoadAnim = new LText(userAnim, SWT.NONE);
		txtUserLoadAnim.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1));
		addControl(txtUserLoadAnim, "userLoadAnim");
		
		Label lblUserCastAnim = new Label(userAnim, SWT.NONE);
		lblUserCastAnim.setBounds(0, 0, 55, 15);
		lblUserCastAnim.setText(Vocab.instance.CAST);
		
		LText txtUserCastAnim = new LText(userAnim, SWT.NONE);
		txtUserCastAnim.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1));
		addControl(txtUserCastAnim, "userCastAnim");
		
		LCheckButton btnStep = new LCheckButton(userAnim, SWT.NONE);
		btnStep.setLayoutData(new GridData(SWT.FILL, SWT.FILL, false, false, 2, 1));
		btnStep.setText(Vocab.instance.STEPONCAST);
		addControl(btnStep, "stepOnCast");
		
		// Animation Options
		
		TabItem tabAnimOptions = new TabItem(tabAnim, SWT.NONE);
		tabAnimOptions.setText(Vocab.instance.OPTIONS);
		Composite animOptions = new Composite(tabAnim, SWT.NONE);
		tabAnimOptions.setControl(animOptions);
		animOptions.setLayout(new GridLayout(4, false));
		
		Label lblIntroTime = new Label(animOptions, SWT.NONE);
		lblIntroTime.setText(Vocab.instance.INTROTIME);
		
		LText txtIntroTime = new LText(animOptions, SWT.NONE);
		txtIntroTime.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1));
		addControl(txtIntroTime, "introTime");
		
		Label lblCastTime = new Label(animOptions, SWT.NONE);
		lblCastTime.setText(Vocab.instance.CASTTIME);
		
		LText txtCastTime = new LText(animOptions, SWT.NONE);
		txtCastTime.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1));
		addControl(txtCastTime, "castTime");
		
		Label lblCenterTime = new Label(animOptions, SWT.NONE);
		lblCenterTime.setText(Vocab.instance.CENTERTIME);
		
		LText txtCenterTime = new LText(animOptions, SWT.NONE);
		txtCenterTime.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1));
		addControl(txtCenterTime, "centerTime");
		
		Label lblTargetTime = new Label(animOptions, SWT.NONE);
		lblTargetTime.setText(Vocab.instance.TARGETTIME);
		
		LText txtTargetTime = new LText(animOptions, SWT.NONE);
		txtTargetTime.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1));
		addControl(txtTargetTime, "targetTime");
		
		Label lblFinishTime = new Label(animOptions, SWT.NONE);
		lblFinishTime.setText(Vocab.instance.FINISHTIME);
		
		LText txtFinishTime = new LText(animOptions, SWT.NONE);
		txtFinishTime.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1));
		addControl(txtFinishTime, "finishTime");
		
		LCheckButton btnDamageAnim = new LCheckButton(animOptions, SWT.NONE);
		btnDamageAnim.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 2, 1));
		btnDamageAnim.setText(Vocab.instance.DAMAGEANIM);
		addControl(btnDamageAnim, "damageAnim");
		
		// Range
		
		Group grpRange = new Group(right, SWT.NONE);
		GridLayout gl_grpRange = new GridLayout(1, false);
		gl_grpRange.marginTop = 5;
		gl_grpRange.marginHeight = 0;
		gl_grpRange.marginWidth = 0;
		grpRange.setLayout(gl_grpRange);
		grpRange.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1));
		grpRange.setText(Vocab.instance.RANGE);
		TabFolder tabRange = new TabFolder(grpRange, SWT.NONE);
		tabRange.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1));
		
		TabItem tabEffectRange = new TabItem(tabRange, SWT.NONE);
		tabEffectRange.setText(Vocab.instance.EFFECT);
		RangeEditor effectRange = new RangeEditor(tabRange, SWT.NONE);
		effectRange.attributeName = "effectRange";
		tabEffectRange.setControl(effectRange);
		addChild(effectRange);

		TabItem tabCastRange = new TabItem(tabRange, SWT.NONE);
		tabCastRange.setText(Vocab.instance.CAST);
		RangeEditor castRange = new RangeEditor(tabRange, SWT.NONE);
		castRange.attributeName = "castRange";
		tabCastRange.setControl(castRange);
		addChild(castRange);
		
		// Effects
		
		Group grpEffects = new Group(right, SWT.NONE);
		GridData gd_grpEffects = new GridData(SWT.FILL, SWT.FILL, true, true, 1, 1);
		gd_grpEffects.heightHint = 70;
		grpEffects.setLayoutData(gd_grpEffects);
		FillLayout fl_grpEffects = new FillLayout(SWT.HORIZONTAL);
		fl_grpEffects.marginHeight = 5;
		fl_grpEffects.marginWidth = 5;
		grpEffects.setLayout(fl_grpEffects);
		grpEffects.setText(Vocab.instance.EFFECTS);
		
		EffectList lstEffects = new EffectList(grpEffects, SWT.NONE);
		addChild(lstEffects);
		
		// Other
		
		Composite bottom = new Composite(contentEditor, SWT.NONE);
		GridLayout gl_bottom = new GridLayout(3, true);
		gl_bottom.verticalSpacing = 0;
		gl_bottom.marginHeight = 0;
		gl_bottom.marginWidth = 0;
		bottom.setLayout(gl_bottom);
		bottom.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true, 2, 1));
		
		Group grpStatus = new Group(bottom, SWT.NONE);
		grpStatus.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true, 1, 1));
		grpStatus.setLayout(new FillLayout(SWT.HORIZONTAL));
		grpStatus.setText(Vocab.instance.STATUS);
		
		BonusList lstStatus = new BonusList(grpStatus, SWT.NONE) {
			@Override
			protected LDataTree<Object> getDataTree() {
				return Project.current.status.getTree();
			}
		};
		lstStatus.attributeName = "status";
		lstStatus.setIncludeID(false);
		addChild(lstStatus);
		
		Group grpElements = new Group(bottom, SWT.NONE);
		GridLayout gl_grpElements = new GridLayout(1, false);
		gl_grpElements.marginWidth = 0;
		gl_grpElements.marginHeight = 0;
		grpElements.setLayout(gl_grpElements);
		grpElements.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true, 1, 1));
		grpElements.setText(Vocab.instance.ELEMENTS);
		
		BonusList lstElements = new BonusList(grpElements, SWT.NONE) {
			@Override
			protected LDataTree<Object> getDataTree() {
				Config conf = (Config) Project.current.config.getData();
				return conf.elements.toObjectTree();
			}
		};
		lstElements.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true, 1, 1));
		lstElements.attributeName = "elements";
		addChild(lstElements);
		
		LCheckButton btnUserElements = new LCheckButton(grpElements, SWT.NONE);
		btnUserElements.setText(Vocab.instance.USERELEMENTS);
		addControl(btnUserElements, "userElements");
		
		Group grpTags = new Group(bottom, SWT.NONE);
		grpTags.setLayoutData(new GridData(SWT.FILL, SWT.FILL, false, false, 1, 1));
		grpTags.setLayout(new FillLayout(SWT.HORIZONTAL));
		grpTags.setText(Vocab.instance.TAGS);
		
		TagList tagEditor = new TagList(grpTags, SWT.NONE);
		addChild(tagEditor);

	}

	@Override
	protected GObjectTreeSerializer getSerializer() {
		return Project.current.skills;
	}

}
