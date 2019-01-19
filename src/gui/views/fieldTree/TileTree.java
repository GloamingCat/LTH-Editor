package gui.views.fieldTree;

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
	
	public void unselect() {
		selector.setValue(null);
	}
	
	public void onVisible() {
		super.onVisible();
		LPath path = selector.getSelectedPath();
		selector.setCollection(getTree());
		selector.setValue(path);
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
	
	public abstract LDataTree<Object> getTree();
	public abstract void updateImage(Object obj, int id);

}
