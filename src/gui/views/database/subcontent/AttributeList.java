package gui.views.database.subcontent;

import gui.shell.database.AttributeBonusShell;
import gui.widgets.SimpleEditableList;

import data.Item.Attribute;
import lwt.container.LContainer;
import lwt.dialog.LShell;
import lwt.dialog.LShellFactory;

public class AttributeList extends SimpleEditableList<Attribute> {
	
	public AttributeList(LContainer parent) {
		super(parent);
		type = Attribute.class;
		setIncludeID(false);
		setShellFactory(new LShellFactory<Attribute>() {
			@Override
			public AttributeBonusShell createShell(LShell parent) {
				return new AttributeBonusShell(parent);
			}
		});
	}

}
