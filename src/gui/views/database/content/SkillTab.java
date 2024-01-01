package gui.views.database.content;

import gson.project.GObjectTreeSerializer;
import gui.Tooltip;
import gui.Vocab;
import gui.shell.database.MaskShell;
import gui.views.database.DatabaseTab;
import gui.views.database.subcontent.AnimInfoEditor;
import gui.views.database.subcontent.PropertyList;
import gui.views.database.subcontent.SkillEffectList;
import gui.views.database.subcontent.TagList;
import gui.widgets.IconButton;
import gui.widgets.LuaButton;
import lwt.LFlags;
import lwt.container.LCanvas;
import lwt.container.LContainer;
import lwt.container.LFrame;
import lwt.container.LImage;
import lwt.container.LPanel;
import lwt.graphics.LColor;
import lwt.graphics.LPainter;
import lwt.dialog.LObjectShell;
import lwt.dialog.LShell;
import lwt.dialog.LShellFactory;
import lwt.event.LControlEvent;
import lwt.event.listener.LControlListener;
import lwt.widget.LCheckBox;
import lwt.widget.LCombo;
import lwt.widget.LLabel;
import lwt.widget.LObjectButton;
import lwt.widget.LTextBox;
import lwt.widget.LText;

import project.Project;

import java.lang.reflect.Type;

import data.Skill;
import data.Skill.Mask;

public class SkillTab extends DatabaseTab<Skill> {


	private PropertyList lstElements;
	
