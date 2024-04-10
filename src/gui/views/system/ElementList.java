package gui.views.system;

import gui.shell.system.ElementShell;
import gui.widgets.SimpleEditableList;

import data.config.Element;
import lui.container.LContainer;
import lui.dialog.LObjectWindow;
import lui.dialog.LWindow;
import lui.dialog.LWindowFactory;

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
