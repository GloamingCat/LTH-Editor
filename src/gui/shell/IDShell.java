package gui.shell;

import lwt.dataestructure.LDataTree;
import lwt.dialog.LObjectShell;
import lwt.dialog.LShell;
import lwt.widget.LNodeSelector;

import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.layout.GridData;

public abstract class IDShell extends LObjectShell<Integer> {
	
	protected LNodeSelector<Object> tree;
	
	public IDShell(LShell parent, boolean optional) {
		super(parent);
		GridData gridData = (GridData) content.getLayoutData();
		gridData.verticalAlignment = SWT.FILL;
		gridData.grabExcessVerticalSpace = true;
		setMinimumSize(350, 500);
		
		content.setLayout(new FillLayout());
		tree = new LNodeSelector<>(content, optional);
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
