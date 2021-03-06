package gui.views.database.subcontent;

import gui.shell.NodeShell;
import gui.widgets.SimpleEditableList;

import org.eclipse.swt.widgets.Composite;

import data.subcontent.Node;
import lwt.dataestructure.LDataTree;
import lwt.dialog.LObjectShell;
import lwt.dialog.LShellFactory;

public abstract class NodeList extends SimpleEditableList<Node> {

	public NodeList(Composite parent, int style) {
		super(parent, style);
		type = Node.class;
		setIncludeID(true);
		setShellFactory(new LShellFactory<Node>() {
			@Override
			public LObjectShell<Node> createShell(
					org.eclipse.swt.widgets.Shell parent) {
				return new NodeShell(parent) {
					public LDataTree<Object> getTree() {
						return getDataTree();
					}
				};
			}
		});
	}
	
	protected abstract LDataTree<Object> getDataTree();

}
