package gui.widgets;

import gui.shell.IDShell;

import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Shell;

import lwt.dataestructure.LDataTree;
import lwt.dataestructure.LPath;
import lwt.dialog.LObjectShell;
import lwt.dialog.LShellFactory;
import lwt.event.LEditEvent;
import lwt.widget.LList;

public abstract class IDList extends SimpleEditableList<Integer> {

	public IDList(Composite parent, int style) {
		super(parent, style);
		type = Integer.class;
		setShellFactory(new LShellFactory<Integer>() {
			@Override
			public LObjectShell<Integer> createShell(Shell parent) {
				IDShell shell = new IDShell(parent, SWT.NONE) {
					public LDataTree<Object> getTree() {
						return getDataTree();
					}
				};
				return shell;
			}
		});
		list.setIncludeID(false);
	}
	
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
				return new LDataTree<Integer>(toObject(path));
			}
			@Override
			protected String dataToString(Integer id) {
				Object obj = getDataTree().get(id);
				String idstr = includeID ? stringID(id) : "";
				if (obj == null)
					return idstr + "    ";
				return idstr + obj.toString();
			}
		};
	}

	public abstract LDataTree<Object> getDataTree();
	
}
