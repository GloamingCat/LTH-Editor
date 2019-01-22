package gui.views.fieldTree.subcontent;

import gui.views.fieldTree.FieldEditor;
import lwt.dataestructure.LDataTree;
import lwt.dataestructure.LPath;
import lwt.editor.LState;
import lwt.editor.LView;
import lwt.event.LControlEvent;
import lwt.event.listener.LControlListener;
import lwt.widget.LImage;
import lwt.widget.LNodeSelector;

import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.SWT;

public abstract class TileTree extends LView {

	private LNodeSelector<Object> selector;
	public LImage image = null;
	
	/**
	 * Create the composite.
	 * @param parent
	 * @param style
	 */
	public TileTree(Composite parent, int style) {
		super(parent, style);
		setLayout(new FillLayout(SWT.HORIZONTAL));

		selector = new LNodeSelector<>(this, 0);
		selector.addModifyListener(new LControlListener<Integer>() {
			public void onModify(LControlEvent<Integer> event) {
				FieldEditor.instance.canvas.setSelection(event.newValue);
				if (image != null) {
					updateImage(selector.getSelectedObject(), event.newValue);
				}
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
	public abstract void updateImage(Object obj, int id);

}
