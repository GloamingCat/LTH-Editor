package gui.shell;

import gui.Vocab;
import gui.helper.FieldHelper;
import gui.views.fieldTree.EditableFieldCanvas;

import org.eclipse.swt.widgets.Combo;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Spinner;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.custom.SashForm;
import org.eclipse.swt.custom.ScrolledComposite;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.PaintListener;
import org.eclipse.swt.events.PaintEvent;
import org.eclipse.wb.swt.SWTResourceManager;

import lwt.dataestructure.LDataTree;
import lwt.dataestructure.LPath;
import lwt.dialog.LObjectShell;
import lwt.event.LSelectionEvent;
import lwt.event.listener.LSelectionListener;
import lwt.widget.LCombo;
import lwt.widget.LTree;

import data.field.Field;
import data.field.Layer;
import data.field.FieldNode;
import data.subcontent.Position;
import project.Project;

public class PositionShell extends LObjectShell<Position> {
	
	private LTree<FieldNode, Field> tree;
	private EditableFieldCanvas canvas;
	private Combo cmbLayer;
	private LCombo cmbDirection;
	private Spinner spnX;
	private Spinner spnY;
	private ScrolledComposite scrolledComposite;
	private Label lblPos;
	
	private LPath path = null;

	/**
	 * @wbp.parser.constructor
	 */
	public PositionShell(Shell parent) {
		this(parent, -1);
	}
	
	public PositionShell(Shell parent, int fieldID) {
		super(parent);
		setMinimumSize(new Point(640, 480));
		GridData gridData = (GridData) content.getLayoutData();
		gridData.verticalAlignment = SWT.FILL;
		gridData.grabExcessVerticalSpace = true;
		content.setLayout(new GridLayout(1, false));

		SashForm sashForm = new SashForm(content, SWT.NONE);
		sashForm.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true, 1, 1));
		
		tree = new LTree<FieldNode, Field>(sashForm, SWT.NONE) {
			@Override
			protected LDataTree<FieldNode> emptyNode() {
				return null;
			}
			@Override
			protected LDataTree<FieldNode> duplicateNode(LDataTree<FieldNode> node) {
				return null;
			}
			@Override
			public LDataTree<FieldNode> toNode(LPath path) {
				return Project.current.fieldTree.getData().getNode(path);
			}
			@Override
			public FieldNode toObject(LPath path) {
				return toNode(path).data;
			}
		};
		tree.setDragEnabled(false);
		tree.setDataCollection(Project.current.fieldTree.getData());
		tree.addSelectionListener(new LSelectionListener() {
			@Override
			public void onSelect(LSelectionEvent event) {
				setField(event.id, (FieldNode) event.data);
			}
		});
		
		if (fieldID >= 0) {
			LDataTree<FieldNode> node = Project.current.fieldTree.getData().findNode(fieldID);
			path = node == null ? null : node.toPath();
			tree.setEnabled(false);
		}
		
		scrolledComposite = new ScrolledComposite(sashForm, SWT.BORDER | SWT.H_SCROLL | SWT.V_SCROLL);

		canvas = new EditableFieldCanvas(scrolledComposite, SWT.NONE) {
			public void onTileLeftDown() {
				spnX.setSelection(tileX);
				spnY.setSelection(tileY);
				canvas.redraw();
			}
			public void onTileRightDown() {}
			public void onTileEnter(int x, int y) {
				lblPos.setText("(" + x + "," + y + ")");
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
				e.gc.setForeground(SWTResourceManager.getColor(SWT.COLOR_RED));
				e.gc.drawPolygon(poly);
			}
		});
		
		scrolledComposite.setContent(canvas);

		Composite bottom = new Composite(content, SWT.NONE);
		GridLayout gl_bottom = new GridLayout(6, false);
		gl_bottom.marginWidth = 0;
		gl_bottom.marginHeight = 0;
		bottom.setLayout(gl_bottom);
		bottom.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, false, false, 1, 1));
		
		Label lblLayer = new Label(bottom, SWT.NONE);
		lblLayer.setText(Vocab.instance.LAYER);
		
		cmbLayer = new Combo(bottom, SWT.READ_ONLY);
		cmbLayer.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent arg0) {
				setLayer(cmbLayer.getSelectionIndex());
			}
		});
		cmbLayer.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1));
		
		Composite coordinates = new Composite(bottom, SWT.NONE);
		coordinates.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1));
		GridLayout gl_coordinates = new GridLayout(4, false);
		gl_coordinates.marginHeight = 0;
		gl_coordinates.marginWidth = 0;
		coordinates.setLayout(gl_coordinates);
		
		Label lblX = new Label(coordinates, SWT.NONE);
		lblX.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, false, false, 1, 1));
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
		lblDirection.setText(Vocab.instance.DIRECTION);
		
		cmbDirection = new LCombo(bottom, SWT.READ_ONLY);
		cmbDirection.setOptional(true);
		cmbDirection.setIncludeID(false);
		String[] d = new String[] {"0°", "45°", "90°", "135°", 
				"180°", "225°", "270°", "315°"};
		cmbDirection.setItems(d);
		cmbDirection.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1));
		
		lblPos = new Label(bottom, SWT.NONE);
		lblPos.setText("(-99, -99)");
		
		sashForm.setWeights(new int[] {1, 3});
		
		pack();
	}
	
	private void setLayer(int i) {
		Layer layer = canvas.field.layers.terrain.get(i);
		canvas.setCurrentLayer(layer);
	}
	
	private void setField(int id, FieldNode node) {
		if (canvas.field != null && canvas.field.id == id)
			return;
		Field field = Project.current.fieldTree.loadField(node);
		canvas.setField(field);
		refreshLayerCombo();
		if (cmbLayer.getSelectionIndex() >= field.layers.terrain.size()
				|| cmbLayer.getSelectionIndex() < 0) {
			cmbLayer.select(0);
		}
		scrolledComposite.layout();
		scrolledComposite.setMinSize(canvas.getSize());
		setLayer(cmbLayer.getSelectionIndex());
	}
	
	public void open(Position initial) {
		super.open(initial);	
		if (path == null) {
			LPath path = findPath(initial.fieldID);
			tree.forceSelection(path);
		} else {
			tree.forceSelection(path);
		}
		spnX.setSelection(initial.x);
		spnY.setSelection(initial.y);
		if (initial.direction == -1) {
			cmbDirection.setValue(-1);
		} else {
			cmbDirection.setValue(initial.direction / 45);
		}
		refreshLayerCombo();
		cmbLayer.select(initial.z);
		setLayer(initial.z);
		content.layout();
	}
	
	private void refreshLayerCombo() {
		String[] layers = new String[canvas.field.layers.terrain.size()];
		for(int i = 0; i < layers.length; i++) {
			layers[i] = canvas.field.layers.terrain.get(i).info.name;
		}
		cmbLayer.setItems(layers);
	}
	
	private LPath findPath(int id) {
		return new LPath(0);
	}

	@Override
	protected Position createResult(Position initial) {
		Position pos = new Position();
		pos.x = spnX.getSelection();
		pos.y = spnY.getSelection();
		pos.z = cmbLayer.getSelectionIndex();
		pos.direction = cmbDirection.getSelectionIndex();
		if (pos.direction >= 0) {
			pos.direction *= 45;
		}
		pos.fieldID = canvas.field.id;
		return pos;
	}
	
}
