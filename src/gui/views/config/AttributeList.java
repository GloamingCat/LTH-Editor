package gui.views.config;

import gui.shell.config.AttributeShell;
import gui.widgets.SimpleEditableList;

import org.eclipse.swt.widgets.Composite;

import data.config.Attribute;
import lwt.dataestructure.LDataList;
import lwt.dialog.LObjectShell;
import lwt.dialog.LShellFactory;

public class AttributeList extends SimpleEditableList<Attribute> {
	
	protected LDataList<Attribute> currentList;
	
	public AttributeList(Composite parent, int style) {
		super(parent, style);
		type = Attribute.class;
		setIncludeID(true);
		setShellFactory(new LShellFactory<Attribute>() {
			@Override
			public LObjectShell<Attribute> createShell(
					org.eclipse.swt.widgets.Shell parent) {
				return new AttributeShell(parent);
			}
		});
	}

}
