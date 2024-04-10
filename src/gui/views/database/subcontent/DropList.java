package gui.views.database.subcontent;

import gui.shell.database.DropDialog;
import gui.widgets.SimpleEditableList;

import data.Battler.Drop;
import lui.container.LContainer;
import lui.dialog.LObjectDialog;
import lui.dialog.LWindow;
import lui.dialog.LWindowFactory;

public class DropList extends SimpleEditableList<Drop> {
	
	public DropList(LContainer parent) {
		super(parent);
		type = Drop.class;
		setIncludeID(false);
		setShellFactory(new LWindowFactory<Drop>() {
			@Override
			public LObjectDialog<Drop> createWindow(LWindow parent) {
				return new DropDialog(parent);
			}
		});
	}

}
