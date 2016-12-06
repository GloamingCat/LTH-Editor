package gui.views.fieldTree;

import gui.views.common.GraphicalList;
import lwt.dataestructure.LDataList;
import lwt.dataestructure.LPath;
import lwt.dialog.LObjectShell;
import lwt.dialog.LShellFactory;
import lwt.editor.LEditor;
import lwt.editor.LListEditor;
import lwt.event.LSelectionEvent;
import lwt.event.listener.LSelectionListener;

import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.SashForm;
import org.eclipse.swt.custom.StackLayout;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.widgets.Composite;

import project.Project;
import data.Field;
import data.Layer;
import data.Layer.Info;
import data.Tileset;
import data.Tileset.*;

public class FieldLayerEditor extends LEditor {

	protected Field field;
	protected StackLayout stack = new StackLayout();
	protected Composite tileset;
	
	private GraphicalList<TerrainTile> lstTerrains;
	private GraphicalList<ObstacleTile> lstObstacles;
	private GraphicalList<CharTile> lstCharacters;
	private GraphicalList<RegionTile> lstRegions;
	
	private LListEditor<Layer, Layer.Info> layerList;
	
	/**
	 * Create the composite.
	 * @param parent
	 * @param style
	 */
	public FieldLayerEditor(Composite parent, int style) {
		super(parent, style);
		
		setLayout(new FillLayout());
		
		SashForm form = new SashForm(this, SWT.VERTICAL);
		
		layerList = new LListEditor<Layer, Layer.Info>(form, SWT.NONE) {
			@Override
			protected LDataList<Layer> getDataCollection() {
				return field == null ? null : field.layers;
			}
			@Override
			protected Layer createNewData() {
				return new Layer(field.sizeX, field.sizeY);
			}
			@Override
			protected Layer duplicateData(Layer original) {
				return new Layer(original);
			}
			@Override
			protected Info getEditableData(LPath path) {
				return field.layers.get(path.index).info;
			}
			@Override
			protected void setEditableData(LPath path, Info newData) {
				field.layers.get(path.index).info = newData;
			}
		};
		addChild(layerList);
		
		layerList.setShellFactory(new LShellFactory<Layer.Info>() {
			@Override
			public LObjectShell<Layer.Info> createShell(
					org.eclipse.swt.widgets.Shell parent) {
				return null;
			}
		});
		
		layerList.getCollectionWidget().addSelectionListener(new LSelectionListener() {
			@Override
			public void onSelect(LSelectionEvent event) {
				Layer l = (Layer) event.data;
				selectLayer(l);
				if (field != null) {
					Project.current.fieldTree.getData().setLastLayer(field.id, event.path.index);
				}
			}
		});
		
		layerList.getCollectionWidget().setEditEnabled(true);
		layerList.getCollectionWidget().setInsertNewEnabled(true);
		layerList.getCollectionWidget().setDuplicateEnabled(true);
		layerList.getCollectionWidget().setDeleteEnabled(true);
		layerList.getCollectionWidget().setDragEnabled(true);
		
		tileset = new Composite(form, SWT.NONE);
		tileset.setLayout(stack);
		
		lstTerrains = new GraphicalList<>(tileset, SWT.NONE);
		lstObstacles = new GraphicalList<>(tileset, SWT.NONE);
		lstCharacters = new GraphicalList<>(tileset, SWT.NONE);
		lstRegions = new GraphicalList<>(tileset, SWT.NONE);
		
		form.setWeights(new int[] {1, 1});
	}

	@Override
	public void setObject(Object object) {
		System.out.println("LayerEditor setObject");
		if (object != null) {
			field = (Field) object;
			Tileset tileset = (Tileset) Project.current.tilesets.getList().get(field.id);
			lstTerrains.setDataCollection(tileset.terrains);
			lstObstacles.setDataCollection(tileset.obstacles);
			lstCharacters.setDataCollection(tileset.characters);
			lstRegions.setDataCollection(tileset.regions);
			layerList.setDataCollection(field.layers);
		} else {
			lstTerrains.setDataCollection(null);
			lstObstacles.setDataCollection(null);
			lstCharacters.setDataCollection(null);
			lstRegions.setDataCollection(null);
			layerList.setDataCollection(null);
		}
	}
	
	public void selectLayer(Layer layer) {
		if (layer == null) {
			stack.topControl = lstTerrains;
		} else {
			System.out.println("lalalalal");
			switch(layer.info.type) {
			case 0:
				stack.topControl = lstTerrains;
				break;
			case 1:
				stack.topControl = lstObstacles;
				break;
			case 2:
				stack.topControl = lstCharacters;
				break;
			case 3:
				stack.topControl = lstRegions;
				break;
			}
		}
		tileset.layout();
	}

}
