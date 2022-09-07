package gui.views.database.content;

import gson.project.GObjectTreeSerializer;
import gui.Vocab;
import gui.shell.database.MaskShell;
import gui.views.database.DatabaseTab;
import gui.views.database.subcontent.BonusList;
import gui.views.database.subcontent.SkillEffectList;
import gui.views.database.subcontent.TagList;
import gui.widgets.IDButton;
import gui.widgets.IconButton;
import gui.widgets.LuaButton;

import lwt.dataestructure.LDataTree;
import lwt.dialog.LObjectShell;
import lwt.dialog.LShellFactory;
import lwt.event.LControlEvent;
import lwt.event.listener.LControlListener;
import lwt.widget.LCheckBox;
import lwt.widget.LCombo;
import lwt.widget.LImage;
import lwt.widget.LLabel;
import lwt.widget.LObjectButton;
import lwt.widget.LTextBox;
import lwt.widget.LText;

import org.eclipse.swt.SWT;
import org.eclipse.swt.events.PaintEvent;
import org.eclipse.swt.events.PaintListener;
import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.graphics.Rectangle;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Group;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.TabFolder;

import project.Project;

import org.eclipse.swt.widgets.TabItem;
import org.eclipse.wb.swt.SWTResourceManager;

import data.Skill.Mask;
import org.eclipse.swt.widgets.Label;

public class SkillTab extends DatabaseTab {