	/**
	 * @wbp.parser.constructor
	 * @wbp.eval.method.parameter parent new lwt.dialog.LShell(800, 600)
	 */
	public SkillTab(LContainer parent) {
		super(parent);

		// Icon
		
		LLabel lblIcon = new LLabel(grpGeneral, Vocab.instance.ICON, Tooltip.instance.ICON);
		LPanel compositeIcon = new LPanel(grpGeneral, 2, false);
		compositeIcon.setAlignment(LFlags.CENTER);
		LImage imgIcon = new LImage(compositeIcon);
		imgIcon.setImage("/javax/swing/plaf/basic/icons/image-delayed.png");
		imgIcon.setExpand(true, true);
		imgIcon.setMinimumHeight(48);
		imgIcon.setAlignment(LFlags.CENTER);
		
		IconButton btnGraphics = new IconButton(compositeIcon, true);
		btnGraphics.setImageWidget(imgIcon);
		btnGraphics.addMenu(lblIcon);
		btnGraphics.addMenu(imgIcon);
		addControl(btnGraphics, "icon");
		
		// Description
		
		LLabel lblDesc = new LLabel(grpGeneral, LFlags.TOP, Vocab.instance.DESCRIPTION,
				Tooltip.instance.DESCRIPTION);
		LTextBox txtDescription = new LTextBox(grpGeneral, 1, 1);
		txtDescription.setMinimumHeight(60);
		txtDescription.addMenu(lblDesc);
		addControl(txtDescription, "description");
		
		// Script
		
		LLabel lblScript = new LLabel(grpGeneral, Vocab.instance.SCRIPT, Tooltip.instance.SCRIPT);
		LPanel script = new LPanel(grpGeneral, 2, false);
		script.setAlignment(LFlags.CENTER);
		
		LText txtScript = new LText(script, true);		
		LuaButton btnScript = new LuaButton(script, true);
		btnScript.setPathWidget(txtScript);
		btnScript.addMenu(lblScript);
		btnScript.addMenu(txtScript);
		addControl(btnScript, "script");
		
		// Restrictions
		
		LFrame grpRestrictions = new LFrame(left, Vocab.instance.RESTRICTIONS, 2, false);
		grpRestrictions.setHoverText(Tooltip.instance.RESTRICTIONS);
		grpRestrictions.setExpand(true, false);
		
		LLabel lblCosts = new LLabel(grpRestrictions, Vocab.instance.COSTS, Tooltip.instance.COSTS);
		TagList lstCosts = new TagList(grpRestrictions);
		lstCosts.setExpand(true, false);
		lstCosts.setMinimumHeight(60);
		lstCosts.addMenu(lblCosts);
		addChild(lstCosts, "costs");
		
		LLabel lblContext = new LLabel(grpRestrictions, Vocab.instance.CONTEXT, Tooltip.instance.CONTEXT);
		LCombo cmbRestrictions = new LCombo(grpRestrictions, true);
		cmbRestrictions.setIncludeID(false);
		cmbRestrictions.setOptional(false);
		cmbRestrictions.setItems(new String[] {
				Vocab.instance.ALWAYS,
				Vocab.instance.BATTLEONLY,
				Vocab.instance.FIELDONLY});
		cmbRestrictions.addMenu(lblContext);
		cmbRestrictions.addMenu(cmbRestrictions);
		addControl(cmbRestrictions, "restriction");
		
		LLabel lblUseCond = new LLabel(grpRestrictions, Vocab.instance.USECONDITION, Tooltip.instance.USECONDITION);
		LText txtCondition = new LText(grpRestrictions);
		txtCondition.addMenu(lblUseCond);
		addControl(txtCondition, "condition");
		
		// Effects
		
		LFrame grpEffects = new LFrame(left, Vocab.instance.EFFECTS, 2, false);
		grpEffects.setHoverText(Tooltip.instance.EFFECTS);
		grpEffects.setExpand(true, true);
		SkillEffectList lstEffects = new SkillEffectList(grpEffects);
		lstEffects.setExpand(true, true);
		lstEffects.setSpread(2, 1);
		lstEffects.addMenu(grpEffects);
		addChild(lstEffects, "effects");
		
		LLabel lblEffCond = new LLabel(grpEffects, Vocab.instance.EFFECTCONDITION, Tooltip.instance.EFFECTCONDITION);
		LText txtEffectCondition = new LText(grpEffects);
		txtEffectCondition.addMenu(lblEffCond);
		addControl(txtEffectCondition, "effectCondition");
		
		// Target Selection
		
		LFrame grpTarget = new LFrame(left, Vocab.instance.TARGET, 2, false);
		grpTarget.setHoverText(Tooltip.instance.TARGET);
		grpTarget.setExpand(true, false);
		
		LLabel lblType = new LLabel(grpTarget, Vocab.instance.TYPE, Tooltip.instance.SKILLTYPE);
		LCombo cmbType = new LCombo(grpTarget, true);
		cmbType.setIncludeID(false);
		cmbType.setOptional(false);
		cmbType.setItems(new String[] {	
			Vocab.instance.GENERAL,
			Vocab.instance.ATTACK,
			Vocab.instance.SUPPORT
		});
		cmbType.addMenu(lblType);
		addControl(cmbType, "type");
		
		LLabel lblSelection = new LLabel(grpTarget, Vocab.instance.TARGETSELECTION, Tooltip.instance.TARGETSELECTION);
		LCombo cmbSelection = new LCombo(grpTarget, true);
		cmbSelection.setIncludeID(false);
		cmbSelection.setOptional(false);
		cmbSelection.setItems(new String[] {
				Vocab.instance.ANYTILE,
				Vocab.instance.EFFECTONLY,
				Vocab.instance.RANGEONLY,
				Vocab.instance.EFFECTRANGE});
		cmbSelection.addMenu(lblSelection);
		addControl(cmbSelection, "selection");
		
		LPanel check = new LPanel(grpTarget, 3, true);
		check.setExpand(true, false);
		check.setSpread(2, 1);
		check.setAlignment(LFlags.CENTER);
		
		LCheckBox btnAllParties = new LCheckBox(check);
		btnAllParties.setText(Vocab.instance.ALLPARTIES);
		btnAllParties.setHoverText(Tooltip.instance.ALLPARTIES);
		addControl(btnAllParties, "allParties");
		
		LCheckBox btnAutopath = new LCheckBox(check);
		btnAutopath.setText(Vocab.instance.AUTOPATH);
		btnAutopath.setHoverText(Tooltip.instance.AUTOPATH);
		addControl(btnAutopath, "autoPath");
		
		LCheckBox btnFreeNavigation = new LCheckBox(check);
		btnFreeNavigation.setText(Vocab.instance.FREENAVIGATION);
		btnFreeNavigation.setHoverText(Tooltip.instance.FREENAVIGATION);
		addControl(btnFreeNavigation, "freeNavigation");

		// Animations
		
		LFrame grpAnimations = new LFrame(right, Vocab.instance.ANIMATIONS, 1);
		grpAnimations.setHoverText(Tooltip.instance.ANIMATIONS);
		grpAnimations.setExpand(true, false);
		AnimInfoEditor animEditor = new AnimInfoEditor(grpAnimations);
		animEditor.setExpand(true, false);
		animEditor.setAlignment(LFlags.CENTER);
		animEditor.addMenu(grpAnimations);
		addChild(animEditor, "animInfo");
		
		// Elements
		
		LFrame grpElements = new LFrame(right, Vocab.instance.ELEMENTS, 2, false);
		grpElements.setHoverText(Tooltip.instance.SKILLELEMENTS);
		grpElements.setExpand(true, true);
		lstElements = new PropertyList(grpElements);
		lstElements.setExpand(true, true);
		lstElements.addMenu(grpElements);
		addChild(lstElements, "elements");
		
		LCheckBox btnUserElements = new LCheckBox(grpElements, 1);
		btnUserElements.setAlignment(LFlags.TOP);
		btnUserElements.setText(Vocab.instance.USERELEMENTS);
		addControl(btnUserElements, "userElements");
		
		// Range
		
		LPanel range = new LPanel(right, 2, false);
		range.setExpand(true, true);
		
		LFrame grpEffect = new LFrame(range, Vocab.instance.EFFECTMASK, 2, false);
		grpEffect.setHoverText(Tooltip.instance.EFFECTMASK);
		grpEffect.setExpand(true, true);
		MaskButton btnEffectMask = new MaskButton(grpEffect);
		btnEffectMask.setAlignment(LFlags.LEFT | LFlags.TOP);
		btnEffectMask.setExpand(true, false);
		btnEffectMask.addMenu(grpEffect);
		addControl(btnEffectMask, "effectMask");
		
		LCanvas effectMask = new LCanvas(grpEffect);
		effectMask.setExpand(true, true);
		effectMask.setMenu(btnEffectMask.getMenu());
		addMaskButton(btnEffectMask, effectMask, effectColor);
		
		LCheckBox btnRotate = new LCheckBox(grpEffect, 2);
		btnRotate.setText(Vocab.instance.ROTATE);
		btnRotate.addMenu();
		addControl(btnRotate, "rotateEffect");
		
		LFrame grpCast = new LFrame(range, Vocab.instance.CASTMASK, 2, false);
		grpCast.setHoverText(Tooltip.instance.CASTMASK);
		grpCast.setExpand(true, true);
		MaskButton btnCastMask = new MaskButton(grpCast);
		btnCastMask.setAlignment(LFlags.LEFT | LFlags.TOP);
		btnCastMask.setExpand(true, false);
		btnCastMask.addMenu(grpCast);
		addControl(btnCastMask, "castMask");
		
		LCanvas castMask = new LCanvas(grpCast);
		castMask.setExpand(true, true);
		castMask.setMenu(btnCastMask.getMenu());
		addMaskButton(btnCastMask, castMask, castColor);	
		
	}
	
