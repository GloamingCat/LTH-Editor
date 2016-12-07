package gui.views.fieldTree;

import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.ToolBar;
import org.eclipse.swt.widgets.ToolItem;
import org.eclipse.wb.swt.SWTResourceManager;

import lwt.widget.LWidget;

public class FieldToolBar extends LWidget {

	public FieldToolBar(Composite parent, int style) {
		super(parent, style);
		setSize(new Point(440, 0));
		FillLayout fillLayout = new FillLayout(SWT.HORIZONTAL);
		setLayout(fillLayout);

		ToolBar toolBar = new ToolBar(this, SWT.FLAT | SWT.RIGHT);
		
		ToolItem tltmPencil = new ToolItem(toolBar, SWT.RADIO);
		tltmPencil.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent arg0) {
				onSelectTool(0);
			}
		});
		tltmPencil.setSelection(true);
		tltmPencil.setImage(SWTResourceManager.getImage(FieldEditor.class, "/img/pencil.png"));
		
		ToolItem tltmBucket = new ToolItem(toolBar, SWT.RADIO);
		tltmBucket.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent arg0) {
				onSelectTool(1);
			}
		});
		tltmBucket.setImage(SWTResourceManager.getImage(FieldEditor.class, "/img/bucket.png"));
		
		ToolItem tltmEraser = new ToolItem(toolBar, SWT.RADIO);
		tltmEraser.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent arg0) {
				onSelectTool(2);
			}
		});
		tltmEraser.setImage(SWTResourceManager.getImage(FieldEditor.class, "/img/eraser.png"));
		
		new ToolItem(toolBar, SWT.SEPARATOR);
		
		ToolItem tltmShowGrid = new ToolItem(toolBar, SWT.CHECK);
		tltmShowGrid.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent arg0) {
				onShowGrid(tltmShowGrid.getSelection());
			}
		});
		tltmShowGrid.setSelection(true);
		tltmShowGrid.setText("Show Grid");
		
		new ToolItem(toolBar, SWT.SEPARATOR);
		
		ToolItem tltmResize = new ToolItem(toolBar, SWT.NONE);
		tltmResize.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent arg0) {
				onResize();
			}
		});
		tltmResize.setText("Resize");
		tltmResize.setSelection(true);
		
		pack();
	}
	
	public void onSelectTool(int i) {}
	public void onShowGrid(boolean i) {}
	public void onResize() {}

}
