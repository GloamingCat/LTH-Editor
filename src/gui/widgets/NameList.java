package gui.widgets;

import gui.shell.NameShell;
import lwt.container.LContainer;
import lwt.dialog.LObjectWindow;
import lwt.dialog.LWindow;
import lwt.dialog.LWindowFactory;

public class NameList extends SimpleEditableList<String> {

	public NameList(LContainer parent, String title) {
		super(parent);
		type = String.class;
		setIncludeID(true);
		setShellFactory(new LWindowFactory<String>() {
			@Override
			public LObjectWindow<String> createWindow(LWindow parent) {
				return new NameShell(parent, title);
			}
		});
	}

}
