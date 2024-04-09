package gui.shell;

import gui.Tooltip;
import gui.Vocab;
import lbase.LFlags;
import data.subcontent.Node;
import lwt.container.LPanel;
import lbase.data.LDataTree;
import lwt.dialog.LWindow;
import lwt.widget.LLabel;
import lwt.widget.LNodeSelector;
import lwt.widget.LText;

public abstract class NodeShell extends ObjectShell<Node> {

	public NodeShell(LWindow parent, String title, int style) {
		super(parent, style, title);
		setMinimumSize(400, 400);
	}
	
	public NodeShell(LWindow parent, String title) {
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
