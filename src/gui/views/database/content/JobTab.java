package gui.views.database.content;

import lwt.container.LContainer;
import lwt.container.LFrame;
import lwt.container.LPanel;
import lwt.dialog.LObjectWindow;
import lwt.dialog.LWindow;
import lwt.dialog.LWindowFactory;
import lwt.widget.LLabel;
import lwt.widget.LText;
import gui.Tooltip;
import gui.Vocab;
import gui.shell.database.JobSkillShell;
import gui.shell.database.JobStatusShell;
import gui.views.database.DatabaseTab;
import gui.views.database.subcontent.BuildEditor;
import gui.widgets.IDButton;
import gui.widgets.SimpleEditableList;
import lbase.LFlags;
import data.Job;
import gson.GObjectTreeSerializer;
import project.Project;

public class JobTab extends DatabaseTab<Job> {

	private IDButton btnAttack;
	
	
	/**
	 * @wbp.parser.constructor
	 * @wbp.eval.method.parameter parent new lwt.dialog.LShell(800, 600)
	 */
	public JobTab(LContainer parent) {
		super(parent);
		
		LLabel lblExp = new LLabel(grpGeneral, Vocab.instance.EXPCURVE, Tooltip.instance.EXPCURVE);
		LText txtCurve = new LText(grpGeneral);
		txtCurve.getCellData().setExpand(true, false);
		txtCurve.addMenu(lblExp);
		addControl(txtCurve, "expCurve");
		
		// Attack Skill
		
		LLabel lblAtk = new LLabel(grpGeneral, Vocab.instance.ATTACKSKILL, Tooltip.instance.ATTACKSKILL);
		LPanel attackSkill = new LPanel(grpGeneral);
		attackSkill.setGridLayout(2);
		attackSkill.getCellData().setExpand(true, false);
		
		LText txtAttack = new LText(attackSkill, true);
		txtAttack.getCellData().setExpand(true, false);
		btnAttack = new IDButton(attackSkill, Vocab.instance.SKILLSHELL, false);
		btnAttack.setNameWidget(txtAttack);
		btnAttack.addMenu(lblAtk);
		btnAttack.addMenu(lblExp);
		addControl(btnAttack, "attackID");
		
		LFrame grpBuild = new LFrame(left, (String) Vocab.instance.BUILD);
		grpBuild.setFillLayout(true);
		grpBuild.setHoverText(Tooltip.instance.BUILD);
		grpBuild.getCellData().setExpand(true, true);
		BuildEditor buildEditor = new BuildEditor(grpBuild, 1);
		buildEditor.addMenu(grpBuild);
		addChild(buildEditor, "build");
		
		LPanel nodes = new LPanel(right);
		nodes.setFillLayout(false);
		nodes.setSpacing(5);
		nodes.getCellData().setExpand(true, true);

		LFrame grpSkillNodes = new LFrame(nodes, (String) Vocab.instance.SKILLNODES);
		grpSkillNodes.setFillLayout(true);
		grpSkillNodes.setHoverText(Tooltip.instance.SKILLNODES);
		SimpleEditableList<Job.Skill> lstSkills = new SimpleEditableList<>(grpSkillNodes);
		lstSkills.getCollectionWidget().setEditEnabled(false);
		lstSkills.setIncludeID(false);
		lstSkills.type = Job.Skill.class;
		lstSkills.addMenu(grpSkillNodes);
		addChild(lstSkills, "skills");
		lstSkills.setShellFactory(new LWindowFactory<Job.Skill>() {
			@Override
			public LObjectWindow<Job.Skill> createWindow(LWindow parent) {
				return new JobSkillShell(parent);
			}
		});
		
		LFrame grpStatusNodes = new LFrame(nodes, (String) Vocab.instance.STATUSNODES);
		grpStatusNodes.setFillLayout(true);
		grpStatusNodes.setHoverText(Tooltip.instance.STATUSNODES);
		SimpleEditableList<Job.Status> lstStatuses = new SimpleEditableList<>(grpStatusNodes);
		lstStatuses.getCollectionWidget().setEditEnabled(false);
		lstStatuses.setIncludeID(false);
		lstStatuses.type = Job.Status.class;
		lstStatuses.addMenu(grpStatusNodes);
		addChild(lstStatuses, "statuses");
		lstStatuses.setShellFactory(new LWindowFactory<Job.Status>() {
			@Override
			public LObjectWindow<Job.Status> createWindow(LWindow parent) {
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
