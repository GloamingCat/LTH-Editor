package gui.views.fieldTree;

import gui.shell.field.ResizeShell;
import gui.views.fieldTree.action.ResizeAction;
import lwt.dialog.LObjectDialog;
import lwt.dialog.LObjectShell;
import lwt.dialog.LShellFactory;
import lwt.editor.LObjectEditor;
import lwt.editor.LTreeEditor;

import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.ScrolledComposite;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Scale;
import org.eclipse.swt.widgets.Shell;

import project.Project;
import data.Field;
import data.Layer;
import data.Node;

import org.eclipse.swt.layout.GridLayout;

public class FieldEditor extends LObjectEditor {

	private FieldToolBar toolBar;
	private Label tileCoord;
	private ScrolledComposite scrolledComposite;
	public EditableFieldCanvas canvas;
	public LTreeEditor<Node, Field.Prefs> treeEditor;
	
	/**
	 * Create the composite.
	 * @param parent
	 * @param style
	 */
	public FieldEditor(Composite parent, int style) {
		super(parent, style);
		setLayout(new GridLayout(1, false));
		
		toolBar = new FieldToolBar(this, SWT.NONE) {
			public void onSelectTool(int i) {
				canvas.setTool(i);
			}
			public void onShowGrid(boolean i) {
				canvas.setShowGrid(i);
			}
			public void onResize() {
				Point size = new Point(canvas.field.sizeX, canvas.field.sizeY);
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
		};
		toolBar.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, false, false, 1, 1));
		
		scrolledComposite = new ScrolledComposite(this, SWT.BORDER | SWT.H_SCROLL | SWT.V_SCROLL);
		scrolledComposite.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true, 1, 1));
		
		canvas = new EditableFieldCanvas(scrolledComposite, SWT.NONE) {
			public void onTileEnter(int x, int y) {
				tileCoord.setText("(" + x + ", " + y + ")");
			}
		};
		addChild(canvas);
		scrolledComposite.setContent(canvas);

		Composite scaleComposite = new Composite(this, SWT.NONE);
		scaleComposite.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, false, false, 1, 1));
		scaleComposite.setLayout(new GridLayout(4, false));
		
		tileCoord = new Label(scaleComposite, SWT.NONE);
		tileCoord.setText("(-99, -99)");
		
		Scale scale = new Scale(scaleComposite, SWT.NONE);
		scale.setMaximum(200);
		scale.setMinimum(25);
		scale.setSelection(100);
		scale.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1));
		
		Label lblScale = new Label(scaleComposite, SWT.NONE);
		lblScale.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, false, false, 1, 1));
		lblScale.setText("100%");
		
		Button btn100 = new Button(scaleComposite, SWT.NONE);
		btn100.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, false, false, 1, 1));
		btn100.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent arg0) {
				scale.setSelection(100);
				lblScale.setText("100%");
				canvas.rescale(1);
			}
		});
		btn100.setText("100%");
		
		scale.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent arg0) {
				canvas.rescale(1.0f * scale.getSelection() / 100);
				lblScale.setText(scale.getSelection() + "%");
				scrolledComposite.setMinSize(canvas.getSize());
			}
		});
		
	}
	
	public void selectLayer(Layer layer) {
		canvas.setCurrentLayer(layer);
	}
	
	public void selectField(Field field) {
		canvas.setField(field);
		scrolledComposite.setMinSize(canvas.getSize());
	}
	
	@Override
	public void setObject(Object obj) {
		if (obj != null) {
			Node node = (Node) obj;
			if (canvas.field == null || canvas.field.id != node.id) {
				Field field = Project.current.fieldTree.loadData(node);
				selectField(field);
				super.setObject(field);
			}
		} else {
			selectField(null);
			super.setObject(null);
		}
	}
	
	public void updateCanvas() {
		canvas.updateAllTileImages();
	}

	public void onSelectTile(int index) {
		canvas.setSelection(new int[][] {{index}}, new Point(0, 0));
	}
	
	private void resizeField(int w, int h) {
		ResizeAction action = new ResizeAction(this, w, h);
		getActionStack().newAction(action);
		action.redo();
		scrolledComposite.setMinSize(canvas.getSize());
	}
	
}
