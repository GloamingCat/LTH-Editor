package gui.widgets;

import gui.shell.NameShell;
import lwt.container.LContainer;
import lwt.dialog.LObjectShell;
import lwt.dialog.LShell;
import lwt.dialog.LShellFactory;

public class NameList extends SimpleEditableList<String> {

	public NameList(LContainer parent, String title) {
		super(parent);
		type = String.class;
		setIncludeID(true);
		setShellFactory(new LShellFactory<String>() {
			@Override
			public LObjectShell<String> createShell(LShell parent) {
				return new NameShell(parent, title);
			}
		});
	}

}
