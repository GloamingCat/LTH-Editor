package gui.shell.config;

import java.util.ArrayList;

import gui.Vocab;
import gui.views.IDList;

import org.eclipse.swt.widgets.Group;
import org.eclipse.swt.widgets.Label;
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
import org.eclipse.swt.graphics.Point;

public class PartyShell extends LObjectShell<Party> {

	private IDList lstBattlers;
	private LDataList<Integer> battlers = new LDataList<>();
	private Spinner spnMax;
	
	public PartyShell(Shell parent) {
		super(parent);
		GridData gridData = (GridData) content.getLayoutData();
		gridData.verticalAlignment = SWT.FILL;
		gridData.grabExcessVerticalSpace = true;
		setMinimumSize(new Point(240, 39));
		
		content.setLayout(new GridLayout(2, false));
		
		Label lblMax = new Label(content, SWT.NONE);
		lblMax.setText(Vocab.instance.MAXBATTLEMEMBERS);
		
		spnMax = new Spinner(content, SWT.BORDER);
		spnMax.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1));
		
		Group grpBattler = new Group(content, SWT.NONE);
		grpBattler.setLayout(new FillLayout(SWT.HORIZONTAL));
		grpBattler.setLayoutData(new GridData(SWT.FILL, SWT.FILL, false, true, 2, 1));
		grpBattler.setText(Vocab.instance.BATTLERS);
		
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
