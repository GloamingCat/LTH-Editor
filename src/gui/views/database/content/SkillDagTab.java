package gui.views.database.content;

import gui.Vocab;
import gui.views.database.DatabaseTab;
import gui.views.database.subcontent.SkillNodeEditor;
import gui.views.database.subcontent.SkillNodeList;

import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Group;

import project.ListSerializer;
import project.Project;

public class SkillDagTab extends DatabaseTab {

	public SkillDagTab(Composite parent, int style) {
		super(parent, style);

		Group grpSkillNodes = new Group(contentEditor, SWT.NONE);
		grpSkillNodes.setLayoutData(new GridData(SWT.FILL, SWT.FILL, false, true, 1, 1));
		grpSkillNodes.setText(Vocab.instance.SKILLNODES);
		grpSkillNodes.setLayout(new GridLayout(2, false));
		
		SkillNodeList lstNodes = new SkillNodeList(grpSkillNodes, SWT.NONE);
		lstNodes.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true, 1, 1));
		addChild(lstNodes);
		
		SkillNodeEditor nodeEditor = new SkillNodeEditor(grpSkillNodes, SWT.NONE);
		nodeEditor.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true, 1, 1));
		lstNodes.addChild(nodeEditor);
	}

	@Override
	protected ListSerializer getSerializer() {
		return Project.current.skillDags;
	}


}