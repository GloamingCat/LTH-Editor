package gui.views.database.subcontent;

import gui.shell.database.PortraitShell;
import gui.widgets.SimpleEditableList;

import data.GameCharacter.Portrait;
import lwt.container.LContainer;
import lwt.dialog.LObjectWindow;
import lwt.dialog.LWindow;
import lwt.dialog.LWindowFactory;

public class PortraitList extends SimpleEditableList<Portrait> {
	
	public PortraitList(LContainer parent) {
		super(parent);
		type = Portrait.class;
		setIncludeID(false);
		setShellFactory(new LWindowFactory<Portrait>() {
			@Override
			public LObjectWindow<Portrait> createWindow(LWindow parent) {
				return new PortraitShell(parent);
			}
		});
	}

}
