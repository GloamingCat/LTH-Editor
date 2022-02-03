package gui.views.database.content;

import lwt.dataestructure.LDataTree;
import lwt.editor.LObjectEditor;
import lwt.widget.LLabel;
import lwt.widget.LSpinner;
import lwt.widget.LText;
import gson.project.GObjectTreeSerializer;
import gui.Vocab;
import gui.views.database.DatabaseTab;
import gui.views.database.subcontent.BuildEditor;
import gui.views.database.subcontent.TagList;
import gui.widgets.IDButton;
import gui.widgets.IDList;
import gui.widgets.SimpleEditableList;

import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Group;

import data.Job.Node;
import project.Project;

public class JobTab extends DatabaseTab {

	public JobTab(Composite parent) {
		super(parent);

		Composite right = new Composite(contentEditor, SWT.NONE);
		right.setLayout(new FillLayout(SWT.VERTICAL));
		GridData gd_right = new GridData(SWT.FILL, SWT.FILL, true, true, 1, 2);
		gd_right.widthHint = 60;
		right.setLayoutData(gd_right);
		
		Group grpBuild = new Group(right, SWT.NONE);
		grpBuild.setLayout(new FillLayout(SWT.HORIZONTAL));
		grpBuild.setText(Vocab.instance.BUILD);
		
		BuildEditor buildEditor = new BuildEditor(grpBuild, SWT.NONE);
		addChild(buildEditor, "build");
		
		Group grpTags = new Group(right, SWT.NONE);
		grpTags.setLayout(new FillLayout(SWT.HORIZONTAL));
		grpTags.setText(Vocab.instance.TAGS);
		
		TagList tagEditor = new TagList(grpTags, SWT.NONE);
		addChild(tagEditor, "tags");
		
		new LLabel(grpGeneral, Vocab.instance.EXPCURVE);
		
		LText txtCurve = new LText(grpGeneral);
		txtCurve.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, false, false, 1, 1));
		addControl(txtCurve, "expCurve");
		
		Group grpSkillNodes = new Group(contentEditor, SWT.NONE);
		grpSkillNodes.setLayoutData(new GridData(SWT.FILL, SWT.FILL, false, true, 1, 1));
		grpSkillNodes.setText(Vocab.instance.SKILLNODES);
		grpSkillNodes.setLayout(new GridLayout(2, false));
		
		SimpleEditableList<Node> lstNodes = new SimpleEditableList<>(grpSkillNodes, SWT.NONE);
		lstNodes.getCollectionWidget().setEditEnabled(false);
		lstNodes.setIncludeID(false);
		lstNodes.type = Node.class;
		GridData gd_lstNodes = new GridData(SWT.FILL, SWT.FILL, true, true, 1, 1);
		gd_lstNodes.widthHint = 60;
		lstNodes.setLayoutData(gd_lstNodes);
		addChild(lstNodes, "skills");
		
		// Skill Nodes
		
		LObjectEditor nodeEditor = new LObjectEditor(grpSkillNodes, SWT.NONE);
		nodeEditor.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true, 1, 1));
		lstNodes.addChild(nodeEditor);
		
		GridLayout gridLayout = new GridLayout(2, false);
		gridLayout.marginHeight = 0;
		gridLayout.marginWidth = 0;
		nodeEditor.setLayout(gridLayout);
		
		Composite id = new Composite(nodeEditor, SWT.NONE);
		id.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 2, 1));
		GridLayout gl_id = new GridLayout(3, false);
		gl_id.marginWidth = 0;
		gl_id.marginHeight = 0;
		id.setLayout(gl_id);
		
		new LLabel(id, Vocab.instance.SKILL);
		
		LText txtID = new LText(id, true);
		txtID.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1));
		
		IDButton btnID = new IDButton(id, 0) {
			public LDataTree<Object> getDataTree() {
				return Project.current.skills.getTree();
			}
		};
		btnID.setLayoutData(new GridData(SWT.RIGHT, SWT.CENTER, false, false, 1, 1));
		btnID.setNameWidget(txtID);
		nodeEditor.addControl(btnID, "id");
		
		new LLabel(nodeEditor, Vocab.instance.MINLEVEL);
		
		LSpinner spnLevel = new LSpinner(nodeEditor, SWT.NONE);
		spnLevel.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1));
		nodeEditor.addControl(spnLevel, "level");
		
		Composite bottom = new Composite(nodeEditor, SWT.NONE);
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
		nodeEditor.addChild(lstRequirements, "requirements");
		
		Group grpSkillTags = new Group(bottom, SWT.NONE);
		grpSkillTags.setText(Vocab.instance.TAGS);
		grpSkillTags.setLayout(new FillLayout(SWT.HORIZONTAL));
		
		TagList lstSkillTags = new TagList(grpSkillTags, SWT.NONE);
		nodeEditor.addChild(lstSkillTags, "tags");

	}

	@Override
	protected GObjectTreeSerializer getSerializer() {
		return Project.current.jobs;
	}


}
