package gui.views.database.subcontent;

import gui.shell.PropertyShell;
import gui.widgets.SimpleEditableList;

import data.subcontent.Property;
import lwt.container.LContainer;
import lwt.dataestructure.LDataTree;
import lwt.dataestructure.LPath;
import lwt.dialog.LObjectShell;
import lwt.dialog.LShell;
import lwt.dialog.LShellFactory;
import lwt.event.LEditEvent;
import lwt.widget.LList;

public class PropertyList extends SimpleEditableList<Property> {

	public LDataTree<Object> dataTree = null;
	
	public PropertyList(LContainer parent) {
		super(parent);
		type = Property.class;
		setIncludeID(false);
		setShellFactory(new LShellFactory<Property>() {
			@Override
			public LObjectShell<Property> createShell(LShell parent) {
				return new PropertyShell(parent) {
					public LDataTree<Object> getTree() {
						return getDataTree();
					};
				};
			}
		});
	}
	
	@Override
	protected LList<Property, Property> createList(boolean check) {
		PropertyList self = this;
		return new LList<Property, Property>(this, check) {
			@Override
			public LEditEvent<Property> edit(LPath path) {
				return onEditItem(path);
			}
			@Override
			public Property toObject(LPath path) {
				if (path == null)
					return null;
				return self.getDataCollection().get(path.index);
			}
			@Override
			public LDataTree<Property> toNode(LPath path) {
				Property i = toObject(path);
				return new LDataTree<Property> (i);
			}
			@Override
			public LDataTree<Property> emptyNode() {
				return new LDataTree<Property>(new Property());
			}
			@Override
			public LDataTree<Property> duplicateNode(LDataTree<Property> node) {
				Property copy = new Property(node.data);
				return new LDataTree<Property> (copy);
			}
			@Override
			protected String dataToString(Property item) {
				Object obj = self.getDataTree().get(item.id);
				String id = includeID ? stringID(item.id) : "";
				if (obj == null)
					return "NULL " + item.id;
				return id + obj.toString() + ": " + item.value;
			}
			@Override
			protected String encodeNode(LDataTree<Property> node) {
				return self.encodeData(node);
			}
			@Override
			protected LDataTree<Property> decodeNode(String str) {
				return self.decodeData(str);
			}
			@Override
			public boolean canDecode(String str) {
				return self.canDecode(str);
			}
		};
	}
	
	protected LDataTree<Object> getDataTree() { return dataTree; }

}
