package gui.views.database.subcontent;

import gui.shell.database.DropShell;
import gui.widgets.SimpleEditableList;

import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Shell;

import data.Battler.Drop;
import lwt.dialog.LObjectShell;
import lwt.dialog.LShellFactory;

public class DropList extends SimpleEditableList<Drop> {
	
	public DropList(Composite parent, int style) {
		super(parent, style);
		type = Drop.class;
		attributeName = "items";
		setIncludeID(false);
		setShellFactory(new LShellFactory<Drop>() {
			@Override
			public LObjectShell<Drop> createShell(Shell parent) {
				return new DropShell(parent);
			}
		});
	}

}
