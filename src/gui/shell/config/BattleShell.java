package gui.shell.config;

import gui.Vocab;
import gui.views.config.BattlerTypeList;

import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Group;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Spinner;

import lwt.dataestructure.LDataList;
import lwt.dialog.LObjectShell;
import lwt.widget.LCombo;

import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.layout.FillLayout;

import project.Project;
import data.BattlerType;
import data.Config.Battle;

public class BattleShell extends LObjectShell<Battle> {

	private BattlerTypeList lstBattlerTypes;
	private LDataList<BattlerType> battlerTypes = new LDataList<>();
	private Button btnPartyTile;
	private Button btnGroupEscape;
	private Button btnIndTurn;
	private Spinner spnCount;
	private LCombo cmbHPAtt;
	private LCombo cmbSPAtt;
	private LCombo cmbTurnAtt;

	public BattleShell(Shell parent) {
		super(parent);
		setText(Vocab.instance.PROPERTIES + " - " + Vocab.instance.BATTLE);
		
		content.setLayout(new GridLayout(2, false));
		
		Group grpGeneral = new Group(content, SWT.NONE);
		grpGeneral.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1));
		grpGeneral.setText(Vocab.instance.GENERAL);
		grpGeneral.setLayout(new GridLayout(2, false));
		
		Label lblCount = new Label(grpGeneral, SWT.NONE);
		lblCount.setText(Vocab.instance.TURNCOUNT);
		
		spnCount = new Spinner(grpGeneral, SWT.BORDER);
		spnCount.setMaximum(99999);
		spnCount.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1));
		
		Composite buttons = new Composite(grpGeneral, SWT.CHECK);
		GridLayout gl_buttons = new GridLayout(1, false);
		gl_buttons.marginHeight = 0;
		gl_buttons.marginWidth = 0;
		buttons.setLayout(gl_buttons);
		buttons.setLayoutData(new GridData(SWT.FILL, SWT.FILL, false, true, 2, 1));
		
		btnIndTurn = new Button(buttons, SWT.CHECK);
		btnIndTurn.setText(Vocab.instance.INDIVIDUALTURN);

		btnGroupEscape = new Button(buttons, SWT.CHECK);
		btnGroupEscape.setText(Vocab.instance.GROUPESCAPE);
		
		btnPartyTile = new Button(buttons, SWT.CHECK);
		btnPartyTile.setText(Vocab.instance.PARTYTILEESCAPE);
		
		Group grpBattlerType = new Group(content, SWT.NONE);
		grpBattlerType.setLayout(new FillLayout(SWT.HORIZONTAL));
		GridData gd_grpBattler = new GridData(SWT.FILL, SWT.FILL, false, true, 1, 2);
		gd_grpBattler.widthHint = 155;
		gd_grpBattler.heightHint = 98;
		grpBattlerType.setLayoutData(gd_grpBattler);
		grpBattlerType.setText(Vocab.instance.BATTLERTYPES);
		
		lstBattlerTypes = new BattlerTypeList(grpBattlerType, SWT.NONE) {
			public LDataList<BattlerType> getDataCollection() {
				return battlerTypes;
			}
		};
		
		Group grpAttributes = new Group(content, SWT.NONE);
		grpAttributes.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1));
		grpAttributes.setText(Vocab.instance.ATTRIBUTES);
		grpAttributes.setLayout(new GridLayout(2, false));
		
		Label lblHP = new Label(grpAttributes, SWT.NONE);
		lblHP.setText(Vocab.instance.ATTHP);
		
		cmbHPAtt = new LCombo(grpAttributes, SWT.NONE);
				
		Label lblSP = new Label(grpAttributes, SWT.NONE);
		lblSP.setText(Vocab.instance.ATTSP);
		
		cmbSPAtt = new LCombo(grpAttributes, SWT.NONE);
		
		Label lblTurn = new Label(grpAttributes, SWT.NONE);
		lblTurn.setText(Vocab.instance.ATTTURN);
		
		cmbTurnAtt = new LCombo(grpAttributes, SWT.NONE);
				
		pack();
	}
	
	public void open(Battle initial) {
		super.open(initial);
		btnIndTurn.setSelection(initial.individualTurn);
		btnGroupEscape.setSelection(initial.groupEscape);
		btnPartyTile.setSelection(initial.partyTileEscape);
		spnCount.setSelection(initial.turnLimit);
		cmbHPAtt.setItems(Project.current.config.getData().attributes);
		cmbSPAtt.setItems(Project.current.config.getData().attributes);
		cmbTurnAtt.setItems(Project.current.config.getData().attributes);
		
		cmbHPAtt.setValue(initial.attHPID);
		cmbSPAtt.setValue(initial.attSPID);
		cmbTurnAtt.setValue(initial.attTurnID);
		
		battlerTypes = new LDataList<BattlerType>();
		for (BattlerType i : initial.battlerTypes) {
			battlerTypes.add(i);
		}
		lstBattlerTypes.onVisible();
	}

	@Override
	protected Battle createResult(Battle initial) {
		if (battlerTypes.equals(initial.battlerTypes) &&
				cmbHPAtt.getValue() == initial.attHPID &&
				cmbSPAtt.getValue() == initial.attSPID &&
				cmbTurnAtt.getValue() == initial.attTurnID &&
				spnCount.getSelection() == initial.turnLimit &&
				btnIndTurn.getSelection() == initial.individualTurn &&
				btnGroupEscape.getSelection() == initial.groupEscape &&
				btnPartyTile.getSelection() == initial.partyTileEscape) {
			return null;
		} else {
			Battle b = new Battle();
			b.battlerTypes = battlerTypes;
			b.individualTurn = btnIndTurn.getSelection();
			b.groupEscape = btnGroupEscape.getSelection();
			b.partyTileEscape = btnPartyTile.getSelection();
			b.turnLimit = spnCount.getSelection();
			b.attHPID = cmbHPAtt.getValue();
			b.attSPID = cmbSPAtt.getValue();
			b.attTurnID = cmbTurnAtt.getValue();
			return b;
		}
	}

}
