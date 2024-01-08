package gui.views.database.content;

import gson.project.GObjectTreeSerializer;
import gui.Tooltip;
import gui.Vocab;
import gui.shell.database.RuleShell;
import gui.shell.database.TransformationShell;
import gui.views.database.DatabaseTab;
import gui.views.database.subcontent.AttributeList;
import gui.views.database.subcontent.BonusList;
import gui.widgets.IDList;
import gui.widgets.IconButton;
import gui.widgets.LuaButton;
import gui.widgets.SimpleEditableList;
import lwt.LFlags;
import lwt.container.LContainer;
import lwt.container.LFrame;
import lwt.container.LImage;
import lwt.container.LPanel;
import lwt.dialog.LObjectShell;
import lwt.dialog.LShell;
import lwt.dialog.LShellFactory;
import lwt.widget.LCheckBox;
import lwt.widget.LLabel;
import lwt.widget.LSpinner;
import lwt.widget.LText;

import data.Status;
import data.subcontent.Rule;
import data.subcontent.Transformation;
import project.Project;

public class StatusTab extends DatabaseTab<Status> {

	private IDList lstCancel;
	
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
		compositeIcon.setExpand(false, false);
		LImage imgIcon = new LImage(compositeIcon);
		imgIcon.setImage("/javax/swing/plaf/basic/icons/image-delayed.png");
		imgIcon.setAlignment(LFlags.CENTER);
		imgIcon.setExpand(true, true);
		imgIcon.setMinimumHeight(48);
		IconButton btnSelectIcon = new IconButton(compositeIcon, true);
		btnSelectIcon.setImageWidget(imgIcon);
		btnSelectIcon.addMenu(lblIcon);
		btnSelectIcon.addMenu(imgIcon);
		addControl(btnSelectIcon, "icon");

		// Cancel

		LLabel lblCancel = new LLabel(grpGeneral, LFlags.TOP, Vocab.instance.STATUSCANCEL,
				Tooltip.instance.STATUSCANCEL);
		lstCancel = new IDList(grpGeneral, Vocab.instance.STATUSSHELL);
		lstCancel.setExpand(true, true);
		lstCancel.setMinimumHeight(70);
		lstCancel.addMenu(lblCancel);
		addChild(lstCancel, "cancel");

		// Behavior Script

		LLabel lblBehavior = new LLabel(grpGeneral, LFlags.TOP, Vocab.instance.BEHAVIOR,
				Tooltip.instance.BEHAVIOR);
		SimpleEditableList<Rule> lstRules = new SimpleEditableList<Rule>(grpGeneral);
		lstRules.setExpand(true, true);
		lstRules.setMinimumHeight(70);
		lstRules.type = Rule.class;
		lstRules.setIncludeID(false);
		lstRules.setShellFactory(new LShellFactory<Rule>() {
			@Override
			public LObjectShell<Rule> createShell(LShell parent) {
				return new RuleShell(parent);
			}
		});
		lstRules.addMenu(lblBehavior);
		addChild(lstRules, "behavior");

		// Script

		LLabel lblScript = new LLabel(grpGeneral, Vocab.instance.SCRIPT, Tooltip.instance.SCRIPT);
		LPanel compositeScript = new LPanel(grpGeneral);
		compositeScript.setGridLayout(2);
		compositeScript.setExpand(false, false);
		compositeScript.setAlignment(LFlags.CENTER);
		LText txtScript = new LText(compositeScript, true);
		LuaButton btnSelectScript = new LuaButton(compositeScript, Vocab.instance.STATUSSCRIPTSHELL, true);
		btnSelectScript.setPathWidget(txtScript);
		btnSelectScript.addMenu(lblScript);
		btnSelectScript.addMenu(txtScript);
		addControl(btnSelectScript, "script");

		// Visibility

		LLabel lblPriority = new LLabel(grpGeneral, Vocab.instance.PRIORITY, Tooltip.instance.PRIORITY);
		LPanel compositeVisible = new LPanel(grpGeneral);
		compositeVisible.setGridLayout(2);
		compositeVisible.setExpand(false, false);
		compositeVisible.setAlignment(LFlags.CENTER);
		
		LSpinner spnPriority = new LSpinner(compositeVisible);
		spnPriority.setMinimum(0);
		spnPriority.setMaximum(200);
		spnPriority.addMenu(lblPriority);
		addControl(spnPriority, "priority");

		LCheckBox btnVisible = new LCheckBox(compositeVisible);
		btnVisible.setText(Vocab.instance.VISIBLE);
		btnVisible.setHoverText(Tooltip.instance.VISIBLE);
		addControl(btnVisible, "visible");
		
		// Other properties
		
		LPanel check = new LPanel(grpGeneral);
		check.setGridLayout(3);
		check.setSpread(2, 1);
		check.setExpand(false, false);
		check.setAlignment(LFlags.CENTER);

		LCheckBox btnKO = new LCheckBox(check);
		btnKO.setText(Vocab.instance.KOLIKE);
		btnKO.setHoverText(Tooltip.instance.KOLIKE);
		btnKO.setExpand(true, false);
		addControl(btnKO, "ko");

