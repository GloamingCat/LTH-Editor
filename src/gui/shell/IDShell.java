package gui.shell;

import org.eclipse.swt.widgets.Shell;

import lwt.dataestructure.LDataTree;
import lwt.dialog.LObjectShell;
import lwt.widget.LNodeSelector;

import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.layout.GridData;

public abstract class IDShell extends LObjectShell<Integer> {
	
	protected LNodeSelector<Object> tree;
	
	public IDShell(Shell parent, int style) {
		super(parent);
		GridData gridData = (GridData) content.getLayoutData();
		gridData.verticalAlignment = SWT.FILL;
		gridData.grabExcessVerticalSpace = true;
		setMinimumSize(350, 500);
		
		content.setLayout(new FillLayout());
		tree = new LNodeSelector<>(content, style);
		tree.setCollection(getTree());

		pack();
	}
	
	public void open(Integer initial) {
		super.open(initial);
		tree.setValue(initial);
	}

	@Override
	protected Integer createResult(Integer initial) {
		return tree.getValue();
	}
	
	protected abstract LDataTree<Object> getTree();
	
}
