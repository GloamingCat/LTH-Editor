package gui.views.database.subcontent;

import gui.shell.database.DropShell;
import gui.widgets.SimpleEditableList;

import data.Battler.Drop;
import lwt.container.LContainer;
import lwt.dialog.LObjectShell;
import lwt.dialog.LShell;
import lwt.dialog.LShellFactory;

public class DropList extends SimpleEditableList<Drop> {
	
	public DropList(LContainer parent) {
		super(parent);
		type = Drop.class;
		setIncludeID(false);
		setShellFactory(new LShellFactory<Drop>() {
			@Override
			public LObjectShell<Drop> createShell(LShell parent) {
				return new DropShell(parent);
			}
		});
	}

}
