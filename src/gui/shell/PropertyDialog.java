package gui.shell;

import gui.Tooltip;
import gui.Vocab;

import data.subcontent.Property;
import lui.base.data.LDataTree;
import lui.dialog.LWindow;
import lui.widget.LLabel;
import lui.widget.LNodeSelector;
import lui.widget.LSpinner;

public abstract class PropertyDialog extends ObjectEditorDialog<Property> {

	public PropertyDialog(LWindow parent, String title) {
		super(parent, title);
		setRequiredSize(400, 320);
		setSize(400, 320);
	}
	
	@Override
	protected void createContent(int style) {
		super.createContent(style);
		contentEditor.setGridLayout(2);
		
		new LLabel(contentEditor, Vocab.instance.VALUE, Tooltip.instance.VALUE);
		LSpinner spnValue = new LSpinner(contentEditor);
		spnValue.getCellData().setExpand(true, false);
		spnValue.setMinimum(-100000);
		spnValue.setMaximum(100000);
		addControl(spnValue, "value");
		
		LNodeSelector<Object> tree = new LNodeSelector<Object>(contentEditor, false);
		tree.getCellData().setExpand(true, true);
		tree.getCellData().setSpread(2, 1);
		tree.setCollection(getTree());
		addControl(tree, "id");

	}
	
	protected abstract LDataTree<Object> getTree();
	
}
