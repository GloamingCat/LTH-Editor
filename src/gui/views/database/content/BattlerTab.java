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

import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Group;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Text;

import project.Project;

import org.eclipse.swt.widgets.TabFolder;
import org.eclipse.swt.widgets.TabItem;

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
		
		// Class
		
		new LLabel(select, Vocab.instance.CLASS);
		
		Text txtClass = new Text(select, SWT.BORDER | SWT.READ_ONLY);
		txtClass.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1));
		
		IDButton btnClass = new IDButton(select, 0) {
			@Override
			public LDataTree<Object> getDataTree() {
				return Project.current.classes.getTree();
			}
		};
		btnClass.setNameText(txtClass);
		addControl(btnClass, "classID");
		
		// Attack Skill
		
		new LLabel(select, Vocab.instance.ATTACKSKILL);
		
		Text txtAttack = new Text(select, SWT.BORDER | SWT.READ_ONLY);
		txtAttack.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1));
		
		IDButton btnAttack = new IDButton(select, 0) {
			@Override
			public LDataTree<Object> getDataTree() {
				return Project.current.skills.getTree();
			}
		};
		btnAttack.setNameText(txtAttack);
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
		
		// Initial state
		
		Composite bottom = new Composite(contentEditor, SWT.NONE);
		GridLayout gl_other = new GridLayout(3, false);
		gl_other.marginHeight = 0;
		gl_other.marginWidth = 0;
		bottom.setLayout(gl_other);
		bottom.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true, 2, 1));
		
		Group grpInitial = new Group(bottom, SWT.NONE);
		grpInitial.setLayout(new FillLayout(SWT.HORIZONTAL));
		GridData gd_grpInitial = new GridData(SWT.FILL, SWT.FILL, true, true, 1, 1);
		gd_grpInitial.widthHint = 129;
		grpInitial.setLayoutData(gd_grpInitial);
		grpInitial.setText(Vocab.instance.INITIAL);
		
		TabFolder tabInitial = new TabFolder(grpInitial, SWT.NONE);
		
		// Skills
		
		TabItem tbtmSkills = new TabItem(tabInitial, SWT.NONE);
		tbtmSkills.setText(Vocab.instance.SKILLS);
		
		IDList lstSkills = new IDList(tabInitial, SWT.NONE) {
			public LDataTree<Object> getDataTree() {
				return Project.current.skills.getTree();
			}
		};
		tbtmSkills.setControl(lstSkills);
		addChild(lstSkills, "skills");
		
		// Status
		
		TabItem tbtmStatus = new TabItem(tabInitial, SWT.NONE);
		tbtmStatus.setText(Vocab.instance.STATUS);
		
		BonusList lstStatus = new BonusList(tabInitial, SWT.NONE) {
			@Override
			protected LDataTree<Object> getDataTree() {
				return Project.current.status.getTree();
			}
		};
		tbtmStatus.setControl(lstStatus);
		addChild(lstStatus, "status");
		
		// Elements
		
		TabItem tbtmElements = new TabItem(tabInitial, SWT.NONE);
		tbtmElements.setText(Vocab.instance.ELEMENTS);
		
		BonusList lstElements = new BonusList(tabInitial, SWT.NONE) {
			@Override
			protected LDataTree<Object> getDataTree() {
				return Project.current.elements.getList().toTree();
			}
		};
		tbtmElements.setControl(lstElements);
		addChild(lstElements, "elements");
		
		// Items
		
		Group grpItems = new Group(bottom, SWT.NONE);
		grpItems.setLayout(new FillLayout(SWT.HORIZONTAL));
		grpItems.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true, 1, 1));
		grpItems.setText(Vocab.instance.ITEMS);
		
		TabFolder tabItems = new TabFolder(grpItems, SWT.NONE);
		
		// Drop
		
		TabItem tbtmDrop = new TabItem(tabItems, SWT.NONE);
		tbtmDrop.setText(Vocab.instance.DROP);
		
		DropList lstDrop = new DropList(tabItems, SWT.NONE);
		tbtmDrop.setControl(lstDrop);
		addChild(lstDrop, "items");
		
		// Equip
		
		TabItem tbtmEquip = new TabItem(tabItems, SWT.NONE);
		tbtmEquip.setText(Vocab.instance.EQUIP);
		
		EquipList lstEquip = new EquipList(tabItems, SWT.NONE);
		tbtmEquip.setControl(lstEquip);
		addChild(lstEquip, "equip");

		// Attributes
		
		Group grpAtt = new Group(bottom, SWT.NONE);
		grpAtt.setLayout(new FillLayout(SWT.HORIZONTAL));
		GridData gd_grpAtt = new GridData(SWT.FILL, SWT.FILL, true, false, 1, 1);
		gd_grpAtt.heightHint = 100;
		grpAtt.setLayoutData(gd_grpAtt);
		grpAtt.setText(Vocab.instance.ATTRIBUTES);

		AttributeEditor attEditor = new AttributeEditor(grpAtt, SWT.NONE);
		attEditor.setColumns(4);
		addChild(attEditor, "attributes");
		
	}

	@Override
	protected GObjectTreeSerializer getSerializer() {
		return Project.current.battlers;
	}

}
