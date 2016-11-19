package gui.views.database.subcontent;

import java.util.ArrayList;

import gui.shell.IDShell;

import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.TreeItem;

import lwt.dataestructure.LDataCollection;
import lwt.dataestructure.LDataList;
import lwt.dataestructure.LDataTree;
import lwt.dataestructure.LPath;
import lwt.dialog.LObjectShell;
import lwt.dialog.LShellFactory;
import lwt.editor.LCollectionEditor;
import lwt.event.LEditEvent;
import lwt.widget.LList;

public class IDList extends LCollectionEditor<Integer, Integer> {

	protected LList<Integer, Integer> list;
	protected LDataList<Integer> currentList;
	
	public IDList(Composite parent, int style) {
		super(parent, style);
		
		list = new LList<Integer, Integer>(this, SWT.NONE) {
			@Override
			public LEditEvent<Integer> edit(LPath path) {
				return onEditItem(path);
			}
			@Override
			public Object toObject(LPath path) {
				if (path == null)
					return null;
				return getList().get(path.index);
			}
			@Override
			public LDataTree<Integer> emptyNode() {
				return new LDataTree<Integer>(0);
			}
			@Override
			public LDataTree<Integer> duplicateNode(LPath path) {
				return new LDataTree<Integer> (getList().get(path.index));
			}
			@Override
			public LDataTree<Integer> toNode(LPath path) {
				return new LDataTree<Integer> (getList().get(path.index));
			}
			@Override
			public void renameItem(LPath path) {
				TreeItem item = toTreeItem(path);
				if (item != null) {
					item.setText(itemName(item.getData()));
				}
			}
			@Override
			public void refreshItems() {
				for(TreeItem item : tree.getItems()) {
					String name = itemName(item.getData());
					item.setText(name);
				}
			}
			protected String itemName(Object item) {
				Integer id = (Integer) item;
				Object obj = comboArray().get(id);
				return stringID(id) + obj.toString();
			}
		};
		setCollection(list);
		setListeners();
		
		setEditEnabled(true);
		setInsertNewEnabled(true);
		setDuplicateEnabled(true);
		setDeleteEnabled(true);
		setDragEnabled(true);
		
		setShellFactory(new LShellFactory<Integer>() {
			@Override
			public LObjectShell<Integer> createShell(Shell parent) {
				return new IDShell(parent) {
					public ArrayList<?> getArray() {
						return comboArray();
					}
				};
			}
		});
	}
	
	public ArrayList<?> comboArray() { return null; }
	public String attributeName() { return ""; }
	
	public void setObject(Object obj) {
		if (obj == null) {
			collection.setItems(new LDataTree<Integer>());
			setList(null);
		} else {
			obj = getFieldValue(obj, attributeName());
			@SuppressWarnings("unchecked")
			LDataList<Integer> db = (LDataList<Integer>) obj;
			collection.setItems(db.toTree());
			setList(db);
		}
	}

	protected void setList(LDataList<Integer> list) {
		currentList = list;
	}
	
	public LDataList<Integer> getList() {
		return currentList;
	}
	
	@Override
	protected Integer getEditableData(LPath path) {
		return getList().get(path.index);
	}

	@Override
	protected void setEditableData(LPath path, Integer newData) {
		getList().set(path.index, newData);
	}
	
	@Override
	protected LDataCollection<Integer> getDataCollection() {
		return getList();
	}

}
