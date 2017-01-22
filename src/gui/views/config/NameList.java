package gui.views.config;

import gui.shell.NameShell;
import gui.views.SimpleEditableList;

import org.eclipse.swt.widgets.Composite;

import lwt.dialog.LObjectShell;
import lwt.dialog.LShellFactory;

public class NameList extends SimpleEditableList<String> {

	public NameList(Composite parent, int style) {
		super(parent, style);
		type = String.class;
		setIncludeID(true);
		setShellFactory(new LShellFactory<String>() {
			@Override
			public LObjectShell<String> createShell(
					org.eclipse.swt.widgets.Shell parent) {
				return new NameShell(parent);
			}
		});
	}

}
