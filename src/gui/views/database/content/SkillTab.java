package gui.views.database.content;

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
import lui.base.LFlags;
import lui.container.LCanvas;
import lui.container.LContainer;
import lui.container.LFrame;
import lui.container.LImage;
import lui.container.LPanel;
import lui.graphics.LColor;
import lui.graphics.LPainter;
import lui.dialog.LObjectDialog;
import lui.dialog.LWindow;
import lui.dialog.LWindowFactory;
import lui.widget.LCheckBox;
import lui.widget.LCombo;
import lui.widget.LLabel;
import lui.widget.LObjectButton;
import lui.widget.LTextBox;
import lui.widget.LText;

import project.Project;

import java.lang.reflect.Type;

import data.Skill;
import data.Skill.Mask;
import gson.GObjectTreeSerializer;

public class SkillTab extends DatabaseTab<Skill> {

	private PropertyList lstElements;
	
	/**
	 * @wbp.parser.constructor
	 * @wbp.eval.method.parameter parent new lwt.dialog.LShell(800, 600)
	 */
	public SkillTab(LContainer parent) {
		super(parent);
	}

	@Override
	protected void createContent() {
		// Icon
		
		LLabel lblIcon = new LLabel(grpGeneral, Vocab.instance.ICON, Tooltip.instance.ICON);
		LPanel compositeIcon = new LPanel(grpGeneral);
		compositeIcon.setGridLayout(2);
		compositeIcon.getCellData().setExpand(true, false);
		LImage imgIcon = new LImage(compositeIcon);
		imgIcon.getCellData().setExpand(true, true);
		imgIcon.getCellData().setMinimumSize(0, 48);
		imgIcon.setAlignment(LFlags.LEFT | LFlags.TOP);
		
		IconButton btnGraphics = new IconButton(compositeIcon, true);
		btnGraphics.setImageWidget(imgIcon);
		btnGraphics.addMenu(lblIcon);
		btnGraphics.addMenu(imgIcon);
		addControl(btnGraphics, "icon");
		
		// Description
		
		LLabel lblDesc = new LLabel(grpGeneral, LFlags.TOP, Vocab.instance.DESCRIPTION,
				Tooltip.instance.DESCRIPTION);
		LTextBox txtDescription = new LTextBox(grpGeneral);
		txtDescription.getCellData().setExpand(true, true);
		txtDescription.getCellData().setMinimumSize(0, 60);
		txtDescription.addMenu(lblDesc);
		addControl(txtDescription, "description");
		
		// Script
		
		LLabel lblScript = new LLabel(grpGeneral, Vocab.instance.SCRIPT, Tooltip.instance.SCRIPT);
		LPanel script = new LPanel(grpGeneral);
		script.setGridLayout(2);
		script.getCellData().setExpand(true, false);
		script.getCellData().setAlignment(LFlags.MIDDLE);

		LText txtScript = new LText(script, true);
		txtScript.getCellData().setExpand(true, false);
		txtScript.getCellData().setAlignment(LFlags.MIDDLE);
		LuaButton btnScript = new LuaButton(script, Vocab.instance.SKILLSCRIPTSHELL, true);
		btnScript.setPathWidget(txtScript);
		btnScript.addMenu(lblScript);
		btnScript.addMenu(txtScript);
		addControl(btnScript, "script");
		
		// Restrictions
		
		LFrame grpRestrictions = new LFrame(left, Vocab.instance.RESTRICTIONS);
		grpRestrictions.setGridLayout(2);
		grpRestrictions.setHoverText(Tooltip.instance.RESTRICTIONS);
		grpRestrictions.getCellData().setExpand(true, true);
		
		LLabel lblCosts = new LLabel(grpRestrictions, Vocab.instance.COSTS, Tooltip.instance.COSTS);
		lblCosts.getCellData().setMinimumSize(LABELWIDTH, 0);
		TagList lstCosts = new TagList(grpRestrictions, Vocab.instance.COST);
		lstCosts.getCellData().setExpand(true, true);
		lstCosts.getCellData().setMinimumSize(0, 60);
		lstCosts.addMenu(lblCosts);
		addChild(lstCosts, "costs");
		
		LLabel lblContext = new LLabel(grpRestrictions, Vocab.instance.CONTEXT, Tooltip.instance.CONTEXT);
		LCombo cmbContext = new LCombo(grpRestrictions, true);
		cmbContext.getCellData().setExpand(true, false);
		cmbContext.setIncludeID(false);
		cmbContext.setOptional(false);
		cmbContext.setItems(new String[] {
				Vocab.instance.ALWAYS,
				Vocab.instance.BATTLEONLY,
				Vocab.instance.FIELDONLY});
		cmbContext.addMenu(lblContext);
		cmbContext.addMenu(cmbContext);
		addControl(cmbContext, "restriction");

		LLabel lblUseCond = new LLabel(grpRestrictions, Vocab.instance.USECONDITION, Tooltip.instance.USECONDITION);
		LText txtCondition = new LText(grpRestrictions);
		txtCondition.getCellData().setExpand(true, false);
		txtCondition.addMenu(lblUseCond);
		addControl(txtCondition, "condition");

		// Effects

		LFrame grpEffects = new LFrame(left, Vocab.instance.EFFECTS);
		grpEffects.setGridLayout(2);
		grpEffects.setHoverText(Tooltip.instance.EFFECTS);
		grpEffects.getCellData().setExpand(true, true);
		SkillEffectList lstEffects = new SkillEffectList(grpEffects);
		lstEffects.getCellData().setExpand(true, true);
		lstEffects.getCellData().setMinimumSize(0, 60);
		lstEffects.getCellData().setSpread(2, 1);
		lstEffects.addMenu(grpEffects);
		addChild(lstEffects, "effects");

		LLabel lblEffCond = new LLabel(grpEffects, Vocab.instance.EFFECTCONDITION, Tooltip.instance.EFFECTCONDITION);
		lblEffCond.getCellData().setMinimumSize(LABELWIDTH, 0);
		LText txtEffectCondition = new LText(grpEffects);
		txtEffectCondition.addMenu(lblEffCond);
		txtEffectCondition.getCellData().setExpand(true, false);
		addControl(txtEffectCondition, "effectCondition");
		
		// Target Selection
		
		LFrame grpTarget = new LFrame(left, Vocab.instance.TARGET);
		grpTarget.setGridLayout(2);
		grpTarget.setHoverText(Tooltip.instance.TARGET);
		grpTarget.getCellData().setExpand(true, false);
		
		LLabel lblType = new LLabel(grpTarget, Vocab.instance.TYPE, Tooltip.instance.SKILLTYPE);
		lblType.getCellData().setMinimumSize(LABELWIDTH, 0);
		LCombo cmbType = new LCombo(grpTarget, true);
		cmbType.getCellData().setExpand(true, false);
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
		cmbSelection.getCellData().setExpand(true, false);
		cmbSelection.setIncludeID(false);
		cmbSelection.setOptional(false);
		cmbSelection.setItems(new String[] {
				Vocab.instance.ANYTILE,
				Vocab.instance.EFFECTONLY,
				Vocab.instance.RANGEONLY,
				Vocab.instance.EFFECTRANGE});
		cmbSelection.addMenu(lblSelection);
		addControl(cmbSelection, "selection");
		
		LPanel check = new LPanel(grpTarget);
		check.setSequentialLayout(true);
		check.getCellData().setSpread(2, 1);
		check.getCellData().setAlignment(LFlags.LEFT);
		
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
		
		LFrame grpAnimations = new LFrame(right, Vocab.instance.ANIMATIONS);
		grpAnimations.setFillLayout(true);
		grpAnimations.setHoverText(Tooltip.instance.ANIMATIONS);
		grpAnimations.getCellData().setExpand(true, false);
		AnimInfoEditor animEditor = new AnimInfoEditor(grpAnimations);
		animEditor.addMenu(grpAnimations);
		addChild(animEditor, "animInfo");
		
		// Elements
		
		LFrame grpElements = new LFrame(right, Vocab.instance.ELEMENTS);
		grpElements.setGridLayout(2);
		grpElements.setHoverText(Tooltip.instance.SKILLELEMENTS);
		grpElements.getCellData().setExpand(true, true);
		lstElements = new PropertyList(grpElements, Vocab.instance.SKILLELEMENTSHELL);
		lstElements.getCellData().setExpand(true, true);
		lstElements.addMenu(grpElements);
		addChild(lstElements, "elements");
		
		LCheckBox btnUserElements = new LCheckBox(grpElements);
		btnUserElements.getCellData().setAlignment(LFlags.TOP);
		btnUserElements.setText(Vocab.instance.USERELEMENTS);
		addControl(btnUserElements, "userElements");
		
		// Range
		
		LPanel range = new LPanel(right);
		range.setGridLayout(2);
		range.setEqualCells(true, false);
		range.getCellData().setExpand(true, true);
	
		LFrame grpEffect = new LFrame(range, Vocab.instance.EFFECTMASK);
		grpEffect.setGridLayout(2);
		grpEffect.setHoverText(Tooltip.instance.EFFECTMASK);
		grpEffect.getCellData().setExpand(true, true);
		MaskButton btnEffectMask = new MaskButton(grpEffect);
		btnEffectMask.getCellData().setAlignment(LFlags.LEFT | LFlags.TOP);
		btnEffectMask.getCellData().setExpand(true, false);
		btnEffectMask.addMenu(grpEffect);
		addControl(btnEffectMask, "effectMask");
		
		LCanvas effectMask = new LCanvas(grpEffect);
		effectMask.getCellData().setExpand(true, true);
		addMaskButton(btnEffectMask, effectMask, effectColor);
		
		LCheckBox btnRotate = new LCheckBox(grpEffect);
		btnRotate.getCellData().setSpread(2, 1);
		btnRotate.setText(Vocab.instance.ROTATE);
		btnRotate.addMenu();
		addControl(btnRotate, "rotateEffect");
		
		LFrame grpCast = new LFrame(range, Vocab.instance.CASTMASK);
		grpCast.setGridLayout(2);
		grpCast.setHoverText(Tooltip.instance.CASTMASK);
		grpCast.getCellData().setExpand(true, true);
		MaskButton btnCastMask = new MaskButton(grpCast);
		btnCastMask.getCellData().setAlignment(LFlags.LEFT | LFlags.TOP);
		btnCastMask.getCellData().setExpand(true, false);
		btnCastMask.addMenu(grpCast);
		addControl(btnCastMask, "castMask");
		
		LCanvas castMask = new LCanvas(grpCast);
		castMask.getCellData().setExpand(true, true);
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
	
	public static final LColor effectColor = new LColor(0, 255, 0);
	public static final LColor castColor = new LColor(0, 0, 255);
	public static final LColor falseColor = new LColor(0, 0, 0);
	public static final LColor centerColor = new LColor(255, 0, 0);
	
	private void addMaskButton(LObjectButton<Mask> button, LCanvas mask, LColor trueColor) {
		button.setShellFactory(new LWindowFactory<>() {
			@Override
			public LObjectDialog<Mask> createWindow(LWindow parent) {
				MaskShell shell = new MaskShell(parent);
				shell.trueColor = trueColor;
				shell.falseColor = falseColor;
				shell.centerColor = centerColor;
				return shell;
			}
		});
		button.addModifyListener(event -> mask.redraw());
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
		button.addMenu(mask);
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
