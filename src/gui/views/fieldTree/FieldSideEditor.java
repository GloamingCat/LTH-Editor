package gui.views.fieldTree;

import gui.Vocab;
import gui.helper.TilePainter;
import gui.views.fieldTree.subcontent.CharTileEditor;
import gui.views.fieldTree.subcontent.LayerList;
import gui.views.fieldTree.subcontent.PartyEditor;
import gui.views.fieldTree.subcontent.TileTree;
import gui.widgets.SimpleEditableList;
import lwt.container.LContainer;
import lwt.container.LFrame;
import lwt.container.LPanel;
import lwt.container.LSashPanel;
import lwt.dataestructure.LDataList;
import lwt.dataestructure.LDataTree;
import lwt.event.LDeleteEvent;
import lwt.event.LInsertEvent;
import lwt.event.LSelectionEvent;
import lwt.event.listener.LCollectionListener;
import lwt.event.listener.LSelectionListener;
import lwt.widget.LCombo;
import lwt.widget.LImage;
import lwt.widget.LLabel;

import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.StackLayout;

import project.Project;
import data.field.CharTile;
import data.field.Field;
import data.field.Layer;
import data.field.Party;
import gson.editor.GDefaultObjectEditor;

import org.eclipse.swt.layout.GridData;

public class FieldSideEditor extends GDefaultObjectEditor<Field> {

	public static FieldSideEditor instance;
	
	public Field field;
	
	private StackLayout stack = new StackLayout();
	private int editor = 0;
	
	private LayerList[] lists;
	private TileTree[] trees;
	private LPanel editors;
	
	private SimpleEditableList<CharTile> lstChars;
	private SimpleEditableList<Party> lstParties;
	private CharTileEditor charEditor;
	private PartyEditor partyEditor;
	private LCombo cmbPlayerParty;
	private LLabel lblTitle;
	
	private static String[] titles = new String[] { 
		Vocab.instance.TERRAIN,
		Vocab.instance.OBSTACLE,
		Vocab.instance.REGION,
		Vocab.instance.CHARACTER,
		Vocab.instance.PARTY
	};
	
