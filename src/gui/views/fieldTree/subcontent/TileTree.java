package gui.views.fieldTree.subcontent;

import gui.views.fieldTree.FieldEditor;
import lwt.container.LContainer;
import lwt.container.LView;
import lwt.dataestructure.LDataTree;
import lwt.dataestructure.LPath;
import lwt.editor.LState;
import lwt.event.LControlEvent;
import lwt.event.listener.LControlListener;
import lwt.widget.LNodeSelector;

public abstract class TileTree extends LView {

	public final LNodeSelector<Object> selector;
	
	/**
	 * Create the composite.
	 * @param parent
	 * @param style
	 */
	public TileTree(LContainer parent) {
		super(parent, false);
		setFillLayout(true);
		selector = new LNodeSelector<>(this, false);
		selector.addModifyListener(new LControlListener<Integer>() {
			public void onModify(LControlEvent<Integer> event) {
				FieldEditor.instance.canvas.setSelection(event.newValue);
			}
		});
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
		return new LState() {
			@Override
			public void reset() {
				selector.setValue(id);
			}
		};
	}
	
	public void updateCollection() {
		selector.setCollection(getTree());
	}
	
	public abstract LDataTree<Object> getTree();

}
