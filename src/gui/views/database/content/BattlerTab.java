package gui.views.database.content;

import gson.project.GObjectTreeSerializer;
import gui.Vocab;
import gui.shell.database.RuleShell;
import gui.views.database.DatabaseTab;
import gui.views.database.subcontent.AttributeEditor;
import gui.views.database.subcontent.BonusList;
import gui.views.database.subcontent.DropList;
import gui.views.database.subcontent.EquipList;
import gui.widgets.IDButton;
import gui.widgets.IDList;
import gui.widgets.IconButton;
import gui.widgets.SimpleEditableList;
import lwt.dataestructure.LDataTree;
import lwt.dialog.LObjectShell;
import lwt.dialog.LShellFactory;
import lwt.widget.LCheckBox;
import lwt.widget.LImage;
import lwt.widget.LLabel;
import lwt.widget.LSpinner;
import lwt.widget.LText;
import lwt.widget.LTextBox;

import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Group;
import org.eclipse.swt.widgets.Shell;

import project.Project;
import data.Battler;
import data.subcontent.Rule;

public class BattlerTab extends DatabaseTab<Battler> {
	
	public BattlerTab(Composite parent) {
		super(parent);

		// Icon
		
		new LLabel(grpGeneral, Vocab.instance.ICON);
		
		Composite compositeIcon = new Composite(grpGeneral, SWT.NONE);
		GridLayout gl_compositeIcon = new GridLayout(2, false);
		gl_compositeIcon.marginWidth = 0;
		gl_compositeIcon.marginHeight = 0;
		compositeIcon.setLayout(gl_compositeIcon);
		compositeIcon.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, false, false, 1, 1));
		
		LImage imgIcon = new LImage(compositeIcon, SWT.NONE);
		imgIcon.setImage("/javax/swing/plaf/basic/icons/image-delayed.png");
		GridData gd_imgIcon = new GridData(SWT.FILL, SWT.FILL, true, true, 1, 1);
		gd_imgIcon.widthHint = 48;
		gd_imgIcon.heightHint = 48;
		imgIcon.setVerticalAlign(SWT.CENTER);
		imgIcon.setLayoutData(gd_imgIcon);
		
		IconButton btnGraphics = new IconButton(compositeIcon, 0);
		btnGraphics.setImageWidget(imgIcon);
		addControl(btnGraphics, "icon");
		
		// Description
		
		new LLabel(grpGeneral, Vocab.instance.DESCRIPTION).setLayoutData(new GridData(SWT.LEFT, SWT.FILL, false, false, 1, 1));
		
		LTextBox txtDescription = new LTextBox(grpGeneral);
		GridData gd_txtDescription = new GridData(SWT.FILL, SWT.FILL, true, true, 1, 1);
		gd_txtDescription.heightHint = 60;
		gd_txtDescription.minimumHeight = 60;
		txtDescription.setLayoutData(gd_txtDescription);
		addControl(txtDescription, "description");
		
		// Properties
		
		new LLabel(grpGeneral, 1);
		
		Composite check = new Composite(grpGeneral, SWT.NONE);
		check.setLayout(new FillLayout(SWT.HORIZONTAL));
		check.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1));
		
		LCheckBox btnPersistent = new LCheckBox(check, SWT.NONE);
		btnPersistent.setText(Vocab.instance.PERSISTENT);
		addControl(btnPersistent, "persistent");
		
		LCheckBox btnRecruit = new LCheckBox(check, SWT.NONE);
		btnRecruit.setText(Vocab.instance.RECRUIT);
		addControl(btnRecruit, "persistent");
		
		// Rewards
		
		new LLabel(grpGeneral, Vocab.instance.MONEY);
		
		Composite compositeReward = new Composite(grpGeneral, SWT.NONE);
		GridLayout gl_compositeReward = new GridLayout(3, false);
		gl_compositeReward.marginWidth = 0;
		gl_compositeReward.marginHeight = 0;
		compositeReward.setLayout(gl_compositeReward);
		compositeReward.setLayoutData(new GridData(SWT.FILL, SWT.FILL, false, false, 1, 1));
		
		LSpinner spnMoney = new LSpinner(compositeReward, SWT.NONE);
		spnMoney.setMaximum(99999999);
		spnMoney.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1));
		addControl(spnMoney, "money");
		
		new LLabel(compositeReward, Vocab.instance.EXP);
		
		LSpinner spnEXP = new LSpinner(compositeReward, SWT.NONE);
		spnEXP.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1));
		spnEXP.setMaximum(99999999);
		addControl(spnEXP, "exp");
		
		// Job
		
		new LLabel(grpGeneral, Vocab.instance.JOB);
		
		Composite job = new Composite(grpGeneral, SWT.NONE);
		job.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1));
		GridLayout gl_job = new GridLayout(4, false);
		gl_job.marginHeight = 0;
		gl_job.marginWidth = 0;
		job.setLayout(gl_job);
		
		LText txtJob = new LText(job, true);
		txtJob.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1));
		
		IDButton btnJob = new IDButton(job, 0) {
			@Override
			public LDataTree<Object> getDataTree() {
				return Project.current.jobs.getTree();
			}
		};
		btnJob.setNameWidget(txtJob);
		addControl(btnJob, "jobID");
		
		new LLabel(job, Vocab.instance.LEVEL);

		LSpinner spnLevel = new LSpinner(job, SWT.NONE);
		spnLevel.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1));
		addControl(spnLevel, "level");
		
		// Attributes
		
		Composite middle = new Composite(left, SWT.NONE);
		GridLayout gl_middle = new GridLayout(2, false);
		gl_middle.marginHeight = 0;
		gl_middle.marginWidth = 0;
		middle.setLayout(gl_middle);
		middle.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true, 1, 1));
		
		Group grpAtt = new Group(middle, SWT.NONE);
		grpAtt.setLayout(new FillLayout(SWT.HORIZONTAL));
		GridData gd_grpAtt = new GridData(SWT.FILL, SWT.FILL, true, true, 1, 3);
		gd_grpAtt.heightHint = 200;
		gd_grpAtt.widthHint = 240;
		
		grpAtt.setLayoutData(gd_grpAtt);
		grpAtt.setText(Vocab.instance.ATTRIBUTES);

		AttributeEditor attEditor = new AttributeEditor(grpAtt, SWT.NONE);
		attEditor.setColumns(4);
		addChild(attEditor, "attributes");

		// Elements
		
		Group grpElements = new Group(middle, SWT.NONE);
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
		
		Composite bottom = new Composite(left, SWT.NONE);
		GridLayout gl_bottom = new GridLayout(2, true);
		gl_bottom.marginHeight = 0;
		gl_bottom.marginWidth = 0;
		bottom.setLayout(gl_bottom);
		bottom.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true, 1, 1));
		
		// Skills
		
		Group grpSkills = new Group(bottom, SWT.NONE);
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
		
		Group grpStatus = new Group(bottom, SWT.NONE);
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
		
		Group grpEquip = new Group(right, SWT.NONE);
		grpEquip.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true, 1, 1));
		grpEquip.setText(Vocab.instance.EQUIP + " (" + Vocab.instance.INITIAL + ")");
		grpEquip.setLayout(new FillLayout(SWT.HORIZONTAL));
		
		EquipList lstEquip = new EquipList(grpEquip, SWT.NONE);
		addChild(lstEquip, "equip");
		
		// Drop
		
		Group grpDrop = new Group(right, SWT.NONE);
		grpDrop.setLayout(new FillLayout(SWT.HORIZONTAL));
		grpDrop.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true, 1, 1));
		grpDrop.setText(Vocab.instance.DROP);
		
		DropList lstDrop = new DropList(grpDrop, SWT.NONE);
		addChild(lstDrop, "items");
		
		// AI

		Group grpAI = new Group(right, SWT.NONE);
		grpAI.setLayout(new FillLayout(SWT.HORIZONTAL));
		grpAI.setText(Vocab.instance.AI);
		grpAI.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true, 1, 1));

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
		
	}

	@Override
	protected GObjectTreeSerializer getSerializer() {
		return Project.current.battlers;
	}

}
