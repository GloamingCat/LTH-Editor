package gui.views.fieldTree;

import lwt.editor.LObjectEditor;

import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.ScrolledComposite;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Scale;

import project.Project;
import data.field.Field;
import data.field.FieldNode;
import data.field.Layer;

import org.eclipse.swt.layout.GridLayout;

public class FieldEditor extends LObjectEditor {

	public static FieldEditor instance;
	public EditableFieldCanvas canvas;
	
	private Label tileCoord;
	private ScrolledComposite scrolledComposite;
		
	/**
	 * Create the composite.
	 * @param parent
	 * @param style
	 */
	public FieldEditor(Composite parent, int style) {
		super(parent, style);
		instance = this;
		setLayout(new GridLayout(2, false));
		
		FieldToolBar toolBar = new FieldToolBar(this, SWT.NONE);
		toolBar.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1));
		
		Label lblID = new Label(this, SWT.NONE);
		lblID.setText("ID: 9999");
		
		scrolledComposite = new ScrolledComposite(this, SWT.BORDER | SWT.H_SCROLL | SWT.V_SCROLL);
		scrolledComposite.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true, 2, 1));
		
		canvas = new EditableFieldCanvas(scrolledComposite, SWT.NONE) {
			
			public void onTileEnter(int x, int y) {
				super.onTileEnter(x, y);
				tileCoord.setText("(" + x + ", " + y + ")");
			}
			
			public void setField(Field field) {
				super.setField(field);
				if (field == null)
					lblID.setText("ID: -1");
				else
					lblID.setText("ID: " + field.id);
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
				canvas.redraw();
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
			FieldNode node = (FieldNode) obj;
			int id = Project.current.fieldTree.getData().getFieldID(node);
			if (canvas.field == null || canvas.field.id != id) {
				Field field = Project.current.fieldTree.loadField(node);
				selectField(field);
				super.setObject(field);
			}
		} else {
			selectField(null);
			super.setObject(null);
		}
	}

}
