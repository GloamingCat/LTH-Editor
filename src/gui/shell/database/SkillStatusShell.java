package gui.shell.database;

import gui.Vocab;
import gui.shell.ObjectShell;

import org.eclipse.swt.widgets.Shell;

import data.Skill.SkillStatus;
import lwt.widget.LNodeSelector;
import lwt.widget.LText;

import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.layout.GridData;

import project.Project;

public class SkillStatusShell extends ObjectShell<SkillStatus> {

	public SkillStatusShell(Shell parent) {
		super(parent);
		GridData gridData = (GridData) content.getLayoutData();
		gridData.verticalAlignment = SWT.FILL;
		gridData.grabExcessVerticalSpace = true;
		setMinimumSize(new Point(400, 300));
		
		contentEditor.setLayout(new GridLayout(2, false));
		
		Label lblRate = new Label(contentEditor, SWT.NONE);
		lblRate.setText(Vocab.instance.SUCCESSRATE);
		
		LText txtRate = new LText(contentEditor, SWT.NONE);
		txtRate.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, false, 1, 1));
		addControl(txtRate, "rate");
		
		LNodeSelector<Object> tree = new LNodeSelector<Object>(contentEditor, SWT.NONE);
		tree.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true, 2, 1));
		tree.setCollection(Project.current.status.getTree());
		addControl(tree, "id");
		
		pack();
	}
	
}
