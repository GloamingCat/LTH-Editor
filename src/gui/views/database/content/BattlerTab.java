package gui.views.database.content;

import gui.Tooltip;
import gui.Vocab;
import gui.shell.database.RuleDialog;
import gui.views.database.DatabaseTab;
import gui.views.database.subcontent.AttributeEditor;
import gui.views.database.subcontent.PropertyList;
import gui.views.database.subcontent.DropList;
import gui.views.database.subcontent.EquipList;
import gui.widgets.IDButton;
import gui.widgets.IDList;
import gui.widgets.IconButton;
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
import lui.widget.LTextBox;

import project.Project;
import data.Battler;
import data.subcontent.Rule;
import gson.GObjectTreeSerializer;

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
	}

	@Override
	protected void createContent() {
		LPanel middle = new LPanel(contentEditor.left);
		middle.setGridLayout(2);
		middle.getCellData().setExpand(true, true);
		LPanel bottom = new LPanel(contentEditor.left);
		bottom.setGridLayout(2);
		bottom.setEqualCells(true, false);
		bottom.getCellData().setExpand(true, true);
		bottom.getCellData().setRequiredSize(0, 80);
		
		// Icon
		
		LLabel lblIcon = new LLabel(contentEditor.grpGeneral, Vocab.instance.ICON, Tooltip.instance.ICON);
		LPanel compositeIcon = new LPanel(contentEditor.grpGeneral);
		compositeIcon.setGridLayout(2);
		compositeIcon.getCellData().setExpand(true, false);
		LImage imgIcon = new LImage(compositeIcon);
		imgIcon.getCellData().setRequiredSize(48, 48);
		imgIcon.getCellData().setExpand(true, true);
		imgIcon.setAlignment(LFlags.LEFT | LFlags.TOP);
		IconButton btnGraphics = new IconButton(compositeIcon, false);
		btnGraphics.setImageWidget(imgIcon);
		btnGraphics.addMenu(lblIcon);
		addControl(btnGraphics, "icon");
		
		// Description
		
		LLabel lblDesc = new LLabel(contentEditor.grpGeneral, LFlags.TOP, Vocab.instance.DESCRIPTION,
			Tooltip.instance.DESCRIPTION);
		LTextBox txtDescription = new LTextBox(contentEditor.grpGeneral);
		txtDescription.getCellData().setExpand(true, true);
		txtDescription.getCellData().setRequiredSize(0, 60);
		txtDescription.addMenu(lblDesc);
		addControl(txtDescription, "description");


		// Job

		LLabel lblJob = new LLabel(contentEditor.grpGeneral, Vocab.instance.JOB, Tooltip.instance.JOB);
		LPanel job = new LPanel(contentEditor.grpGeneral);
		job.setGridLayout(3);
		job.getCellData().setAlignment(LFlags.FILL);

		LPanel jobId = new LPanel(job);
		jobId.setGridLayout(2);
		jobId.getCellData().setExpand(true, false);
		jobId.getCellData().setTargetSize(LPrefs.BUTTONWIDTH * 2, LPrefs.WIDGETHEIGHT);

		LText txtJob = new LText(jobId, true);
		txtJob.getCellData().setExpand(true, false);
		btnJob = new IDButton(jobId, Vocab.instance.JOBSHELL, false);
		btnJob.setNameWidget(txtJob);
		btnJob.addMenu(lblJob);
		btnJob.addMenu(txtJob);
		addControl(btnJob, "jobID");

		LLabel lblLevel = new LLabel(job, Vocab.instance.LEVEL, Tooltip.instance.LEVEL);
		lblLevel.getCellData().setRequiredSize(LPrefs.LABELWIDTH / 2, 0);
		LSpinner spnLevel = new LSpinner(job);
		spnLevel.getCellData().setExpand(true, false);
		spnLevel.getCellData().setTargetSize(LPrefs.BUTTONWIDTH * 2 - LPrefs.LABELWIDTH * 2, LPrefs.WIDGETHEIGHT);
		spnLevel.addMenu(lblLevel);
		addControl(spnLevel, "level");

		// Rewards

		LLabel lblMoney = new LLabel(contentEditor.grpGeneral, Vocab.instance.MONEY, Tooltip.instance.MONEY);
		LPanel rewards = new LPanel(contentEditor.grpGeneral);
		rewards.setGridLayout(3);
		rewards.getCellData().setExpand(true, false);

		LSpinner spnMoney = new LSpinner(rewards);
		spnMoney.getCellData().setTargetSize(LPrefs.BUTTONWIDTH * 2, LPrefs.WIDGETHEIGHT);
		spnMoney.getCellData().setExpand(true, false);
		spnMoney.setMaximum(99999999);
		spnMoney.addMenu(lblMoney);
		addControl(spnMoney, "money");
		
		LLabel lblExp = new LLabel(rewards, Vocab.instance.EXP, Tooltip.instance.EXP);
		lblExp.getCellData().setRequiredSize(LPrefs.LABELWIDTH / 2, 0);

		LSpinner spnEXP = new LSpinner(rewards);
		spnEXP.getCellData().setTargetSize(LPrefs.BUTTONWIDTH * 2 - LPrefs.LABELWIDTH * 2, LPrefs.WIDGETHEIGHT);
		spnEXP.getCellData().setExpand(true, false);
		spnEXP.setMaximum(99999999);
		spnEXP.addMenu(lblExp);
		addControl(spnEXP, "exp");

		// Properties

		LPanel check = new LPanel(contentEditor.grpGeneral);
		check.setGridLayout(2);
		check.getCellData().setExpand(true, false);
		check.getCellData().setAlignment(LFlags.LEFT);
		check.getCellData().setSpread(2, 1);

		LCheckBox btnPersistent = new LCheckBox(check);
		btnPersistent.setText(Vocab.instance.PERSISTENT);
		btnPersistent.setHoverText(Tooltip.instance.PERSISTENT);
		btnPersistent.getCellData().setExpand(true, false);
		addControl(btnPersistent, "persistent");

		LCheckBox btnRecruit = new LCheckBox(check);
		btnRecruit.setText(Vocab.instance.RECRUIT);
		btnRecruit.setHoverText(Tooltip.instance.RECRUIT);
		btnRecruit.getCellData().setExpand(true, false);
		addControl(btnRecruit, "persistent");

		// Attributes
		
		LFrame grpAtt = new LFrame(middle, Vocab.instance.ATTRIBUTES);
		grpAtt.setFillLayout(true);
		grpAtt.setHoverText(Tooltip.instance.BATTLERATT);
		grpAtt.getCellData().setExpand(true, true);
		grpAtt.getCellData().setRequiredSize(160, 0);
		AttributeEditor attEditor = new AttributeEditor(grpAtt, 2);
		attEditor.addMenu(grpAtt);
		addChild(attEditor, "attributes");

		// Elements
		
		LFrame grpElements = new LFrame(middle, Vocab.instance.ELEMENTS);
		grpElements.setFillLayout(true);
		grpElements.setHoverText(Tooltip.instance.ELEMENTDEF);
		grpElements.getCellData().setExpand(true, true);
		grpElements.getCellData().setRequiredSize(160, 0);
		lstElements = new PropertyList(grpElements, Vocab.instance.BATTLERELEMENTSHELL);
		lstElements.addMenu(grpElements);
		addChild(lstElements, "elements");
		
		// Skills
		
		LFrame grpSkills = new LFrame(bottom, Vocab.instance.INITSKILLS);
		grpSkills.setFillLayout(true);
		grpSkills.setHoverText(Tooltip.instance.INITSKILLS);
		grpSkills.getCellData().setExpand(true, true);
		lstSkills = new IDList(grpSkills, Vocab.instance.SKILLSHELL);
		lstSkills.addMenu(grpSkills);
		addChild(lstSkills, "skills");
		
		// Status
		
		LFrame grpStatus = new LFrame(bottom, Vocab.instance.INITSTATUS);
		grpStatus.setFillLayout(true);
		grpStatus.setHoverText(Tooltip.instance.INITSTATUS);
		grpStatus.getCellData().setExpand(true, true);
		lstStatus = new IDList(grpStatus, Vocab.instance.STATUSSHELL);
		lstStatus.addMenu(grpStatus);
		addChild(lstStatus, "status");
		
		// Equip
		
		LFrame grpEquip = new LFrame(contentEditor.right, Vocab.instance.INITEQUIP);
		grpEquip.setFillLayout(true);
		grpEquip.setHoverText(Tooltip.instance.INITEQUIP);
		grpEquip.getCellData().setExpand(true, true);
		EquipList lstEquip = new EquipList(grpEquip);
		lstEquip.addMenu(grpEquip);
		addChild(lstEquip, "equip");
		
		// Drop
		
		LFrame grpDrop = new LFrame(contentEditor.right, Vocab.instance.DROP);
		grpDrop.setFillLayout(true);
		grpDrop.setHoverText(Tooltip.instance.DROP);
		grpDrop.getCellData().setExpand(true, true);
		DropList lstDrop = new DropList(grpDrop);
		lstDrop.addMenu(grpDrop);
		addChild(lstDrop, "items");
		
		// AI

		LFrame grpAI = new LFrame(contentEditor.right, Vocab.instance.RULES);
		grpAI.setFillLayout(true);
		grpAI.setHoverText(Tooltip.instance.RULES);
		grpAI.getCellData().setExpand(true, true);
		SimpleEditableList<Rule> lstRules = new SimpleEditableList<>(grpAI);
		lstRules.type = Rule.class;
		lstRules.setIncludeID(false);
		lstRules.setShellFactory(new LWindowFactory<>() {
			@Override
			public LObjectDialog<Rule> createWindow(LWindow parent) {
				return new RuleDialog(parent);
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
