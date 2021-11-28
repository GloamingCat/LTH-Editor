package gui.shell.database;

import gui.Vocab;

import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Spinner;

import lwt.dataestructure.LDataTree;
import lwt.dataestructure.LPath;
import lwt.dialog.LObjectShell;
import lwt.editor.LDefaultTreeEditor;
import lwt.widget.LLabel;

import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.layout.GridData;

import project.Project;
import data.Battler.Drop;

public class DropShell extends LObjectShell<Drop> {
	
	private LDefaultTreeEditor<Object> tree;
	private Spinner spnChance;
	private Spinner spnCount;

	public DropShell(Shell parent) {
		super(parent);
		setMinimumSize(400, 200);
		GridData gridData = (GridData) content.getLayoutData();
		gridData.verticalAlignment = SWT.FILL;
		gridData.grabExcessVerticalSpace = true;
		content.setLayout(new GridLayout(2, false));
		
		new LLabel(content, Vocab.instance.CHANCE);
		
		spnChance = new Spinner(content, SWT.BORDER);
		spnChance.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1));
		
		new LLabel(content, Vocab.instance.COUNT);
		
		spnCount = new Spinner(content, SWT.BORDER);
		spnCount.setMaximum(999999999);
		spnCount.setMinimum(1);
		spnCount.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1));
		
		tree = new LDefaultTreeEditor<Object>(content, 0) {
			@Override
			public LDataTree<Object> getDataCollection() { return getTree(); }
			@Override
			protected Object createNewData() { return null; }
			@Override
			protected Object duplicateData(Object original) { return null; }
		};
		tree.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true, 2, 1));
		tree.getCollectionWidget().dragEnabled = false;
		
		pack();
	}
	
	public void open(Drop initial) {
		super.open(initial);
		tree.onVisible();
		tree.getCollectionWidget().select(null);
		if (initial.id >= 0) {
			LDataTree<Object> node = getTree().findNode(initial.id);
			if (node != null) {
				tree.getCollectionWidget().select(node.toPath());
			}
		}
		spnChance.setSelection(initial.value);
		spnCount.setSelection(initial.count);
	}

	@Override
	protected Drop createResult(Drop initial) {
		LPath path = tree.getCollectionWidget().getSelectedPath();
		Drop drop = new Drop();
		drop.id = path == null ? -1 : getTree().getNode(path).id;
		drop.value = spnChance.getSelection();
		drop.count = spnCount.getSelection();
		return drop;
	}
	
	protected LDataTree<Object> getTree() {
		return Project.current.items.getTree();
	}
	
}
