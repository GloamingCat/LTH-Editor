package gui.shell;

import gui.Vocab;

import data.subcontent.Property;
import lwt.dataestructure.LDataTree;
import lwt.dialog.LShell;
import lwt.widget.LLabel;
import lwt.widget.LNodeSelector;
import lwt.widget.LSpinner;

public abstract class PropertyShell extends ObjectShell<Property> {

	public PropertyShell(LShell parent) {
		super(parent);
		setMinimumSize(400, 320);
		contentEditor.setGridLayout(2, false);
		
		new LLabel(contentEditor, Vocab.instance.VALUE);
		LSpinner spnValue = new LSpinner(contentEditor);
		spnValue.setMinimum(-100000);
		spnValue.setMaximum(100000);
		addControl(spnValue, "value");
		
		LNodeSelector<Object> tree = new LNodeSelector<Object>(contentEditor, false);
		tree.setExpand(true, true);
		tree.setSpread(2, 1);
		tree.setCollection(getTree());
		addControl(tree, "id");
		
		pack();
	}
	
	protected abstract LDataTree<Object> getTree();
	
}
