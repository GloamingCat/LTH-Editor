package gui.views.database.subcontent;

import gui.shell.database.AttributeBonusShell;
import gui.widgets.SimpleEditableList;

import data.Item.Attribute;
import lui.container.LContainer;
import lui.dialog.LWindow;
import lui.dialog.LWindowFactory;

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
