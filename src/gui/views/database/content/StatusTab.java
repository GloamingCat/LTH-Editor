package gui.views.database.content;

import gui.Tooltip;
import gui.Vocab;
import gui.shell.database.RuleDialog;
import gui.shell.database.TransformationDialog;
import gui.views.database.DatabaseTab;
import gui.views.database.subcontent.AttributeList;
import gui.views.database.subcontent.BonusList;
import gui.widgets.IDList;
import gui.widgets.IconButton;
import gui.widgets.LuaButton;
import gui.widgets.SimpleEditableList;
import lui.base.LFlags;
import lui.base.LPrefs;
import lui.container.LContainer;
import lui.container.LFrame;
import lui.container.LImage;
import lui.container.LPanel;
import lui.dialog.LObjectDialog;
import lui.dialog.LWindow;
import lui.dialog.LWindowFactory;
import lui.widget.LCheckBox;
import lui.widget.LLabel;
import lui.widget.LSpinner;
import lui.widget.LText;

import data.Status;
import data.subcontent.Rule;
import data.subcontent.Transformation;
import gson.GObjectTreeSerializer;
import project.Project;

public class StatusTab extends DatabaseTab<Status> {

	private final IDList lstCancel;
	
	/**
	 * @wbp.parser.constructor
	 * @wbp.eval.method.parameter parent new lwt.dialog.LShell(800, 600)
	 */
	public StatusTab(LContainer parent) {
		super(parent);

		// Icon

		LLabel lblIcon = new LLabel(grpGeneral, Vocab.instance.ICON, Tooltip.instance.ICON);
		LPanel compositeIcon = new LPanel(grpGeneral);
		compositeIcon.setGridLayout(2);
		compositeIcon.getCellData().setExpand(true, false);
		LImage imgIcon = new LImage(compositeIcon);
		imgIcon.setImage("/javax/swing/plaf/basic/icons/image-delayed.png");
		imgIcon.getCellData().setExpand(true, true);
		imgIcon.getCellData().setMinimumSize(0, 48);
		imgIcon.setAlignment(LFlags.LEFT | LFlags.TOP);
		IconButton btnSelectIcon = new IconButton(compositeIcon, true);
		btnSelectIcon.setImageWidget(imgIcon);
		btnSelectIcon.addMenu(lblIcon);
		btnSelectIcon.addMenu(imgIcon);
		addControl(btnSelectIcon, "icon");

		// Cancel

		LLabel lblCancel = new LLabel(grpGeneral, LFlags.TOP, Vocab.instance.STATUSCANCEL,
				Tooltip.instance.STATUSCANCEL);
		lstCancel = new IDList(grpGeneral, Vocab.instance.STATUSSHELL);
		lstCancel.getCellData().setExpand(true, true);
		lstCancel.getCellData().setMinimumSize(0, 70);
		lstCancel.addMenu(lblCancel);
		addChild(lstCancel, "cancel");

		// Behavior Script

		LLabel lblBehavior = new LLabel(grpGeneral, LFlags.TOP, Vocab.instance.BEHAVIOR,
				Tooltip.instance.BEHAVIOR);
		SimpleEditableList<Rule> lstRules = new SimpleEditableList<>(grpGeneral);
		lstRules.getCellData().setExpand(true, true);
		lstRules.getCellData().setMinimumSize(0, 70);
		lstRules.type = Rule.class;
		lstRules.setIncludeID(false);
		lstRules.setShellFactory(new LWindowFactory<>() {
			@Override
			public LObjectDialog<Rule> createWindow(LWindow parent) {
				return new RuleDialog(parent);
			}
		});
		lstRules.addMenu(lblBehavior);
		addChild(lstRules, "behavior");

		// Script

		LLabel lblScript = new LLabel(grpGeneral, Vocab.instance.SCRIPT, Tooltip.instance.SCRIPT);
		LPanel compositeScript = new LPanel(grpGeneral);
		compositeScript.setGridLayout(2);
		compositeScript.getCellData().setExpand(true, false);
		LText txtScript = new LText(compositeScript, true);
		txtScript.getCellData().setExpand(true, false);
		txtScript.getCellData().setAlignment(LFlags.MIDDLE);
		LuaButton btnSelectScript = new LuaButton(compositeScript, Vocab.instance.STATUSSCRIPTSHELL, true);
		btnSelectScript.setPathWidget(txtScript);
		btnSelectScript.addMenu(lblScript);
		btnSelectScript.addMenu(txtScript);
		addControl(btnSelectScript, "script");

		// Visibility

		LLabel lblPriority = new LLabel(grpGeneral, Vocab.instance.PRIORITY, Tooltip.instance.PRIORITY);
		LPanel compositeVisible = new LPanel(grpGeneral);
		compositeVisible.setGridLayout(2);
		compositeVisible.getCellData().setExpand(true, false);
		
		LSpinner spnPriority = new LSpinner(compositeVisible);
		spnPriority.getCellData().setExpand(true, false);
		spnPriority.setMinimum(0);
		spnPriority.setMaximum(200);
		spnPriority.addMenu(lblPriority);
		addControl(spnPriority, "priority");

		LCheckBox btnVisible = new LCheckBox(compositeVisible);
		btnVisible.setText(Vocab.instance.VISIBLE);
		btnVisible.setHoverText(Tooltip.instance.VISIBLE);
		btnVisible.getCellData().setAlignment(LFlags.MIDDLE);
		addControl(btnVisible, "visible");
		
		// Other properties
		
		LPanel check = new LPanel(grpGeneral);
		check.setSequentialLayout(true);
		check.setEqualCells(true);
		check.getCellData().setSpread(2, 1);
		check.getCellData().setExpand(false, false);
		check.getCellData().setAlignment(LFlags.LEFT);

		LCheckBox btnKO = new LCheckBox(check);
		btnKO.setText(Vocab.instance.KOLIKE);
		btnKO.setHoverText(Tooltip.instance.KOLIKE);
		addControl(btnKO, "ko");

		LCheckBox btnDeactivate = new LCheckBox(check);
		btnDeactivate.setText(Vocab.instance.DEACTIVATE);
		btnDeactivate.setHoverText(Tooltip.instance.DEACTIVATE);
		addControl(btnDeactivate, "deactivate");

		LCheckBox btnCumulative = new LCheckBox(check);
		btnCumulative.setText(Vocab.instance.CUMULATIVE);
		btnCumulative.setHoverText(Tooltip.instance.CUMULATIVE);
		addControl(btnCumulative, "cumulative");

		// Durability

		LFrame grpDurability = new LFrame(left, Vocab.instance.DURABILITY);
		grpDurability.setGridLayout(3);
		grpDurability.setHoverText(Tooltip.instance.DURABILITY);
		grpDurability.getCellData().setExpand(true, false);
		grpDurability.getCellData().setMinimumSize(200, 0);

		LLabel lblTurns = new LLabel(grpDurability, Vocab.instance.TURNS, Tooltip.instance.TURNS);
		LSpinner spnTurns = new LSpinner(grpDurability);
		spnTurns.getCellData().setExpand(true, false);
		spnTurns.setMaximum(999999);
		spnTurns.setMinimum(-1);
		spnTurns.addMenu(lblTurns);
		addControl(spnTurns, "duration");

		LCheckBox btnBattleOnly = new LCheckBox(grpDurability);
		btnBattleOnly.setText(Vocab.instance.BATTLEONLY);
		btnBattleOnly.setHoverText(Tooltip.instance.BATTLEONLY);
		btnBattleOnly.getCellData().setAlignment(LFlags.MIDDLE);
		addControl(btnBattleOnly, "battleOnly");

		LPanel checkDurability = new LPanel(grpDurability);
		checkDurability.setSequentialLayout(true);
		checkDurability.setEqualCells(true);
		checkDurability.getCellData().setSpread(2, 1);
		checkDurability.getCellData().setExpand(false, false);
		checkDurability.getCellData().setAlignment(LFlags.LEFT);

		LCheckBox btnRemoveOnKO = new LCheckBox(checkDurability);
		btnRemoveOnKO.setText(Vocab.instance.REMOVEONKO);
		btnRemoveOnKO.setHoverText(Tooltip.instance.REMOVEONKO);
		addControl(btnRemoveOnKO, "removeOnKO");

		LCheckBox btnRemoveOnDamage = new LCheckBox(checkDurability);
		btnRemoveOnDamage.setText(Vocab.instance.REMOVEONDAMAGE);
		btnRemoveOnDamage.setHoverText(Tooltip.instance.REMOVEONDAMAGE);
		addControl(btnRemoveOnDamage, "removeOnDamage");

		// Graphics

		LFrame grpGraphics = new LFrame(left, Vocab.instance.GRAPHICS);
		grpGraphics.setGridLayout(2);
		grpGraphics.setHoverText(Tooltip.instance.GRAPHICS);
		grpGraphics.getCellData().setExpand(true, true);

		LLabel lblAnim = new LLabel(grpGraphics, Vocab.instance.CHARANIM, Tooltip.instance.CHARANIM);
		LText txtCharAnim = new LText(grpGraphics);
		txtCharAnim.getCellData().setExpand(true, false);
		txtCharAnim.addMenu(lblAnim);
		addControl(txtCharAnim, "charAnim");

		LLabel lblTransform = new LLabel(grpGraphics, LFlags.TOP,
				Vocab.instance.TRANSFORMATIONS,
				Tooltip.instance.TRANSFORMATIONS);
		SimpleEditableList<Transformation> lstTransformations = new SimpleEditableList<>(grpGraphics);
		lstTransformations.type = Transformation.class;
		lstTransformations.getCellData().setExpand(true, true);
		lstTransformations.setShellFactory(new LWindowFactory<>() {
			@Override
			public LObjectDialog<Transformation> createWindow(LWindow parent) {
				return new TransformationDialog(parent);
			}
		});
		lstTransformations.setIncludeID(false);
		lstTransformations.addMenu(lblTransform);
		addChild(lstTransformations, "transformations");
		
		// Drain

		LFrame grpDrain = new LFrame(right, Vocab.instance.DRAIN);
		grpDrain.setGridLayout(3);
		grpDrain.setHoverText(Tooltip.instance.DRAIN);
		grpDrain.getCellData().setExpand(true, false);
		grpDrain.getCellData().setMinimumSize(200, 0);

		LLabel lblDrainAtt = new LLabel(grpDrain, Vocab.instance.DRAINATT, Tooltip.instance.DRAINATT);
		lblDrainAtt.getCellData().setMinimumSize(LPrefs.LABELWIDTH, LPrefs.WIDGETHEIGHT);
		LText txtDrainAtt = new LText(grpDrain);
		txtDrainAtt.getCellData().setSpread(2, 1);
		txtDrainAtt.getCellData().setExpand(true, false);
		txtDrainAtt.addMenu(lblDrainAtt);
		addControl(txtDrainAtt, "drainAtt");

		LLabel lblDrain = new LLabel(grpDrain, Vocab.instance.DRAINVALUE, Tooltip.instance.DRAINVALUE);
		LSpinner spnDrain = new LSpinner(grpDrain);
		spnDrain.getCellData().setExpand(true, false);
		spnDrain.setMaximum(999999);
		spnDrain.setMinimum(0);
		spnDrain.addMenu(lblDrain);
		addControl(spnDrain, "drainValue");

		LCheckBox btnPercentage = new LCheckBox(grpDrain);
		btnPercentage.setText(Vocab.instance.PERCENTAGE);
		btnPercentage.setToolTipText(Tooltip.instance.PERCENTAGE);
		addControl(btnPercentage, "percentage");
		
		// Effects

		LPanel effects = new LPanel(right);
		effects.setGridLayout(2);
		effects.setEqualCells(true, false);
		effects.getCellData().setExpand(true, true);
		
		LFrame grpAtt = new LFrame(effects, Vocab.instance.ATTRIBUTES);
		grpAtt.setFillLayout(true);
		grpAtt.setHoverText(Tooltip.instance.STATUSATT);
		grpAtt.getCellData().setExpand(true, true);
		AttributeList lstAttributes = new AttributeList(grpAtt);
		lstAttributes.addMenu(grpAtt);
		addChild(lstAttributes, "attributes");
		
		LFrame grpBonuses = new LFrame(effects, Vocab.instance.PROPERTIES);
		grpBonuses.setFillLayout(true);
		grpBonuses.setHoverText(Tooltip.instance.PROPERTIES);
		grpBonuses.getCellData().setExpand(true, true);
		BonusList lstBonus = new BonusList(grpBonuses, Vocab.instance.PROPERTYSHELL);
		lstBonus.addMenu(grpBonuses);
		addChild(lstBonus, "bonuses");

	}
	
	@Override
	public void onVisible() {
		lstCancel.dataTree = Project.current.status.getTree();
		super.onVisible();
	}
	
	@Override
	protected GObjectTreeSerializer getSerializer() {
		return Project.current.status;
	}

}
