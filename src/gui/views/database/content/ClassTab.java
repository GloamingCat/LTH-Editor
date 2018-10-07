package gui.views.database.content;

import gui.Vocab;
import gui.views.database.DatabaseTab;
import gui.views.database.subcontent.SkillNodeEditor;
import gui.widgets.SimpleEditableList;

import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Group;

import data.BattleClass.Node;
import project.GObjectTreeSerializer;
import project.Project;

public class ClassTab extends DatabaseTab {

	public ClassTab(Composite parent, int style) {
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
	protected GObjectTreeSerializer getSerializer() {
		return Project.current.classes;
	}


}
