package gui.views.database.subcontent;

import java.util.ArrayList;

import gui.shell.NodeShell;
import gui.views.SimpleEditableList;

import org.eclipse.swt.widgets.Composite;

import data.subcontent.Node;
import lwt.dialog.LObjectShell;
import lwt.dialog.LShellFactory;

public class NodeList extends SimpleEditableList<Node> {

	public NodeList(Composite parent, int style) {
		super(parent, style);
		type = Node.class;
		setIncludeID(true);
		setShellFactory(new LShellFactory<Node>() {
			@Override
			public LObjectShell<Node> createShell(
					org.eclipse.swt.widgets.Shell parent) {
				return new NodeShell(parent) {
					public ArrayList<?> getArray() {
						return comboArray();
					}
				};
			}
		});
	}
	
	protected ArrayList<?> comboArray() { return null; }

}
