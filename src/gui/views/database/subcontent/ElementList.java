package gui.views.database.subcontent;

import gui.shell.database.ElementShell;
import gui.widgets.SimpleEditableList;

import org.eclipse.swt.widgets.Composite;

import data.subcontent.Element;
import lwt.dialog.LObjectShell;
import lwt.dialog.LShellFactory;

public class ElementList extends SimpleEditableList<Element> {

	public ElementList(Composite parent, int style) {
		super(parent, style);
		type = Element.class;
		setIncludeID(false);
		setShellFactory(new LShellFactory<Element>() {
			@Override
			public LObjectShell<Element> createShell(
					org.eclipse.swt.widgets.Shell parent) {
				return new ElementShell(parent);
			}
		});
	}

}
