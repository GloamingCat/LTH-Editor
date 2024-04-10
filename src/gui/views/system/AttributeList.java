package gui.views.system;

import gui.shell.system.AttributeShell;
import gui.widgets.SimpleEditableList;

import data.config.Attribute;
import lui.container.LContainer;
import lui.dialog.LObjectWindow;
import lui.dialog.LWindow;
import lui.dialog.LWindowFactory;

public class AttributeList extends SimpleEditableList<Attribute> {

	public AttributeList(LContainer parent) {
		super(parent);
		type = Attribute.class;
		setIncludeID(true);
		setShellFactory(new LWindowFactory<Attribute>() {
			@Override
			public LObjectWindow<Attribute> createWindow(LWindow parent) {
				return new AttributeShell(parent);
			}
		});
	}

}