		LCheckBox btnDeactivate = new LCheckBox(check);
		btnDeactivate.setText(Vocab.instance.DEACTIVATE);
		btnDeactivate.setHoverText(Tooltip.instance.DEACTIVATE);
		btnDeactivate.setExpand(true, false);
		addControl(btnDeactivate, "deactivate");

		LCheckBox btnCumulative = new LCheckBox(check);
		btnCumulative.setText(Vocab.instance.CUMULATIVE);
		btnCumulative.setHoverText(Tooltip.instance.CUMULATIVE);
		btnCumulative.setExpand(true, false);
		addControl(btnCumulative, "cumulative");

		// Durability

		LFrame grpDurability = new LFrame(left, Vocab.instance.DURABILITY);
		grpDurability.setGridLayout(3);
		grpDurability.setHoverText(Tooltip.instance.DURABILITY);
		grpDurability.setExpand(true, false);
		grpDurability.setMinimumWidth(200);

		LLabel lblTurns = new LLabel(grpDurability, Vocab.instance.TURNS, Tooltip.instance.TURNS);
		LSpinner spnTurns = new LSpinner(grpDurability);
		spnTurns.setMaximum(999999);
		spnTurns.setMinimum(-1);
		spnTurns.addMenu(lblTurns);
		addControl(spnTurns, "duration");

		LCheckBox btnBattleOnly = new LCheckBox(grpDurability);
		btnBattleOnly.setText(Vocab.instance.BATTLEONLY);
		btnBattleOnly.setHoverText(Tooltip.instance.BATTLEONLY);
		addControl(btnBattleOnly, "battleOnly");

		LPanel checkDurability = new LPanel(grpDurability);
		checkDurability.setSequentialLayout(true);
		checkDurability.setExpand(true, false);
		checkDurability.setSpread(2, 1);

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
		grpGraphics.setExpand(true, true);

		LLabel lblAnim = new LLabel(grpGraphics, Vocab.instance.CHARANIM, Tooltip.instance.CHARANIM);
		LText txtCharAnim = new LText(grpGraphics);
		txtCharAnim.addMenu(lblAnim);
		addControl(txtCharAnim, "charAnim");

		LLabel lblTransform = new LLabel(grpGraphics, LFlags.TOP, Vocab.instance.TRANSFORMATIONS,
				Tooltip.instance.TRANSFORMATIONS);
		SimpleEditableList<Transformation> lstTransformations = new SimpleEditableList<>(grpGraphics);
		lstTransformations.type = Transformation.class;
		lstTransformations.setExpand(true, true);
		lstTransformations.setShellFactory(new LShellFactory<Transformation>() {
			@Override
			public LObjectShell<Transformation> createShell(LShell parent) {
				return new TransformationShell(parent);
			}
		});
		lstTransformations.setIncludeID(false);
		lstTransformations.addMenu(lblTransform);
		addChild(lstTransformations, "transformations");
		
		// Drain

		LFrame grpDrain = new LFrame(right, Vocab.instance.DRAIN);
		grpDrain.setGridLayout(3);
		grpDrain.setHoverText(Tooltip.instance.DRAIN);
		grpDrain.setExpand(true, false);
		grpDrain.setMinimumWidth(200);

		LLabel lblDrainAtt = new LLabel(grpDrain, Vocab.instance.DRAINATT, Tooltip.instance.DRAINATT);
		LText txtDrainAtt = new LText(grpDrain, 2);
		txtDrainAtt.addMenu(lblDrainAtt);
		addControl(txtDrainAtt, "drainAtt");

		LLabel lblDrain = new LLabel(grpDrain, Vocab.instance.DRAINVALUE, Tooltip.instance.DRAINVALUE);
		LSpinner spnDrain = new LSpinner(grpDrain);
		spnDrain.setMaximum(999999);
		spnDrain.setMinimum(0);
		spnDrain.addMenu(lblDrain);
		addControl(spnDrain, "drainValue");

		LCheckBox btnPercentage = new LCheckBox(grpDrain);
		btnPercentage.setText(Vocab.instance.PERCENTAGE);
		btnPercentage.setText(Tooltip.instance.PERCENTAGE);
		addControl(btnPercentage, "percentage");
		
		// Effects

		LPanel effects = new LPanel(right);
		effects.setGridLayout(2);
		effects.setEqualCells(true, false);
		effects.setExpand(true, true);
		
		LFrame grpAtt = new LFrame(effects, (String) Vocab.instance.ATTRIBUTES);
		grpAtt.setFillLayout(true);
		grpAtt.setHoverText(Tooltip.instance.STATUSATT);
		grpAtt.setExpand(true, true);
		AttributeList lstAttributes = new AttributeList(grpAtt);
		lstAttributes.addMenu(grpAtt);
		addChild(lstAttributes, "attributes");
		
		LFrame grpBonuses = new LFrame(effects, (String) Vocab.instance.PROPERTIES);
		grpBonuses.setFillLayout(true);
		grpBonuses.setHoverText(Tooltip.instance.PROPERTIES);
		grpBonuses.setExpand(true, true);
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
