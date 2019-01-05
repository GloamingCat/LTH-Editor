package gui.shell.database;

import gui.Vocab;

import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Shell;

import data.Skill.Effect;
import lwt.dialog.LObjectShell;

import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Text;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.layout.GridData;

public class EffectShell extends LObjectShell<Effect> {
	
	private Text txtKey;
	private Text txtSuccessRate;
	private Text txtBasicResult;
	private Button btnHeal;
	private Button btnAbsorb;

	public EffectShell(Shell parent) {
		super(parent);
		setMinimumSize(new Point(455, 193));
		content.setLayout(new GridLayout(2, false));
		
		Label lblName = new Label(content, SWT.NONE);
		lblName.setText(Vocab.instance.KEY);
		
		txtKey = new Text(content, SWT.BORDER);
		GridData gd_txtKey = new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1);
		gd_txtKey.widthHint = 136;
		txtKey.setLayoutData(gd_txtKey);
		
		Label lblBasicResult = new Label(content, SWT.NONE);
		lblBasicResult.setText(Vocab.instance.BASICRESULT);
		
		txtBasicResult = new Text(content, SWT.BORDER);
		txtBasicResult.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1));
		
		Label lblSuccessRate = new Label(content, SWT.NONE);
		lblSuccessRate.setText(Vocab.instance.SUCCESSRATE);

		txtSuccessRate = new Text(content, SWT.BORDER);
		txtSuccessRate.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1));
		
		btnHeal = new Button(content, SWT.CHECK);
		btnHeal.setText(Vocab.instance.HEAL);
		
		btnAbsorb = new Button(content, SWT.CHECK);
		btnAbsorb.setText(Vocab.instance.ABSORB);
		
		pack();
	}
	
	public void open(Effect initial) {
		super.open(initial);
		txtKey.setText(initial.key);
		txtBasicResult.setText(initial.basicResult);
		txtSuccessRate.setText(initial.successRate);
	}

	@Override
	protected Effect createResult(Effect initial) {
		Effect effect = new Effect();
		effect.key = txtKey.getText();
		effect.successRate = txtSuccessRate.getText();
		effect.basicResult = txtBasicResult.getText();
		effect.heal = btnHeal.getSelection();
		effect.absorb = btnAbsorb.getSelection();
		return effect;
	}
	
}
