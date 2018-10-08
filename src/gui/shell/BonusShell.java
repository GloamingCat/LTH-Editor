package gui.shell;

import gui.Vocab;

import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Spinner;

import data.subcontent.Bonus;
import lwt.dataestructure.LDataTree;
import lwt.dataestructure.LPath;
import lwt.dialog.LObjectShell;
import lwt.editor.LDefaultTreeEditor;

import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.graphics.Point;

public abstract class BonusShell extends LObjectShell<Bonus> {
	
	private LDefaultTreeEditor<Object> tree;
	private Spinner spnValue;

	public BonusShell(Shell parent) {
		super(parent);
		GridData gridData = (GridData) content.getLayoutData();
		gridData.verticalAlignment = SWT.FILL;
		gridData.grabExcessVerticalSpace = true;
		setMinimumSize(new Point(372, 329));
		content.setLayout(new GridLayout(2, false));
		
		Label lblValue = new Label(content, SWT.NONE);
		lblValue.setText(Vocab.instance.VALUE);
		
		spnValue = new Spinner(content, SWT.BORDER);
		spnValue.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1));
		
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
	
	public void open(Bonus initial) {
		super.open(initial);
		tree.onVisible();
		tree.getCollectionWidget().select(null);
		if (initial.id >= 0) {
			LDataTree<Object> node = getTree().findNode(initial.id);
			if (node != null) {
				tree.getCollectionWidget().select(node.toPath());
			}
		}
		spnValue.setSelection(initial.value);
	}

	@Override
	protected Bonus createResult(Bonus initial) {
		LPath path = tree.getCollectionWidget().getSelectedPath();
		int id = path == null ? -1 : getTree().getNode(path).id;
		
		if (id == initial.id && spnValue.getSelection() == initial.value) {
			return null;
		} else {
			Bonus bonus = new Bonus();
			bonus.id = id;
			bonus.value = spnValue.getSelection();
			return bonus;
		}
	}
	
	protected abstract LDataTree<Object> getTree();
	
}
