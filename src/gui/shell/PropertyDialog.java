package gui.shell;

import gui.Tooltip;
import gui.Vocab;

import data.subcontent.Property;
import lui.base.data.LDataTree;
import lui.dialog.LWindow;
import lui.gson.GObjectDialog;
import lui.widget.LLabel;
import lui.widget.LNodeSelector;
import lui.widget.LSpinner;

public abstract class PropertyDialog extends GObjectDialog<Property> {

	public static final int PERCENTAGE = 2;
	public static final int OPTIONAL = 4;
	public static final int NEGATIVE = 8;

	public PropertyDialog(LWindow parent, String title, int flags) {
		super(parent, 400, 320, flags, title);
	}
	
	@Override
	protected void createContent(int style) {
		super.createContent(0);
		contentEditor.setGridLayout(2);
		
		new LLabel(contentEditor, Vocab.instance.VALUE, Tooltip.instance.VALUE);
		LSpinner spnValue = new LSpinner(contentEditor);
		spnValue.getCellData().setExpand(true, false);
		if ((style & OPTIONAL) > 0)
			spnValue.setMinimum(-1);
		else
			spnValue.setMinimum(0);
		if ((style & PERCENTAGE) > 0) {
			spnValue.setMaximum(100);
			if ((style & NEGATIVE) > 0)
				spnValue.setMinimum(-100);
		} else {
			spnValue.setMaximum(Integer.MAX_VALUE);
			if ((style & NEGATIVE) > 0)
				spnValue.setMinimum(Integer.MIN_VALUE);
		}
		addControl(spnValue, "value");
		
		LNodeSelector<Object> tree = new LNodeSelector<>(contentEditor, 0);
		tree.getCellData().setExpand(true, true);
		tree.getCellData().setSpread(2, 1);
		tree.setCollection(getTree());
		addControl(tree, "id");

	}
	
	protected abstract LDataTree<Object> getTree();
	
}
