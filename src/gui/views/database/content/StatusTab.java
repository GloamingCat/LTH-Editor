package gui.views.database.content;

import gson.project.GObjectTreeSerializer;
import gui.Vocab;
import gui.shell.database.RuleShell;
import gui.shell.database.TransformationShell;
import gui.views.database.DatabaseTab;
import gui.views.database.subcontent.AttributeList;
import gui.views.database.subcontent.ElementList;
import gui.views.database.subcontent.TagList;
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
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Group;
import org.eclipse.swt.widgets.Shell;

import data.subcontent.Rule;
import data.subcontent.Transformation;
import project.Project;

public class StatusTab extends DatabaseTab {

	/**
	 * Create the composite.
	 * @param parent
	 */
	public StatusTab(Composite parent) {
		super(parent);
		
		GridLayout gridLayout = new GridLayout(2, false);
		gridLayout.verticalSpacing = 0;
		contentEditor.setLayout(gridLayout);
		
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
		
		// General
		
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
		Composite options = new Composite(grpGeneral,  SWT.NONE);
		options.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, false, false, 2, 1));
		GridLayout gl_options = new GridLayout(3, false);
		gl_options.marginHeight = 0;
		options.setLayout(gl_options);
		
		LCheckBox btnKO = new LCheckBox(options, SWT.NONE);
		btnKO.setLayoutData(new GridData(SWT.LEFT, SWT.CENTER, true, false, 1, 1));
		btnKO.setText(Vocab.instance.KO);
		addControl(btnKO, "ko");
		
		LCheckBox btnDeactivate = new LCheckBox(options, SWT.NONE);
		btnDeactivate.setLayoutData(new GridData(SWT.LEFT, SWT.CENTER, true, false, 1, 1));
		btnDeactivate.setText(Vocab.instance.DEACTIVATE);
		addControl(btnDeactivate, "deactivate");
		
		LCheckBox btnCumulative = new LCheckBox(options, SWT.NONE);
		btnCumulative.setLayoutData(new GridData(SWT.LEFT, SWT.CENTER, true, false, 1, 1));
		btnCumulative.setText(Vocab.instance.CUMULATIVE);
		addControl(btnCumulative, "cumulative");
		
		// Behavior Script
		
		new LLabel(grpGeneral, Vocab.instance.BEHAVIOR).setLayoutData(new GridData(SWT.LEFT, SWT.TOP, false, false, 1, 1));
		
		SimpleEditableList<Rule> lstRules = new SimpleEditableList<Rule>(grpGeneral, SWT.NONE);
		GridData gd_lstRules = new GridData(SWT.FILL, SWT.FILL, true, true, 1, 1);
		gd_lstRules.heightHint = 21;
		lstRules.setLayoutData(gd_lstRules);
		lstRules.type = Rule.class;
		lstRules.setIncludeID(false);
		lstRules.setShellFactory(new LShellFactory<Rule>() {
			@Override
			public LObjectShell<Rule> createShell(Shell parent) {
				return new RuleShell(parent);
			}
		});
		addChild(lstRules, "behavior");
		
		// Graphics
		
		Group grpGraphics = new Group(contentEditor, SWT.NONE);
		grpGraphics.setLayout(new GridLayout(2, false));
		grpGraphics.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, false, 1, 1));
		grpGraphics.setText(Vocab.instance.GRAPHICS);
		
		new LLabel(grpGraphics, Vocab.instance.CHARANIM);
		
		LText txtCharAnim = new LText(grpGraphics);
		txtCharAnim.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1));
		addControl(txtCharAnim, "charAnim");
		
		new LLabel(grpGraphics, Vocab.instance.TRANSFORMATIONS).setLayoutData(new GridData(SWT.LEFT, SWT.TOP, false, false, 1, 1));
		
		SimpleEditableList<Transformation> lstTransformations = new SimpleEditableList<>(grpGraphics, SWT.NONE);
		lstTransformations.type = Transformation.class;
		lstTransformations.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true, 1, 1));
		lstTransformations.setShellFactory(new LShellFactory<Transformation>() {
			@Override
			public LObjectShell<Transformation> createShell(
					org.eclipse.swt.widgets.Shell parent) {
				return new TransformationShell(parent);
			}
		});
		lstTransformations.setIncludeID(false);
		addChild(lstTransformations, "transformations");

		Composite middle = new Composite(contentEditor, SWT.NONE);
		GridLayout gl_middle = new GridLayout(3, false);
		gl_middle.marginWidth = 0;
		gl_middle.marginHeight = 0;
		middle.setLayout(gl_middle);
		middle.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, false, 2, 1));
		
		// Drain
		
		Group grpDrain = new Group(middle, SWT.NONE);
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
		
		// Durability
		
		Group grpDurability = new Group(middle, SWT.NONE);
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
		
		LCheckBox btnRemoveOnKO = new LCheckBox(grpDurability, SWT.NONE);
		btnRemoveOnKO.setLayoutData(new GridData(SWT.LEFT, SWT.CENTER, false, false, 3, 1));
		btnRemoveOnKO.setText(Vocab.instance.REMOVEONKO);
		addControl(btnRemoveOnKO, "removeOnKO");
		
		LCheckBox btnRemoveOnDamage = new LCheckBox(grpDurability, SWT.NONE);
		btnRemoveOnDamage.setLayoutData(new GridData(SWT.LEFT, SWT.CENTER, false, false, 3, 1));
		btnRemoveOnDamage.setText(Vocab.instance.REMOVEONDAMAGE);
		addControl(btnRemoveOnDamage, "removeOnDamage");
		
		
		Group grpCancel = new Group(middle, SWT.NONE);
		GridData gd_grpCancel = new GridData(SWT.FILL, SWT.FILL, true, false, 1, 1);
		gd_grpCancel.widthHint = 129;
		gd_grpCancel.heightHint = 26;
		grpCancel.setLayoutData(gd_grpCancel);
		grpCancel.setLayout(new FillLayout(SWT.HORIZONTAL));
		grpCancel.setText(Vocab.instance.STATUSCANCEL);
		
		IDList lstCancel = new IDList(grpCancel, SWT.NONE) {
			@Override
			public LDataTree<Object> getDataTree() {
				return Project.current.status.getTree();
			}
		};
		addChild(lstCancel, "cancel");
		
		// Lists
		
		Composite bottom = new Composite(contentEditor, SWT.NONE);
		GridLayout gl_bottom = new GridLayout(4, true);
		gl_bottom.marginHeight = 0;
		gl_bottom.marginWidth = 0;
		bottom.setLayout(gl_bottom);
		bottom.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true, 2, 1));
		
		Group grpAttributes = new Group(bottom, SWT.NONE);
		grpAttributes.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true, 1, 1));
		grpAttributes.setLayout(new FillLayout(SWT.HORIZONTAL));
		grpAttributes.setText(Vocab.instance.ATTRIBUTES);
		
		AttributeList lstAttributes = new AttributeList(grpAttributes, SWT.NONE);
		addChild(lstAttributes, "attributes");

		Group grpElement = new Group(bottom, SWT.NONE);
		grpElement.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true, 1, 1));
		grpElement.setLayout(new FillLayout(SWT.HORIZONTAL));
		grpElement.setText(Vocab.instance.ELEMENTS);
		
		ElementList lstElements = new ElementList(grpElement, SWT.NONE);
		addChild(lstElements, "elements");
		
		Group grpStatusDef = new Group(bottom, SWT.NONE);
		grpStatusDef.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true, 1, 1));
		grpStatusDef.setLayout(new FillLayout(SWT.HORIZONTAL));
		grpStatusDef.setText(Vocab.instance.STATUSDEF);
		
		IDList lstStatusDef = new IDList(grpStatusDef, SWT.NONE) {
			@Override
			public LDataTree<Object> getDataTree() {
				return Project.current.status.getData();
			}
		};
		addChild(lstStatusDef, "statusDef");
		
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
		return Project.current.status;
	}

}
