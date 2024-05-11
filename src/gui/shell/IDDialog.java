package gui.shell;

import lui.base.data.LDataTree;
import lui.dialog.LObjectDialog;
import lui.dialog.LWindow;
import lui.widget.LNodeSelector;

public abstract class IDDialog extends LObjectDialog<Integer> {
	
	protected LNodeSelector<Object> tree;
	
	public static final int OPTIONAL = 0x01;
	
	/**
	 * @wbp.parser.constructor
	 */
	public IDDialog(LWindow parent, String title, int style) {
		super(parent, style, title);
		setRequiredSize(350, 500);
		setSize(350, 500);
	}
	
	@Override
	protected void createContent(int style) {
		super.createContent(style);
		content.setFillLayout(true);
		tree = new LNodeSelector<>(content, (style & OPTIONAL) > 0);
		tree.setCollection(getTree());
	}
	
	@Override
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
