package gui.shell.config;

import java.util.ArrayList;

import gui.Vocab;
import gui.views.IDList;

import org.eclipse.swt.widgets.Group;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Spinner;

import lwt.dataestructure.LDataList;
import lwt.dialog.LObjectShell;

import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.layout.FillLayout;

import project.Project;
import data.Config.Party;

public class PartyShell extends LObjectShell<Party> {

	private IDList lstBattlers;
	private LDataList<Integer> battlers = new LDataList<>();
	private Spinner spnMax;
	
	public PartyShell(Shell parent) {
		super(parent);
		
		content.setLayout(new GridLayout(1, false));
		
		spnMax = new Spinner(content, SWT.NONE);
		
		Group grpBattler = new Group(content, SWT.NONE);
		grpBattler.setLayout(new FillLayout(SWT.HORIZONTAL));
		GridData gd_grpBattler = new GridData(SWT.FILL, SWT.FILL, false, true, 2, 1);
		grpBattler.setLayoutData(gd_grpBattler);
		grpBattler.setText(Vocab.instance.BATTLERTYPES);
		
		lstBattlers = new IDList(grpBattler, SWT.NONE) {
			public ArrayList<?> comboArray() {
				return Project.current.battlers.getList();
			}
			public LDataList<Integer> getDataCollection() {
				return battlers;
			}
		};

		pack();
	}
	
	public void open(Party initial) {
		super.open(initial);
		spnMax.setSelection(initial.maxBattleMembers);
		battlers = new LDataList<Integer>();
		for (Integer i : initial.initialMembers) {
			battlers.add(i);
		}
		lstBattlers.onVisible();
	}

	@Override
	protected Party createResult(Party initial) {
		if (battlers.equals(initial.initialMembers) &&
				spnMax.getSelection() == initial.maxBattleMembers) {
			return null;
		} else {
			Party b = new Party();
			b.initialMembers = battlers;
			b.maxBattleMembers = spnMax.getSelection();
			return b;
		}
	}

}
