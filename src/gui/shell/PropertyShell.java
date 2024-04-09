package gui.shell;

import gui.Tooltip;
import gui.Vocab;

import data.subcontent.Property;
import lbase.data.LDataTree;
import lwt.dialog.LWindow;
import lwt.widget.LLabel;
import lwt.widget.LNodeSelector;
import lwt.widget.LSpinner;

public abstract class PropertyShell extends ObjectShell<Property> {

	public PropertyShell(LWindow parent, String title) {
		super(parent, title);
		setMinimumSize(400, 320);
	}
	
	@Override
	protected void createContent(int style) {
		super.createContent(style);
		contentEditor.setGridLayout(2);
		
		new LLabel(contentEditor, Vocab.instance.VALUE, Tooltip.instance.VALUE);
		LSpinner spnValue = new LSpinner(contentEditor);
		spnValue.setMinimum(-100000);
		spnValue.setMaximum(100000);
		addControl(spnValue, "value");
		
		LNodeSelector<Object> tree = new LNodeSelector<Object>(contentEditor, false);
		tree.getCellData().setExpand(true, true);
		tree.getCellData().setSpread(2, 1);
		tree.setCollection(getTree());
		addControl(tree, "id");
		
		pack();
	}
	
	protected abstract LDataTree<Object> getTree();
	
}
