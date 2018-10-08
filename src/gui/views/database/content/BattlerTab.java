package gui.views.database.content;

import gson.project.GObjectTreeSerializer;
import gui.Vocab;
import gui.views.database.DatabaseTab;
import gui.views.database.subcontent.AttributeEditor;
import gui.views.database.subcontent.BonusList;
import gui.views.database.subcontent.EquipList;
import gui.views.database.subcontent.TagList;
import gui.widgets.IDButton;
import gui.widgets.IDList;
import lwt.dataestructure.LDataTree;
import lwt.widget.LCheckButton;
import lwt.widget.LSpinner;

import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Group;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Text;

import data.config.Config;
import project.Project;

import org.eclipse.swt.widgets.TabFolder;
import org.eclipse.swt.widgets.TabItem;

public class BattlerTab extends DatabaseTab {
	
	public BattlerTab(Composite parent, int style) {
		super(parent, style);
		
		contentEditor.setLayout(new GridLayout(2, false));
		
		new Label(grpGeneral,  SWT.NONE);
		LCheckButton btnPersistent = new LCheckButton(grpGeneral, SWT.NONE);
		btnPersistent.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1));
		btnPersistent.setText(Vocab.instance.PERSISTENT);
		addControl(btnPersistent, "persistent");
		
		Composite select = new Composite(grpGeneral, SWT.NONE);
		select.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 2, 1));
		GridLayout gl_select = new GridLayout(3, false);
		gl_select.marginWidth = 0;
		gl_select.marginHeight = 0;
		select.setLayout(gl_select);
		
		// AI
		
		Label lblAI = new Label(select, SWT.NONE);
		lblAI.setText(Vocab.instance.AI);
		
		Text txtAI = new Text(select, SWT.BORDER | SWT.READ_ONLY);
		txtAI.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1));
		
		IDButton btnAI = new IDButton(select, SWT.NONE) {
			@Override
			public LDataTree<Object> getDataTree() {
				return Project.current.scripts.getTree();
			}
		};
		btnAI.optional = false;
		btnAI.setNameText(txtAI);
		addControl(btnAI, "scriptID");
		
		// Class
		
		Label lblClass = new Label(select, SWT.NONE);
		lblClass.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, false, false, 1, 1));
		lblClass.setText(Vocab.instance.CLASS);
		
		Text txtClass = new Text(select, SWT.BORDER | SWT.READ_ONLY);
		txtClass.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1));
		
		IDButton btnClass = new IDButton(select, SWT.NONE) {
			@Override
			public LDataTree<Object> getDataTree() {
				return Project.current.classes.getTree();
			}
		};
		btnClass.optional = true;
		btnClass.setNameText(txtClass);
		addControl(btnClass, "classID");
		
		// Attack Skill
		
		Label lblAttackSkill = new Label(select, SWT.NONE);
		lblAttackSkill.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, false, false, 1, 1));
		lblAttackSkill.setText(Vocab.instance.ATTACKSKILL);
		
		Text txtAttack = new Text(select, SWT.BORDER | SWT.READ_ONLY);
		txtAttack.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1));
		
		IDButton btnAttack = new IDButton(select, SWT.NONE) {
			@Override
			public LDataTree<Object> getDataTree() {
				return Project.current.skills.getTree();
			}
		};
		btnAttack.optional = true;
		btnAttack.setNameText(txtAttack);
		addControl(btnAttack, "attackID");
		
		// Level
		
		Label lblLevel = new Label(grpGeneral, SWT.NONE);
		lblLevel.setText(Vocab.instance.LEVEL);
		
		LSpinner spnLevel = new LSpinner(grpGeneral, SWT.NONE);
		spnLevel.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, false, false, 1, 1));
		addControl(spnLevel, "level");
		
		// Rewards
		
		Label lblMoney = new Label(grpGeneral, SWT.NONE);
		lblMoney.setText(Vocab.instance.MONEY);
		
		Composite compositeReward = new Composite(grpGeneral, SWT.NONE);
		GridLayout gl_compositeReward = new GridLayout(3, false);
		gl_compositeReward.marginWidth = 0;
		gl_compositeReward.marginHeight = 0;
		compositeReward.setLayout(gl_compositeReward);
		compositeReward.setLayoutData(new GridData(SWT.FILL, SWT.FILL, false, false, 1, 1));
		
		LSpinner spnMoney = new LSpinner(compositeReward, SWT.NONE);
		spnMoney.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1));
		addControl(spnMoney, "money");
		
		Label lblExp = new Label(compositeReward, SWT.NONE);
		lblExp.setText(Vocab.instance.EXP);
		
		LSpinner spnEXP = new LSpinner(compositeReward, SWT.NONE);
		spnEXP.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1));
		addControl(spnEXP, "exp");
		
		Group grpAtt = new Group(contentEditor, SWT.NONE);
		grpAtt.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, false, 1, 1));
		grpAtt.setLayout(new GridLayout(1, true));
		grpAtt.setText(Vocab.instance.ATTRIBUTES);
		
		Composite build = new Composite(grpAtt, SWT.NONE);
		build.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, false, 1, 1));
		GridLayout gl_build = new GridLayout(1, false);
		gl_build.marginWidth = 0;
		gl_build.marginHeight = 0;
		build.setLayout(gl_build);
		
		AttributeEditor attEditor = new AttributeEditor(grpAtt, SWT.NONE);
		attEditor.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true, 1, 1));		
		addChild(attEditor);
		
		Composite other = new Composite(contentEditor, SWT.NONE);
		GridLayout gl_other = new GridLayout(3, false);
		gl_other.marginHeight = 0;
		gl_other.marginWidth = 0;
		other.setLayout(gl_other);
		other.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true, 2, 1));
		
		Group grpInitial = new Group(other, SWT.NONE);
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
		lstStatus.attributeName = "status";
		tbtmStatus.setControl(lstStatus);
		addChild(lstStatus);
		
		// Elements
		
		TabItem tbtmElements = new TabItem(tabInitial, SWT.NONE);
		tbtmElements.setText(Vocab.instance.ELEMENTS);
		
		BonusList lstElements = new BonusList(tabInitial, SWT.NONE) {
			@Override
			protected LDataTree<Object> getDataTree() {
				Config conf = (Config) Project.current.config.getData();
				return conf.elements.toObjectTree();
			}
		};
		lstElements.attributeName = "elements";
		tbtmElements.setControl(lstElements);
		addChild(lstElements);
		
		// Items
		
		Group grpItems = new Group(other, SWT.NONE);
		grpItems.setLayout(new FillLayout(SWT.HORIZONTAL));
		grpItems.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true, 1, 1));
		grpItems.setText(Vocab.instance.ITEMS);
		
		TabFolder tabItems = new TabFolder(grpItems, SWT.NONE);
		
		// Drop
		
		TabItem tbtmDrop = new TabItem(tabItems, SWT.NONE);
		tbtmDrop.setText(Vocab.instance.DROP);
		
		BonusList lstDrop = new BonusList(tabItems, SWT.NONE) {
			@Override
			protected LDataTree<Object> getDataTree() {
				return Project.current.items.getTree();
			}
		};
		lstDrop.attributeName = "items";
		tbtmDrop.setControl(lstDrop);
		addChild(lstDrop);
		
		// Equip
		
		TabItem tbtmEquip = new TabItem(tabItems, SWT.NONE);
		tbtmEquip.setText(Vocab.instance.EQUIP);
		
		EquipList lstEquip = new EquipList(tabItems, SWT.NONE);
		tbtmEquip.setControl(lstEquip);
		addChild(lstEquip);

		// Tags
		
		Group grpTags = new Group(other, SWT.NONE);
		grpTags.setLayout(new FillLayout(SWT.HORIZONTAL));
		grpTags.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true, 1, 1));
		grpTags.setText(Vocab.instance.TAGS);
		
		TagList tagEditor = new TagList(grpTags, SWT.NONE);
		addChild(tagEditor);
		
	}

	@Override
	protected GObjectTreeSerializer getSerializer() {
		return Project.current.battlers;
	}

}
