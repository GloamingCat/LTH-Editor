package gui.views.database.subcontent;

import gui.shell.NodeShell;
import gui.widgets.SimpleEditableList;

import data.subcontent.Node;
import lui.container.LContainer;
import lui.base.data.LDataTree;
import lui.dialog.LObjectWindow;
import lui.dialog.LWindow;
import lui.dialog.LWindowFactory;

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
