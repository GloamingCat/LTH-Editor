package gui.shell.config;

import gui.Vocab;

import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Shell;

import lwt.dialog.LObjectShell;
import lwt.widget.LCombo;

import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;

import project.Project;
import data.Config.Battle;

public class BattleShell extends LObjectShell<Battle> {

	private LCombo cmbTrade;
	private LCombo cmbHPAtt;
	private LCombo cmbSPAtt;
	private LCombo cmbTurnAtt;
	private LCombo cmbStepAtt;
	private LCombo cmbJumpAtt;

	public BattleShell(Shell parent) {
		super(parent);
		GridLayout gridLayout = new GridLayout();
		gridLayout.numColumns = 2;
		content.setLayout(gridLayout);
		setText(Vocab.instance.PROPERTIES + " - " + Vocab.instance.BATTLE);

		Label lblTrade = new Label(content, SWT.NONE);
		lblTrade.setText(Vocab.instance.TRADESKILL);
		
		cmbTrade = new LCombo(content, SWT.NONE);
		cmbTrade.setOptional(false);
		cmbTrade.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1));

		Label lblHP = new Label(content, SWT.NONE);
		lblHP.setText(Vocab.instance.ATTHP);
		
		cmbHPAtt = new LCombo(content, SWT.NONE);
		cmbHPAtt.setOptional(false);
		cmbHPAtt.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, false, false, 1, 1));
				
		Label lblSP = new Label(content, SWT.NONE);
		lblSP.setText(Vocab.instance.ATTSP);
		
		cmbSPAtt = new LCombo(content, SWT.NONE);
		cmbSPAtt.setOptional(false);
		cmbSPAtt.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, false, false, 1, 1));
		
		Label lblTurn = new Label(content, SWT.NONE);
		lblTurn.setText(Vocab.instance.ATTTURN);
		
		cmbTurnAtt = new LCombo(content, SWT.NONE);
		cmbTurnAtt.setOptional(false);
		cmbTurnAtt.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, false, false, 1, 1));
		
		Label lblStep = new Label(content, SWT.NONE);
		lblStep.setText(Vocab.instance.ATTSTEP);
		
		cmbStepAtt = new LCombo(content, SWT.NONE);
		cmbStepAtt.setOptional(false);
		cmbStepAtt.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, false, false, 1, 1));
		
		Label lblJump = new Label(content, SWT.NONE);
		lblJump.setText(Vocab.instance.ATTJUMP);
		
		cmbJumpAtt = new LCombo(content, SWT.NONE);
		cmbJumpAtt.setOptional(false);
		cmbJumpAtt.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, false, false, 1, 1));
		
		pack();
	}
	
	public void open(Battle initial) {
		super.open(initial);
		cmbHPAtt.setItems(Project.current.config.getData().attributes);
		cmbSPAtt.setItems(Project.current.config.getData().attributes);
		cmbTurnAtt.setItems(Project.current.config.getData().attributes);
		cmbStepAtt.setItems(Project.current.config.getData().attributes);
		cmbJumpAtt.setItems(Project.current.config.getData().attributes);
		cmbTrade.setItems(Project.current.skills.getList());
		
		cmbHPAtt.setValue(initial.attHPID);
		cmbSPAtt.setValue(initial.attSPID);
		cmbTurnAtt.setValue(initial.attTurnID);
		cmbStepAtt.setValue(initial.attStepID);
		cmbJumpAtt.setValue(initial.attJumpID);
		cmbTrade.setValue(initial.tradeSkillID);
	}

	@Override
	protected Battle createResult(Battle initial) {
		if (cmbHPAtt.getValue() == initial.attHPID &&
				cmbSPAtt.getValue() == initial.attSPID &&
				cmbTurnAtt.getValue() == initial.attTurnID &&
				cmbStepAtt.getValue() == initial.attStepID &&
				cmbJumpAtt.getValue() == initial.attJumpID &&
				cmbTrade.getValue() == initial.tradeSkillID) {
			return null;
		} else {
			Battle b = new Battle();
			b.attHPID = cmbHPAtt.getValue();
			b.attSPID = cmbSPAtt.getValue();
			b.attStepID = cmbStepAtt.getValue();
			b.attJumpID = cmbJumpAtt.getValue();
			b.attTurnID = cmbTurnAtt.getValue();
			b.tradeSkillID = cmbTrade.getValue();
			return b;
		}
	}

}
