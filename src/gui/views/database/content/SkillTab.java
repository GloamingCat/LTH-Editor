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
import org.eclipse.swt.layout.GridData;
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
		compositeIcon.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, false, false, 1, 1));
		
		LImage imgIcon = new LImage(compositeIcon, SWT.NONE);
		imgIcon.setImage("/javax/swing/plaf/basic/icons/image-delayed.png");
		GridData gd_imgIcon = new GridData(SWT.FILL, SWT.FILL, true, true, 1, 1);
		gd_imgIcon.widthHint = 48;
		gd_imgIcon.heightHint = 48;
		imgIcon.setVerticalAlign(SWT.CENTER);
		imgIcon.setLayoutData(gd_imgIcon);
		
		IconButton btnGraphics = new IconButton(compositeIcon, true);
		btnGraphics.setImageWidget(imgIcon);
		addControl(btnGraphics, "icon");
		
		// Description
		
		new LLabel(grpGeneral, LLabel.TOP, Vocab.instance.DESCRIPTION);
		
		LTextBox txtDescription = new LTextBox(grpGeneral);
		GridData gd_txtDescription = new GridData(SWT.FILL, SWT.FILL, true, true, 1, 1);
		gd_txtDescription.minimumHeight = 60;
		gd_txtDescription.heightHint = 60;
		txtDescription.setLayoutData(gd_txtDescription);
		addControl(txtDescription, "description");
		
		// Script
		
		new LLabel(grpGeneral, Vocab.instance.SCRIPT);
		
		LPanel script = new LPanel(grpGeneral, 2, false);
		script.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, false, false, 1, 1));
		
		LText txtScript = new LText(script, true);		
		LuaButton btnScript = new LuaButton(script, true);
		btnScript.setPathWidget(txtScript);
		addControl(btnScript, "script");
		
		// Restrictions
		
		LFrame grpRestrictions = new LFrame(left, Vocab.instance.RESTRICTIONS, 2, false);
		grpRestrictions.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, false, 1, 1));
		
		new LLabel(grpRestrictions, Vocab.instance.COSTS);
		
		TagList lstCosts = new TagList(grpRestrictions);
		GridData gd_lstCosts = new GridData(SWT.FILL, SWT.TOP, true, false, 1, 1);
		gd_lstCosts.minimumHeight = 60;
		gd_lstCosts.heightHint = 60;
		lstCosts.setLayoutData(gd_lstCosts);
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
		grpEffects.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true, 1, 1));
		
		SkillEffectList lstEffects = new SkillEffectList(grpEffects);
		lstEffects.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true, 2, 1));
		addChild(lstEffects, "effects");
		
		new LLabel(grpEffects, Vocab.instance.EFFECTCONDITION);
		
		LText txtEffectCondition = new LText(grpEffects);
		addControl(txtEffectCondition, "effectCondition");
		
		// Target Selection
		
		LFrame grpTarget = new LFrame(left, Vocab.instance.TARGET, 2, false);
		grpTarget.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, false, 1, 1));
		
		new LLabel(grpTarget, Vocab.instance.TYPE);
		
		LCombo cmbType = new LCombo(grpTarget, true);
		cmbType.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1));
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
		check.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 2, 1));
		
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
		grpAnimations.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, false, 1, 1));
		
		LViewFolder tabAnim = new LViewFolder(grpAnimations, false);
		tabAnim.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1));
		
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
		btnMirror.setLayoutData(new GridData(SWT.FILL, SWT.FILL, false, false, 3, 1));
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
		
		LCheckBox btnStep = new LCheckBox(userAnim);
		btnStep.setLayoutData(new GridData(SWT.FILL, SWT.FILL, false, false, 2, 1));
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
		
		LCheckBox btnDamageAnim = new LCheckBox(animOptions);
		btnDamageAnim.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 2, 1));
		btnDamageAnim.setText(Vocab.instance.DAMAGEANIM);
		addControl(btnDamageAnim, "damageAnim");
		
		// Elements
		
		LFrame grpElements = new LFrame(right, Vocab.instance.ELEMENTS, 2, false);
		grpElements.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true, 1, 1));
		
		lstElements = new PropertyList(grpElements);
		lstElements.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true, 1, 1));
		addChild(lstElements, "elements");
		
		LCheckBox btnUserElements = new LCheckBox(grpElements);
		btnUserElements.setText(Vocab.instance.USERELEMENTS);
		btnUserElements.setLayoutData(new GridData(SWT.FILL, SWT.TOP, false, false, 1, 1));
		addControl(btnUserElements, "userElements");
		
		// Range
		
		LPanel range = new LPanel(right, 2, false);
		range.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true, 1, 1));
		
		LFrame grpEffect = new LFrame(range, Vocab.instance.EFFECTMASK, 2, false);
		grpEffect.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true, 1, 1));

		LObjectButton<Mask> btnEffectMask = new LObjectButton<Mask>(grpEffect);
		btnEffectMask.setLayoutData(new GridData(SWT.LEFT, SWT.TOP, false, true, 1, 1));
		addControl(btnEffectMask, "effectMask");
		
		LPanel effectMask = new LPanel(grpEffect);
		effectMask.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true, 1, 1));
		addMaskButton(btnEffectMask, effectMask, effectColor);
		
		LCheckBox btnRotate = new LCheckBox(grpEffect);
		btnRotate.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, false, false, 2, 1));
		btnRotate.setText(Vocab.instance.ROTATE);
		addControl(btnRotate, "rotateEffect");
		
		LFrame grpCast = new LFrame(range, Vocab.instance.CASTMASK, 2, false);
		grpCast.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true, 1, 1));
		
		LObjectButton<Mask> btnCastMask = new LObjectButton<Mask>(grpCast);
		btnCastMask.setLayoutData(new GridData(SWT.LEFT, SWT.TOP, false, true, 1, 1));
		addControl(btnCastMask, "castMask");
		
		LPanel castMask = new LPanel(grpCast);
		castMask.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true, 1, 1));
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
