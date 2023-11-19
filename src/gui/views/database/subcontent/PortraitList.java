package gui.views.database.subcontent;

import gui.shell.database.PortraitShell;
import gui.widgets.SimpleEditableList;

import data.GameCharacter.Portrait;
import lwt.container.LContainer;
import lwt.dialog.LObjectShell;
import lwt.dialog.LShell;
import lwt.dialog.LShellFactory;

public class PortraitList extends SimpleEditableList<Portrait> {
	
	public PortraitList(LContainer parent) {
		super(parent);
		type = Portrait.class;
		setIncludeID(false);
		setShellFactory(new LShellFactory<Portrait>() {
			@Override
			public LObjectShell<Portrait> createShell(LShell parent) {
				return new PortraitShell(parent);
			}
		});
	}

}