	@Override
	public void onVisible() {
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
	
	public LColor effectColor = new LColor(0, 255, 0);
	public LColor castColor = new LColor(0, 0, 255);
	public LColor falseColor = new LColor(0, 0, 0);
	public LColor centerColor = new LColor(255, 0, 0);
	
	private void addMaskButton(LObjectButton<Mask> button, LCanvas mask, LColor trueColor) {
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
		mask.addPainter(new LPainter() {
			@Override
			public void paint() {
				Mask m = button.getValue();
				if (m == null) return;
				boolean[][] middle = m.grid[m.centerH - 1];
				drawRect(0, 0, 
						middle.length * cellSize, 
						middle[0].length * cellSize);
				for (int i = 0; i < middle.length; i++) {
					for (int j = 0; j < middle[i].length; j++) {
						setFillColor(middle[i][j] ? trueColor : falseColor);
						fillRect(border + i * cellSize, border + j * cellSize,
									cellSize - border, cellSize - border);
						drawRect(i * cellSize, j * cellSize,
									cellSize, cellSize);
					}
				}
				setPaintColor(centerColor);
				drawRect(border + (m.centerX - 1) * cellSize, 
						border + (m.centerY - 1)* cellSize,
						cellSize - border - 2, 
						cellSize - border - 2);
			}
		});
	}
	
	private static class MaskButton extends LObjectButton<Mask> {
		public MaskButton(LContainer parent) {
			super(parent);
		}

		@Override
		protected Type getType() {
			return Mask.class;
		}
	}

}
