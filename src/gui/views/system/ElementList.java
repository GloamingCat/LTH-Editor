package gui.views.system;

import gui.shell.system.ElementShell;
import gui.widgets.SimpleEditableList;

import data.config.Element;
import lwt.container.LContainer;
import lwt.dialog.LObjectShell;
import lwt.dialog.LShell;
import lwt.dialog.LShellFactory;

public class ElementList extends SimpleEditableList<Element> {

	public ElementList(LContainer parent) {
		super(parent);
		type = Element.class;
		setIncludeID(true);
		setShellFactory(new LShellFactory<Element>() {
			@Override
			public LObjectShell<Element> createShell(LShell parent) {
				return new ElementShell(parent);
			}
		});
	}

}