	/**
	 * @wbp.parser.constructor
	 * @wbp.eval.method.parameter parent new lwt.dialog.LShell()
	 */
	FieldSideEditor(LContainer parent) {
		super(parent, 1, false, false);
		instance = this;
		
		lblTitle = new LLabel(this, LLabel.CENTER, Vocab.instance.TERRAIN);
		
		editors = new LPanel(this);
		editors.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true, 1, 1));
		editors.setLayout(stack);
		stack.marginWidth = 5;
		stack.marginHeight = 5;
		
		// Terrain
		
		LSashPanel terrain = new LSashPanel(editors, false);
		
		LFrame grpTerrainLayers = new LFrame(terrain, Vocab.instance.LAYERS, true, true);
		LayerList lstTerrain = new LayerList(grpTerrainLayers, 0) {
			@Override
			public LDataList<Layer> getLayerList(Field field) {
				return field.layers.terrain;
			}
		};
		lstTerrain.setEditor(this);
		
		LFrame grpTerrainTiles = new LFrame(terrain, Vocab.instance.TILES, false);
		TileTree selTerrain = new TileTree(grpTerrainTiles) {
			@Override
			public LDataTree<Object> getTree() {
				return Project.current.terrains.getTree();
			}
			@Override
			public void updateImage(Object obj, int id) {
				image.setImage(TilePainter.getTerrainTile(id, false));
			}
		};
		
		LImage imgTerrain = new LImage(grpTerrainTiles, SWT.NONE);
		imgTerrain.setHorizontalAlign(SWT.CENTER);
		imgTerrain.setVerticalAlign(SWT.CENTER);
		selTerrain.image = imgTerrain;
		terrain.setWeights(new int[] {1, 2});
		
		// Obstacle
		
		LSashPanel obstacle = new LSashPanel(editors, false);
		
		LFrame grpObstacleLayers = new LFrame(obstacle, Vocab.instance.LAYERS, true, true);
		LayerList lstObstacle = new LayerList(grpObstacleLayers, 1) {
			@Override
			public LDataList<Layer> getLayerList(Field field) {
				return field.layers.obstacle;
			}
		};
		lstObstacle.setEditor(this);
		
		LFrame grpObstacleTiles = new LFrame(obstacle, Vocab.instance.TILES, false);
		TileTree selObstacle = new TileTree(grpObstacleTiles) {
			@Override
			public LDataTree<Object> getTree() {
				return Project.current.obstacles.getTree();
			}
			@Override
			public void updateImage(Object obj, int id) {
				image.setImage(TilePainter.getObstacleTile(id));
			}
		};
		
		LImage imgObstacle = new LImage(grpObstacleTiles, SWT.NONE);
		imgObstacle.setHorizontalAlign(SWT.CENTER);
		imgObstacle.setVerticalAlign(SWT.CENTER);
		selObstacle.image = imgObstacle;
		obstacle.setWeights(new int[] {1, 2});
		
		// Region
		
		LSashPanel region = new LSashPanel(editors, false);
		region.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1));
		
		LFrame grpRegionLayers = new LFrame(region, Vocab.instance.LAYERS, true, true);
		LayerList lstRegion = new LayerList(grpRegionLayers, 2) {
			public LDataList<Layer> getLayerList(Field field) {
				return field.layers.region;
			}
		};
		lstRegion.setEditor(this);
		
		LFrame grpRegionTiles = new LFrame(region, Vocab.instance.TILES, false);
		TileTree selRegion = new TileTree(grpRegionTiles) {
			@Override
			public LDataTree<Object> getTree() {
				return Project.current.regions.getList().toTree();
			}
			@Override
			public void updateImage(Object obj, int id) {
				image.setImage(TilePainter.getRegionTile(id, true));
			}
		};
		
		LImage imgRegion = new LImage(grpRegionTiles, SWT.NONE);
		imgRegion.setHorizontalAlign(SWT.CENTER);
		imgRegion.setVerticalAlign(SWT.CENTER);
		selRegion.image = imgRegion;
		region.setWeights(new int[] {1, 2});
		
		// Characters
		
		LSashPanel character = new LSashPanel(editors, false);
		character.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1));
		
		LCollectionListener<CharTile> charListener = new LCollectionListener<CharTile>() {
			public void onInsert(LInsertEvent<CharTile> event) {
				if (event == null || event.node == null) return;
				FieldEditor.instance.canvas.onTileChange(event.node.data.x - 1, event.node.data.y - 1);
				FieldEditor.instance.canvas.redrawBuffer();
				FieldEditor.instance.canvas.redraw();
			}
			public void onDelete(LDeleteEvent<CharTile> event) {
				if (event == null || event.node == null) return;
				FieldEditor.instance.canvas.onTileChange(event.node.data.x - 1, event.node.data.y - 1);
				FieldEditor.instance.canvas.redrawBuffer();
				FieldEditor.instance.canvas.redraw();
			}
		};
		
		LFrame grpChars = new LFrame(character, Vocab.instance.LAYERS, true, true);
		grpChars.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true, 1, 1));
		lstChars = new SimpleEditableList<>(grpChars);
		lstChars.getCollectionWidget().setEditEnabled(false);
		lstChars.setIncludeID(false);
		lstChars.type = CharTile.class;
		addChild(lstChars, "characters");
		
		charEditor = new CharTileEditor(character);
		charEditor.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, false, 1, 1));
		lstChars.addChild(charEditor);
		lstChars.getCollectionWidget().addInsertListener(charListener);
		lstChars.getCollectionWidget().addDeleteListener(charListener);
		lstChars.getCollectionWidget().addSelectionListener(new LSelectionListener() {
			@Override
			public void onSelect(LSelectionEvent event) {
				if (event == null || event.data == null)
					return;
				CharTile tile = (CharTile) event.data;
				FieldEditor.instance.canvas.setCharacter(tile);
			}
		});
		character.setWeights(new int[] {1, 3});

		// Party
		
		LPanel party = new LPanel(editors, 2, false);
		party.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1));
		
		new LLabel(party, Vocab.instance.PLAYERPARTY);
		
		cmbPlayerParty = new LCombo(party);
		cmbPlayerParty.setIncludeID(false);
		cmbPlayerParty.setOptional(true);
		addControl(cmbPlayerParty, "playerParty");
		
		LSashPanel partylist = new LSashPanel(party, false);
		partylist.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true, 2, 1));
		
		LCollectionListener<Party> partyListener = new LCollectionListener<Party>() {
			public void onInsert(LInsertEvent<Party> event) {
				cmbPlayerParty.setItems(field.parties);
				cmbPlayerParty.setValue(field.playerParty);
			}
			public void onDelete(LDeleteEvent<Party> event) {
				cmbPlayerParty.setItems(field.parties);
				cmbPlayerParty.setValue(field.playerParty);
			}
		};
		
		lstParties = new SimpleEditableList<>(partylist);
		lstParties.getCollectionWidget().setEditEnabled(false);
		lstParties.setIncludeID(true);
		lstParties.type = Party.class;
		addChild(lstParties, "parties");
		lstParties.getCollectionWidget().addInsertListener(partyListener);
		lstParties.getCollectionWidget().addDeleteListener(partyListener);
		lstParties.getCollectionWidget().addSelectionListener(new LSelectionListener() {
			@Override
			public void onSelect(LSelectionEvent event) {
				if (event == null)
					return;
				Party party = (Party) event.data;
				FieldEditor.instance.canvas.setParty(party);
			}
		});
		
		partyEditor = new PartyEditor(partylist);
		lstParties.addChild(partyEditor);
		
		lists = new LayerList[] { lstTerrain, lstObstacle, lstRegion };
		trees = new TileTree[] { selTerrain, selObstacle, selRegion };
	}
	
	public void onVisible() {
		for (TileTree t : trees)
			t.updateCollection();
	}

	@Override
	public void setObject(Object object) {
		field = (Field) object;
		if (field != null) {
			cmbPlayerParty.setItems(field.parties);
			charEditor.setField(field);
		}
		super.setObject(object);
		for (LayerList l : lists)
			l.setField(field);	
		selectEditor(editor);
	}
	
	public void selectLayer(Layer layer) {
		FieldEditor.instance.selectLayer(layer);
	}

	public void selectEditor(int i) {
		editor = i;
		if (i < lists.length){ 			// Terrain, Obstacle, Region
			lists[i].onVisible();
			selectLayer(lists[i].getLayer());
			FieldEditor.instance.canvas.setParty(null);
			FieldEditor.instance.canvas.setCharacter(null);
			FieldEditor.instance.canvas.setMode(0);
		} else {
			selectLayer(null);
			if (i == lists.length) { 	// Character
				lstChars.onVisible();
				FieldEditor.instance.canvas.setParty(null);
				FieldEditor.instance.canvas.setMode(1);
			} else { 					// Party
				lstParties.onVisible();
				FieldEditor.instance.canvas.setCharacter(null);
				FieldEditor.instance.canvas.setMode(2);
			}
		}
		lblTitle.setText(titles[i]);
		stack.topControl = editors.getChildren()[i];
		editors.layout();
	}
	
	public void selectTile(int i) {
		trees[editor].setTile(i);
	}

	public void unselectTiles() {
		for (TileTree t : trees) {
			t.setTile(-1);
		}
	}

	public void onMoveCharacter(CharTile tile) {
		charEditor.setPosition(tile);
	}
	
	public void updatePartyNames() {
		if (field == null)
			cmbPlayerParty.setItems((String[]) null);
		else {
			int index = cmbPlayerParty.getSelectionIndex();
			cmbPlayerParty.setItems(field.parties);
			cmbPlayerParty.setSelectionIndex(index);
		}
	}

}
