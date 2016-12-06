package gui.views.fieldTree;

import lwt.editor.LObjectEditor;

import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.ScrolledComposite;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Scale;

import project.Project;
import data.Field;
import data.Layer;
import data.Node;

import org.eclipse.swt.layout.GridLayout;

public class FieldEditor extends LObjectEditor {

	private FieldToolBar toolBar;
	private Label tileCoord;
	private EditableFieldCanvas canvas;
	
	/**
	 * Create the composite.
	 * @param parent
	 * @param style
	 */
	public FieldEditor(Composite parent, int style) {
		super(parent, style);
		setLayout(new GridLayout(1, false));

		toolBar = new FieldToolBar(this, SWT.NONE);
		toolBar.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, false, false, 1, 1));
		
		ScrolledComposite scrolledComposite = new ScrolledComposite(this, SWT.BORDER | SWT.H_SCROLL | SWT.V_SCROLL);
		scrolledComposite.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true, 1, 1));
		
		canvas = new EditableFieldCanvas(scrolledComposite, SWT.NONE) {
			public void onTileEnter(int x, int y) {
				tileCoord.setText("(" + x + ", " + y + ")");
			}
		};
		canvas.setLayout(new FillLayout(SWT.HORIZONTAL));
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
				canvas.rescale(scale.getSelection() / 100);
				lblScale.setText(scale.getSelection() + "%");
				scrolledComposite.setMinWidth(canvas.getSize().x);
				scrolledComposite.setMinHeight(canvas.getSize().y);
				System.out.println(canvas.getSize());
			}
		});
		
	}
	
	public void selectLayer(Layer layer) {
		canvas.setCurrentLayer(layer);
		canvas.updateAllTileImages();
	}
	
	@Override
	public void setObject(Object obj) {
		System.out.println("FieldEditor setObject");
		if (obj != null) {
			Node node = (Node) obj;
			Field field = Project.current.fieldTree.loadField(node);
			int layer = Project.current.fieldTree.getData().getLastLayer(field.id);
			canvas.setField(field, layer);
			super.setObject(field);
		} else {
			canvas.setField(null, -1);
			super.setObject(null);
		}
	}
	
	public void onSelectField(Field field) {}
	
}
