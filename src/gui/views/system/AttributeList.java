package gui.views.system;

import gui.shell.system.AttributeDialog;
import gui.widgets.SimpleEditableList;

import data.config.Attribute;
import lui.container.LContainer;
import lui.dialog.LObjectDialog;
import lui.dialog.LWindow;
import lui.dialog.LWindowFactory;

public class AttributeList extends SimpleEditableList<Attribute> {

	public AttributeList(LContainer parent) {
		super(parent);
		type = Attribute.class;
		setIncludeID(true);
		setShellFactory(new LWindowFactory<Attribute>() {
			@Override
			public LObjectDialog<Attribute> createWindow(LWindow parent) {
				return new AttributeDialog(parent);
			}
		});
	}

}
