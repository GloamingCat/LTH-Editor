package gui.views.fieldTree;

import gui.Vocab;
import gui.shell.field.ResizeShell;
import gui.views.fieldTree.action.ResizeAction;

import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.widgets.ToolBar;
import org.eclipse.swt.widgets.ToolItem;
import org.eclipse.wb.swt.SWTResourceManager;

import lwt.LFlags;
import lwt.container.LContainer;
import lwt.container.LPanel;
import lwt.dialog.LObjectDialog;
import lwt.dialog.LObjectShell;
import lwt.dialog.LShell;
import lwt.dialog.LShellFactory;
import lwt.graphics.LRect;

public class FieldToolBar extends LPanel {

	// External
	public static FieldToolBar instance;
	
	public FieldToolBar(LContainer parent) {
		super(parent, true);
		instance = this;
		setCurrentSize(440, 0);
		setFillLayout(true);

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
		LRect size = new LRect(LFlags.LEFT, LFlags.TOP, 
				FieldSideEditor.instance.field.sizeX, 
				FieldSideEditor.instance.field.sizeY);
		LObjectDialog<LRect> dialog = new LObjectDialog<>(getShell());
		dialog.setFactory(new LShellFactory<LRect>() {
			@Override
			public LObjectShell<LRect> createShell(LShell parent) {
				return new ResizeShell(parent);
			}
		});
		size = dialog.open(size);
		if (size != null) {
			resizeField(size);
		}
	}
	
	private void resizeField(LRect size) {
		ResizeAction action = new ResizeAction(size.width, size.height, size.x, size.y);
		FieldEditor.instance.canvas.getActionStack().newAction(action);
		action.redo();
	}

}
