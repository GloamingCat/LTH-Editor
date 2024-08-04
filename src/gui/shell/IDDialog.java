package gui.shell;

import lui.base.data.LDataTree;
import lui.dialog.LObjectDialog;
import lui.dialog.LWindow;
import lui.widget.LNodeSelector;

public abstract class IDDialog extends LObjectDialog<Integer> {
	
	protected LNodeSelector<Object> node;
	
	public static final int OPTIONAL = 1;
	
	/**
	 * @wbp.parser.constructor
	 */
	public IDDialog(LWindow parent, String title, int style) {
		super(parent, 350, 500, style, title);
	}
	
	@Override
	protected void createContent(int style) {
		super.createContent(style);
		content.setFillLayout(true);
		node = new LNodeSelector<>(content, style & LNodeSelector.OPTIONAL);
		node.setCollection(getTree());
	}
	
	@Override
	public void open(Integer initial) {
		node.setValue(initial);
		super.open(initial);
	}

	@Override
	protected Integer createResult(Integer initial) {
		return node.getValue();
	}
	
	protected abstract LDataTree<Object> getTree();
	
}
