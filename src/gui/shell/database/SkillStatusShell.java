package gui.shell.database;

import gui.Vocab;
import gui.shell.ObjectShell;

import org.eclipse.swt.widgets.Composite;
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
		
		contentEditor.setLayout(new GridLayout(1, false));
		
		Composite name = new Composite(contentEditor, SWT.NONE);
		name.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1));
		GridLayout gl_name = new GridLayout(2, false);
		gl_name.marginWidth = 0;
		gl_name.marginHeight = 0;
		name.setLayout(gl_name);
		
		Label lblRate = new Label(name, SWT.NONE);
		lblRate.setText(Vocab.instance.SUCCESSRATE);
		
		LText txtRate = new LText(name, SWT.NONE);
		txtRate.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true, 1, 1));
		addControl(txtRate, "rate");
		
		LNodeSelector<Object> tree = new LNodeSelector<Object>(contentEditor, SWT.NONE);
		tree.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true, 1, 1));
		tree.setCollection(Project.current.status.getTree());
		addControl(tree, "id");
		
		pack();
	}
	
}
