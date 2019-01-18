package gui.views.fieldTree;

import lwt.dataestructure.LDataList;
import lwt.dataestructure.LDataTree;
import lwt.editor.LEditor;
import lwt.widget.LImage;

import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.SashForm;
import org.eclipse.swt.custom.StackLayout;
import org.eclipse.swt.widgets.Composite;

import project.Project;
import data.Obstacle;
import data.Terrain;
import data.field.Field;
import data.field.Layer;
import data.subcontent.Icon;

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
			@Override
			public void updateImage(Object obj) {
				int id = ((Terrain) obj).animID;
				Icon icon = new Icon(id, 0, 0);
				image.setImage(icon.getImage(), icon.getRectangle());
			}
		};
		addChild(selTerrain);
		
		LImage imgTerrain = new LImage(terrain, SWT.NONE);
		imgTerrain.setHorizontalAlign(SWT.CENTER);
		imgTerrain.setVerticalAlign(SWT.CENTER);
		selTerrain.image = imgTerrain;
		
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
			@Override
			public void updateImage(Object obj) {
				Icon icon = ((Obstacle) obj).image;
				image.setImage(icon.getImage(), icon.getRectangle());
			}
		};
		addChild(selObstacle);
		
		LImage imgObstacle = new LImage(obstacle, SWT.NONE);
		imgObstacle.setHorizontalAlign(SWT.CENTER);
		imgObstacle.setVerticalAlign(SWT.CENTER);
		selObstacle.image = imgObstacle;
		
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
