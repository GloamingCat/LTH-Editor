package gui.views.database.content;

import lwt.dataestructure.LDataTree;
import lwt.dialog.LObjectShell;
import lwt.dialog.LShellFactory;
import lwt.widget.LLabel;
import lwt.widget.LText;
import gson.project.GObjectTreeSerializer;
import gui.Vocab;
import gui.shell.database.JobSkillShell;
import gui.views.database.DatabaseTab;
import gui.views.database.subcontent.BuildEditor;
import gui.views.database.subcontent.TagList;
import gui.widgets.IDButton;
import gui.widgets.SimpleEditableList;

import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Group;
import org.eclipse.swt.widgets.Shell;

import data.Job;
import project.Project;

public class JobTab extends DatabaseTab {

	public JobTab(Composite parent) {
		super(parent);
		
		GridLayout gridLayout = new GridLayout(3, false);
		gridLayout.verticalSpacing = 0;
		contentEditor.setLayout(gridLayout);
		
		new LLabel(grpGeneral, Vocab.instance.EXPCURVE);
		
		LText txtCurve = new LText(grpGeneral);
		txtCurve.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, false, false, 1, 1));
		addControl(txtCurve, "expCurve");
				
		//grpGeneral.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, false, 1, 1));
		
		Group grpBuild = new Group(contentEditor, SWT.NONE);
		grpBuild.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, false, 1, 2));
		grpBuild.setLayout(new FillLayout(SWT.HORIZONTAL));
		grpBuild.setText(Vocab.instance.BUILD);
		
		BuildEditor buildEditor = new BuildEditor(grpBuild, SWT.NONE);
		addChild(buildEditor, "build");
		
		Group grpTags = new Group(contentEditor, SWT.NONE);
		grpTags.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, false, 1, 2));
		grpTags.setLayout(new FillLayout(SWT.HORIZONTAL));
		grpTags.setText(Vocab.instance.TAGS);
		
		TagList tagEditor = new TagList(grpTags, SWT.NONE);
		addChild(tagEditor, "tags");

		Group grpSkillNodes = new Group(contentEditor, SWT.NONE);
		grpSkillNodes.setLayoutData(new GridData(SWT.FILL, SWT.FILL, false, true, 1, 1));
		grpSkillNodes.setText(Vocab.instance.SKILLNODES);
		grpSkillNodes.setLayout(new FillLayout());
		
		SimpleEditableList<Job.Skill> lstNodes = new SimpleEditableList<>(grpSkillNodes, SWT.NONE);
		lstNodes.getCollectionWidget().setEditEnabled(false);
		lstNodes.setIncludeID(false);
		lstNodes.type = Job.Skill.class;
		addChild(lstNodes, "skills");
		lstNodes.setShellFactory(new LShellFactory<Job.Skill>() {
			@Override
			public LObjectShell<Job.Skill> createShell(Shell parent) {
				return new JobSkillShell(parent);
			}
		});
		
		// Attack Skill
		
		new LLabel(grpGeneral, Vocab.instance.ATTACKSKILL);
		
		Composite attackSkill = new Composite(grpGeneral, SWT.NONE);
		GridLayout gl_attackSkill = new GridLayout(2, false);
		gl_attackSkill.marginWidth = 0;
		gl_attackSkill.marginHeight = 0;
		attackSkill.setLayout(gl_attackSkill);
		attackSkill.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1));
		
		LText txtAttack = new LText(attackSkill, true);
		txtAttack.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1));
		
		IDButton btnAttack = new IDButton(attackSkill, 0) {
			@Override
			public LDataTree<Object> getDataTree() {
				return Project.current.skills.getTree();
			}
		};
		btnAttack.setNameWidget(txtAttack);
		addControl(btnAttack, "attackID");

	}

	@Override
	protected GObjectTreeSerializer getSerializer() {
		return Project.current.jobs;
	}


}
