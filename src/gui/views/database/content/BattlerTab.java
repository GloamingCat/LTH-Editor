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
import lwt.widget.LTextBox;

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
		
		LPanel middle = new LPanel(left, 2, false);
		middle.setExpand(true, true);
		LPanel bottom = new LPanel(left, 2, true);
		bottom.setExpand(true, true);
		
		// Icon
		
		LLabel lblIcon = new LLabel(grpGeneral, Vocab.instance.ICON);
		LPanel compositeIcon = new LPanel(grpGeneral, 2, false);
		compositeIcon.setAlignment(LFlags.CENTER);
		LImage imgIcon = new LImage(compositeIcon);
		imgIcon.setImage("/javax/swing/plaf/basic/icons/image-delayed.png");
		imgIcon.setMinimumWidth(48);
		imgIcon.setMinimumHeight(48);
		imgIcon.setExpand(true, true);
		IconButton btnGraphics = new IconButton(compositeIcon, false);
		btnGraphics.setImageWidget(imgIcon);
		btnGraphics.addMenu(lblIcon);
		addControl(btnGraphics, "icon");
		
		// Description
		
		LLabel lblDesc = new LLabel(grpGeneral, LFlags.TOP, Vocab.instance.DESCRIPTION);
		
		LTextBox txtDescription = new LTextBox(grpGeneral, 1, 1);
		txtDescription.setMinimumHeight(60);
		txtDescription.addMenu(lblDesc);
		addControl(txtDescription, "description");
		
		// Properties
		
		new LLabel(grpGeneral, 1, 1);
		LPanel check = new LPanel(grpGeneral, 2, true);
		check.setExpand(true, false);
		check.setAlignment(LFlags.CENTER);
		
		LCheckBox btnPersistent = new LCheckBox(check);
		btnPersistent.setText(Vocab.instance.PERSISTENT);
		addControl(btnPersistent, "persistent");
		
		LCheckBox btnRecruit = new LCheckBox(check);
		btnRecruit.setText(Vocab.instance.RECRUIT);
		addControl(btnRecruit, "persistent");
		
		// Rewards
		
		LLabel lblMoney = new LLabel(grpGeneral, Vocab.instance.MONEY);
		
		LPanel compositeReward = new LPanel(grpGeneral, 3, false);
		compositeReward.setExpand(true, false);
		
		LSpinner spnMoney = new LSpinner(compositeReward);
		spnMoney.setMaximum(99999999);
		spnMoney.addMenu(lblMoney);
		addControl(spnMoney, "money");
		
		LLabel lblExp = new LLabel(compositeReward, Vocab.instance.EXP);
		LSpinner spnEXP = new LSpinner(compositeReward);
		spnEXP.setMaximum(99999999);
		spnEXP.addMenu(lblExp);
		addControl(spnEXP, "exp");
		
		// Job
		
		LLabel lblJob = new LLabel(grpGeneral, Vocab.instance.JOB);
		LPanel job = new LPanel(grpGeneral, 4, false);
		job.setExpand(true, false);
		job.setAlignment(LFlags.CENTER);
		LText txtJob = new LText(job, true);
		btnJob = new IDButton(job, false);
		btnJob.setNameWidget(txtJob);
		btnJob.addMenu(lblJob);
		btnJob.addMenu(txtJob);
		addControl(btnJob, "jobID");
		
		LLabel lblLevel = new LLabel(job, Vocab.instance.LEVEL);
		LSpinner spnLevel = new LSpinner(job);
		spnLevel.addMenu(lblLevel);
		addControl(spnLevel, "level");
		
		// Attributes
		
		LFrame grpAtt = new LFrame(middle, Vocab.instance.ATTRIBUTES, true, true);
		grpAtt.setExpand(true, true);
		grpAtt.setMinimumWidth(220);
		AttributeEditor attEditor = new AttributeEditor(grpAtt, 2);
		attEditor.addMenu(grpAtt);
		addChild(attEditor, "attributes");

		// Elements
		
		LFrame grpElements = new LFrame(middle, Vocab.instance.ELEMENTS, true, true);
		grpElements.setExpand(true, true);
		lstElements = new PropertyList(grpElements);
		lstElements.addMenu(grpElements);
		addChild(lstElements, "elements");
		
		// Skills
		
		LFrame grpSkills = new LFrame(bottom,
			Vocab.instance.SKILLS + " (" + Vocab.instance.INITIAL + ")", true, true);
		grpSkills.setExpand(true, true);
		lstSkills = new IDList(grpSkills);
		lstSkills.addMenu(grpSkills);
		addChild(lstSkills, "skills");
		
		// Status
		
		LFrame grpStatus = new LFrame(bottom, 
			Vocab.instance.STATUS + " (" + Vocab.instance.INITIAL + ")", true, true);
		grpStatus.setExpand(true, true);
		lstStatus = new IDList(grpStatus);
		lstStatus.addMenu(grpStatus);
		addChild(lstStatus, "status");
		
		// Equip
		
		LFrame grpEquip = new LFrame(right, 
			Vocab.instance.EQUIP + " (" + Vocab.instance.INITIAL + ")", true, true);
		grpEquip.setExpand(true, true);
		EquipList lstEquip = new EquipList(grpEquip);
		lstEquip.addMenu(grpEquip);
		addChild(lstEquip, "equip");
		
		// Drop
		
		LFrame grpDrop = new LFrame(right, Vocab.instance.DROP, true, true);
		grpDrop.setExpand(true, true);
		DropList lstDrop = new DropList(grpDrop);
		lstDrop.addMenu(grpDrop);
		addChild(lstDrop, "items");
		
		// AI

		LFrame grpAI = new LFrame(right, Vocab.instance.AI, true, true);
		grpAI.setExpand(true, true);
		SimpleEditableList<Rule> lstRules = new SimpleEditableList<Rule>(grpAI);
		lstRules.type = Rule.class;
		lstRules.setIncludeID(false);
		lstRules.setShellFactory(new LShellFactory<Rule>() {
			@Override
			public LObjectShell<Rule> createShell(LShell parent) {
				return new RuleShell(parent);
			}
		});
		lstRules.addMenu(grpAI);
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
