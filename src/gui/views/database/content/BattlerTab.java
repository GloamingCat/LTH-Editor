package gui.views.database.content;

import gson.project.GObjectTreeSerializer;
import gui.Vocab;
import gui.views.database.DatabaseTab;
import gui.views.database.subcontent.AttributeEditor;
import gui.views.database.subcontent.BonusList;
import gui.views.database.subcontent.TagList;
import gui.widgets.ScriptButton;

import java.util.ArrayList;

import lwt.dataestructure.LDataTree;
import lwt.editor.LComboView;
import lwt.event.LControlEvent;
import lwt.event.LSelectionEvent;
import lwt.event.listener.LControlListener;
import lwt.event.listener.LSelectionListener;
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
import org.eclipse.wb.swt.SWTResourceManager;

import data.Animation;
import data.Battler;
import data.GameCharacter;
import data.config.Config;
import project.Project;

public class BattlerTab extends DatabaseTab {

	private Label image;
	
	public BattlerTab(Composite parent, int style) {
		super(parent, style);
		
		contentEditor.setLayout(new GridLayout(2, false));
		
		Label lblBattleChar = new Label(grpGeneral, SWT.NONE);
		lblBattleChar.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, false, false, 1, 1));
		lblBattleChar.setText(Vocab.instance.BATTLECHAR);
		
		LComboView cmbBattleChar = new LComboView(grpGeneral, SWT.NONE) {
			public ArrayList<?> getArray() {
				//TODO return Project.current.characters.getList();
				return null;
			}
		};
		cmbBattleChar.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1));
		cmbBattleChar.setOptional(false);
		addControl(cmbBattleChar, "battleCharID");
		
		Label lblFieldChar = new Label(grpGeneral, SWT.NONE);
		lblFieldChar.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, false, false, 1, 1));
		lblFieldChar.setText(Vocab.instance.FIELDCHAR);
		
		LComboView cmbFieldChar = new LComboView(grpGeneral, SWT.NONE) {
			public ArrayList<?> getArray() {
				//TODO return Project.current.characters.getList();
				return null;
			}
		};
		cmbFieldChar.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1));
		cmbFieldChar.setOptional(true);
		addControl(cmbFieldChar, "fieldCharID");

		Label lblSkillTree = new Label(grpGeneral, SWT.NONE);
		lblSkillTree.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, false, false, 1, 1));
		lblSkillTree.setText(Vocab.instance.SKILLDAG);
		
		LComboView cmbClass = new LComboView(grpGeneral, SWT.READ_ONLY) {
			public ArrayList<?> getArray() {
				//TODO return Project.current.classes.getList();
				return null;
			}
		};
		cmbClass.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1));
		cmbClass.setOptional(true);
		addControl(cmbClass, "classID");
		
		Label lblAttackSkill = new Label(grpGeneral, SWT.NONE);
		lblAttackSkill.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, false, false, 1, 1));
		lblAttackSkill.setText(Vocab.instance.ATTACKSKILL);
		
		LComboView cmbAttackSkill = new LComboView(grpGeneral, SWT.READ_ONLY) {
			public ArrayList<Object> getArray() {
				//TODO return Project.current.skills.getList();
				return null;
			}
		};
		cmbAttackSkill.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1));
		cmbAttackSkill.setOptional(false);
		addControl(cmbAttackSkill, "attackID");
		
		Label lblPersistent = new Label(grpGeneral,  SWT.NONE);
		lblPersistent.setText(Vocab.instance.PERSISTENT);
		
		LCheckButton btnPersistent = new LCheckButton(grpGeneral, SWT.NONE);
		btnPersistent.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1));
		addControl(btnPersistent, "persistent");
		
		Composite compositeAI = new Composite(grpGeneral, SWT.NONE);
		GridLayout gl_compositeAI = new GridLayout(3, false);
		gl_compositeAI.marginWidth = 0;
		gl_compositeAI.marginHeight = 0;
		compositeAI.setLayout(gl_compositeAI);
		compositeAI.setLayoutData(new GridData(SWT.FILL, SWT.FILL, false, false, 2, 1));
		
		Label lblAI = new Label(compositeAI, SWT.NONE);
		lblAI.setText(Vocab.instance.AI);
		
		Text txtAI = new Text(compositeAI, SWT.BORDER | SWT.READ_ONLY);
		txtAI.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1));
		
		ScriptButton btnSelect = new ScriptButton(compositeAI, SWT.NONE);
		addScriptButton(btnSelect, txtAI, "ai", "scriptAI");
		
		Composite compositeReward = new Composite(grpGeneral, SWT.NONE);
		GridLayout gl_compositeReward = new GridLayout(4, false);
		gl_compositeReward.marginWidth = 0;
		gl_compositeReward.marginHeight = 0;
		compositeReward.setLayout(gl_compositeReward);
		compositeReward.setLayoutData(new GridData(SWT.FILL, SWT.FILL, false, false, 2, 1));
		
		Label lblMoney = new Label(compositeReward, SWT.NONE);
		lblMoney.setText(Vocab.instance.MONEY);
		
		LSpinner spnMoney = new LSpinner(compositeReward, SWT.NONE);
		spnMoney.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1));
		addControl(spnMoney, "money");
		
		Label lblExp = new Label(compositeReward, SWT.NONE);
		lblExp.setText(Vocab.instance.EXP);
		
		LSpinner spnEXP = new LSpinner(compositeReward, SWT.NONE);
		spnEXP.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1));
		addControl(spnEXP, "exp");
		
		grpGeneral.pack();
		
		image = new Label(grpGeneral, SWT.NONE);
		image.setAlignment(SWT.CENTER);
		GridData gd_image = new GridData(SWT.FILL, SWT.FILL, true, true, 2, 1);
		gd_image.heightHint = 48;
		image.setLayoutData(gd_image);
		
		cmbBattleChar.getControl().addModifyListener(new LControlListener<Integer>() {
			@Override
			public void onModify(LControlEvent<Integer> event) {
				updateImage((Integer) event.newValue);
			}
		});
		
		Group grpAtt = new Group(contentEditor, SWT.NONE);
		grpAtt.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true, 1, 1));
		grpAtt.setLayout(new GridLayout(1, true));
		grpAtt.setText(Vocab.instance.ATTRIBUTES);
		
		Composite build = new Composite(grpAtt, SWT.NONE);
		build.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, false, 1, 1));
		GridLayout gl_build = new GridLayout(3, false);
		gl_build.marginWidth = 0;
		gl_build.marginHeight = 0;
		build.setLayout(gl_build);
		
		Label lblBuild = new Label(build, SWT.NONE);
		lblBuild.setText(Vocab.instance.BUILD);
		
		Text txtBuild = new Text(build, SWT.BORDER | SWT.READ_ONLY);
		txtBuild.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1));
		
		ScriptButton btnBuild = new ScriptButton(build, SWT.NONE);
		btnBuild.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, false, false, 1, 1));
		addScriptButton(btnBuild, txtBuild, "build", "build");
		
		Label lblLevel = new Label(build, SWT.NONE);
		lblLevel.setText(Vocab.instance.LEVEL);
		
		LSpinner spnLevel = new LSpinner(build, SWT.NONE);
		spnLevel.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, false, false, 2, 1));
		addControl(spnLevel, "level");
		
		AttributeEditor attEditor = new AttributeEditor(grpAtt, SWT.NONE);
		attEditor.setColumns(2);
		attEditor.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true, 1, 1));		
		addChild(attEditor);
		
		Composite other = new Composite(contentEditor, SWT.NONE);
		GridLayout gl_other = new GridLayout(3, true);
		gl_other.marginHeight = 0;
		gl_other.marginWidth = 0;
		other.setLayout(gl_other);
		other.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true, 2, 1));
		
		Group grpElements = new Group(other, SWT.NONE);
		grpElements.setLayout(new FillLayout(SWT.HORIZONTAL));
		grpElements.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true, 1, 1));
		grpElements.setText(Vocab.instance.ELEMENTS);
		
		BonusList lstElements = new BonusList(grpElements, SWT.NONE) {
			@Override
			protected LDataTree<Object> dataTree() {
				Config conf = (Config) Project.current.config.getData();
				return conf.elements.toObjectTree();
			}
		};
		lstElements.attributeName = "elements";
		addChild(lstElements);
		
		Group grpItems = new Group(other, SWT.NONE);
		grpItems.setLayout(new FillLayout(SWT.HORIZONTAL));
		grpItems.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true, 1, 1));
		grpItems.setText(Vocab.instance.ITEMS);
		
		BonusList lstItems = new BonusList(grpItems, SWT.NONE) {
			@Override
			protected LDataTree<Object> dataTree() {
				return Project.current.items.getTree();
			}
		};
		lstItems.attributeName = "items";
		addChild(lstItems);

		Group grpTags = new Group(other, SWT.NONE);
		grpTags.setLayout(new FillLayout(SWT.HORIZONTAL));
		grpTags.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true, 1, 1));
		grpTags.setText(Vocab.instance.TAGS);
		
		TagList tagEditor = new TagList(grpTags, SWT.NONE);
		addChild(tagEditor);
		
		contentEditor.addSelectionListener(new LSelectionListener() {
			@Override
			public void onSelect(LSelectionEvent event) {
				if (event.data == null) {
					updateImage(-1);
				} else {
					Battler b = (Battler) event.data;
					updateImage(b.battleCharID);
				}
			}
		});
	}

	@Override
	protected GObjectTreeSerializer getSerializer() {
		return Project.current.battlers;
	}

	private void updateImage(int id) {
		if (id == -1) {
			image.setImage(null);
		} else {
			GameCharacter gc = (GameCharacter) Project.current.characters.getTree().get(id);
			if (gc.animations.size() > 0) {
				int animID = gc.animations.get(0).id;
				LDataTree<?> animations = Project.current.animations.getTree();
				Animation anim = (Animation) animations.get(animID);
				image.setImage(SWTResourceManager.getImage(
						Project.current.imagePath() + anim.quad.path));
			}
		}
	}

}
