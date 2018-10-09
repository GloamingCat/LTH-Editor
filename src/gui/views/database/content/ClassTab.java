package gui.views.database.content;

import lwt.widget.LText;
import gson.project.GObjectTreeSerializer;
import gui.Vocab;
import gui.views.database.DatabaseTab;
import gui.views.database.subcontent.BuildEditor;
import gui.views.database.subcontent.SkillNodeEditor;
import gui.views.database.subcontent.TagList;
import gui.widgets.SimpleEditableList;

import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Group;
import org.eclipse.swt.widgets.Label;

import data.BattleClass.Node;
import project.Project;

public class ClassTab extends DatabaseTab {

	public ClassTab(Composite parent, int style) {
		super(parent, style);

		Composite right = new Composite(contentEditor, SWT.NONE);
		right.setLayout(new FillLayout(SWT.VERTICAL));
		GridData gd_right = new GridData(SWT.FILL, SWT.FILL, true, true, 1, 2);
		gd_right.widthHint = 60;
		right.setLayoutData(gd_right);
		
		Group grpBuild = new Group(right, SWT.NONE);
		grpBuild.setLayout(new FillLayout(SWT.HORIZONTAL));
		grpBuild.setText(Vocab.instance.BUILD);
		
		BuildEditor buildEditor = new BuildEditor(grpBuild, SWT.NONE);
		addChild(buildEditor);
		
		Group grpTags = new Group(right, SWT.NONE);
		grpTags.setLayout(new FillLayout(SWT.HORIZONTAL));
		grpTags.setText(Vocab.instance.TAGS);
		
		TagList tagEditor = new TagList(grpTags, SWT.NONE);
		addChild(tagEditor);
		
		Label lblCurve = new Label(grpGeneral, SWT.NONE);
		lblCurve.setText(Vocab.instance.EXPCURVE);
		
		LText txtCurve = new LText(grpGeneral, SWT.NONE);
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
		
		SkillNodeEditor nodeEditor = new SkillNodeEditor(grpSkillNodes, SWT.NONE);
		nodeEditor.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true, 1, 1));
		lstNodes.addChild(nodeEditor);

	}

	@Override
	protected GObjectTreeSerializer getSerializer() {
		return Project.current.classes;
	}


}
