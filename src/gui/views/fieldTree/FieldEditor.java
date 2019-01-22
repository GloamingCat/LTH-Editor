package gui.views.fieldTree;

import lwt.editor.LObjectEditor;

import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.ScrolledComposite;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Label;

import project.Project;
import data.field.CharTile;
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
			
			public CharTile moveCharacter(int x, int y, Point origin) {
				CharTile tile = super.moveCharacter(x, y, origin);
				if (tile == null)
					return null;
				FieldSideEditor.instance.onMoveCharacter(tile);
				return tile;
			}
			
		};
		addChild(canvas);
		scrolledComposite.setContent(canvas);

		Composite bottom = new Composite(this, SWT.NONE);
		bottom.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, false, false, 2, 1));
		bottom.setLayout(new GridLayout(2, false));
		
		tileCoord = new Label(bottom, SWT.NONE);
		tileCoord.setText("(-99, -99)");
		
		Composite scale = new Composite(bottom, SWT.NONE);
		GridLayout gl_scale = new GridLayout(3, true);
		gl_scale.marginHeight = 0;
		gl_scale.marginWidth = 0;
		scale.setLayout(gl_scale);
		scale.setLayoutData(new GridData(SWT.RIGHT, SWT.CENTER, true, false, 1, 1));
		
		Button btn50 = new Button(scale, SWT.NONE);
		btn50.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, false, false, 1, 1));
		btn50.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent arg0) {
				canvas.rescale(0.5f);
				scrolledComposite.setMinSize(canvas.getSize());
			}
		});
		btn50.setText("1:2");
		
		Button btn100 = new Button(scale, SWT.NONE);
		btn100.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, false, false, 1, 1));
		btn100.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent arg0) {
				canvas.rescale(1);
				scrolledComposite.setMinSize(canvas.getSize());
			}
		});
		btn100.setText("1:1");
		
		Button btn200 = new Button(scale, SWT.NONE);
		btn200.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, false, false, 1, 1));
		btn200.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent arg0) {
				canvas.rescale(2);
				scrolledComposite.setMinSize(canvas.getSize());
			}
		});
		btn200.setText("2:1");
		
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
