package gui.views.database.content;

import gson.project.GObjectTreeSerializer;
import gui.Vocab;
import gui.views.database.DatabaseTab;
import gui.views.database.subcontent.AttributeList;
import gui.views.database.subcontent.BonusList;
import gui.views.database.subcontent.TagList;
import gui.views.database.subcontent.TransformEditor;
import gui.widgets.IDList;
import gui.widgets.IconButton;
import gui.widgets.LuaButton;
import gui.widgets.ScriptButton;
import lwt.dataestructure.LDataTree;
import lwt.widget.LCheckButton;
import lwt.widget.LImage;
import lwt.widget.LSpinner;
import lwt.widget.LText;

import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Group;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Text;

import project.Project;

public class StatusTab extends DatabaseTab {

	/**
	 * Create the composite.
	 * @param parent
	 * @param style
	 */
	public StatusTab(Composite parent, int style) {
		super(parent, style);
		
		GridLayout gridLayout = new GridLayout(2, false);
		gridLayout.verticalSpacing = 0;
		contentEditor.setLayout(gridLayout);
		
		// Script
		
		Label lblScript = new Label(grpGeneral, SWT.NONE);
		lblScript.setText(Vocab.instance.SCRIPT);
		
		Composite compositeScript = new Composite(grpGeneral, SWT.NONE);
		GridLayout gl_compositeScript = new GridLayout(2, false);
		gl_compositeScript.marginHeight = 0;
		gl_compositeScript.marginWidth = 0;
		compositeScript.setLayout(gl_compositeScript);
		compositeScript.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, false, false, 1, 1));
		
		Text txtScript = new Text(compositeScript, SWT.BORDER | SWT.READ_ONLY);
		txtScript.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1));
		
		LuaButton btnSelectScript = new LuaButton(compositeScript, 1);
		btnSelectScript.setPathText(txtScript);
		addControl(btnSelectScript, "script");
		
		// Icon
		
		Label lblIcon = new Label(grpGeneral, SWT.NONE);
		lblIcon.setText(Vocab.instance.ICON);
		
		Composite compositeIcon = new Composite(grpGeneral, SWT.NONE);
		GridLayout gl_compositeIcon = new GridLayout(2, false);
		gl_compositeIcon.marginWidth = 0;
		gl_compositeIcon.marginHeight = 0;
		compositeIcon.setLayout(gl_compositeIcon);
		compositeIcon.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, false, 1, 1));
		
		LImage imgIcon = new LImage(compositeIcon, SWT.NONE);
		imgIcon.setImage("/javax/swing/plaf/basic/icons/image-delayed.png");
		GridData gd_imgIcon = new GridData(SWT.FILL, SWT.FILL, true, true, 1, 1);
		gd_imgIcon.widthHint = 48;
		gd_imgIcon.heightHint = 48;
		imgIcon.setVerticalAlign(SWT.CENTER);
		imgIcon.setLayoutData(gd_imgIcon);
		
		IconButton btnSelectIcon = new IconButton(compositeIcon, 1);
		btnSelectIcon.setImage(imgIcon);
		addControl(btnSelectIcon, "icon");
		
		// General
		
		Label lblPrioriy = new Label(grpGeneral, SWT.NONE);
		lblPrioriy.setText(Vocab.instance.PRIORITY);
		
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
		
		LCheckButton btnVisible = new LCheckButton(compositeVisible, SWT.NONE);
		btnVisible.setText(Vocab.instance.VISIBLE);
		addControl(btnVisible, "visible");
		
		new Label(grpGeneral, SWT.NONE);
		Composite options = new Composite(grpGeneral,  SWT.NONE);
		options.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, false, false, 1, 1));
		options.setLayout(new GridLayout(2, false));
		
		LCheckButton btnKO = new LCheckButton(options, SWT.NONE);
		btnKO.setText(Vocab.instance.KO);
		addControl(btnKO, "ko");
		
		LCheckButton btnDeactivate = new LCheckButton(options, SWT.NONE);
		btnDeactivate.setText(Vocab.instance.DEACTIVATE);
		addControl(btnDeactivate, "deactivate");
		
		LCheckButton btnCumulative = new LCheckButton(options, SWT.NONE);
		btnCumulative.setText(Vocab.instance.CUMULATIVE);
		addControl(btnCumulative, "cumulative");
		new Label(options, SWT.NONE);
		
		// Behavior Script
		
		Label lblBehavior = new Label(grpGeneral, SWT.NONE);
		lblBehavior.setText(Vocab.instance.BEHAVIOR);
		
		Composite compBehavior = new Composite(grpGeneral, SWT.NONE);
		GridLayout gl_compBehavior = new GridLayout(2, false);
		gl_compBehavior.marginHeight = 0;
		gl_compBehavior.marginWidth = 0;
		compBehavior.setLayout(gl_compBehavior);
		compBehavior.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, false, false, 1, 1));
		
		Text txtBehavior = new Text(compBehavior, SWT.BORDER | SWT.READ_ONLY);
		txtBehavior.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1));
		
		ScriptButton btnBehavior = new ScriptButton(compBehavior, 1);
		btnBehavior.setPathText(txtBehavior);
		addControl(btnBehavior, "behavior");
		
		// Graphics
		
		Group grpGraphics = new Group(contentEditor, SWT.NONE);
		grpGraphics.setLayout(new GridLayout(2, false));
		grpGraphics.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, false, 1, 1));
		grpGraphics.setText(Vocab.instance.GRAPHICS);
		
		Label lblCharAnim = new Label(grpGraphics, SWT.NONE);
		lblCharAnim.setText(Vocab.instance.CHARANIM);
		
		LText txtCharAnim = new LText(grpGraphics, SWT.NONE);
		txtCharAnim.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1));
		addControl(txtCharAnim, "charAnim");
		
		TransformEditor transformEditor = new TransformEditor(grpGraphics, SWT.NONE);
		transformEditor.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, false, 2, 1));
		addChild(transformEditor, "transform");
		
		LCheckButton btnOverride = new LCheckButton(grpGraphics, SWT.NONE);
		btnOverride.setLayoutData(new GridData(SWT.LEFT, SWT.FILL, true, false, 2, 1));
		btnOverride.setText(Vocab.instance.OVERRIDE);
		addControl(btnOverride, "override");
		
		// Drain
		
		Group grpDrain = new Group(contentEditor, SWT.NONE);
		grpDrain.setText(Vocab.instance.DRAIN);
		grpDrain.setLayout(new GridLayout(3, false));
		grpDrain.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, false, 1, 1));
		
		Label lblDrainAtt = new Label(grpDrain, SWT.NONE);
		lblDrainAtt.setText(Vocab.instance.DRAINATT);
		
		LText txtDrainAtt = new LText(grpDrain, SWT.NONE);
		txtDrainAtt.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 2, 1));
		addControl(txtDrainAtt, "drainAtt");
		
		Label lblDrain = new Label(grpDrain, SWT.NONE);
		lblDrain.setText(Vocab.instance.DRAINVALUE);
		
		LSpinner spnDrain = new LSpinner(grpDrain, SWT.NONE);
		spnDrain.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1));
		addControl(spnDrain, "drainValue");
		
		LCheckButton btnPercentage = new LCheckButton(grpDrain, SWT.NONE);
		btnPercentage.setText(Vocab.instance.PERCENTAGE);
		addControl(btnPercentage, "percentage");
		
		Label lblFrequency = new Label(grpDrain, SWT.NONE);
		lblFrequency.setText(Vocab.instance.FREQUENCY);
		
		LSpinner spnFrequency = new LSpinner(grpDrain, SWT.NONE);
		spnFrequency.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, false, false, 2, 1));
		addControl(spnFrequency, "frequency");
		
		// Durability
		
		Composite durability = new Composite(contentEditor, SWT.NONE);
		GridLayout gl_durability = new GridLayout(2, false);
		gl_durability.marginWidth = 0;
		gl_durability.marginHeight = 0;
		durability.setLayout(gl_durability);
		durability.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, false, 1, 1));
		
		Group grpDurability = new Group(durability, SWT.NONE);
		grpDurability.setLayoutData(new GridData(SWT.FILL, SWT.FILL, false, false, 1, 1));
		grpDurability.setLayout(new GridLayout(2, false));
		grpDurability.setText(Vocab.instance.DURABILITY);
		
		Label lblTurns = new Label(grpDurability, SWT.NONE);
		lblTurns.setText(Vocab.instance.TURNS);
		
		LSpinner spnTurns = new LSpinner(grpDurability, SWT.NONE);
		spnTurns.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1));
		spnTurns.setMinimum(-1);
		addControl(spnTurns, "duration");
		
		LCheckButton btnRemoveOnKO = new LCheckButton(grpDurability, SWT.NONE);
		btnRemoveOnKO.setLayoutData(new GridData(SWT.LEFT, SWT.CENTER, false, false, 2, 1));
		btnRemoveOnKO.setText(Vocab.instance.REMOVEONKO);
		addControl(btnRemoveOnKO, "removeOnKO");
		
		LCheckButton btnRemoveOnDamage = new LCheckButton(grpDurability, SWT.NONE);
		btnRemoveOnDamage.setLayoutData(new GridData(SWT.LEFT, SWT.CENTER, false, false, 2, 1));
		btnRemoveOnDamage.setText(Vocab.instance.REMOVEONDAMAGE);
		addControl(btnRemoveOnDamage, "removeOnDamage");
		
		Group grpCancel = new Group(durability, SWT.NONE);
		grpCancel.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, false, 1, 1));
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
		
		Composite compositeBottom = new Composite(contentEditor, SWT.NONE);
		GridLayout gl_compositeBottom = new GridLayout(5, true);
		gl_compositeBottom.marginHeight = 0;
		gl_compositeBottom.marginWidth = 0;
		compositeBottom.setLayout(gl_compositeBottom);
		compositeBottom.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true, 2, 1));
		
		Group grpAttributes = new Group(compositeBottom, SWT.NONE);
		grpAttributes.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, false, false, 1, 1));
		grpAttributes.setLayout(new FillLayout(SWT.HORIZONTAL));
		grpAttributes.setText(Vocab.instance.ATTRIBUTES);
		
		AttributeList lstAttributes = new AttributeList(grpAttributes, SWT.NONE);
		addChild(lstAttributes, "attributes");

		Group grpElementAtk = new Group(compositeBottom, SWT.NONE);
		grpElementAtk.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, false, false, 1, 1));
		grpElementAtk.setLayout(new FillLayout(SWT.HORIZONTAL));
		grpElementAtk.setText(Vocab.instance.ELEMENTATK);
		
		BonusList lstElementAtk = new BonusList(grpElementAtk, SWT.NONE) {
			@Override
			protected LDataTree<Object> getDataTree() {
				return Project.current.elements.getList().toTree();
			}
		};
		addChild(lstElementAtk, "elementAtk");
		
		Group grpElementDef = new Group(compositeBottom, SWT.NONE);
		grpElementDef.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, false, false, 1, 1));
		grpElementDef.setLayout(new FillLayout(SWT.HORIZONTAL));
		grpElementDef.setText(Vocab.instance.ELEMENTDEF);
		
		BonusList lstElementDef = new BonusList(grpElementDef, SWT.NONE) {
			@Override
			protected LDataTree<Object> getDataTree() {
				return Project.current.elements.getList().toTree();
			}
		};
		addChild(lstElementDef, "elementDef");
		
		Group grpStatusDef = new Group(compositeBottom, SWT.NONE);
		grpStatusDef.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, false, false, 1, 1));
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
		
		Group grpTags = new Group(compositeBottom, SWT.NONE);
		grpTags.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, false, false, 1, 1));
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
