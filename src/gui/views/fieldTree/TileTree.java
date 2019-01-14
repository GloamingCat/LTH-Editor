package gui.views.fieldTree;

import lwt.dataestructure.LDataTree;
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
			}
		});
		
	}
	
	public void unselect() {
		selector.setValue(null);
	}
	
	public void onVisible() {
		super.onVisible();
		selector.setCollection(getTree());
		selector.setValue(0);
		System.out.println("bla 0");
	}
	
	public abstract LDataTree<Object> getTree();

}
