package gui.shell;

import gui.Vocab;

import org.eclipse.swt.widgets.Shell;

import data.subcontent.Bonus;
import lwt.dataestructure.LDataTree;
import lwt.widget.LNodeSelector;
import lwt.widget.LSpinner;

import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.graphics.Point;

public abstract class BonusShell extends ObjectShell<Bonus> {

	public BonusShell(Shell parent) {
		super(parent);
		GridData gridData = (GridData) contentEditor.getLayoutData();
		gridData.verticalAlignment = SWT.FILL;
		gridData.grabExcessVerticalSpace = true;
		setMinimumSize(new Point(372, 329));
		contentEditor.setLayout(new GridLayout(2, false));
		
		Label lblValue = new Label(contentEditor, SWT.NONE);
		lblValue.setText(Vocab.instance.VALUE);
		
		LSpinner spnValue = new LSpinner(contentEditor, SWT.NONE);
		spnValue.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1));
		addControl(spnValue, "value");
		
		LNodeSelector<Object> tree = new LNodeSelector<Object>(contentEditor, SWT.NONE);
		tree.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true, 2, 1));
		tree.setCollection(getTree());
		addControl(tree, "id");
		
		pack();
	}
	
	protected abstract LDataTree<Object> getTree();
	
}
