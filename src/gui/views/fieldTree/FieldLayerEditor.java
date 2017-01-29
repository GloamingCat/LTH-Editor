package gui.views.fieldTree;

import gui.shell.field.LayerShell;
import gui.views.GraphicalList;
import lwt.dataestructure.LDataList;
import lwt.dataestructure.LPath;
import lwt.dialog.LObjectShell;
import lwt.dialog.LShellFactory;
import lwt.editor.LEditor;
import lwt.editor.LListEditor;
import lwt.event.LEditEvent;
import lwt.event.LSelectionEvent;
import lwt.event.listener.LCollectionListener;
import lwt.event.listener.LSelectionListener;

import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.SashForm;
import org.eclipse.swt.custom.StackLayout;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Shell;

import project.Project;
import data.BattlerType;
import data.Config;
import data.Field;
import data.Layer;
import data.Layer.Info;
import data.Tileset;
import data.Tileset.*;

public class FieldLayerEditor extends LEditor {

	protected FieldEditor fieldEditor;
	protected Field field;
	protected StackLayout stack = new StackLayout();
	protected Composite tileset;
	
	private GraphicalList<TerrainTile> lstTerrains;
	private GraphicalList<ObstacleTile> lstObstacles;
	private GraphicalList<CharTile> lstCharacters;
	private GraphicalList<RegionTile> lstRegions;
	private GraphicalList<BattlerType> lstBattlerTypes;
	private GraphicalList<PartyTile> lstParties;
	
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
		layerList.setShellFactory(new LShellFactory<Info>() {
			@Override
			public LObjectShell<Info> createShell(Shell parent) {
				return new LayerShell(parent);
			}
		});
		addChild(layerList);
		layerList.getCollectionWidget().addEditListener(new LCollectionListener<Info>() {
			public void onEdit(LEditEvent<Info> e) {
				onLayerEdit(e.newData);
			}
		});
		
		layerList.getCollectionWidget().addSelectionListener(new LSelectionListener() {
			@Override
			public void onSelect(LSelectionEvent event) {
				Layer l = (Layer) event.data;
				selectLayer(l);
				if (field != null && event.path != null) {
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
		
		LSelectionListener sl = new LSelectionListener() {
			@Override
			public void onSelect(LSelectionEvent event) {
				fieldEditor.onSelectTile(event.path.index);
			}
		};
		
		lstTerrains = new GraphicalList<>(tileset, SWT.NONE);
		lstTerrains.getCollectionWidget().addSelectionListener(sl);
		lstObstacles = new GraphicalList<>(tileset, SWT.NONE);
		lstObstacles.getCollectionWidget().addSelectionListener(sl);
		lstCharacters = new GraphicalList<>(tileset, SWT.NONE);
		lstCharacters.getCollectionWidget().addSelectionListener(sl);
		lstRegions = new GraphicalList<>(tileset, SWT.NONE);
		lstRegions.getCollectionWidget().addSelectionListener(sl);
		lstBattlerTypes = new GraphicalList<>(tileset, SWT.NONE);
		lstBattlerTypes.getCollectionWidget().addSelectionListener(sl);
		lstParties = new GraphicalList<>(tileset, SWT.NONE);
		lstParties.getCollectionWidget().addSelectionListener(sl);
		
		form.setWeights(new int[] {1, 1});
	}

	public void setFieldEditor(FieldEditor fieldEditor) {
		this.fieldEditor = fieldEditor;
	}

	@Override
	public void setObject(Object object) {
		if (object != null) {
			field = (Field) object;
			Tileset tileset = (Tileset) Project.current.tilesets.getList().get(field.prefs.tilesetID);
			Config conf = Project.current.config.getData();
			LDataList<PartyTile> parties = Tileset.createPartyArray(field.prefs.partyCount);
			lstTerrains.setDataCollection(tileset.terrains);
			lstObstacles.setDataCollection(tileset.obstacles);
			lstCharacters.setDataCollection(tileset.characters);
			lstRegions.setDataCollection(tileset.regions);
			lstBattlerTypes.setDataCollection(conf.battle.battlerTypes);
			lstParties.setDataCollection(parties);
			layerList.setDataCollection(field.layers);
			int layer = Project.current.fieldTree.getData().getLastLayer(field.id);
			layerList.getCollectionWidget().forceSelection(new LPath(layer));
		} else {
			lstTerrains.setDataCollection(null);
			lstObstacles.setDataCollection(null);
			lstCharacters.setDataCollection(null);
			lstRegions.setDataCollection(null);
			lstBattlerTypes.setDataCollection(null);
			lstParties.setDataCollection(null);
			layerList.setDataCollection(null);
		}
	}
	
	protected void setType(int type) {
		switch(type) {
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
		case 4:
			stack.topControl = lstBattlerTypes;
			break;
		case 5:
			stack.topControl = lstParties;
			break;
		}
		tileset.layout();
	}
	
	public void selectLayer(Layer layer) {
		fieldEditor.selectLayer(layer);
		if (layer == null) {
			stack.topControl = lstTerrains;
			tileset.layout();
		} else {
			setType(layer.info.type);
		}
	}
	
	public void onLayerEdit(Info newInfo) {
		fieldEditor.updateCanvas();
		setType(newInfo.type);
	}

}
