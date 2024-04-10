package gui.widgets;

import gui.shell.IDShell;
import lui.container.LContainer;
import lui.base.data.LDataTree;
import lui.base.data.LPath;
import lui.dialog.LObjectDialog;
import lui.dialog.LWindow;
import lui.dialog.LWindowFactory;
import lui.base.event.LEditEvent;
import lui.widget.LList;

public class IDList extends SimpleEditableList<Integer> {

	public LDataTree<Object> dataTree = null;
	
	public IDList(LContainer parent, String title) {
		super(parent);
		type = Integer.class;
		setShellFactory(new LWindowFactory<Integer>() {
			@Override
			public LObjectDialog<Integer> createWindow(LWindow parent) {
				IDShell shell = new IDShell(parent, title, 0) {
					public LDataTree<Object> getTree() {
						return getDataTree();
					}
				};
				return shell;
			}
		});
		list.setIncludeID(false);
	}
	
	protected LList<Integer, Integer> createList(boolean check) {
		IDList self = this;
		return new LList<Integer, Integer>(this, check) {
			@Override
			public LEditEvent<Integer> edit(LPath path) {
				return onEditItem(path);
			}
			@Override
			public Integer toObject(LPath path) {
				if (path == null)
					return null;
				return self.getDataCollection().get(path.index);
			}
			@Override
			public LDataTree<Integer> toNode(LPath path) {
				Integer i = toObject(path);
				return new LDataTree<Integer> (i);
			}
			@Override
			public LDataTree<Integer> emptyNode() {
				return new LDataTree<Integer>(0);
			}
			@Override
			public LDataTree<Integer> duplicateNode(LDataTree<Integer> node) {
				return new LDataTree<Integer>(node.data);
			}
			@Override
			protected String dataToString(Integer id) {
				Object obj = self.getDataTree().get(id);
				String idstr = includeID ? stringID(id) : "";
				if (obj == null)
					return idstr + "    ";
				return idstr + obj.toString();
			}
			@Override
			protected String encodeNode(LDataTree<Integer> node) {
				return node.data + "";
			}
			@Override
			protected LDataTree<Integer> decodeNode(String str) {
				return new LDataTree<Integer>(Integer.parseInt(str));
			}
			@Override
			public boolean canDecode(String str) {
				try {
					Integer.parseInt(str);
					return true;
				} catch(NumberFormatException e) {
					return false;
				}
			}
		};
	}

	public LDataTree<Object> getDataTree() { return dataTree; }
	
}
