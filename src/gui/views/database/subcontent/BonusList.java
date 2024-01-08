package gui.views.database.subcontent;

import gui.shell.database.BonusShell;
import gui.widgets.SimpleEditableList;

import data.subcontent.Bonus;
import lwt.container.LContainer;
import lwt.dialog.LObjectShell;
import lwt.dialog.LShell;
import lwt.dialog.LShellFactory;

public class BonusList extends SimpleEditableList<Bonus> {

	public BonusList(LContainer parent, String title) {
		super(parent);
		type = Bonus.class;
		setIncludeID(false);
		setShellFactory(new LShellFactory<Bonus>() {
			@Override
			public LObjectShell<Bonus> createShell(LShell parent) {
				return new BonusShell(parent, title);
			}
		});
	}

}
