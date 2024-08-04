package gui.widgets;

import gui.shell.IDDialog;
import lui.container.LContainer;
import lui.base.data.LDataTree;
import lui.base.data.LPath;
import lui.dialog.LObjectDialog;
import lui.dialog.LWindow;
import lui.dialog.LWindowFactory;
import lui.base.event.LEditEvent;
import lui.collection.LEditableList;

public class IDList extends SimpleEditableList<Integer> {

	public LDataTree<Object> dataTree = null;
	
	public IDList(LContainer parent, String title) {
		super(parent);
		type = Integer.class;
		setShellFactory(new LWindowFactory<>() {
			@Override
			public LObjectDialog<Integer> createWindow(LWindow parent) {
                return new IDDialog(parent, title, 0) {
					public LDataTree<Object> getTree() {
						return getDataTree();
					}
				};
			}
		});
		list.setIncludeID(false);
	}

	@Override
	protected void createContent(int style) {
		list = new LEditableList<>(this, style == 1) {
			@Override
			public LEditEvent<Integer> edit(LPath path) {
				return onEditItem(path);
			}
			@Override
			public Integer toObject(LPath path) {
				if (path == null)
					return null;
				return IDList.this.getDataCollection().get(path.index);
			}
			@Override
			public LDataTree<Integer> toNode(LPath path) {
				Integer i = toObject(path);
				return new LDataTree<> (i);
			}
			@Override
			public LDataTree<Integer> emptyNode() {
				return new LDataTree<>(0);
			}
			@Override
			public LDataTree<Integer> duplicateNode(LDataTree<Integer> node) {
				return new LDataTree<>(node.data);
			}
			@Override
			protected String dataToString(Integer id) {
				Object obj = IDList.this.getDataTree().get(id);
				String str = includeID ? stringID(id) : "";
				if (obj == null)
					return str + "    ";
				return str + obj;
			}
			@Override
			protected String encodeNode(LDataTree<Integer> node) {
				return node.data + "";
			}
			@Override
			protected LDataTree<Integer> decodeNode(String str) {
				return new LDataTree<>(Integer.parseInt(str));
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
