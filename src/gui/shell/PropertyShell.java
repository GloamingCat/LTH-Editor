package gui.shell;

import gui.Vocab;

import data.subcontent.Property;
import lwt.dataestructure.LDataTree;
import lwt.dialog.LShell;
import lwt.widget.LLabel;
import lwt.widget.LNodeSelector;
import lwt.widget.LSpinner;

import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.layout.GridData;

public abstract class PropertyShell extends ObjectShell<Property> {

	public PropertyShell(LShell parent) {
		super(parent);
		setMinimumSize(372, 329);
		contentEditor.setLayout(new GridLayout(2, false));
		
		new LLabel(contentEditor, Vocab.instance.VALUE);
		LSpinner spnValue = new LSpinner(contentEditor);
		spnValue.setMinimum(-100000);
		spnValue.setMaximum(100000);
		addControl(spnValue, "value");
		
		LNodeSelector<Object> tree = new LNodeSelector<Object>(contentEditor, false);
		tree.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true, 2, 1));
		tree.setCollection(getTree());
		addControl(tree, "id");
		
		pack();
	}
	
	protected abstract LDataTree<Object> getTree();
	
}
