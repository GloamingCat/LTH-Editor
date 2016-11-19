package gui.views.database.content;

import gui.Vocab;
import gui.views.database.DatabaseTab;
import gui.views.database.ImageButton;
import gui.views.database.subcontent.AttributeEditor;
import gui.views.database.subcontent.BonusEditor;
import gui.views.database.subcontent.TagEditor;

import java.util.ArrayList;

import lwt.editor.LComboView;
import lwt.event.LControlEvent;
import lwt.event.LSelectionEvent;
import lwt.event.listener.LControlListener;
import lwt.event.listener.LSelectionListener;
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
import data.Config;
import project.ListSerializer;
import project.Project;

public class BattlerTab extends DatabaseTab {

	private Label image;
	
	public BattlerTab(Composite parent, int style) {
		super(parent, style);
		
		contentEditor.setLayout(new GridLayout(2, false));
		
		Label lblCharacter = new Label(grpGeneral, SWT.NONE);
		lblCharacter.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, false, false, 1, 1));
		lblCharacter.setText(Vocab.instance.CHARACTER);
		
		LComboView cmbCharacter = new LComboView(grpGeneral, SWT.NONE) {
			public ArrayList<Object> getArray() {
				return Project.current.characters.getList();
			}
		};
		cmbCharacter.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1));
		cmbCharacter.setOptional(false);
		addControl(cmbCharacter, "animationID");
		
		Label lblSkillTree = new Label(grpGeneral, SWT.NONE);
		lblSkillTree.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, false, false, 1, 1));
		lblSkillTree.setText(Vocab.instance.SKILLTREE);
		
		LComboView cmbSkillTree = new LComboView(grpGeneral, SWT.READ_ONLY) {
			public ArrayList<Object> getArray() {
				return Project.current.skillDags.getList();
			}
		};
		cmbSkillTree.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1));
		cmbSkillTree.setOptional(true);
		addControl(cmbSkillTree, "skillDagID");
		
		Label lblAttackSkill = new Label(grpGeneral, SWT.NONE);
		lblAttackSkill.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, false, false, 1, 1));
		lblAttackSkill.setText(Vocab.instance.ATTACKSKILL);
		
		LComboView cmbAttackSkill = new LComboView(grpGeneral, SWT.READ_ONLY) {
			public ArrayList<Object> getArray() {
				return Project.current.skills.getList();
			}
		};
		cmbAttackSkill.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1));
		cmbAttackSkill.setOptional(false);
		addControl(cmbAttackSkill, "attackID");
		
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
		
		ImageButton btnSelect = new ImageButton(compositeAI, SWT.NONE);
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
		GridData gd_image = new GridData(SWT.CENTER, SWT.FILL, true, true, 2, 1);
		gd_image.heightHint = 48;
		image.setLayoutData(gd_image);
		
		cmbCharacter.getControl().addModifyListener(new LControlListener() {
			@Override
			public void onModify(LControlEvent event) {
				updateImage((Integer) event.newValue);
			}
		});
		
		AttributeEditor attEditor = new AttributeEditor(contentEditor, SWT.NONE);
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
		
		BonusEditor lstElements = new BonusEditor(grpElements, SWT.NONE) {
			@Override
			protected ArrayList<?> getArray() {
				Config conf = (Config) Project.current.config.getData();
				return conf.elements;
			}
			@Override
			protected String attributeName() {
				return "elements";
			}
		};
		addChild(lstElements);
		
		Group grpItems = new Group(other, SWT.NONE);
		grpItems.setLayout(new FillLayout(SWT.HORIZONTAL));
		grpItems.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true, 1, 1));
		grpItems.setText(Vocab.instance.ITEMS);
		
		BonusEditor lstItems = new BonusEditor(grpItems, SWT.NONE) {
			@Override
			protected ArrayList<?> getArray() {
				return Project.current.items.getList();
			}
			@Override
			protected String attributeName() {
				return "items";
			}
		};
		addChild(lstItems);

		Group grpTags = new Group(other, SWT.NONE);
		grpTags.setLayout(new FillLayout(SWT.HORIZONTAL));
		grpTags.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true, 1, 1));
		grpTags.setText(Vocab.instance.TAGS);
		
		TagEditor tagEditor = new TagEditor(grpTags, SWT.NONE);
		addChild(tagEditor);
		
		contentEditor.addSelectionListener(new LSelectionListener() {
			@Override
			public void onSelect(LSelectionEvent event) {
				if (event.data == null) {
					updateImage(-1);
				} else {
					Battler b = (Battler) event.data;
					updateImage(b.animationID);
				}
			}
		});
	}

	@Override
	protected ListSerializer getSerializer() {
		return Project.current.battlers;
	}

	private void updateImage(int id) {
		if (id == -1) {
			image.setImage(null);
		} else {
			Animation anim = (Animation) Project.current.animCharacter.getList().get(id);
			image.setImage(SWTResourceManager.getImage(Project.current.imagePath() + anim.imagePath));
		}
	}

}
