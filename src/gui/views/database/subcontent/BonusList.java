package gui.views.database.subcontent;

import gui.shell.database.BonusShell;
import gui.widgets.SimpleEditableList;

import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Shell;

import data.subcontent.Bonus;
import lwt.dialog.LObjectShell;
import lwt.dialog.LShellFactory;

public class BonusList extends SimpleEditableList<Bonus> {

	public BonusList(Composite parent, int style) {
		super(parent, style);
		type = Bonus.class;
		setIncludeID(false);
		setShellFactory(new LShellFactory<Bonus>() {
			@Override
			public LObjectShell<Bonus> createShell(Shell parent) {
				return new BonusShell(parent);
			}
		});
	}

}
