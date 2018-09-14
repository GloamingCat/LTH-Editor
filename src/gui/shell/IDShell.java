package gui.shell;

import gui.Vocab;

import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Shell;

import lwt.dataestructure.LDataTree;
import lwt.dataestructure.LPath;
import lwt.dialog.LObjectShell;
import lwt.editor.LDefaultTreeEditor;

import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;

public abstract class IDShell extends LObjectShell<Integer> {
	
	private LDefaultTreeEditor<Object> tree;
	private Button btnNull;
	
	public IDShell(Shell parent, boolean optional) {
		super(parent);
		setMinimumSize(new Point(224, 39));
		
		content.setLayout(new GridLayout(1, false));
		
		tree = new LDefaultTreeEditor<Object>(content, 0) {
			@Override
			public LDataTree<Object> getDataCollection() { return null; }
			@Override
			protected Object createNewData() { return null; }
			@Override
			protected Object duplicateData(Object original) { return null; }
		};
		tree.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1));
		 		
		btnNull = new Button(content, 0);
		btnNull.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent arg0) {
				tree.getCollectionWidget().select(null);
			}
		});
		btnNull.setText(Vocab.instance.NONE);
		btnNull.setEnabled(optional);

		pack();
	}
	
	public void open(Integer initial) {
		super.open(initial);
		tree.setDataCollection(getTree());
		if (initial >= 0) {
			LDataTree<Object> node = getTree().findNode(initial);
			if (node != null) {
				tree.getCollectionWidget().select(node.toPath());
			}
		}
	}

	@Override
	protected Integer createResult(Integer initial) {
		LPath path = tree.getCollectionWidget().getSelectedPath();
		if (path == null) {
			if (initial < 0)
				return null;
			else
				return getTree().getNode(path).id;
		} else {
			int id = getTree().getNode(path).id;
			if (id != initial)
				return id;
			else
				return null;
		}
	}
	
	protected void checkSubclass() { }
	
	protected LDataTree<Object> getTree() { return null; }
	
}
