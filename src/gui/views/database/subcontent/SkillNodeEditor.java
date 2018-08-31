package gui.views.database.subcontent;

import java.util.ArrayList;

import gui.Vocab;
import gui.views.IDList;

import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Group;
import org.eclipse.swt.widgets.Label;

import project.Project;
import lwt.editor.LComboView;
import lwt.editor.LObjectEditor;
import lwt.widget.LSpinner;

public class SkillNodeEditor extends LObjectEditor {

	public SkillNodeEditor(Composite parent, int style) {
		super(parent, style);
		
		GridLayout gridLayout = new GridLayout(2, false);
		gridLayout.marginHeight = 0;
		gridLayout.marginWidth = 0;
		setLayout(gridLayout);
		
		Label lblSkill = new Label(this, SWT.NONE);
		lblSkill.setLayoutData(new GridData(SWT.LEFT, SWT.FILL, false, false, 1, 1));
		lblSkill.setText(Vocab.instance.SKILL);
		
		LComboView cmbSkillID = new LComboView(this, SWT.NONE) {
			public ArrayList<Object> getArray() {
				//TODO return Project.current.skills.getList();
				return null;
			}
		};
		cmbSkillID.setLayoutData(new GridData(SWT.FILL, SWT.FILL, false, false, 1, 1));
		cmbSkillID.setOptional(false);
		addControl(cmbSkillID, "skillID");
		
		Label lblCost = new Label(this, SWT.NONE);
		lblCost.setText(Vocab.instance.EXPCOST);
		
		LSpinner spnCost = new LSpinner(this, SWT.NONE);
		spnCost.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1));
		addControl(spnCost, "cost");
		
		Label lblLevel = new Label(this, SWT.NONE);
		lblLevel.setText(Vocab.instance.MINLEVEL);
		
		LSpinner spnLevel = new LSpinner(this, SWT.NONE);
		spnLevel.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1));
		addControl(spnLevel, "minLevel");
		
		Group grpRequiredSkills = new Group(this, SWT.NONE);
		grpRequiredSkills.setLayoutData(new GridData(SWT.FILL, SWT.FILL, false, true, 2, 1));
		grpRequiredSkills.setLayout(new FillLayout(SWT.HORIZONTAL));
		grpRequiredSkills.setText(Vocab.instance.REQUIREDSKILLS);

		IDList lstRequirements = new IDList(grpRequiredSkills, SWT.NONE) {
			public ArrayList<?> comboArray() {
				//TODO return Project.current.skills.getList();
				return null;
			}
		};
		lstRequirements.attributeName = "requirements";
		lstRequirements.setLayout(new FillLayout(SWT.HORIZONTAL));
		addChild(lstRequirements);
		
	}

	
}
