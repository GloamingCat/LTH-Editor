package gui.views.database.content;

import gson.project.GObjectTreeSerializer;
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
import lwt.container.LContainer;
import lwt.container.LFrame;
import lwt.container.LPanel;
import lwt.dialog.LObjectShell;
import lwt.dialog.LShell;
import lwt.dialog.LShellFactory;
import lwt.widget.LCheckBox;
import lwt.widget.LImage;
import lwt.widget.LLabel;
import lwt.widget.LSpinner;
import lwt.widget.LText;

import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.GridData;

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

		new LLabel(grpGeneral, Vocab.instance.ICON);

		LPanel compositeIcon = new LPanel(grpGeneral, 2, false);
		compositeIcon.setLayoutData(new GridData(SWT.FILL, SWT.FILL, false, false, 1, 1));

		LImage imgIcon = new LImage(compositeIcon, SWT.NONE);
		imgIcon.setImage("/javax/swing/plaf/basic/icons/image-delayed.png");
		GridData gd_imgIcon = new GridData(SWT.FILL, SWT.FILL, true, true, 1, 1);
		gd_imgIcon.widthHint = 48;
		gd_imgIcon.heightHint = 48;
		imgIcon.setVerticalAlign(SWT.CENTER);
		imgIcon.setLayoutData(gd_imgIcon);

		IconButton btnSelectIcon = new IconButton(compositeIcon, true);
		btnSelectIcon.setImageWidget(imgIcon);
		addControl(btnSelectIcon, "icon");

		// Cancel

		new LLabel(grpGeneral, LLabel.TOP, Vocab.instance.STATUSCANCEL);

		lstCancel = new IDList(grpGeneral);
		GridData gd_cancel = new GridData(SWT.FILL, SWT.FILL, true, true, 1, 1);
		gd_cancel.heightHint = 70;
		gd_cancel.minimumHeight = 70;
		lstCancel.setLayoutData(gd_cancel);
		addChild(lstCancel, "cancel");

		// Behavior Script

		new LLabel(grpGeneral, LLabel.TOP, Vocab.instance.BEHAVIOR);

		SimpleEditableList<Rule> lstRules = new SimpleEditableList<Rule>(grpGeneral);
		GridData gd_rules = new GridData(SWT.FILL, SWT.FILL, true, true, 1, 1);
		gd_rules.heightHint = 70;
		gd_rules.minimumHeight = 70;
		lstRules.setLayoutData(gd_rules);
		lstRules.type = Rule.class;
		lstRules.setIncludeID(false);
		lstRules.setShellFactory(new LShellFactory<Rule>() {
			@Override
			public LObjectShell<Rule> createShell(LShell parent) {
				return new RuleShell(parent);
			}
		});
		addChild(lstRules, "behavior");

		// Script

		new LLabel(grpGeneral, Vocab.instance.SCRIPT);

		LPanel compositeScript = new LPanel(grpGeneral, 2, false);
		compositeScript.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, false, false, 1, 1));

		LText txtScript = new LText(compositeScript, true);
		LuaButton btnSelectScript = new LuaButton(compositeScript, true);
		btnSelectScript.setPathWidget(txtScript);
		addControl(btnSelectScript, "script");

		// Visibility

		new LLabel(grpGeneral, Vocab.instance.PRIORITY);

		LPanel compositeVisible = new LPanel(grpGeneral, 2, false);
		compositeVisible.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, false, false, 1, 1));

		LSpinner spnPriority = new LSpinner(compositeVisible);
		spnPriority.setMinimum(0);
		spnPriority.setMaximum(200);
		addControl(spnPriority, "priority");

		LCheckBox btnVisible = new LCheckBox(compositeVisible);
		btnVisible.setText(Vocab.instance.VISIBLE);
		addControl(btnVisible, "visible");
		
		// Other properties
		
		LPanel check = new LPanel(grpGeneral, 3, true);
		check.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, false, false, 2, 1));

		LCheckBox btnKO = new LCheckBox(check);
		btnKO.setText(Vocab.instance.KOLIKE);
		addControl(btnKO, "ko");

		LCheckBox btnDeactivate = new LCheckBox(check);
		btnDeactivate.setText(Vocab.instance.DEACTIVATE);
		addControl(btnDeactivate, "deactivate");

		LCheckBox btnCumulative = new LCheckBox(check);
		btnCumulative.setText(Vocab.instance.CUMULATIVE);
		addControl(btnCumulative, "cumulative");

		// Durability

		LFrame grpDurability = new LFrame(left, Vocab.instance.DURABILITY, 3, false);
		GridData gd_grpDurability = new GridData(SWT.FILL, SWT.FILL, true, false, 1, 1);
		gd_grpDurability.widthHint = 212;
		grpDurability.setLayoutData(gd_grpDurability);

		new LLabel(grpDurability, Vocab.instance.TURNS);

		LSpinner spnTurns = new LSpinner(grpDurability);
		spnTurns.setMaximum(999999);
		spnTurns.setMinimum(-1);
		addControl(spnTurns, "duration");

		LCheckBox btnBattleOnly = new LCheckBox(grpDurability);
		btnBattleOnly.setText(Vocab.instance.BATTLEONLY);
		addControl(btnBattleOnly, "battleOnly");

		LPanel checkDurability = new LPanel(grpDurability, true, false);
		checkDurability.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, false, 2, 1));

		LCheckBox btnRemoveOnKO = new LCheckBox(checkDurability);
		btnRemoveOnKO.setText(Vocab.instance.REMOVEONKO);
		addControl(btnRemoveOnKO, "removeOnKO");

		LCheckBox btnRemoveOnDamage = new LCheckBox(checkDurability);
		btnRemoveOnDamage.setText(Vocab.instance.REMOVEONDAMAGE);
		addControl(btnRemoveOnDamage, "removeOnDamage");

		// Graphics

		LFrame grpGraphics = new LFrame(left, Vocab.instance.GRAPHICS, 2, false);
		grpGraphics.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true, 1, 1));

		new LLabel(grpGraphics, Vocab.instance.CHARANIM);
		LText txtCharAnim = new LText(grpGraphics);
		addControl(txtCharAnim, "charAnim");

		new LLabel(grpGraphics, LLabel.TOP, Vocab.instance.TRANSFORMATIONS);
		SimpleEditableList<Transformation> lstTransformations = new SimpleEditableList<>(grpGraphics);
		lstTransformations.type = Transformation.class;
		lstTransformations.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true, 1, 1));
		lstTransformations.setShellFactory(new LShellFactory<Transformation>() {
			@Override
			public LObjectShell<Transformation> createShell(LShell parent) {
				return new TransformationShell(parent);
			}
		});
		lstTransformations.setIncludeID(false);
		addChild(lstTransformations, "transformations");
		
		// Drain

		LFrame grpDrain = new LFrame(right, Vocab.instance.DRAIN, 3, false);
		GridData gd_grpDrain = new GridData(SWT.FILL, SWT.FILL, true, false, 1, 1);
		gd_grpDrain.widthHint = 215;
		grpDrain.setLayoutData(gd_grpDrain);

		new LLabel(grpDrain, Vocab.instance.DRAINATT);
		LText txtDrainAtt = new LText(grpDrain, 2);
		addControl(txtDrainAtt, "drainAtt");

		new LLabel(grpDrain, Vocab.instance.DRAINVALUE);
		LSpinner spnDrain = new LSpinner(grpDrain);
		spnDrain.setMaximum(999999);
		spnDrain.setMinimum(0);
		addControl(spnDrain, "drainValue");

		LCheckBox btnPercentage = new LCheckBox(grpDrain);
		btnPercentage.setText(Vocab.instance.PERCENTAGE);
		addControl(btnPercentage, "percentage");
		
		// Effects

		LPanel effects = new LPanel(right, 2, true);
		effects.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true, 1, 1));

		LFrame grpAtt = new LFrame(effects, Vocab.instance.ATTRIBUTES, true, true);
		grpAtt.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true, 1, 1));
		AttributeList lstAttributes = new AttributeList(grpAtt);
		addChild(lstAttributes, "attributes");

		LFrame grpBonuses = new LFrame(effects, Vocab.instance.PROPERTIES, true, true);
		grpBonuses.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true, 1, 1));
		BonusList lstBonus = new BonusList(grpBonuses);
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
