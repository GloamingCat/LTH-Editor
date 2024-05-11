package gui.views.database.subcontent;

import gui.shell.PropertyDialog;
import gui.widgets.SimpleEditableList;

import data.subcontent.Property;
import lui.container.LContainer;
import lui.base.data.LDataTree;
import lui.base.data.LPath;
import lui.dialog.LObjectDialog;
import lui.dialog.LWindow;
import lui.dialog.LWindowFactory;
import lui.base.event.LEditEvent;
import lui.widget.LList;

public class PropertyList extends SimpleEditableList<Property> {

	public LDataTree<Object> dataTree = null;
	
	public PropertyList(LContainer parent, String title) {
		super(parent);
		type = Property.class;
		setIncludeID(false);
		setShellFactory(new LWindowFactory<>() {
			@Override
			public LObjectDialog<Property> createWindow(LWindow parent) {
				return new PropertyDialog(parent, title) {
					public LDataTree<Object> getTree() {
						return getDataTree();
					}
				};
			}
		});
	}
	
	@Override
	protected void createContent(int style) {
		list = new LList<>(this, style == 1) {
			@Override
			public LEditEvent<Property> edit(LPath path) {
				return onEditItem(path);
			}
			@Override
			public Property toObject(LPath path) {
				if (path == null)
					return null;
				return PropertyList.this.getDataCollection().get(path.index);
			}
			@Override
			public LDataTree<Property> toNode(LPath path) {
				Property i = toObject(path);
				return new LDataTree<> (i);
			}
			@Override
			public LDataTree<Property> emptyNode() {
				return new LDataTree<>(new Property());
			}
			@Override
			public LDataTree<Property> duplicateNode(LDataTree<Property> node) {
				Property copy = new Property(node.data);
				return new LDataTree<> (copy);
			}
			@Override
			protected String dataToString(Property item) {
				Object obj = PropertyList.this.getDataTree().get(item.id);
				String id = includeID ? stringID(item.id) : "";
				String name = obj != null ? obj.toString() : ("NULL " + item.id);
				return id + name + ": " + item.value;
			}
			@Override
			protected String encodeNode(LDataTree<Property> node) {
				return PropertyList.this.encodeData(node);
			}
			@Override
			protected LDataTree<Property> decodeNode(String str) {
				return PropertyList.this.decodeData(str);
			}
			@Override
			public boolean canDecode(String str) {
				return PropertyList.this.canDecode(str);
			}
		};
	}
	
	protected LDataTree<Object> getDataTree() { return dataTree; }

}
