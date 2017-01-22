package gui.shell.config;

import gui.Vocab;
import gui.views.config.BattlerTypeList;

import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Group;
import org.eclipse.swt.widgets.Shell;

import lwt.dataestructure.LDataList;
import lwt.dialog.LObjectShell;

import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.layout.FillLayout;

import data.BattlerType;
import data.Config.Battle;

public class BattleShell extends LObjectShell<Battle> {

	private BattlerTypeList lstBattlerTypes;
	private LDataList<BattlerType> battlerTypes = new LDataList<>();
	private Button btnPartyTile;
	private Button btnGroupEscape;
	private Button btnIndTurn;

	public BattleShell(Shell parent) {
		super(parent);
		
		content.setLayout(new GridLayout(1, false));
		
		btnIndTurn = new Button(content, SWT.CHECK);
		btnIndTurn.setText(Vocab.instance.INDIVIDUALTURN);

		btnGroupEscape = new Button(content, SWT.CHECK);
		btnGroupEscape.setText(Vocab.instance.GROUPESCAPE);
		
		btnPartyTile = new Button(content, SWT.CHECK);
		btnPartyTile.setText(Vocab.instance.PARTYTILEESCAPE);
		
		Group grpBattlerType = new Group(content, SWT.NONE);
		grpBattlerType.setLayout(new FillLayout(SWT.HORIZONTAL));
		GridData gd_grpBattler = new GridData(SWT.FILL, SWT.FILL, false, true, 2, 1);
		gd_grpBattler.widthHint = 155;
		gd_grpBattler.heightHint = 98;
		grpBattlerType.setLayoutData(gd_grpBattler);
		grpBattlerType.setText(Vocab.instance.BATTLERTYPES);
		
		lstBattlerTypes = new BattlerTypeList(grpBattlerType, SWT.NONE) {
			public LDataList<BattlerType> getDataCollection() {
				return battlerTypes;
			}
		};

		pack();
	}
	
	public void open(Battle initial) {
		super.open(initial);
		btnIndTurn.setSelection(initial.individualTurn);
		btnGroupEscape.setSelection(initial.groupEscape);
		btnPartyTile.setSelection(initial.partyTileEscape);
		
		battlerTypes = new LDataList<BattlerType>();
		for (BattlerType i : initial.battlerTypes) {
			battlerTypes.add(i);
		}
		lstBattlerTypes.onVisible();
	}

	@Override
	protected Battle createResult(Battle initial) {
		if (battlerTypes.equals(initial.battlerTypes) &&
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
			return b;
		}
	}

}
