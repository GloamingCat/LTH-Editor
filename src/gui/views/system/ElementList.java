package gui.views.system;

import gui.shell.system.ElementShell;
import gui.widgets.SimpleEditableList;

import org.eclipse.swt.widgets.Composite;

import data.config.Element;
import lwt.dialog.LObjectShell;
import lwt.dialog.LShellFactory;

public class ElementList extends SimpleEditableList<Element> {

	public ElementList(Composite parent, int style) {
		super(parent, style);
		type = Element.class;
		setIncludeID(true);
		setShellFactory(new LShellFactory<Element>() {
			@Override
			public LObjectShell<Element> createShell(
					org.eclipse.swt.widgets.Shell parent) {
				return new ElementShell(parent);
			}
		});
	}

}
