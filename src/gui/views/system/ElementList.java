package gui.views.system;

import gui.shell.system.ElementShell;
import gui.widgets.SimpleEditableList;

import data.config.Element;
import lwt.container.LContainer;
import lwt.dialog.LObjectWindow;
import lwt.dialog.LWindow;
import lwt.dialog.LWindowFactory;

public class ElementList extends SimpleEditableList<Element> {

	public ElementList(LContainer parent) {
		super(parent);
		type = Element.class;
		setIncludeID(true);
		setShellFactory(new LWindowFactory<Element>() {
			@Override
			public LObjectWindow<Element> createWindow(LWindow parent) {
				return new ElementShell(parent);
			}
		});
	}

}
