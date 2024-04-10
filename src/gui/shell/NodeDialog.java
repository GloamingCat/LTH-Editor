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
		setMinimumSize(400, 400);
	}
	
	public NodeDialog(LWindow parent, String title) {
		this(parent, title, 0);
	}
	
	@Override
	protected void createContent(int style) {
		contentEditor.setGridLayout(1);
		
		LPanel name = new LPanel(contentEditor);
		name.setGridLayout(2);
		name.getCellData().setExpand(true, false);
		name.getCellData().setAlignment(LFlags.CENTER);
		
		new LLabel(name, Vocab.instance.NAME, Tooltip.instance.KEY);
		
		LText txtName = new LText(name);
		addControl(txtName, "name");
		
		LNodeSelector<Object> tree = new LNodeSelector<Object>(contentEditor, false);
		tree.getCellData().setExpand(true, true);
		tree.setCollection(getTree());
		addControl(tree, "id");
		
		pack();
	}
	
	protected abstract LDataTree<Object> getTree();
	
}
