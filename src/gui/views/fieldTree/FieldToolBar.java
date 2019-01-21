package gui.views.fieldTree;

import gui.Vocab;
import gui.shell.field.ResizeShell;
import gui.views.fieldTree.action.ResizeAction;

import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.ToolBar;
import org.eclipse.swt.widgets.ToolItem;
import org.eclipse.wb.swt.SWTResourceManager;

import lwt.dialog.LObjectDialog;
import lwt.dialog.LObjectShell;
import lwt.dialog.LShellFactory;
import lwt.widget.LWidget;

public class FieldToolBar extends LWidget {

	// External
	public static FieldToolBar instance;
	
	public FieldToolBar(Composite parent, int style) {
		super(parent, style);
		instance = this;
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
		
		ToolItem tltmTerrain = new ToolItem(toolBar, SWT.RADIO);
		tltmTerrain.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent arg0) {
				onSelectEditor(0);
			}
		});
		tltmTerrain.setSelection(true);
		tltmTerrain.setImage(SWTResourceManager.getImage(FieldEditor.class, "/img/terrain.png"));
		
		ToolItem tltmObstacle = new ToolItem(toolBar, SWT.RADIO);
		tltmObstacle.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent arg0) {
				onSelectEditor(1);
			}
		});
		tltmObstacle.setImage(SWTResourceManager.getImage(FieldEditor.class, "/img/object.png"));

		ToolItem tltmRegion = new ToolItem(toolBar, SWT.RADIO);
		tltmRegion.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent arg0) {
				onSelectEditor(2);
			}
		});
		tltmRegion.setImage(SWTResourceManager.getImage(FieldEditor.class, "/img/region.png"));
		
		ToolItem tltmChar = new ToolItem(toolBar, SWT.RADIO);
		tltmChar.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent arg0) {
				onSelectEditor(3);
			}
		});
		tltmChar.setImage(SWTResourceManager.getImage(FieldEditor.class, "/img/character.png"));
		
		ToolItem tltmParty = new ToolItem(toolBar, SWT.RADIO);
		tltmParty.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent arg0) {
				onSelectEditor(4);
			}
		});
		tltmParty.setImage(SWTResourceManager.getImage(FieldEditor.class, "/img/party.png"));
		
		new ToolItem(toolBar, SWT.SEPARATOR);
		
		ToolItem tltmShowGrid = new ToolItem(toolBar, SWT.CHECK);
		tltmShowGrid.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent arg0) {
				onShowGrid(tltmShowGrid.getSelection());
			}
		});
		tltmShowGrid.setSelection(true);
		tltmShowGrid.setText(Vocab.instance.SHOWGRID);
		
		new ToolItem(toolBar, SWT.SEPARATOR);
		
		ToolItem tltmResize = new ToolItem(toolBar, SWT.NONE);
		tltmResize.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent arg0) {
				onResize();
			}
		});
		tltmResize.setText(Vocab.instance.RESIZE);
		tltmResize.setSelection(true);
		
		pack();
	}
	
	public void onSelectEditor(int i) {
		FieldSideEditor.instance.selectEditor(i);
	}
	
	public void onSelectTool(int i) {
		FieldEditor.instance.canvas.setTool(i);
	}
	
	public void onShowGrid(boolean i) {
		FieldEditor.instance.canvas.setShowGrid(i);
	}
	
	public void onResize() {
		Point size = new Point(FieldSideEditor.instance.field.sizeX, 
				FieldSideEditor.instance.field.sizeY);
		LObjectDialog<Point> dialog = new LObjectDialog<>(getShell(), getShell().getStyle());
		dialog.setFactory(new LShellFactory<Point>() {
			@Override
			public LObjectShell<Point> createShell(Shell parent) {
				return new ResizeShell(parent);
			}
		});
		size = dialog.open(size);
		if (size != null) {
			resizeField(size.x, size.y);
		}
	}
	
	private void resizeField(int w, int h) {
		ResizeAction action = new ResizeAction(w, h);
		FieldEditor.instance.canvas.getActionStack().newAction(action);
		action.redo();
	}

}
