package gui.views.database.subcontent;

import gui.shell.database.AttributeBonusShell;
import gui.widgets.SimpleEditableList;

import data.Item.Attribute;
import lwt.container.LContainer;
import lwt.dialog.LWindow;
import lwt.dialog.LWindowFactory;

public class AttributeList extends SimpleEditableList<Attribute> {
	
	public AttributeList(LContainer parent) {
		super(parent);
		type = Attribute.class;
		setIncludeID(false);
		setShellFactory(new LWindowFactory<Attribute>() {
			@Override
			public AttributeBonusShell createWindow(LWindow parent) {
				return new AttributeBonusShell(parent);
			}
		});
	}

}