	/**
	 * Create the composite.
	 * @param parent
	 */
	public SkillTab(Composite parent) {
		super(parent);
		GridLayout gridLayout = (GridLayout) contentEditor.getLayout();
		gridLayout.makeColumnsEqualWidth = true;
		
		Composite right = new Composite(contentEditor, SWT.NONE);
		GridLayout gl_right = new GridLayout(1, false);
		gl_right.verticalSpacing = 0;
		gl_right.marginWidth = 0;
		gl_right.marginHeight = 0;
		right.setLayout(gl_right);
		GridData gd_right = new GridData(SWT.FILL, SWT.FILL, true, false, 1, 2);
		gd_right.widthHint = 200;
		right.setLayoutData(gd_right);
		
		Group grpAnimations = new Group(right, SWT.NONE);
		GridLayout gl_grpAnimations = new GridLayout(1, false);
		gl_grpAnimations.marginTop = 5;
		gl_grpAnimations.marginHeight = 0;
		gl_grpAnimations.marginWidth = 0;
		grpAnimations.setLayout(gl_grpAnimations);
		grpAnimations.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, false, 1, 1));
		grpAnimations.setText(Vocab.instance.ANIMATIONS);

		Composite range = new Composite(contentEditor, SWT.NONE);
		range.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, false, 1, 1));
		GridLayout gl_range = new GridLayout(2, false);
		gl_range.marginWidth = 0;
		gl_range.marginHeight = 0;
		range.setLayout(gl_range);
		
		Composite bottom = new Composite(contentEditor, SWT.NONE);
		GridLayout gl_bottom = new GridLayout(3, true);
		gl_bottom.verticalSpacing = 0;
		gl_bottom.marginHeight = 0;
		gl_bottom.marginWidth = 0;
		bottom.setLayout(gl_bottom);
		bottom.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true, 2, 1));
		
		// Script
		
		new LLabel(grpGeneral, Vocab.instance.SCRIPT);
		
		Composite script = new Composite(grpGeneral,  SWT.NONE);
		script.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, false, false, 1, 1));
		GridLayout gl_script = new GridLayout(2, false);
		gl_script.marginWidth = 0;
		gl_script.marginHeight = 0;
		script.setLayout(gl_script);
		
		LText txtScript = new LText(script, true);
		txtScript.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1));
		
		LuaButton btnScript = new LuaButton(script, 1);
		btnScript.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, false, false, 1, 1));
		btnScript.setPathWidget(txtScript);
		addControl(btnScript, "script");
		
		// Description
		
		new LLabel(grpGeneral, Vocab.instance.DESCRIPTION).setLayoutData(new GridData(SWT.LEFT, SWT.FILL, false, false, 1, 1));
		
		LTextBox txtDescription = new LTextBox(grpGeneral);
		GridData gd_txtDescription = new GridData(SWT.FILL, SWT.FILL, true, true, 1, 1);
		gd_txtDescription.heightHint = 84;
		txtDescription.setLayoutData(gd_txtDescription);
		addControl(txtDescription, "description");
		
		// Icon
		
		new LLabel(grpGeneral, Vocab.instance.ICON);
		
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
		
		IconButton btnGraphics = new IconButton(compositeIcon, 1);
		btnGraphics.setImageWidget(imgIcon);
		addControl(btnGraphics, "icon");
		
		// Range
		
		Group grpEffect = new Group(range, SWT.NONE);
		grpEffect.setLayout(new GridLayout(2, false));
		grpEffect.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true, 1, 1));
		grpEffect.setText(Vocab.instance.EFFECTMASK);
		
		Group grpCast = new Group(range, SWT.NONE);
		grpCast.setLayout(new GridLayout(2, false));
		grpCast.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true, 1, 1));
		grpCast.setText(Vocab.instance.CASTMASK);
		
		LObjectButton<Mask> btnEffectMask = new LObjectButton<Mask>(grpEffect, SWT.NONE);
		btnEffectMask.setLayoutData(new GridData(SWT.LEFT, SWT.TOP, false, true, 1, 1));
		addControl(btnEffectMask, "effectMask");
		
		Composite effectMask = new Composite(grpEffect, SWT.NONE);
		effectMask.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true, 1, 1));
		addMaskButton(btnEffectMask, effectMask, effectColor);
		
		LObjectButton<Mask> btnCastMask = new LObjectButton<Mask>(grpCast, SWT.NONE);
		btnCastMask.setLayoutData(new GridData(SWT.LEFT, SWT.TOP, false, true, 1, 1));
		addControl(btnCastMask, "castMask");
		
		Composite castMask = new Composite(grpCast, SWT.NONE);
		castMask.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true, 1, 1));
		addMaskButton(btnCastMask, castMask, castColor);	
		
		// Target Selection
		
		Group grpTarget = new Group(right, SWT.NONE);
		GridLayout gl_grpTarget = new GridLayout(2, false);
		grpTarget.setLayout(gl_grpTarget);
		grpTarget.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, false, 1, 1));
		grpTarget.setText(Vocab.instance.TARGET);
		
		new LLabel(grpTarget, Vocab.instance.TYPE);

		Composite type = new Composite(grpTarget, SWT.NONE);
		GridLayout gl_type = new GridLayout(2, false);
		gl_type.marginWidth = 0;
		gl_type.marginHeight = 0;
		type.setLayout(gl_type);
		type.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1));
		
		LCombo cmbType = new LCombo(type, SWT.NONE);
		cmbType.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1));
		cmbType.setIncludeID(false);
		cmbType.setOptional(false);
		cmbType.setItems(new String[] {	
			Vocab.instance.GENERAL,
			Vocab.instance.ATTACK,
			Vocab.instance.SUPPORT
		});
		addControl(cmbType, "type");
		
		LCheckBox btnAllParties = new LCheckBox(type);
		btnAllParties.setText(Vocab.instance.ALLPARTIES);
		addControl(btnAllParties, "allParties");
		
		new LLabel(grpTarget, Vocab.instance.TARGETSELECTION);
		
		Composite selection = new Composite(grpTarget, SWT.NONE);
		GridLayout gl_selection = new GridLayout(2, false);
		gl_selection.marginHeight = 0;
		gl_selection.marginWidth = 0;
		selection.setLayout(gl_selection);
		selection.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, false, 1, 1));
		
		LCombo cmbSelection = new LCombo(selection);
		cmbSelection.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1));
		cmbSelection.setIncludeID(false);
		cmbSelection.setOptional(false);
		cmbSelection.setItems(new String[] {
				Vocab.instance.ANYTILE,
				Vocab.instance.EFFECTONLY,
				Vocab.instance.RANGEONLY,
				Vocab.instance.EFFECTRANGE});
		addControl(cmbSelection, "selection");
		
		LCheckBox btnAutopath = new LCheckBox(selection);
		btnAutopath.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, false, false, 1, 1));
		btnAutopath.setText(Vocab.instance.AUTOPATH);
		addControl(btnAutopath, "autoPath");
		
		new Label(grpTarget, SWT.NONE);
		
		LCheckBox btnFreeNavigation = new LCheckBox(grpTarget);
		btnFreeNavigation.setText(Vocab.instance.FREENAVIGATION);
		addControl(btnFreeNavigation, "freeNavigation");
		
		// Animations
		
		TabFolder tabAnim = new TabFolder(grpAnimations, SWT.NONE);
		tabAnim.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1));
		
		// Battle Animations
		
		TabItem tabBattleAnim = new TabItem(tabAnim, SWT.NONE);
		tabBattleAnim.setText(Vocab.instance.BATTLE);
		Composite battleAnim = new Composite(tabAnim, SWT.NONE);
		tabBattleAnim.setControl(battleAnim);
		battleAnim.setLayout(new GridLayout(3, false));
		
		new LLabel(battleAnim, Vocab.instance.LOAD);
		
		LText txtLoadAnim = new LText(battleAnim, true);
		txtLoadAnim.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, false, 1, 1));
		
		IDButton btnLoadAnim = new IDButton(battleAnim, 1) {
			@Override
			public LDataTree<Object> getDataTree() {
				return Project.current.animations.getTree();
			}
		};
		btnLoadAnim.setNameWidget(txtLoadAnim);
		addControl(btnLoadAnim, "loadAnimID");
		
		new LLabel(battleAnim, Vocab.instance.CAST);
		
		LText txtCastAnim = new LText(battleAnim, true);
		txtCastAnim.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, false, 1, 1));
		
		IDButton btnCastAnim = new IDButton(battleAnim, 1) {
			@Override
			public LDataTree<Object> getDataTree() {
				return Project.current.animations.getTree();
			}
		};
		btnCastAnim.setNameWidget(txtCastAnim);
		addControl(btnCastAnim, "castAnimID");
		
		new LLabel(battleAnim, Vocab.instance.INDIVIDUAL);

		LText txtIndAnim = new LText(battleAnim, true);
		txtIndAnim.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, false, 1, 1));
		
		IDButton btnIndAnim = new IDButton(battleAnim, 1) {
			@Override
			public LDataTree<Object> getDataTree() {
				return Project.current.animations.getTree();
			}
		};
		btnIndAnim.setNameWidget(txtIndAnim);
		addControl(btnIndAnim, "individualAnimID");
		
		LCheckBox btnMirror = new LCheckBox(battleAnim, SWT.NONE);
		btnMirror.setLayoutData(new GridData(SWT.FILL, SWT.FILL, false, false, 3, 1));
		btnMirror.setText(Vocab.instance.MIRROR);
		addControl(btnMirror, "mirror");
		
		// User Animations
		
		TabItem tabUserAnim = new TabItem(tabAnim, SWT.NONE);
		tabUserAnim.setText(Vocab.instance.USER);
		Composite userAnim = new Composite(tabAnim, SWT.NONE);
		tabUserAnim.setControl(userAnim);
		userAnim.setLayout(new GridLayout(2, false));
		
		new LLabel(userAnim, Vocab.instance.LOAD).setBounds(0, 0, 55, 15);
		
		LText txtUserLoadAnim = new LText(userAnim);
		txtUserLoadAnim.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1));
		addControl(txtUserLoadAnim, "userLoadAnim");
		
		new LLabel(userAnim, Vocab.instance.CAST).setBounds(0, 0, 55, 15);
		
		LText txtUserCastAnim = new LText(userAnim);
		txtUserCastAnim.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1));
		addControl(txtUserCastAnim, "userCastAnim");
		
		LCheckBox btnStep = new LCheckBox(userAnim, SWT.NONE);
		btnStep.setLayoutData(new GridData(SWT.FILL, SWT.FILL, false, false, 2, 1));
		btnStep.setText(Vocab.instance.STEPONCAST);
		addControl(btnStep, "stepOnCast");
		
		// Animation Options
		
		TabItem tabAnimOptions = new TabItem(tabAnim, SWT.NONE);
		tabAnimOptions.setText(Vocab.instance.OPTIONS);
		Composite animOptions = new Composite(tabAnim, SWT.NONE);
		tabAnimOptions.setControl(animOptions);
		animOptions.setLayout(new GridLayout(4, false));
		
		new LLabel(animOptions, Vocab.instance.INTROTIME);
		
		LText txtIntroTime = new LText(animOptions);
		txtIntroTime.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1));
		addControl(txtIntroTime, "introTime");
		
		new LLabel(animOptions, Vocab.instance.CASTTIME);
		
		LText txtCastTime = new LText(animOptions);
		txtCastTime.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1));
		addControl(txtCastTime, "castTime");
		
		new LLabel(animOptions, Vocab.instance.CENTERTIME);
		
		LText txtCenterTime = new LText(animOptions);
		txtCenterTime.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1));
		addControl(txtCenterTime, "centerTime");
		
		new LLabel(animOptions, Vocab.instance.TARGETTIME);
		
		LText txtTargetTime = new LText(animOptions);
		txtTargetTime.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1));
		addControl(txtTargetTime, "targetTime");
		
		new LLabel(animOptions, Vocab.instance.FINISHTIME);
		
		LText txtFinishTime = new LText(animOptions);
		txtFinishTime.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1));
		addControl(txtFinishTime, "finishTime");
		
		LCheckBox btnDamageAnim = new LCheckBox(animOptions, SWT.NONE);
		btnDamageAnim.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 2, 1));
		btnDamageAnim.setText(Vocab.instance.DAMAGEANIM);
		addControl(btnDamageAnim, "damageAnim");
		
		// Restrictions
		
		Group grpRestrictions = new Group(right, SWT.NONE);
		grpRestrictions.setLayout(new GridLayout(2, false));
		grpRestrictions.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, false, 1, 1));
		grpRestrictions.setText(Vocab.instance.RESTRICTIONS);
		
		new LLabel(grpRestrictions, Vocab.instance.COSTS);
		
		TagList lstCosts = new TagList(grpRestrictions, SWT.NONE);
		GridData gd_lstCosts = new GridData(SWT.FILL, SWT.TOP, true, false, 1, 1);
		gd_lstCosts.heightHint = 61;
		lstCosts.setLayoutData(gd_lstCosts);
		addChild(lstCosts, "costs");
		
		new LLabel(grpRestrictions, Vocab.instance.CONTEXT);
		
		LCombo cmbRestrictions = new LCombo(grpRestrictions, SWT.NONE);
		cmbRestrictions.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1));
		cmbRestrictions.setIncludeID(false);
		cmbRestrictions.setOptional(false);
		cmbRestrictions.setItems(new String[] {
				Vocab.instance.ALWAYS,
				Vocab.instance.BATTLEONLY,
				Vocab.instance.FIELDONLY});
		addControl(cmbRestrictions, "restriction");
		
		new LLabel(grpRestrictions, Vocab.instance.USECONDITION);
		
		LText txtCondition = new LText(grpRestrictions);
		txtCondition.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1));
		addControl(txtCondition, "condition");
		
		// Effects
		
		Group grpEffects = new Group(bottom, SWT.NONE);
		grpEffects.setLayout(new GridLayout(2, false));
		grpEffects.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true, 1, 1));
		grpEffects.setText(Vocab.instance.EFFECTS);

		SkillEffectList lstEffects = new SkillEffectList(grpEffects, SWT.NONE);
		lstEffects.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true, 2, 1));
		addChild(lstEffects, "effects");
		
		new LLabel(grpEffects, Vocab.instance.EFFECTCONDITION);
		
		LText txtEffectCondition = new LText(grpEffects);
		txtEffectCondition.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1));
		txtEffectCondition.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, false, 1, 1));
		addControl(txtEffectCondition, "effectCondition");
		
		// Elements
		
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
				return Project.current.elements.getList().toTree();
			}
		};
		lstElements.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true, 1, 1));
		addChild(lstElements, "elements");
		
		LCheckBox btnUserElements = new LCheckBox(grpElements, SWT.NONE);
		btnUserElements.setText(Vocab.instance.USERELEMENTS);
		addControl(btnUserElements, "userElements");
		
		// Tags
		
		Group grpTags = new Group(bottom, SWT.NONE);
		grpTags.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true, 1, 1));
		grpTags.setLayout(new FillLayout(SWT.HORIZONTAL));
		grpTags.setText(Vocab.instance.TAGS);
		
		TagList lstTags = new TagList(grpTags, SWT.NONE);
		addChild(lstTags, "tags");

	}
	
	@Override
	protected GObjectTreeSerializer getSerializer() {
		return Project.current.skills;
	}
	
	//-------------------------------------------------------------------------------------
	// Mask Drawing
	//-------------------------------------------------------------------------------------
	
	protected static final int cellSize = 8;
	protected static final int border = 2;
	
	public Color effectColor = SWTResourceManager.getColor(SWT.COLOR_GREEN);
	public Color castColor = SWTResourceManager.getColor(SWT.COLOR_BLUE);
	public Color falseColor = SWTResourceManager.getColor(SWT.COLOR_BLACK);
	public Color centerColor = SWTResourceManager.getColor(SWT.COLOR_RED);
	
	private void addMaskButton(LObjectButton<Mask> button, Composite mask, Color trueColor) {
		button.setShellFactory(new LShellFactory<Mask>() {
			@Override
			public LObjectShell<Mask> createShell(Shell parent) {
				MaskShell shell = new MaskShell(parent);
				shell.trueColor = trueColor;
				shell.falseColor = falseColor;
				shell.centerColor = centerColor;
				return shell;
			}
		});
		button.addModifyListener(new LControlListener<Mask>() {
			@Override
			public void onModify(LControlEvent<Mask> event) {
				mask.redraw();
			}
		});
		mask.addPaintListener(new PaintListener() {
			@Override
			public void paintControl(PaintEvent e) {
				Mask m = button.getValue();
				if (m == null) return;
				boolean[][] middle = m.grid[m.centerH - 1];
				e.gc.drawRectangle(new Rectangle(0, 0, 
						middle.length * cellSize, 
						middle[0].length * cellSize));
				for (int i = 0; i < middle.length; i++) {
					for (int j = 0; j < middle[i].length; j++) {
						e.gc.setBackground(middle[i][j] ? trueColor : falseColor);
						e.gc.fillRectangle(border + i * cellSize, border + j * cellSize,
									cellSize - border, cellSize - border);
						e.gc.drawRectangle(i * cellSize, j * cellSize,
									cellSize, cellSize);
					}
				}
				e.gc.setForeground(centerColor);
				e.gc.drawRectangle(border + (m.centerX - 1) * cellSize, 
						border + (m.centerY - 1)* cellSize,
						cellSize - border - 2, 
						cellSize - border - 2);
			}
		});
	}

}
