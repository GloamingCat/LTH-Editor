package gui.views.database.subcontent;

import gui.shell.database.PortraitDialog;
import gui.widgets.SimpleEditableList;

import data.GameCharacter.Portrait;
import lui.container.LContainer;
import lui.dialog.LObjectDialog;
import lui.dialog.LWindow;
import lui.dialog.LWindowFactory;

public class PortraitList extends SimpleEditableList<Portrait> {
	
	public PortraitList(LContainer parent) {
		super(parent);
		type = Portrait.class;
		setIncludeID(false);
		setShellFactory(new LWindowFactory<>() {
			@Override
			public LObjectDialog<Portrait> createWindow(LWindow parent) {
				return new PortraitDialog(parent);
			}
		});
	}

}
