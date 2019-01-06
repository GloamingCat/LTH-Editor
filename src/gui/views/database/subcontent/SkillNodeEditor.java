package gui.views.database.subcontent;

import gui.Vocab;
import gui.widgets.IDButton;
import gui.widgets.IDList;

import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Group;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Text;

import project.Project;
import lwt.dataestructure.LDataTree;
import lwt.editor.LObjectEditor;
import lwt.widget.LSpinner;

public class SkillNodeEditor extends LObjectEditor {

	public SkillNodeEditor(Composite parent, int style) {
		super(parent, style);
		
		GridLayout gridLayout = new GridLayout(2, false);
		gridLayout.marginHeight = 0;
		gridLayout.marginWidth = 0;
		setLayout(gridLayout);
		
		Composite id = new Composite(this, SWT.NONE);
		id.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 2, 1));
		GridLayout gl_id = new GridLayout(3, false);
		gl_id.marginWidth = 0;
		gl_id.marginHeight = 0;
		id.setLayout(gl_id);
		
		Label lblSkill = new Label(id, SWT.NONE);
		lblSkill.setText(Vocab.instance.SKILL);
		
		Text txtID = new Text(id, SWT.BORDER | SWT.READ_ONLY);
		txtID.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1));
		
		IDButton btnID = new IDButton(id, SWT.NONE, false) {
			public LDataTree<Object> getDataTree() {
				return Project.current.skills.getTree();
			}
		};
		btnID.setLayoutData(new GridData(SWT.RIGHT, SWT.CENTER, false, false, 1, 1));
		addControl(btnID, "id");
		
		Label lblLevel = new Label(this, SWT.NONE);
		lblLevel.setText(Vocab.instance.MINLEVEL);
		
		LSpinner spnLevel = new LSpinner(this, SWT.NONE);
		spnLevel.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1));
		addControl(spnLevel, "level");
		
		Composite bottom = new Composite(this, SWT.NONE);
		bottom.setLayout(new FillLayout(SWT.VERTICAL));
		bottom.setLayoutData(new GridData(SWT.FILL, SWT.FILL, false, true, 2, 1));
		
		Group grpRequiredSkills = new Group(bottom, SWT.NONE);
		grpRequiredSkills.setLayout(new FillLayout(SWT.HORIZONTAL));
		grpRequiredSkills.setText(Vocab.instance.REQUIREDSKILLS);

		IDList lstRequirements = new IDList(grpRequiredSkills, SWT.NONE) {
			public LDataTree<Object> getDataTree() {
				return Project.current.skills.getTree();
			}
		};
		lstRequirements.setLayout(new FillLayout(SWT.HORIZONTAL));
		addChild(lstRequirements, "requirements");
		
		Group grpTags = new Group(bottom, SWT.NONE);
		grpTags.setText(Vocab.instance.TAGS);
		grpTags.setLayout(new FillLayout(SWT.HORIZONTAL));
		
		TagList lstTags = new TagList(grpTags, SWT.NONE);
		addChild(lstTags, "tags");
		
	}

}
