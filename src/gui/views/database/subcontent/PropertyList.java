package gui.views.database.subcontent;

import gui.shell.PropertyShell;
import gui.widgets.SimpleEditableList;

import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Shell;

import data.subcontent.Property;
import lwt.dataestructure.LDataTree;
import lwt.dataestructure.LPath;
import lwt.dialog.LObjectShell;
import lwt.dialog.LShellFactory;
import lwt.event.LEditEvent;
import lwt.widget.LList;

public abstract class PropertyList extends SimpleEditableList<Property> {

	public PropertyList(Composite parent, int style) {
		super(parent, style);
		type = Property.class;
		setIncludeID(false);
		setShellFactory(new LShellFactory<Property>() {
			@Override
			public LObjectShell<Property> createShell(Shell parent) {
				return new PropertyShell(parent) {
					public LDataTree<Object> getTree() {
						return getDataTree();
					};
				};
			}
		});
	}
	
	protected LList<Property, Property> createList() {
		PropertyList self = this;
		return new LList<Property, Property>(this, SWT.NONE) {
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
					return "NULL";
				return id + obj.toString() + ": " + item.value;
			}
		};
	}
	
	protected abstract LDataTree<Object> getDataTree();

}
