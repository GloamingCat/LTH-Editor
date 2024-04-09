package gui.views.fieldTree.subcontent;

import lbase.action.LState;
import lwt.container.LContainer;
import lwt.container.LView;
import lbase.data.LDataTree;
import lbase.data.LPath;
import lwt.widget.LNodeSelector;

public abstract class TileTree extends LView {

	public final LNodeSelector<Object> selector;


	public TileTree(LContainer parent) {
		super(parent, false);
		setFillLayout(true);
		selector = new LNodeSelector<>(this, false);
	}
	
	public void setTile(int id) {
		if (id == -1)
			selector.setValue(null);
		else {
			LDataTree<Object> node = getTree().findNode(id);
			selector.setValue(node == null ? null : node.toPath());
		}
	}
	
	public void onVisible() {
		super.onVisible();
		LPath path = selector.getSelectedPath();
		updateCollection();
		if (path != null)
			selector.setValue(path);
		else
			selector.forceFirstSelection();
	}
	
	public LState getState() {
		final Integer id = selector.getValue();
		return () -> selector.setValue(id);
	}
	
	public void updateCollection() {
		selector.setCollection(getTree());
	}
	
	public abstract LDataTree<Object> getTree();

}
