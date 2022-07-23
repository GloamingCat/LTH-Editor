package gui.views.database.content;

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
import gui.widgets.SimpleEditableList;

import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Group;
import org.eclipse.swt.widgets.Shell;

import data.Job;
import data.Job.Skill;
import project.Project;
import org.eclipse.swt.widgets.Label;

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
		grpSkillNodes.setLayout(new GridLayout(2, false));
		
		SimpleEditableList<Job.Skill> lstNodes = new SimpleEditableList<>(grpSkillNodes, SWT.NONE);
		lstNodes.getCollectionWidget().setEditEnabled(false);
		lstNodes.setIncludeID(false);
		lstNodes.type = Job.Skill.class;
		GridData gd_lstNodes = new GridData(SWT.FILL, SWT.FILL, true, true, 1, 1);
		gd_lstNodes.widthHint = 60;
		lstNodes.setLayoutData(gd_lstNodes);
		addChild(lstNodes, "skills");
		lstNodes.setShellFactory(new LShellFactory<Job.Skill>() {
			@Override
			public LObjectShell<Skill> createShell(Shell parent) {
				return new JobSkillShell(parent);
			}
		});
		new Label(grpSkillNodes, SWT.NONE);

	}

	@Override
	protected GObjectTreeSerializer getSerializer() {
		return Project.current.jobs;
	}


}
