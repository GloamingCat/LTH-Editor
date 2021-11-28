package gui.shell.database;

import gui.Vocab;
import gui.shell.ObjectShell;

import org.eclipse.swt.widgets.Shell;

import data.Skill.Effect;
import lwt.widget.LCheckButton;
import lwt.widget.LText;

import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.layout.GridData;

public class SkillEffectShell extends ObjectShell<Effect> {

	public SkillEffectShell(Shell parent) {
		super(parent);
		setMinimumSize(new Point(300, 100));
		contentEditor.setLayout(new GridLayout(2, false));
		
		Label lblName = new Label(contentEditor, SWT.NONE);
		lblName.setText(Vocab.instance.KEY);
		
		LText txtKey = new LText(contentEditor);
		txtKey.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1));
		addControl(txtKey, "key");
		
		Label lblBasicResult = new Label(contentEditor, SWT.NONE);
		lblBasicResult.setText(Vocab.instance.BASICRESULT);
		
		LText txtBasicResult = new LText(contentEditor);
		txtBasicResult.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1));
		addControl(txtBasicResult, "basicResult");
		
		Label lblSuccessRate = new Label(contentEditor, SWT.NONE);
		lblSuccessRate.setText(Vocab.instance.SUCCESSRATE);

		LText txtSuccessRate = new LText(contentEditor);
		txtSuccessRate.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1));
		addControl(txtSuccessRate, "successRate");
		
		LCheckButton btnHeal = new LCheckButton(contentEditor);
		btnHeal.setText(Vocab.instance.HEAL);
		addControl(btnHeal, "heal");
		
		LCheckButton btnAbsorb = new LCheckButton(contentEditor);
		btnAbsorb.setText(Vocab.instance.ABSORB);
		addControl(btnAbsorb, "absorb");
		
		pack();
	}
	
}
