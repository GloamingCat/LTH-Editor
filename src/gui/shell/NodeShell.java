package gui.shell;

import gui.Tooltip;
import gui.Vocab;

import data.subcontent.Node;
import lwt.LFlags;
import lwt.container.LPanel;
import lwt.dataestructure.LDataTree;
import lwt.dialog.LShell;
import lwt.widget.LLabel;
import lwt.widget.LNodeSelector;
import lwt.widget.LText;

public abstract class NodeShell extends ObjectShell<Node> {

	public NodeShell(LShell parent, String title, int style) {
		super(parent, title, style);
		setMinimumSize(400, 400);
	}
	
	public NodeShell(LShell parent, String title) {
		this(parent, title, 0);
	}
	
	@Override
	protected void createContent(int style) {
		contentEditor.setGridLayout(1);
		
		LPanel name = new LPanel(contentEditor);
		name.setGridLayout(2);
		name.setExpand(true, false);
		name.setAlignment(LFlags.CENTER);
		
		new LLabel(name, Vocab.instance.NAME, Tooltip.instance.KEY);
		
		LText txtName = new LText(name);
		addControl(txtName, "name");
		
		LNodeSelector<Object> tree = new LNodeSelector<Object>(contentEditor, false);
		tree.setExpand(true, true);
		tree.setCollection(getTree());
		addControl(tree, "id");
		
		pack();
	}
	
	protected abstract LDataTree<Object> getTree();
	
}
