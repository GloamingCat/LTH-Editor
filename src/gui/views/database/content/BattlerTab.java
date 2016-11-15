package gui.views.database.content;

import gui.Vocab;
import gui.shell.ScriptShell;
import gui.views.database.subcontent.AttributeEditor;
import gui.views.database.subcontent.BonusEditor;
import gui.views.database.subcontent.TagEditor;

import java.util.ArrayList;

import lwt.dialog.LObjectShell;
import lwt.dialog.LShellFactory;
import lwt.editor.LComboView;
import lwt.widget.LSpinner;
import lwt.widget.LStringButton;

import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Group;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Text;

import data.Config;
import project.ListSerializer;
import project.Project;

public class BattlerTab extends DatabaseTab {

	public BattlerTab(Composite parent, int style) {
		super(parent, style);
		
		contentEditor.setLayout(new GridLayout(2, false));
		
		Label lblCharacter = new Label(grpGeneral, SWT.NONE);
		lblCharacter.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, false, false, 1, 1));
		lblCharacter.setText(Vocab.instance.CHARACTER);
		
		LComboView cmbCharacter = new LComboView(grpGeneral, SWT.READ_ONLY) {
			public ArrayList<Object> getArray() {
				return Project.current.characters.getList();
			}
		};
		cmbCharacter.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1));
		cmbCharacter.setOptional(false);
		contentEditor.addControl(cmbCharacter, "animationID");
		
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
		contentEditor.addControl(cmbSkillTree, "skillDagID");
		
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
		contentEditor.addControl(cmbAttackSkill, "attackID");
		
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
		
		LStringButton btnSelect = new LStringButton(compositeAI, SWT.NONE);
		btnSelect.setShellFactory(new LShellFactory<String>() {
			@Override
			public LObjectShell<String> createShell(Shell parent) {
				return new ScriptShell(parent, "ai");
			}
		});
		btnSelect.setText(txtAI);
		contentEditor.addControl(btnSelect, "scriptAI");
		
		Composite composite = new Composite(grpGeneral, SWT.NONE);
		GridLayout gl_composite = new GridLayout(4, false);
		gl_composite.marginWidth = 0;
		gl_composite.marginHeight = 0;
		composite.setLayout(gl_composite);
		composite.setLayoutData(new GridData(SWT.FILL, SWT.FILL, false, false, 2, 1));
		
		Label lblMoney = new Label(composite, SWT.NONE);
		lblMoney.setText(Vocab.instance.MONEY);
		
		LSpinner spnMoney = new LSpinner(composite, SWT.BORDER);
		spnMoney.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1));
		contentEditor.addControl(spnMoney, "money");
		
		Label lblExp = new Label(composite, SWT.NONE);
		lblExp.setText(Vocab.instance.EXP);
		
		LSpinner spnEXP = new LSpinner(composite, SWT.BORDER);
		spnEXP.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1));
		contentEditor.addControl(spnEXP, "exp");
		
		grpGeneral.pack();
		
		Group grpAttributes = new Group(contentEditor, SWT.NONE);
		grpAttributes.setLayout(new FillLayout());
		GridData gd_grpAttributes = new GridData(SWT.FILL, SWT.FILL, true, false, 1, 1);
		grpAttributes.setLayoutData(gd_grpAttributes);
		grpAttributes.setText(Vocab.instance.ATTRIBUTES);
		
		AttributeEditor attEditor = new AttributeEditor(grpAttributes, SWT.NONE);
		attEditor.setColumns(2);
		contentEditor.addChild(attEditor);
		
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
		contentEditor.addChild(lstElements);
		
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
		contentEditor.addChild(lstItems);

		Group grpTags = new Group(other, SWT.NONE);
		grpTags.setLayout(new FillLayout(SWT.HORIZONTAL));
		grpTags.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true, 1, 1));
		grpTags.setText(Vocab.instance.TAGS);
		
		TagEditor tagEditor = new TagEditor(grpTags, SWT.NONE);
		contentEditor.addChild(tagEditor);
	}

	@Override
	protected ListSerializer getSerializer() {
		return Project.current.battlers;
	}


}
