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
import lwt.dataestructure.LDataTree;
import lwt.dialog.LObjectShell;
import lwt.dialog.LShellFactory;
import lwt.widget.LCheckBox;
import lwt.widget.LImage;
import lwt.widget.LLabel;
import lwt.widget.LSpinner;
import lwt.widget.LText;

import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.layout.RowLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Group;
import org.eclipse.swt.widgets.Shell;

import data.Status;
import data.subcontent.Rule;
import data.subcontent.Transformation;
import project.Project;

public class StatusTab extends DatabaseTab<Status> {

	/**
	 * Create the composite.
	 * 
	 * @param parent
	 */
	public StatusTab(Composite parent) {
		super(parent);

		// Icon

		new LLabel(grpGeneral, Vocab.instance.ICON);

		Composite compositeIcon = new Composite(grpGeneral, SWT.NONE);
		GridLayout gl_compositeIcon = new GridLayout(2, false);
		gl_compositeIcon.marginWidth = 0;
		gl_compositeIcon.marginHeight = 0;
		compositeIcon.setLayout(gl_compositeIcon);
		compositeIcon.setLayoutData(new GridData(SWT.FILL, SWT.FILL, false, false, 1, 1));

		LImage imgIcon = new LImage(compositeIcon, SWT.NONE);
		imgIcon.setImage("/javax/swing/plaf/basic/icons/image-delayed.png");
		GridData gd_imgIcon = new GridData(SWT.FILL, SWT.FILL, true, true, 1, 1);
		gd_imgIcon.widthHint = 48;
		gd_imgIcon.heightHint = 48;
		imgIcon.setVerticalAlign(SWT.CENTER);
		imgIcon.setLayoutData(gd_imgIcon);

		IconButton btnSelectIcon = new IconButton(compositeIcon, 1);
		btnSelectIcon.setImageWidget(imgIcon);
		addControl(btnSelectIcon, "icon");

		// Cancel

		new LLabel(grpGeneral, Vocab.instance.STATUSCANCEL)
				.setLayoutData(new GridData(SWT.LEFT, SWT.TOP, false, false, 1, 1));

		IDList lstCancel = new IDList(grpGeneral, SWT.NONE) {
			@Override
			public LDataTree<Object> getDataTree() {
				return Project.current.status.getTree();
			}
		};
		GridData gd_cancel = new GridData(SWT.FILL, SWT.FILL, true, true, 1, 1);
		gd_cancel.heightHint = 70;
		gd_cancel.minimumHeight = 70;
		lstCancel.setLayoutData(gd_cancel);
		addChild(lstCancel, "cancel");

		// Behavior Script

		new LLabel(grpGeneral, Vocab.instance.BEHAVIOR)
				.setLayoutData(new GridData(SWT.LEFT, SWT.TOP, false, false, 1, 1));

		SimpleEditableList<Rule> lstRules = new SimpleEditableList<Rule>(grpGeneral, SWT.NONE);
		GridData gd_rules = new GridData(SWT.FILL, SWT.FILL, true, true, 1, 1);
		gd_rules.heightHint = 70;
		gd_rules.minimumHeight = 70;
		lstRules.setLayoutData(gd_rules);
		lstRules.type = Rule.class;
		lstRules.setIncludeID(false);
		lstRules.setShellFactory(new LShellFactory<Rule>() {
			@Override
			public LObjectShell<Rule> createShell(Shell parent) {
				return new RuleShell(parent);
			}
		});
		addChild(lstRules, "behavior");

		// Script

		new LLabel(grpGeneral, Vocab.instance.SCRIPT);

		Composite compositeScript = new Composite(grpGeneral, SWT.NONE);
		GridLayout gl_compositeScript = new GridLayout(2, false);
		gl_compositeScript.marginHeight = 0;
		gl_compositeScript.marginWidth = 0;
		compositeScript.setLayout(gl_compositeScript);
		compositeScript.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, false, false, 1, 1));

		LText txtScript = new LText(compositeScript, true);
		txtScript.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1));

		LuaButton btnSelectScript = new LuaButton(compositeScript, 1);
		btnSelectScript.setPathWidget(txtScript);
		addControl(btnSelectScript, "script");

		// Visibility

		new LLabel(grpGeneral, Vocab.instance.PRIORITY);

		Composite compositeVisible = new Composite(grpGeneral, SWT.NONE);
		GridLayout gl_compositeVisible = new GridLayout(2, false);
		gl_compositeVisible.marginHeight = 0;
		gl_compositeVisible.marginWidth = 0;
		compositeVisible.setLayout(gl_compositeVisible);
		compositeVisible.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, false, false, 1, 1));

		LSpinner spnPriority = new LSpinner(compositeVisible, SWT.NONE);
		spnPriority.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1));
		spnPriority.setMinimum(0);
		spnPriority.setMaximum(200);
		addControl(spnPriority, "priority");

		LCheckBox btnVisible = new LCheckBox(compositeVisible, SWT.NONE);
		btnVisible.setText(Vocab.instance.VISIBLE);
		addControl(btnVisible, "visible");
		
		// Other properties
		
		Composite check = new Composite(grpGeneral, SWT.NONE);
		check.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, false, false, 2, 1));
		check.setLayout(new RowLayout());

		LCheckBox btnKO = new LCheckBox(check, SWT.NONE);
		btnKO.setText(Vocab.instance.KOLIKE);
		addControl(btnKO, "ko");

		LCheckBox btnDeactivate = new LCheckBox(check, SWT.NONE);
		btnDeactivate.setText(Vocab.instance.DEACTIVATE);
		addControl(btnDeactivate, "deactivate");

		LCheckBox btnCumulative = new LCheckBox(check, SWT.NONE);
		btnCumulative.setText(Vocab.instance.CUMULATIVE);
		addControl(btnCumulative, "cumulative");

		// Durability

		Group grpDurability = new Group(left, SWT.NONE);
		GridData gd_grpDurability = new GridData(SWT.FILL, SWT.FILL, true, false, 1, 1);
		gd_grpDurability.widthHint = 212;
		grpDurability.setLayoutData(gd_grpDurability);
		grpDurability.setLayout(new GridLayout(3, false));
		grpDurability.setText(Vocab.instance.DURABILITY);

		new LLabel(grpDurability, Vocab.instance.TURNS);

		LSpinner spnTurns = new LSpinner(grpDurability, SWT.NONE);
		spnTurns.setMaximum(999999);
		spnTurns.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1));
		spnTurns.setMinimum(-1);
		addControl(spnTurns, "duration");

		LCheckBox btnBattleOnly = new LCheckBox(grpDurability, SWT.NONE);
		btnBattleOnly.setText(Vocab.instance.BATTLEONLY);
		addControl(btnBattleOnly, "battleOnly");

		Composite checkDurability = new Composite(grpDurability, SWT.NONE);
		checkDurability.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, false, 2, 1));
		checkDurability.setLayout(new RowLayout());

		LCheckBox btnRemoveOnKO = new LCheckBox(checkDurability, SWT.NONE);
		btnRemoveOnKO.setText(Vocab.instance.REMOVEONKO);
		addControl(btnRemoveOnKO, "removeOnKO");

		LCheckBox btnRemoveOnDamage = new LCheckBox(checkDurability, SWT.NONE);
		btnRemoveOnDamage.setText(Vocab.instance.REMOVEONDAMAGE);
		addControl(btnRemoveOnDamage, "removeOnDamage");

		// Graphics

		Group grpGraphics = new Group(left, SWT.NONE);
		grpGraphics.setLayout(new GridLayout(2, false));
		grpGraphics.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true, 1, 1));
		grpGraphics.setText(Vocab.instance.GRAPHICS);

		new LLabel(grpGraphics, Vocab.instance.CHARANIM);

		LText txtCharAnim = new LText(grpGraphics);
		txtCharAnim.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1));
		addControl(txtCharAnim, "charAnim");

		new LLabel(grpGraphics, Vocab.instance.TRANSFORMATIONS)
				.setLayoutData(new GridData(SWT.LEFT, SWT.TOP, false, false, 1, 1));

		SimpleEditableList<Transformation> lstTransformations = new SimpleEditableList<>(grpGraphics, SWT.NONE);
		lstTransformations.type = Transformation.class;
		lstTransformations.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true, 1, 1));
		lstTransformations.setShellFactory(new LShellFactory<Transformation>() {
			@Override
			public LObjectShell<Transformation> createShell(org.eclipse.swt.widgets.Shell parent) {
				return new TransformationShell(parent);
			}
		});
		lstTransformations.setIncludeID(false);
		addChild(lstTransformations, "transformations");
		
		// Drain

		Group grpDrain = new Group(right, SWT.NONE);
		grpDrain.setText(Vocab.instance.DRAIN);
		grpDrain.setLayout(new GridLayout(3, false));
		GridData gd_grpDrain = new GridData(SWT.FILL, SWT.FILL, true, false, 1, 1);
		gd_grpDrain.widthHint = 215;
		grpDrain.setLayoutData(gd_grpDrain);

		new LLabel(grpDrain, Vocab.instance.DRAINATT);

		LText txtDrainAtt = new LText(grpDrain);
		txtDrainAtt.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 2, 1));
		addControl(txtDrainAtt, "drainAtt");

		new LLabel(grpDrain, Vocab.instance.DRAINVALUE);

		LSpinner spnDrain = new LSpinner(grpDrain, SWT.NONE);
		spnDrain.setMaximum(999999);
		spnDrain.setMinimum(0);
		spnDrain.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1));
		addControl(spnDrain, "drainValue");

		LCheckBox btnPercentage = new LCheckBox(grpDrain, SWT.NONE);
		btnPercentage.setText(Vocab.instance.PERCENTAGE);
		addControl(btnPercentage, "percentage");
		
		// Effects

		Composite effects = new Composite(right, SWT.NONE);
		effects.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true, 1, 1));
		GridLayout gl_effects = new GridLayout(2, true);

		Group grpAtt = new Group(effects, SWT.NONE);
		FillLayout fl_Att = new FillLayout(SWT.HORIZONTAL);
		fl_Att.marginWidth = gl_effects.marginWidth;
		fl_Att.marginHeight = fl_Att.marginWidth;
		grpAtt.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true, 1, 1));
		grpAtt.setLayout(fl_Att);
		grpAtt.setText(Vocab.instance.ATTRIBUTES);

		AttributeList lstAttributes = new AttributeList(grpAtt, SWT.NONE);
		addChild(lstAttributes, "attributes");

		Group grpBonuses = new Group(effects, SWT.NONE);
		grpBonuses.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true, 1, 1));
		FillLayout fl_bonuses = new FillLayout(SWT.HORIZONTAL);
		fl_bonuses.marginWidth = gl_effects.marginWidth;
		fl_bonuses.marginHeight = fl_bonuses.marginWidth;
		grpBonuses.setLayout(fl_bonuses);
		grpBonuses.setText(Vocab.instance.PROPERTIES);

		BonusList lstBonus = new BonusList(grpBonuses, SWT.NONE);
		addChild(lstBonus, "bonuses");
		
		gl_effects.marginHeight = 0;
		gl_effects.marginWidth = 0;
		effects.setLayout(gl_effects);

	}

	@Override
	protected GObjectTreeSerializer getSerializer() {
		return Project.current.status;
	}

}
