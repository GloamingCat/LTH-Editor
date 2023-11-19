package gui.views.database.content;

import gson.project.GObjectTreeSerializer;
import gui.Vocab;
import gui.shell.database.RuleShell;
import gui.views.database.DatabaseTab;
import gui.views.database.subcontent.AttributeEditor;
import gui.views.database.subcontent.PropertyList;
import gui.views.database.subcontent.DropList;
import gui.views.database.subcontent.EquipList;
import gui.widgets.IDButton;
import gui.widgets.IDList;
import gui.widgets.IconButton;
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
import lwt.widget.LTextBox;

import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.GridData;

import project.Project;
import data.Battler;
import data.subcontent.Rule;

public class BattlerTab extends DatabaseTab<Battler> {
	
	private IDList lstSkills;
	private IDList lstStatus;
	private PropertyList lstElements;
	private IDButton btnJob;
	
	/**
	 * @wbp.parser.constructor
	 * @wbp.eval.method.parameter parent new lwt.dialog.LShell(800, 600)
	 */
	public BattlerTab(LContainer parent) {
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
		
		IconButton btnGraphics = new IconButton(compositeIcon, false);
		btnGraphics.setImageWidget(imgIcon);
		addControl(btnGraphics, "icon");
		
		// Description
		
		new LLabel(grpGeneral, LLabel.TOP, Vocab.instance.DESCRIPTION);
		
		LTextBox txtDescription = new LTextBox(grpGeneral);
		GridData gd_txtDescription = new GridData(SWT.FILL, SWT.FILL, true, true, 1, 1);
		gd_txtDescription.heightHint = 60;
		gd_txtDescription.minimumHeight = 60;
		txtDescription.setLayoutData(gd_txtDescription);
		addControl(txtDescription, "description");
		
		// Properties
		
		new LLabel(grpGeneral, 1, 1);
		
		LPanel check = new LPanel(grpGeneral, 2, true);
		check.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1));
		
		LCheckBox btnPersistent = new LCheckBox(check);
		btnPersistent.setText(Vocab.instance.PERSISTENT);
		addControl(btnPersistent, "persistent");
		
		LCheckBox btnRecruit = new LCheckBox(check);
		btnRecruit.setText(Vocab.instance.RECRUIT);
		addControl(btnRecruit, "persistent");
		
		// Rewards
		
		new LLabel(grpGeneral, Vocab.instance.MONEY);
		
		LPanel compositeReward = new LPanel(grpGeneral, 3, false);
		compositeReward.setLayoutData(new GridData(SWT.FILL, SWT.FILL, false, false, 1, 1));
		
		LSpinner spnMoney = new LSpinner(compositeReward);
		spnMoney.setMaximum(99999999);
		addControl(spnMoney, "money");
		
		new LLabel(compositeReward, Vocab.instance.EXP);
		
		LSpinner spnEXP = new LSpinner(compositeReward);
		spnEXP.setMaximum(99999999);
		addControl(spnEXP, "exp");
		
		// Job
		
		new LLabel(grpGeneral, Vocab.instance.JOB);
		
		LPanel job = new LPanel(grpGeneral, 4, false);
		job.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1));
		
		LText txtJob = new LText(job, true);
		btnJob = new IDButton(job, false);
		btnJob.setNameWidget(txtJob);
		addControl(btnJob, "jobID");
		
		new LLabel(job, Vocab.instance.LEVEL);

		LSpinner spnLevel = new LSpinner(job);
		addControl(spnLevel, "level");
		
		// Attributes
		
		LPanel middle = new LPanel(left, 2, false);
		middle.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true, 1, 1));
		
		LFrame grpAtt = new LFrame(middle, Vocab.instance.ATTRIBUTES, true, true);
		GridData gd_grpAtt = new GridData(SWT.FILL, SWT.FILL, true, true, 1, 1);
		gd_grpAtt.widthHint = 220;
		grpAtt.setLayoutData(gd_grpAtt);

		AttributeEditor attEditor = new AttributeEditor(grpAtt, 2);
		addChild(attEditor, "attributes");

		// Elements
		
		LFrame grpElements = new LFrame(middle, Vocab.instance.ELEMENTS, true, true);
		grpElements.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true, 1, 1));
		lstElements = new PropertyList(grpElements);
		addChild(lstElements, "elements");
		
		LPanel bottom = new LPanel(left, 2, true);
		bottom.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true, 1, 1));
		
		// Skills
		
		LFrame grpSkills = new LFrame(bottom,
				Vocab.instance.SKILLS + " (" + Vocab.instance.INITIAL + ")", true, true);
		grpSkills.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true, 1, 1));
		
		lstSkills = new IDList(grpSkills);
		addChild(lstSkills, "skills");
		
		// Status
		
		LFrame grpStatus = new LFrame(bottom, 
				Vocab.instance.STATUS + " (" + Vocab.instance.INITIAL + ")", true, true);
		grpStatus.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true, 1, 1));
		
		lstStatus = new IDList(grpStatus);
		addChild(lstStatus, "status");
		
		// Equip
		
		LFrame grpEquip = new LFrame(right, 
				Vocab.instance.EQUIP + " (" + Vocab.instance.INITIAL + ")", true, true);
		grpEquip.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true, 1, 1));
		
		EquipList lstEquip = new EquipList(grpEquip);
		addChild(lstEquip, "equip");
		
		// Drop
		
		LFrame grpDrop = new LFrame(right, Vocab.instance.DROP, true, true);
		grpDrop.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true, 1, 1));
		
		DropList lstDrop = new DropList(grpDrop);
		addChild(lstDrop, "items");
		
		// AI

		LFrame grpAI = new LFrame(right, Vocab.instance.AI, true, true);
		grpAI.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true, 1, 1));

		SimpleEditableList<Rule> lstRules = new SimpleEditableList<Rule>(grpAI);
		lstRules.type = Rule.class;
		lstRules.setIncludeID(false);
		lstRules.setShellFactory(new LShellFactory<Rule>() {
			@Override
			public LObjectShell<Rule> createShell(LShell parent) {
				return new RuleShell(parent);
			}
		});
		addChild(lstRules, "ai");
		
	}
	
	@Override
	public void onVisible() {
		lstSkills.dataTree = Project.current.skills.getTree();
		lstStatus.dataTree = Project.current.status.getTree();
		lstElements.dataTree = Project.current.elements.getList().toTree();
		btnJob.dataTree = Project.current.jobs.getTree();
		super.onVisible();
	}

	@Override
	protected GObjectTreeSerializer getSerializer() {
		return Project.current.battlers;
	}

}
