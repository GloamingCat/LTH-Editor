package gui.views.database.subcontent;

import gui.shell.database.AttributeBonusShell;
import gui.widgets.SimpleEditableList;

import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Shell;

import data.Item.Attribute;
import lwt.dialog.LShellFactory;

public class AttributeList extends SimpleEditableList<Attribute> {
	
	public AttributeList(Composite parent, int style) {
		super(parent, style);
		type = Attribute.class;
		setIncludeID(false);
		setShellFactory(new LShellFactory<Attribute>() {
			@Override
			public AttributeBonusShell createShell(Shell parent) {
				return new AttributeBonusShell(parent);
			}
		});
	}

}
