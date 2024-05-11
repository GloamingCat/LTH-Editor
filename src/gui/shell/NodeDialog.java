package gui.shell;

import gui.Tooltip;
import gui.Vocab;
import lui.base.LFlags;
import data.subcontent.Node;
import lui.container.LPanel;
import lui.base.data.LDataTree;
import lui.dialog.LWindow;
import lui.widget.LLabel;
import lui.widget.LNodeSelector;
import lui.widget.LText;

public abstract class NodeDialog extends ObjectEditorDialog<Node> {

	public NodeDialog(LWindow parent, String title, int style) {
		super(parent, style, title);
		setRequiredSize(600, 400);
		setSize(600, 400);
	}
	
	public NodeDialog(LWindow parent, String title) {
		this(parent, title, 0);
	}
	
	@Override
	protected void createContent(int style) {
		super.createContent(style);
		contentEditor.setGridLayout(2);
		
		new LLabel(contentEditor, Vocab.instance.NAME, Tooltip.instance.KEY);
		LText txtName = new LText(contentEditor);
		txtName.getCellData().setExpand(true, false);
		addControl(txtName, "name");
		
		LNodeSelector<Object> tree = new LNodeSelector<Object>(contentEditor, false);
		tree.getCellData().setSpread(2, 1);
		tree.getCellData().setExpand(true, true);
		tree.setCollection(getTree());
		addControl(tree, "id");
	}
	
	protected abstract LDataTree<Object> getTree();
	
}
