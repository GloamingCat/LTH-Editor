package gui.views.database.subcontent;

import gui.shell.database.BonusShell;
import gui.widgets.SimpleEditableList;

import data.subcontent.Bonus;
import lwt.container.LContainer;
import lwt.dialog.LObjectWindow;
import lwt.dialog.LWindow;
import lwt.dialog.LWindowFactory;

public class BonusList extends SimpleEditableList<Bonus> {

	public BonusList(LContainer parent, String title) {
		super(parent);
		type = Bonus.class;
		setIncludeID(false);
		setShellFactory(new LWindowFactory<Bonus>() {
			@Override
			public LObjectWindow<Bonus> createWindow(LWindow parent) {
				return new BonusShell(parent, title);
			}
		});
	}

}
