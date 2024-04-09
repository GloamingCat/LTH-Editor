package gui.views.database.subcontent;

import gui.shell.NodeShell;
import gui.widgets.SimpleEditableList;

import data.subcontent.Node;
import lwt.container.LContainer;
import lbase.data.LDataTree;
import lwt.dialog.LObjectWindow;
import lwt.dialog.LWindow;
import lwt.dialog.LWindowFactory;

public class NodeList extends SimpleEditableList<Node> {

	public LDataTree<Object> dataTree;
	
	public NodeList(LContainer parent, String title) {
		super(parent);
		type = Node.class;
		setIncludeID(true);
		setShellFactory(new LWindowFactory<Node>() {
			@Override
			public LObjectWindow<Node> createWindow(LWindow parent) {
				return new NodeShell(parent, title) {
					public LDataTree<Object> getTree() {
						return getDataTree();
					}
				};
			}
		});
	}
	
	protected LDataTree<Object> getDataTree() {
		return dataTree;
	}

}
