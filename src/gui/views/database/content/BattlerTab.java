package gui.views.database.content;

import gson.project.GObjectTreeSerializer;
import gui.Vocab;
import gui.shell.database.RuleShell;
import gui.views.database.DatabaseTab;
import gui.views.database.subcontent.AttributeEditor;
import gui.views.database.subcontent.BonusList;
import gui.views.database.subcontent.DropList;
import gui.views.database.subcontent.EquipList;
import gui.views.database.subcontent.TagList;
import gui.widgets.IDButton;
import gui.widgets.IDList;
import gui.widgets.SimpleEditableList;
import lwt.dataestructure.LDataTree;
import lwt.dialog.LObjectShell;
import lwt.dialog.LShellFactory;
import lwt.widget.LCheckBox;
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

import project.Project;

import data.subcontent.Rule;

public class BattlerTab extends DatabaseTab {
	
	public BattlerTab(Composite parent) {
		super(parent);
		
		new LLabel(grpGeneral, 1);
		LCheckBox btnPersistent = new LCheckBox(grpGeneral, SWT.NONE);
		btnPersistent.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1));
		btnPersistent.setText(Vocab.instance.PERSISTENT);
		addControl(btnPersistent, "persistent");
		
		// Level
		
		new LLabel(grpGeneral, Vocab.instance.LEVEL);

		LSpinner spnLevel = new LSpinner(grpGeneral, SWT.NONE);
		spnLevel.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, false, false, 1, 1));
		addControl(spnLevel, "level");
		
		// Rewards
		
		new LLabel(grpGeneral, Vocab.instance.MONEY);
		
		Composite compositeReward = new Composite(grpGeneral, SWT.NONE);
		GridLayout gl_compositeReward = new GridLayout(3, false);
		gl_compositeReward.marginWidth = 0;
		gl_compositeReward.marginHeight = 0;
		compositeReward.setLayout(gl_compositeReward);
		compositeReward.setLayoutData(new GridData(SWT.FILL, SWT.FILL, false, false, 1, 1));
		
		LSpinner spnMoney = new LSpinner(compositeReward, SWT.NONE);
		spnMoney.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1));
		addControl(spnMoney, "money");
		
		new LLabel(compositeReward, Vocab.instance.EXP);
		
		LSpinner spnEXP = new LSpinner(compositeReward, SWT.NONE);
		spnEXP.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1));
		addControl(spnEXP, "exp");
		
		Composite select = new Composite(grpGeneral, SWT.NONE);
		select.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 2, 1));
		GridLayout gl_select = new GridLayout(3, false);
		gl_select.marginWidth = 0;
		gl_select.marginHeight = 0;
		select.setLayout(gl_select);
		
		// Job
		
		new LLabel(select, Vocab.instance.JOB);
		
		LText txtJob = new LText(select, true);
		txtJob.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1));
		
		IDButton btnJob = new IDButton(select, 0) {
			@Override
			public LDataTree<Object> getDataTree() {
				return Project.current.jobs.getTree();
			}
		};
		btnJob.setNameWidget(txtJob);
		addControl(btnJob, "jobID");
		
		// Attack Skill
		
		new LLabel(select, Vocab.instance.ATTACKSKILL);
		
		LText txtAttack = new LText(select, true);
		txtAttack.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1));
		
		IDButton btnAttack = new IDButton(select, 0) {
			@Override
			public LDataTree<Object> getDataTree() {
				return Project.current.skills.getTree();
			}
		};
		btnAttack.setNameWidget(txtAttack);
		addControl(btnAttack, "attackID");
		
		// AI
		
		Composite side = new Composite(contentEditor, SWT.NONE);
		side.setLayout(new FillLayout(SWT.VERTICAL));
		side.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, false, 1, 1));
		
		Group grpAI = new Group(side, SWT.NONE);
		grpAI.setLayout(new FillLayout(SWT.HORIZONTAL));
		grpAI.setText(Vocab.instance.AI);

		SimpleEditableList<Rule> lstRules = new SimpleEditableList<Rule>(grpAI, SWT.NONE);
		lstRules.type = Rule.class;
		lstRules.setIncludeID(false);
		lstRules.setShellFactory(new LShellFactory<Rule>() {
			@Override
			public LObjectShell<Rule> createShell(Shell parent) {
				return new RuleShell(parent);
			}
		});
		addChild(lstRules, "ai");
		
		// Tags
		
		Group grpTags = new Group(side, SWT.NONE);
		grpTags.setLayout(new FillLayout(SWT.HORIZONTAL));
		grpTags.setText(Vocab.instance.TAGS);
		
		TagList lstTags = new TagList(grpTags, SWT.NONE);
		addChild(lstTags, "tags");
		
		// Elements
		
		Composite bottom = new Composite(contentEditor, SWT.NONE);
		GridLayout gl_other = new GridLayout(3, true);
		gl_other.verticalSpacing = 0;
		gl_other.marginHeight = 0;
		gl_other.marginWidth = 0;
		bottom.setLayout(gl_other);
		bottom.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true, 2, 1));
		
		Group grpElements = new Group(bottom, SWT.NONE);
		grpElements.setLayout(new FillLayout(SWT.HORIZONTAL));
		grpElements.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true, 1, 1));
		grpElements.setText(Vocab.instance.ELEMENTS);
		
		BonusList lstElements = new BonusList(grpElements, SWT.NONE) {
			@Override
			protected LDataTree<Object> getDataTree() {
				return Project.current.elements.getList().toTree();
			}
		};
		addChild(lstElements, "elements");
		
		// Initial state
		
		Composite compInitial = new Composite(bottom, SWT.NONE);
		GridLayout gl_compInitial = new GridLayout(1, false);
		gl_compInitial.verticalSpacing = 0;
		compInitial.setLayout(gl_compInitial);
		compInitial.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true, 1, 2));
		
		// Skills
		
		Group grpSkills = new Group(compInitial, SWT.NONE);
		grpSkills.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true, 1, 1));
		grpSkills.setText(Vocab.instance.SKILLS + " (" + Vocab.instance.INITIAL + ")");
		grpSkills.setLayout(new FillLayout(SWT.HORIZONTAL));
		
		IDList lstSkills = new IDList(grpSkills, SWT.NONE) {
			public LDataTree<Object> getDataTree() {
				return Project.current.skills.getTree();
			}
		};
		addChild(lstSkills, "skills");
		
		// Status
		
		Group grpStatus = new Group(compInitial, SWT.NONE);
		grpStatus.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true, 1, 1));
		grpStatus.setText(Vocab.instance.STATUS + " (" + Vocab.instance.INITIAL + ")");
		grpStatus.setLayout(new FillLayout(SWT.HORIZONTAL));
		
		IDList lstStatus = new IDList(grpStatus, SWT.NONE) {
			public LDataTree<Object> getDataTree() {
				return Project.current.status.getTree();
			}
		};
		addChild(lstStatus, "status");
		
		// Equip
		
		Group grpEquip = new Group(compInitial, SWT.NONE);
		grpEquip.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true, 1, 1));
		grpEquip.setText(Vocab.instance.EQUIP + " (" + Vocab.instance.INITIAL + ")");
		grpEquip.setLayout(new FillLayout(SWT.HORIZONTAL));
		
		EquipList lstEquip = new EquipList(grpEquip, SWT.NONE);
		addChild(lstEquip, "equip");

		// Attributes
		
		Group grpAtt = new Group(bottom, SWT.NONE);
		grpAtt.setLayout(new FillLayout(SWT.HORIZONTAL));
		GridData gd_grpAtt = new GridData(SWT.FILL, SWT.FILL, true, false, 1, 2);
		gd_grpAtt.heightHint = 100;
		grpAtt.setLayoutData(gd_grpAtt);
		grpAtt.setText(Vocab.instance.ATTRIBUTES);

		AttributeEditor attEditor = new AttributeEditor(grpAtt, SWT.NONE);
		attEditor.setColumns(4);
		addChild(attEditor, "attributes");
		
		// Drop
		
		Group grpDrop = new Group(bottom, SWT.NONE);
		grpDrop.setLayout(new FillLayout(SWT.HORIZONTAL));
		grpDrop.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true, 1, 1));
		grpDrop.setText(Vocab.instance.DROP);
		
		DropList lstDrop = new DropList(grpDrop, SWT.NONE);
		addChild(lstDrop, "items");
		
	}

	@Override
	protected GObjectTreeSerializer getSerializer() {
		return Project.current.battlers;
	}

}
