package gui.shell.database;

import gui.Vocab;
import gui.shell.ObjectShell;

import org.eclipse.swt.widgets.Group;
import org.eclipse.swt.widgets.Shell;

import data.Skill.Effect;
import lwt.widget.LCheckBox;
import lwt.widget.LLabel;
import lwt.widget.LNodeSelector;
import lwt.widget.LText;
import project.Project;

import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.FillLayout;

public class SkillEffectShell extends ObjectShell<Effect> {

	public SkillEffectShell(Shell parent) {
		super(parent);
		setMinimumSize(300, 100);
		contentEditor.setLayout(new GridLayout(2, false));
		
		new LLabel(contentEditor, Vocab.instance.KEY);
		
		LText txtKey = new LText(contentEditor);
		txtKey.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1));
		addControl(txtKey, "key");
		
		new LLabel(contentEditor, Vocab.instance.BASICRESULT);
		
		LText txtBasicResult = new LText(contentEditor);
		txtBasicResult.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1));
		addControl(txtBasicResult, "basicResult");
		
		new LLabel(contentEditor, Vocab.instance.SUCCESSRATE);

		LText txtSuccessRate = new LText(contentEditor);
		txtSuccessRate.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1));
		addControl(txtSuccessRate, "successRate");
		
		LCheckBox btnHeal = new LCheckBox(contentEditor);
		btnHeal.setText(Vocab.instance.HEAL);
		addControl(btnHeal, "heal");
		
		LCheckBox btnAbsorb = new LCheckBox(contentEditor);
		btnAbsorb.setText(Vocab.instance.ABSORB);
		addControl(btnAbsorb, "absorb");
		
		Group grpStatus = new Group(contentEditor, SWT.NONE);
		grpStatus.setLayout(new FillLayout(SWT.HORIZONTAL));
		grpStatus.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true, 2, 1));
		grpStatus.setText(Vocab.instance.STATUS);
		
		LNodeSelector<Object> tree = new LNodeSelector<Object>(grpStatus, 1);
		tree.setCollection(Project.current.status.getTree());
		addControl(tree, "statusID");
		
		pack();
	}
	
}
