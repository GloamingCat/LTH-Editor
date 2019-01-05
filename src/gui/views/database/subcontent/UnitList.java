package gui.views.database.subcontent;

import gui.shell.UnitShell;
import gui.widgets.SimpleEditableList;

import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Shell;

import data.subcontent.Unit;
import lwt.dialog.LObjectShell;
import lwt.dialog.LShellFactory;

public class UnitList extends SimpleEditableList<Unit> {
	
	public UnitList(Composite parent, int style) {
		super(parent, style);
		type = Unit.class;
		setIncludeID(false);
		setShellFactory(new LShellFactory<Unit>() {
			@Override
			public LObjectShell<Unit> createShell(Shell parent) {
				return new UnitShell(parent);
			}
		});
	}

}
