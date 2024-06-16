package gui.shell;

import gui.Tooltip;
import gui.Vocab;
import lui.base.LFlags;
import data.subcontent.Node;
import lui.container.LPanel;
import lui.base.data.LDataTree;
import lui.dialog.LWindow;
import lui.gson.GObjectDialog;
import lui.widget.LLabel;
import lui.widget.LNodeSelector;
import lui.widget.LText;

public abstract class NodeDialog extends GObjectDialog<Node> {

	public NodeDialog(LWindow parent, String title, int style) {
		super(parent, 600, 400, style, title);
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
		
		LNodeSelector<Object> tree = new LNodeSelector<>(contentEditor, 0);
		tree.getCellData().setSpread(2, 1);
		tree.getCellData().setExpand(true, true);
		tree.setCollection(getTree());
		addControl(tree, "id");
	}
	
	protected abstract LDataTree<Object> getTree();
	
}
