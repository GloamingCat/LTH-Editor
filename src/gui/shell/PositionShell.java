package gui.shell;

import gui.helper.FieldHelper;
import gui.views.fieldTree.EditableFieldCanvas;

import org.eclipse.swt.widgets.Combo;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Spinner;

import lwt.dataestructure.LDataTree;
import lwt.dataestructure.LPath;
import lwt.dialog.LObjectShell;
import lwt.event.LSelectionEvent;
import lwt.event.listener.LSelectionListener;
import lwt.widget.LTree;

import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.SashForm;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;

import data.Field;
import data.Layer;
import data.Node;
import data.Position;
import project.Project;

import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.custom.ScrolledComposite;
import org.eclipse.swt.events.PaintListener;
import org.eclipse.swt.events.PaintEvent;

public class PositionShell extends LObjectShell<Position> {
	
	private LTree<Node, Field.Prefs> tree;
	private EditableFieldCanvas canvas;
	private Combo cmbLayer;
	private Combo cmbDirection;
	private Spinner spnX;
	private Spinner spnY;
	private ScrolledComposite scrolledComposite;

	public PositionShell(Shell parent) {
		super(parent);
		setMinimumSize(new Point(640, 480));
		GridData gridData = (GridData) content.getLayoutData();
		gridData.verticalAlignment = SWT.FILL;
		gridData.grabExcessVerticalSpace = true;
		content.setLayout(new GridLayout(1, false));

		SashForm sashForm = new SashForm(content, SWT.NONE);
		sashForm.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true, 1, 1));
		
		tree = new LTree<Node, Field.Prefs>(sashForm, SWT.NONE) {
			@Override
			protected LDataTree<Node> emptyNode() {
				return null;
			}
			@Override
			protected LDataTree<Node> duplicateNode(LPath nodePath) {
				return null;
			}
			@Override
			public LDataTree<Node> toNode(LPath path) {
				return Project.current.fieldTree.getData().root.getNode(path);
			}
			@Override
			public Node toObject(LPath path) {
				return toNode(path).data;
			}
		};
		tree.setDragEnabled(false);
		tree.setDataCollection(Project.current.fieldTree.getData().root);
		tree.addSelectionListener(new LSelectionListener() {
			@Override
			public void onSelect(LSelectionEvent event) {
				Node node = (Node) event.data;
				setField(node);
			}
		});
		
		Composite field = new Composite(sashForm, SWT.NONE);
		field.setLayout(new GridLayout(3, false));
		
		Label lblLayer = new Label(field, SWT.NONE);
		lblLayer.setText("Height");
		
		cmbLayer = new Combo(field, SWT.READ_ONLY);
		cmbLayer.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent arg0) {
				setLayer(cmbLayer.getSelectionIndex());
			}
		});
		cmbLayer.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1));
		
		Label label = new Label(field, SWT.NONE);
		label.setText("(-99, -99)");
		
		scrolledComposite = new ScrolledComposite(field, SWT.BORDER | SWT.H_SCROLL | SWT.V_SCROLL);
		scrolledComposite.setLayoutData(new GridData(SWT.FILL, SWT.FILL, false, true, 3, 1));

		canvas = new EditableFieldCanvas(scrolledComposite, SWT.NONE) {
			public void onTileLeftClick(int x, int y) {
				spnX.setSelection(x);
				spnY.setSelection(y);
				canvas.redraw();
			}
			public void onTileRightClick(int x, int y, Point origin) {}
			public void onTileEnter(int x, int y) {
				label.setText("(" + x + "," + y + ")");
			}
		};
		canvas.addPaintListener(new PaintListener() {
			public void paintControl(PaintEvent e) {
				int x = spnX.getSelection();
				int y = spnY.getSelection();
				int h = 0;
				if (canvas.currentLayer != null) {
					h = canvas.currentLayer.info.height;
				}
				Point p = FieldHelper.math.tile2Pixel(x, y, h);
				int[] poly = canvas.painter.getTilePolygon(0, 0);
				for(int i = 0; i < poly.length; i++) {
					poly[i] *= 3;
					poly[i] /= 4;
				}
				for(int i = 0; i < poly.length; i += 2) {
					poly[i] += p.x + canvas.x0;
					poly[i + 1] += p.y + canvas.y0;
				}
				e.gc.drawPolygon(poly);
			}
		});
		
		scrolledComposite.setContent(canvas);

		Composite bottom = new Composite(content, SWT.NONE);
		GridLayout gl_bottom = new GridLayout(3, false);
		gl_bottom.marginWidth = 0;
		gl_bottom.marginHeight = 0;
		bottom.setLayout(gl_bottom);
		bottom.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, false, false, 1, 1));
		
		Composite coordinates = new Composite(bottom, SWT.NONE);
		GridLayout gl_coordinates = new GridLayout(4, false);
		gl_coordinates.marginHeight = 0;
		gl_coordinates.marginWidth = 0;
		coordinates.setLayout(gl_coordinates);
		
		Label lblX = new Label(coordinates, SWT.NONE);
		lblX.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, false, false, 1, 1));
		lblX.setBounds(0, 0, 55, 15);
		lblX.setText("X");
		
		spnX = new Spinner(coordinates, SWT.BORDER);
		spnX.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1));
		
		Label lblY = new Label(coordinates, SWT.NONE);
		lblY.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, false, false, 1, 1));
		lblY.setText("Y");
		
		spnY = new Spinner(coordinates, SWT.BORDER);
		spnY.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1));
		
		Label lblDirection = new Label(bottom, SWT.NONE);
		lblDirection.setLayoutData(new GridData(SWT.RIGHT, SWT.CENTER, false, false, 1, 1));
		lblDirection.setText("Direction");
		
		cmbDirection = new Combo(bottom, SWT.READ_ONLY);
		cmbDirection.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1));
		String[] d = new String[] {"", "0°", "45°", "90°", "135°", 
				"180°", "225°", "270°", "315°"};
		cmbDirection.setItems(d);
		
		sashForm.setWeights(new int[] {1, 3});
	}
	
	private void setLayer(int i) {
		Layer layer = canvas.field.layers.get(i);
		canvas.setCurrentLayer(layer);
	}
	
	private void setField(Node node) {
		if (canvas.field != null && canvas.field.id == node.id)
			return;
		Field field = Project.current.fieldTree.loadField(node);
		canvas.setField(field);
		refreshLayerCombo();
		if (cmbLayer.getSelectionIndex() >= field.layers.size()
				|| cmbLayer.getSelectionIndex() < 0) {
			cmbLayer.select(0);
		}
		scrolledComposite.layout();
		scrolledComposite.setMinSize(canvas.getSize());
		setLayer(cmbLayer.getSelectionIndex());
	}
	
	public void open(Position initial) {
		super.open(initial);		
		LPath path = findPath(initial.fieldID);
		tree.forceSelection(path);
		
		spnX.setSelection(initial.x);
		spnY.setSelection(initial.y);
		if (initial.direction == -1) {
			cmbDirection.select(0);
		} else {
			cmbDirection.select(initial.direction / 45 + 1);
		}
		refreshLayerCombo();
		cmbLayer.select(initial.z);
		setLayer(initial.z);
	}
	
	private void refreshLayerCombo() {
		String[] layers = new String[canvas.field.layers.size()];
		for(int i = 0; i < layers.length; i++) {
			layers[i] = canvas.field.layers.get(i).info.name;
		}
		cmbLayer.setItems(layers);
	}
	
	private LPath findPath(int id) {
		return new LPath(0);
	}

	@Override
	protected Position createResult(Position initial) {
		if (spnX.getSelection() == initial.x && spnY.getSelection() == initial.y &&
				cmbDirection.getSelectionIndex() == initial.direction &&
				cmbLayer.getSelectionIndex() == initial.z &&
				canvas.field.id == initial.fieldID)
			return null;
		else {
			Position pos = new Position();
			pos.x = spnX.getSelection();
			pos.y = spnY.getSelection();
			pos.z = cmbLayer.getSelectionIndex();
			pos.direction = cmbDirection.getSelectionIndex() - 1;
			if (pos.direction >= 0) {
				pos.direction *= 45;
			}
			pos.fieldID = canvas.field.id;
			return pos;
		}
	}
	
}
