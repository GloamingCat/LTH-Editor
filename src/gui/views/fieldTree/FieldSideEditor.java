package gui.views.fieldTree;

import lwt.dataestructure.LDataList;
import lwt.dataestructure.LDataTree;
import lwt.editor.LEditor;

import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.SashForm;
import org.eclipse.swt.custom.StackLayout;
import org.eclipse.swt.widgets.Composite;

import project.Project;
import data.field.Field;
import data.field.Layer;

public class FieldSideEditor extends LEditor {

	public static FieldSideEditor instance;
	
	public Field field;
	
	private StackLayout stack = new StackLayout();
	
	private LayerList[] lists;
	private TileTree[] trees;
	
	/**
	 * Create the composite.
	 * @param parent
	 * @param style
	 */
	public FieldSideEditor(Composite parent, int style) {
		super(parent, style);
		instance = this;
		
		setLayout(stack);
		
		// Terrain
		
		SashForm terrain = new SashForm(this, SWT.VERTICAL);
		LayerList lstTerrain = new LayerList(terrain, 0) {
			public LDataList<Layer> getLayerList() {
				return field.layers.terrain;
			}
		};
		lstTerrain.setEditor(this);
		TileTree selTerrain = new TileTree(terrain, SWT.NONE) {
			@Override
			public LDataTree<Object> getTree() {
				return Project.current.terrains.getTree();
			}
		};
		addChild(selTerrain);
		
		// Obstacle
		
		SashForm obstacle = new SashForm(this, SWT.VERTICAL);
		LayerList lstObstacle = new LayerList(obstacle, 1) {
			public LDataList<Layer> getLayerList() {
				return field.layers.obstacle;
			}
		};
		lstObstacle.setEditor(this);
		TileTree selObstacle = new TileTree(obstacle, SWT.NONE) {
			@Override
			public LDataTree<Object> getTree() {
				return Project.current.obstacles.getTree();
			}
		};
		addChild(selObstacle);

		lists = new LayerList[] { lstTerrain, lstObstacle };
		trees = new TileTree[] { selTerrain, selObstacle };
	}

	@Override
	public void setObject(Object object) {
		field = (Field) object;
		for (LayerList l : lists)
			l.onSetField();
	}
	
	public void selectLayer(Layer layer) {
		FieldEditor.instance.selectLayer(layer);
		if (layer == null) {
			stack.topControl = getChildren()[0];
			layout();
		}
	}

	public void selectEditor(int i) {
		stack.topControl = getChildren()[i];
		layout();
	}

	public void unselectTiles() {
		for (TileTree t : trees) {
			t.unselect();
		}
	}
	
	public void onVisible() {
		super.onVisible();
		lists[0].onVisible();
	}

}
