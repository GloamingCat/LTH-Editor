package gui.views.database.subcontent;

import gui.shell.database.PortraitShell;
import gui.views.SimpleEditableList;

import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Shell;

import data.GameCharacter.Portrait;
import lwt.dialog.LObjectShell;
import lwt.dialog.LShellFactory;

public class PortraitList extends SimpleEditableList<Portrait> {
	
	public PortraitList(Composite parent, int style) {
		super(parent, style);
		type = Portrait.class;
		setIncludeID(false);
		setShellFactory(new LShellFactory<Portrait>() {
			@Override
			public LObjectShell<Portrait> createShell(Shell parent) {
				return new PortraitShell(parent);
			}
		});
	}

}
