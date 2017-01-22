package gui.views.database.content;

import gui.Vocab;
import gui.views.SimpleEditableList;
import gui.views.database.DatabaseTab;
import gui.views.database.subcontent.SkillNodeEditor;

import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Group;

import data.SkillDag.Node;
import project.GObjectListSerializer;
import project.Project;

public class SkillDagTab extends DatabaseTab {

	public SkillDagTab(Composite parent, int style) {
		super(parent, style);

		Group grpSkillNodes = new Group(contentEditor, SWT.NONE);
		grpSkillNodes.setLayoutData(new GridData(SWT.FILL, SWT.FILL, false, true, 1, 1));
		grpSkillNodes.setText(Vocab.instance.SKILLNODES);
		grpSkillNodes.setLayout(new GridLayout(2, false));
		
		SimpleEditableList<Node> lstNodes = new SimpleEditableList<>(grpSkillNodes, SWT.NONE);
		lstNodes.getCollectionWidget().setEditEnabled(false);
		lstNodes.setIncludeID(false);
		lstNodes.type = Node.class;
		lstNodes.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true, 1, 1));
		addChild(lstNodes, "nodes");
		
		SkillNodeEditor nodeEditor = new SkillNodeEditor(grpSkillNodes, SWT.NONE);
		nodeEditor.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true, 1, 1));
		lstNodes.addChild(nodeEditor);
	}

	@Override
	protected GObjectListSerializer getSerializer() {
		return Project.current.skillDags;
	}


}
