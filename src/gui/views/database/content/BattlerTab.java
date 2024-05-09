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
		LPanel middle = new LPanel(left);
		middle.setGridLayout(2);
		middle.getCellData().setExpand(true, true);
		LPanel bottom = new LPanel(left);
		bottom.setGridLayout(2);
		bottom.setEqualCells(true, false);
		bottom.getCellData().setExpand(true, true);
		
		// Icon
		
		LLabel lblIcon = new LLabel(grpGeneral, Vocab.instance.ICON, Tooltip.instance.ICON);
		LPanel compositeIcon = new LPanel(grpGeneral);
		compositeIcon.setGridLayout(2);
		compositeIcon.getCellData().setExpand(true, false);
		LImage imgIcon = new LImage(compositeIcon);
		imgIcon.getCellData().setMinimumSize(48, 48);
		imgIcon.getCellData().setExpand(true, true);
		imgIcon.setAlignment(LFlags.LEFT | LFlags.TOP);
		IconButton btnGraphics = new IconButton(compositeIcon, false);
		btnGraphics.setImageWidget(imgIcon);
		btnGraphics.addMenu(lblIcon);
		addControl(btnGraphics, "icon");
		
		// Description
		
		LLabel lblDesc = new LLabel(grpGeneral, LFlags.TOP, Vocab.instance.DESCRIPTION,
			Tooltip.instance.DESCRIPTION);
		LTextBox txtDescription = new LTextBox(grpGeneral);
		txtDescription.getCellData().setExpand(true, true);
		txtDescription.getCellData().setMinimumSize(0, 60);
		txtDescription.addMenu(lblDesc);
		addControl(txtDescription, "description");

		// Rewards + Job

		LPanel other = new LPanel(grpGeneral);
		other.setGridLayout(4);
		other.getCellData().setExpand(true, false);
		other.getCellData().setSpread(2, 1);

		// Rewards
		
		LLabel lblMoney = new LLabel(other, Vocab.instance.MONEY, Tooltip.instance.MONEY);
		lblMoney.getCellData().setMinimumSize(LABELWIDTH, 0);

		LSpinner spnMoney = new LSpinner(other);
		spnMoney.getCellData().setExpand(true, false);
		spnMoney.setMaximum(99999999);
		spnMoney.addMenu(lblMoney);
		addControl(spnMoney, "money");
		
		LLabel lblExp = new LLabel(other, Vocab.instance.EXP, Tooltip.instance.EXP);
		lblExp.getCellData().setMinimumSize(LABELWIDTH, 0);
		LSpinner spnEXP = new LSpinner(other);
		spnEXP.getCellData().setExpand(true, false);
		spnEXP.setMaximum(99999999);
		spnEXP.addMenu(lblExp);
		addControl(spnEXP, "exp");
		
		// Job
		
		LLabel lblJob = new LLabel(other, Vocab.instance.JOB, Tooltip.instance.JOB);
		LPanel job = new LPanel(other);
		job.setGridLayout(2);
		job.getCellData().setSpread(2, 1);
		job.getCellData().setAlignment(LFlags.FILL);
		LText txtJob = new LText(job, true);
		txtJob.getCellData().setExpand(true, false);
		btnJob = new IDButton(job, Vocab.instance.JOBSHELL, false);
		btnJob.setNameWidget(txtJob);
		btnJob.addMenu(lblJob);
		btnJob.addMenu(txtJob);
		addControl(btnJob, "jobID");

		LPanel level = new LPanel(other);
		level.setGridLayout(2);
		level.getCellData().setAlignment(LFlags.FILL);
		LLabel lblLevel = new LLabel(level, Vocab.instance.LEVEL, Tooltip.instance.LEVEL);
		lblLevel.getCellData().setMinimumSize(LABELWIDTH / 2, 0);
		LSpinner spnLevel = new LSpinner(level);
		spnLevel.getCellData().setExpand(true, false);
		spnLevel.addMenu(lblLevel);
		addControl(spnLevel, "level");

		// Properties

		LPanel check = new LPanel(grpGeneral);
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
		grpAtt.getCellData().setMinimumSize(220, 0);
		AttributeEditor attEditor = new AttributeEditor(grpAtt, 2);
		attEditor.addMenu(grpAtt);
		addChild(attEditor, "attributes");

		// Elements
		
		LFrame grpElements = new LFrame(middle, Vocab.instance.ELEMENTS);
		grpElements.setFillLayout(true);
		grpElements.setHoverText(Tooltip.instance.ELEMENTDEF);
		grpElements.getCellData().setExpand(true, true);
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
		
		LFrame grpStatus = new LFrame(bottom, (String) Vocab.instance.INITSTATUS);
		grpStatus.setFillLayout(true);
		grpStatus.setHoverText(Tooltip.instance.INITSTATUS);
		grpStatus.getCellData().setExpand(true, true);
		lstStatus = new IDList(grpStatus, Vocab.instance.STATUSSHELL);
		lstStatus.addMenu(grpStatus);
		addChild(lstStatus, "status");
		
		// Equip
		
		LFrame grpEquip = new LFrame(right, (String) Vocab.instance.INITEQUIP);
		grpEquip.setFillLayout(true);
		grpEquip.setHoverText(Tooltip.instance.INITEQUIP);
		grpEquip.getCellData().setExpand(true, true);
		EquipList lstEquip = new EquipList(grpEquip);
		lstEquip.addMenu(grpEquip);
		addChild(lstEquip, "equip");
		
		// Drop
		
		LFrame grpDrop = new LFrame(right, Vocab.instance.DROP);
		grpDrop.setFillLayout(true);
		grpDrop.setHoverText(Tooltip.instance.DROP);
		grpDrop.getCellData().setExpand(true, true);
		DropList lstDrop = new DropList(grpDrop);
		lstDrop.addMenu(grpDrop);
		addChild(lstDrop, "items");
		
		// AI

		LFrame grpAI = new LFrame(right, Vocab.instance.RULES);
		grpAI.setFillLayout(true);
		grpAI.setHoverText(Tooltip.instance.RULES);
		grpAI.getCellData().setExpand(true, true);
		SimpleEditableList<Rule> lstRules = new SimpleEditableList<Rule>(grpAI);
		lstRules.type = Rule.class;
		lstRules.setIncludeID(false);
		lstRules.setShellFactory(new LWindowFactory<Rule>() {
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
