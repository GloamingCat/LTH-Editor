package gui.views.database.content;

import lwt.dataestructure.LDataTree;
import lwt.dialog.LObjectShell;
import lwt.dialog.LShellFactory;
import lwt.widget.LLabel;
import lwt.widget.LText;
import gson.project.GObjectTreeSerializer;
import gui.Vocab;
import gui.shell.database.JobSkillShell;
import gui.shell.database.JobStatusShell;
import gui.views.database.DatabaseTab;
import gui.views.database.subcontent.BuildEditor;
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

public class JobTab extends DatabaseTab<Job> {

	public JobTab(Composite parent) {
		super(parent);
		
		new LLabel(grpGeneral, Vocab.instance.EXPCURVE);
		
		LText txtCurve = new LText(grpGeneral);
		txtCurve.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, false, false, 1, 1));
		addControl(txtCurve, "expCurve");
		
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
		
		Group grpBuild = new Group(left, SWT.NONE);
		grpBuild.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true, 1, 1));
		grpBuild.setLayout(new FillLayout(SWT.HORIZONTAL));
		grpBuild.setText(Vocab.instance.BUILD);
		
		BuildEditor buildEditor = new BuildEditor(grpBuild, SWT.NONE);
		addChild(buildEditor, "build");

		Group grpSkillNodes = new Group(right, SWT.NONE);
		grpSkillNodes.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true, 1, 1));
		grpSkillNodes.setText(Vocab.instance.SKILLNODES);
		grpSkillNodes.setLayout(new FillLayout());
		
		SimpleEditableList<Job.Skill> lstSkills = new SimpleEditableList<>(grpSkillNodes, SWT.NONE);
		lstSkills.getCollectionWidget().setEditEnabled(false);
		lstSkills.setIncludeID(false);
		lstSkills.type = Job.Skill.class;
		addChild(lstSkills, "skills");
		lstSkills.setShellFactory(new LShellFactory<Job.Skill>() {
			@Override
			public LObjectShell<Job.Skill> createShell(Shell parent) {
				return new JobSkillShell(parent);
			}
		});

		Group grpStatusNodes = new Group(right, SWT.NONE);
		grpStatusNodes.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true, 1, 1));
		grpStatusNodes.setText(Vocab.instance.STATUSNODES);
		grpStatusNodes.setLayout(new FillLayout());
		
		SimpleEditableList<Job.Status> lstStatuses = new SimpleEditableList<>(grpStatusNodes, SWT.NONE);
		lstStatuses.getCollectionWidget().setEditEnabled(false);
		lstStatuses.setIncludeID(false);
		lstStatuses.type = Job.Status.class;
		addChild(lstStatuses, "skills");
		lstStatuses.setShellFactory(new LShellFactory<Job.Status>() {
			@Override
			public LObjectShell<Job.Status> createShell(Shell parent) {
				return new JobStatusShell(parent);
			}
		});

	}

	@Override
	protected GObjectTreeSerializer getSerializer() {
		return Project.current.jobs;
	}


}
