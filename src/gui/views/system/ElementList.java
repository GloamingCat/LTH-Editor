package gui.views.system;

import gui.shell.system.ElementDialog;
import gui.widgets.SimpleEditableList;

import data.config.Element;
import lui.container.LContainer;
import lui.dialog.LObjectDialog;
import lui.dialog.LWindow;
import lui.dialog.LWindowFactory;

public class ElementList extends SimpleEditableList<Element> {

	public ElementList(LContainer parent) {
		super(parent);
		type = Element.class;
		setIncludeID(true);
		setShellFactory(new LWindowFactory<Element>() {
			@Override
			public LObjectDialog<Element> createWindow(LWindow parent) {
				return new ElementDialog(parent);
			}
		});
	}

}
