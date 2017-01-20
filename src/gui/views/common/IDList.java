package gui.views.common;

import java.util.ArrayList;

import gui.shell.IDShell;

import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.TreeItem;

import lwt.dataestructure.LDataTree;
import lwt.dataestructure.LPath;
import lwt.dialog.LObjectShell;
import lwt.dialog.LShellFactory;
import lwt.event.LEditEvent;
import lwt.widget.LList;

public class IDList extends SimpleEditableList<Integer> {

	public IDList(Composite parent, int style) {
		super(parent, style);
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
	
	protected LList<Integer, Integer> createList() {
		return new LList<Integer, Integer>(this, SWT.NONE) {
			@Override
			public LEditEvent<Integer> edit(LPath path) {
				return onEditItem(path);
			}
			@Override
			public Integer toObject(LPath path) {
				if (path == null)
					return null;
				return getDataCollection().get(path.index);
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
			public LDataTree<Integer> duplicateNode(LPath path) {
				return toNode (path);
			}
			@Override
			public void refreshObject(LPath path) {
				TreeItem item = toTreeItem(path);
				if (item != null) {
					item.setText(itemName(item.getData()));
				}
			}
			@Override
			public void refreshAll() {
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
	}

}
