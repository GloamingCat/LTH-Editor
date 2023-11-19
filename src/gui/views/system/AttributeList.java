package gui.views.system;

import gui.shell.system.AttributeShell;
import gui.widgets.SimpleEditableList;

import data.config.Attribute;
import lwt.container.LContainer;
import lwt.dataestructure.LDataList;
import lwt.dialog.LObjectShell;
import lwt.dialog.LShell;
import lwt.dialog.LShellFactory;

public class AttributeList extends SimpleEditableList<Attribute> {
	
	protected LDataList<Attribute> currentList;
	
	public AttributeList(LContainer parent) {
		super(parent);
		type = Attribute.class;
		setIncludeID(true);
		setShellFactory(new LShellFactory<Attribute>() {
			@Override
			public LObjectShell<Attribute> createShell(LShell parent) {
				return new AttributeShell(parent);
			}
		});
	}

}
