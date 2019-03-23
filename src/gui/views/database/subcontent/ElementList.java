package gui.views.database.subcontent;

import gui.shell.database.ElementShell;
import gui.widgets.SimpleEditableList;

import org.eclipse.swt.widgets.Composite;

import data.subcontent.Bonus;
import lwt.dialog.LObjectShell;
import lwt.dialog.LShellFactory;

public class ElementList extends SimpleEditableList<Bonus> {

	public ElementList(Composite parent, int style) {
		super(parent, style);
		type = Bonus.class;
		setIncludeID(false);
		setShellFactory(new LShellFactory<Bonus>() {
			@Override
			public LObjectShell<Bonus> createShell(
					org.eclipse.swt.widgets.Shell parent) {
				return new ElementShell(parent);
			}
		});
	}

}
