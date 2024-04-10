package gui.views.database.subcontent;

import gui.shell.database.DropShell;
import gui.widgets.SimpleEditableList;

import data.Battler.Drop;
import lui.container.LContainer;
import lui.dialog.LObjectWindow;
import lui.dialog.LWindow;
import lui.dialog.LWindowFactory;

public class DropList extends SimpleEditableList<Drop> {
	
	public DropList(LContainer parent) {
		super(parent);
		type = Drop.class;
		setIncludeID(false);
		setShellFactory(new LWindowFactory<Drop>() {
			@Override
			public LObjectWindow<Drop> createWindow(LWindow parent) {
				return new DropShell(parent);
			}
		});
	}

}
