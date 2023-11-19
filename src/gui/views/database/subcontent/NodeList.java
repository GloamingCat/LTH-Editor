package gui.views.database.subcontent;

import gui.shell.NodeShell;
import gui.widgets.SimpleEditableList;

import data.subcontent.Node;
import lwt.container.LContainer;
import lwt.dataestructure.LDataTree;
import lwt.dialog.LObjectShell;
import lwt.dialog.LShell;
import lwt.dialog.LShellFactory;

public abstract class NodeList extends SimpleEditableList<Node> {

	public NodeList(LContainer parent) {
		super(parent);
		type = Node.class;
		setIncludeID(true);
		setShellFactory(new LShellFactory<Node>() {
			@Override
			public LObjectShell<Node> createShell(LShell parent) {
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
