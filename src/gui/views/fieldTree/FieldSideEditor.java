package gui.views.fieldTree;

import gui.Vocab;
import gui.views.fieldTree.subcontent.CharTileEditor;
import gui.views.fieldTree.subcontent.LayerList;
import gui.views.fieldTree.subcontent.PartyEditor;
import gui.views.fieldTree.subcontent.TileTree;
import gui.widgets.SimpleEditableList;
import lwt.LFlags;
import lwt.container.LContainer;
import lwt.container.LFrame;
import lwt.container.LImage;
import lwt.container.LPanel;
import lwt.container.LSashPanel;
import lwt.container.LStack;
import lwt.dataestructure.LDataList;
import lwt.dataestructure.LDataTree;
import lwt.event.LControlEvent;
import lwt.event.LDeleteEvent;
import lwt.event.LInsertEvent;
import lwt.event.LSelectionEvent;
import lwt.event.listener.LCollectionListener;
import lwt.event.listener.LControlListener;
import lwt.event.listener.LSelectionListener;
import lwt.widget.LCombo;
import lwt.widget.LLabel;

import project.Project;
import data.Animation;
import data.Obstacle;
import data.Terrain;
import data.field.CharTile;
import data.field.Field;
import data.field.Layer;
import data.field.Party;
import gson.editor.GDefaultObjectEditor;

public class FieldSideEditor extends GDefaultObjectEditor<Field> {

	public static FieldSideEditor instance;
	
	public Field field;
	
	private int editor = 0;
	
	private LayerList[] lists;
	private TileTree[] trees;
	private LContainer[] editors;
	private LStack stack;
	
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
		
		lblTitle = new LLabel(this, LFlags.CENTER | LFlags.EXPAND, Vocab.instance.TERRAIN);
		
		stack = new LStack(this);
		stack.setExpand(true, true);
		
		// Terrain
		
		LSashPanel terrain = new LSashPanel(stack, false);
		
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
		};
		LImage imgTerrain = new LImage(grpTerrainTiles);
		selTerrain.selector.addModifyListener(new LControlListener<Integer>() {
			@Override
			public void onModify(LControlEvent<Integer> event) {
				if (event.newValue == -1) {
					imgTerrain.setImage((String) null);
					return;
				}
				Terrain terrain = (Terrain) Project.current.terrains.getData().get(event.newValue);
				Animation anim = (Animation) Project.current.animations.getData().get(terrain.animID);
				if (anim == null)
					imgTerrain.setImage((String) null);
				else
					imgTerrain.setImage(anim.quad.fullPath(), anim.getCell(0, 0));
			}
		});
		terrain.setWeights(1, 2);
		
		// Obstacle
		
		LSashPanel obstacle = new LSashPanel(stack, false);
		
		LFrame grpObstacleLayers = new LFrame(obstacle, Vocab.instance.LAYERS, true, true);
		LayerList lstObstacle = new LayerList(grpObstacleLayers, 1) {
			@Override
			public LDataList<Layer> getLayerList(Field field) {
				return field.layers.obstacle;
			}
		};
		lstObstacle.setEditor(this);
		
		LFrame grpObstacleTiles = new LFrame(obstacle, Vocab.instance.TILES, false);
		LImage imgObstacle = new LImage(grpObstacleTiles);
		TileTree selObstacle = new TileTree(grpObstacleTiles) {
			@Override
			public LDataTree<Object> getTree() {
				return Project.current.obstacles.getTree();
			}
		};
		selObstacle.selector.addModifyListener(new LControlListener<Integer>() {
			@Override
			public void onModify(LControlEvent<Integer> event) {
				if (event.newValue == -1) {
					imgObstacle.setImage((String) null);
					return;
				}
				Obstacle obstacle = (Obstacle) Project.current.obstacles.getData().get(event.newValue);
				imgObstacle.setImage(obstacle.image.fullPath(), obstacle.image.getRectangle());
			}
		});
		obstacle.setWeights(1, 2);
		
		// Region
		
		LSashPanel region = new LSashPanel(stack, false);
		region.setExpand(true, false);
		region.setAlignment(LFlags.CENTER);
		
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
		};
		LLabel lblRegion = new LLabel(grpRegionTiles);
		selRegion.selector.addModifyListener(new LControlListener<Integer>() {
			@Override
			public void onModify(LControlEvent<Integer> event) {
				if (event.newValue == -1) {
					imgTerrain.setImage((String) null);
					return;
				}
				lblRegion.setText(Project.current.regions.getList().get(event.newValue).toString());
			}
		});
		region.setWeights(1, 2);
		
		// Characters
		
		LSashPanel character = new LSashPanel(stack, false);
		character.setExpand(true, false);
		character.setAlignment(LFlags.CENTER);
		
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
		grpChars.setExpand(true, true);
		lstChars = new SimpleEditableList<>(grpChars);
		lstChars.getCollectionWidget().setEditEnabled(false);
		lstChars.setIncludeID(false);
		lstChars.type = CharTile.class;
		addChild(lstChars, "characters");
		
		charEditor = new CharTileEditor(character);
		charEditor.setExpand(true, false);
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
		
		LPanel party = new LPanel(stack, 2, false);
		party.setAlignment(LFlags.CENTER);
		party.setExpand(true, false);
		
		new LLabel(party, Vocab.instance.PLAYERPARTY);
		
		cmbPlayerParty = new LCombo(party);
		cmbPlayerParty.setIncludeID(false);
		cmbPlayerParty.setOptional(true);
		addControl(cmbPlayerParty, "playerParty");
		
		LSashPanel partylist = new LSashPanel(party, false);
		partylist.setExpand(true, true);
		partylist.setSpread(2, 1);
		
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
		editors = new LContainer[] { terrain, obstacle, region, character, party };
		
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
		stack.setTop(editors[i]);
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
