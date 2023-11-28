package gui.views.database.content;

import gson.project.GObjectTreeSerializer;
import gui.Vocab;
import gui.shell.database.MaskShell;
import gui.views.database.DatabaseTab;
import gui.views.database.subcontent.PropertyList;
import gui.views.database.subcontent.SkillEffectList;
import gui.views.database.subcontent.TagList;
import gui.widgets.IDButton;
import gui.widgets.IconButton;
import gui.widgets.LuaButton;
import lwt.LFlags;
import lwt.container.LContainer;
import lwt.container.LFrame;
import lwt.container.LPanel;
import lwt.container.LViewFolder;
import lwt.dialog.LObjectShell;
import lwt.dialog.LShell;
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
import org.eclipse.swt.widgets.Composite;
import org.eclipse.wb.swt.SWTResourceManager;

import project.Project;

import data.Skill;
import data.Skill.Mask;

public class SkillTab extends DatabaseTab<Skill> {

	private IDButton btnLoadAnim;
	private IDButton btnCastAnim;
	private IDButton btnIndAnim;
	private PropertyList lstElements;
	
	/**
	 * @wbp.parser.constructor
	 * @wbp.eval.method.parameter parent new lwt.dialog.LShell(800, 600)
	 */
	public SkillTab(LContainer parent) {
		super(parent);

		// Icon
		
		new LLabel(grpGeneral, Vocab.instance.ICON);
		LPanel compositeIcon = new LPanel(grpGeneral, 2, false);
		compositeIcon.setAlignment(LFlags.CENTER);
		
		LImage imgIcon = new LImage(compositeIcon);
		imgIcon.setImage("/javax/swing/plaf/basic/icons/image-delayed.png");
		imgIcon.setExpand(true, true);
		imgIcon.setMinimumHeight(48);
		imgIcon.setAlignment(LFlags.CENTER);
		
		IconButton btnGraphics = new IconButton(compositeIcon, true);
		btnGraphics.setImageWidget(imgIcon);
		addControl(btnGraphics, "icon");
		
		// Description
		
		new LLabel(grpGeneral, LFlags.TOP, Vocab.instance.DESCRIPTION);
		LTextBox txtDescription = new LTextBox(grpGeneral, 1, 1);
		txtDescription.setMinimumHeight(60);
		addControl(txtDescription, "description");
		
		// Script
		
		new LLabel(grpGeneral, Vocab.instance.SCRIPT);
		
		LPanel script = new LPanel(grpGeneral, 2, false);
		script.setAlignment(LFlags.CENTER);
		
		LText txtScript = new LText(script, true);		
		LuaButton btnScript = new LuaButton(script, true);
		btnScript.setPathWidget(txtScript);
		addControl(btnScript, "script");
		
		// Restrictions
		
		LFrame grpRestrictions = new LFrame(left, Vocab.instance.RESTRICTIONS, 2, false);
		grpRestrictions.setExpand(true, false);
		
		new LLabel(grpRestrictions, Vocab.instance.COSTS);
		
		TagList lstCosts = new TagList(grpRestrictions);
		lstCosts.setExpand(true, false);
		lstCosts.setMinimumHeight(60);
		addChild(lstCosts, "costs");
		
		new LLabel(grpRestrictions, Vocab.instance.CONTEXT);
		
		LCombo cmbRestrictions = new LCombo(grpRestrictions, true);
		cmbRestrictions.setIncludeID(false);
		cmbRestrictions.setOptional(false);
		cmbRestrictions.setItems(new String[] {
				Vocab.instance.ALWAYS,
				Vocab.instance.BATTLEONLY,
				Vocab.instance.FIELDONLY});
		addControl(cmbRestrictions, "restriction");
		
		new LLabel(grpRestrictions, Vocab.instance.USECONDITION);
		
		LText txtCondition = new LText(grpRestrictions);
		addControl(txtCondition, "condition");
		
		// Effects
		
		LFrame grpEffects = new LFrame(left, Vocab.instance.EFFECTS, 2, false);
		grpEffects.setExpand(true, true);
		
		SkillEffectList lstEffects = new SkillEffectList(grpEffects);
		lstEffects.setExpand(true, true);
		lstEffects.setSpread(2, 1);
		addChild(lstEffects, "effects");
		
		new LLabel(grpEffects, Vocab.instance.EFFECTCONDITION);
		
		LText txtEffectCondition = new LText(grpEffects);
		addControl(txtEffectCondition, "effectCondition");
		
		// Target Selection
		
		LFrame grpTarget = new LFrame(left, Vocab.instance.TARGET, 2, false);
		grpTarget.setExpand(true, false);
		
		new LLabel(grpTarget, Vocab.instance.TYPE);
		
		LCombo cmbType = new LCombo(grpTarget, true);
		cmbType.setIncludeID(false);
		cmbType.setOptional(false);
		cmbType.setItems(new String[] {	
			Vocab.instance.GENERAL,
			Vocab.instance.ATTACK,
			Vocab.instance.SUPPORT
		});
		addControl(cmbType, "type");
		
		new LLabel(grpTarget, Vocab.instance.TARGETSELECTION);
		
		LCombo cmbSelection = new LCombo(grpTarget);
		cmbSelection.setIncludeID(false);
		cmbSelection.setOptional(false);
		cmbSelection.setItems(new String[] {
				Vocab.instance.ANYTILE,
				Vocab.instance.EFFECTONLY,
				Vocab.instance.RANGEONLY,
				Vocab.instance.EFFECTRANGE});
		addControl(cmbSelection, "selection");
		
		LPanel check = new LPanel(grpTarget, 3, true);
		check.setExpand(true, false);
		check.setSpread(2, 1);
		check.setAlignment(LFlags.CENTER);
		
		LCheckBox btnAllParties = new LCheckBox(check);
		btnAllParties.setText(Vocab.instance.ALLPARTIES);
		addControl(btnAllParties, "allParties");
		
		LCheckBox btnAutopath = new LCheckBox(check);
		btnAutopath.setText(Vocab.instance.AUTOPATH);
		addControl(btnAutopath, "autoPath");
		
		LCheckBox btnFreeNavigation = new LCheckBox(check);
		btnFreeNavigation.setText(Vocab.instance.FREENAVIGATION);
		addControl(btnFreeNavigation, "freeNavigation");

		// Animations
		
		LFrame grpAnimations = new LFrame(right, Vocab.instance.ANIMATIONS, 1);
		grpAnimations.setExpand(true, true);
		
		LViewFolder tabAnim = new LViewFolder(grpAnimations, false);
		tabAnim.setExpand(true, false);
		tabAnim.setAlignment(LFlags.CENTER);
		
		// Battle Animations
		
		LPanel battleAnim = new LPanel(tabAnim, 3, false);
		tabAnim.addTab(Vocab.instance.BATTLE, battleAnim);
		
		new LLabel(battleAnim, Vocab.instance.LOAD);
		
		LText txtLoadAnim = new LText(battleAnim, true);
		btnLoadAnim = new IDButton(battleAnim, true);
		btnLoadAnim.setNameWidget(txtLoadAnim);
		addControl(btnLoadAnim, "loadAnimID");
		
		new LLabel(battleAnim, Vocab.instance.CAST);
		
		LText txtCastAnim = new LText(battleAnim, true);
		btnCastAnim = new IDButton(battleAnim, true);
		btnCastAnim.setNameWidget(txtCastAnim);
		addControl(btnCastAnim, "castAnimID");
		
		new LLabel(battleAnim, Vocab.instance.INDIVIDUAL);

		LText txtIndAnim = new LText(battleAnim, true);
		btnIndAnim = new IDButton(battleAnim, true);
		btnIndAnim.setNameWidget(txtIndAnim);
		addControl(btnIndAnim, "individualAnimID");
		
		LCheckBox btnMirror = new LCheckBox(battleAnim);
		btnMirror.setSpread(3, 1);
		btnMirror.setExpand(false, false);
		btnMirror.setText(Vocab.instance.MIRROR);
		addControl(btnMirror, "mirror");
		
		// User Animations
		
		LPanel userAnim = new LPanel(tabAnim, 2, false);
		tabAnim.addTab(Vocab.instance.USER, userAnim);
		
		new LLabel(userAnim, Vocab.instance.LOAD).setBounds(0, 0, 55, 15);
		LText txtUserLoadAnim = new LText(userAnim);
		addControl(txtUserLoadAnim, "userLoadAnim");
		
		new LLabel(userAnim, Vocab.instance.CAST).setBounds(0, 0, 55, 15);
		LText txtUserCastAnim = new LText(userAnim);
		addControl(txtUserCastAnim, "userCastAnim");
		
		LCheckBox btnStep = new LCheckBox(userAnim, 2);
		btnStep.setText(Vocab.instance.STEPONCAST);
		addControl(btnStep, "stepOnCast");
		
		// Animation Options
		
		LPanel animOptions = new LPanel(tabAnim, 4, false);
		tabAnim.addTab(Vocab.instance.OPTIONS, animOptions);
		
		new LLabel(animOptions, Vocab.instance.INTROTIME);
		LText txtIntroTime = new LText(animOptions);
		addControl(txtIntroTime, "introTime");
		
		new LLabel(animOptions, Vocab.instance.CASTTIME);
		LText txtCastTime = new LText(animOptions);
		addControl(txtCastTime, "castTime");
		
		new LLabel(animOptions, Vocab.instance.CENTERTIME);
		LText txtCenterTime = new LText(animOptions);
		addControl(txtCenterTime, "centerTime");
		
		new LLabel(animOptions, Vocab.instance.TARGETTIME);
		LText txtTargetTime = new LText(animOptions);
		addControl(txtTargetTime, "targetTime");
		
		new LLabel(animOptions, Vocab.instance.FINISHTIME);
		
		LText txtFinishTime = new LText(animOptions);
		addControl(txtFinishTime, "finishTime");
		
		LCheckBox btnDamageAnim = new LCheckBox(animOptions, 2);
		btnDamageAnim.setText(Vocab.instance.DAMAGEANIM);
		addControl(btnDamageAnim, "damageAnim");
		
		// Elements
		
		LFrame grpElements = new LFrame(right, Vocab.instance.ELEMENTS, 2, false);
		grpElements.setExpand(true, true);
		
		lstElements = new PropertyList(grpElements);
		lstElements.setExpand(true, true);
		addChild(lstElements, "elements");
		
		LCheckBox btnUserElements = new LCheckBox(grpElements, 1);
		btnUserElements.setAlignment(LFlags.TOP);
		btnUserElements.setText(Vocab.instance.USERELEMENTS);
		addControl(btnUserElements, "userElements");
		
		// Range
		
		LPanel range = new LPanel(right, 2, false);
		range.setExpand(true, true);
		
		LFrame grpEffect = new LFrame(range, Vocab.instance.EFFECTMASK, 2, false);
		grpEffect.setExpand(true, true);

		LObjectButton<Mask> btnEffectMask = new LObjectButton<Mask>(grpEffect);
		btnEffectMask.setAlignment(LFlags.LEFT | LFlags.TOP);
		btnEffectMask.setExpand(true, false);
		addControl(btnEffectMask, "effectMask");
		
		LPanel effectMask = new LPanel(grpEffect);
		effectMask.setExpand(true, true);
		addMaskButton(btnEffectMask, effectMask, effectColor);
		
		LCheckBox btnRotate = new LCheckBox(grpEffect, 2);
		btnRotate.setText(Vocab.instance.ROTATE);
		addControl(btnRotate, "rotateEffect");
		
		LFrame grpCast = new LFrame(range, Vocab.instance.CASTMASK, 2, false);
		grpCast.setExpand(true, true);
		
		LObjectButton<Mask> btnCastMask = new LObjectButton<Mask>(grpCast);
		btnCastMask.setAlignment(LFlags.LEFT | LFlags.TOP);
		btnCastMask.setExpand(true, false);
		addControl(btnCastMask, "castMask");
		
		LPanel castMask = new LPanel(grpCast);
		castMask.setExpand(true, true);
		addMaskButton(btnCastMask, castMask, castColor);	
		
	}
	
	@Override
	public void onVisible() {
		btnCastAnim.dataTree = Project.current.animations.getTree();
		btnIndAnim.dataTree = Project.current.animations.getTree();
		btnLoadAnim.dataTree = Project.current.animations.getTree();
		lstElements.dataTree = Project.current.elements.getList().toTree();
		super.onVisible();
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
			public LObjectShell<Mask> createShell(LShell parent) {
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
