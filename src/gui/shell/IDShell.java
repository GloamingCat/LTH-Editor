package gui.shell;

import lbase.data.LDataTree;
import lwt.dialog.LObjectWindow;
import lwt.dialog.LWindow;
import lwt.widget.LNodeSelector;

public abstract class IDShell extends LObjectWindow<Integer> {
	
	protected LNodeSelector<Object> tree;
	
	public static final int OPTIONAL = 0x01;
	
	/**
	 * @wbp.parser.constructor
	 */
	public IDShell(LWindow parent, String title, int style) {
		super(parent, style, title);
		setMinimumSize(350, 500);
	}
	
	@Override
	protected void createContent(int style) {
		super.createContent(style);
		content.setFillLayout(true);
		tree = new LNodeSelector<>(content, (style & OPTIONAL) > 0);
		tree.setCollection(getTree());
		pack();
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
