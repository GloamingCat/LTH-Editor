package gui.views.database.content;

import lwt.LFlags;
import lwt.container.LContainer;
import lwt.container.LFrame;
import lwt.container.LPanel;
import lwt.dialog.LObjectShell;
import lwt.dialog.LShell;
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

import data.Job;
import project.Project;

public class JobTab extends DatabaseTab<Job> {

	private IDButton btnAttack;
	
	
	/**
	 * @wbp.parser.constructor
	 * @wbp.eval.method.parameter parent new lwt.dialog.LShell(800, 600)
	 */
	public JobTab(LContainer parent) {
		super(parent);
		
		LLabel lblExp = new LLabel(grpGeneral, Vocab.instance.EXPCURVE);
		LText txtCurve = new LText(grpGeneral);
		txtCurve.addMenu(lblExp);
		addControl(txtCurve, "expCurve");
		
		// Attack Skill
		
		LLabel lblAtk = new LLabel(grpGeneral, Vocab.instance.ATTACKSKILL);
		LPanel attackSkill = new LPanel(grpGeneral, 2, false);
		attackSkill.setExpand(true, false);
		attackSkill.setAlignment(LFlags.CENTER);
		
		LText txtAttack = new LText(attackSkill, true);
		btnAttack = new IDButton(attackSkill, false);
		btnAttack.setNameWidget(txtAttack);
		btnAttack.addMenu(lblAtk);
		btnAttack.addMenu(lblExp);
		addControl(btnAttack, "attackID");
		
		LFrame grpBuild = new LFrame(left, Vocab.instance.BUILD, true, true);
		grpBuild.setExpand(true, true);
		BuildEditor buildEditor = new BuildEditor(grpBuild, 1);
		buildEditor.addMenu(grpBuild);
		addChild(buildEditor, "build");
		
		LPanel nodes = new LPanel(right, false, true);
		nodes.setExpand(true, true);

		LFrame grpSkillNodes = new LFrame(nodes, Vocab.instance.SKILLNODES, true, true);
		SimpleEditableList<Job.Skill> lstSkills = new SimpleEditableList<>(grpSkillNodes);
		lstSkills.getCollectionWidget().setEditEnabled(false);
		lstSkills.setIncludeID(false);
		lstSkills.type = Job.Skill.class;
		lstSkills.addMenu(grpSkillNodes);
		addChild(lstSkills, "skills");
		lstSkills.setShellFactory(new LShellFactory<Job.Skill>() {
			@Override
			public LObjectShell<Job.Skill> createShell(LShell parent) {
				return new JobSkillShell(parent);
			}
		});

		LFrame grpStatusNodes = new LFrame(nodes, Vocab.instance.STATUSNODES, true, true);
		SimpleEditableList<Job.Status> lstStatuses = new SimpleEditableList<>(grpStatusNodes);
		lstStatuses.getCollectionWidget().setEditEnabled(false);
		lstStatuses.setIncludeID(false);
		lstStatuses.type = Job.Status.class;
		lstStatuses.addMenu(grpStatusNodes);
		addChild(lstStatuses, "statuses");
		lstStatuses.setShellFactory(new LShellFactory<Job.Status>() {
			@Override
			public LObjectShell<Job.Status> createShell(LShell parent) {
				return new JobStatusShell(parent);
			}
		});

	}
	
	@Override
	public void onVisible() {
		btnAttack.dataTree = Project.current.skills.getTree();
		super.onVisible();
	}

	@Override
	protected GObjectTreeSerializer getSerializer() {
		return Project.current.jobs;
	}


}
