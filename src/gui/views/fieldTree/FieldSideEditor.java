package gui.views.fieldTree;

import lbase.data.LDataTree;
import lbase.event.LDeleteEvent;
import lbase.event.LInsertEvent;
import lbase.event.listener.LCollectionListener;
import lbase.LFlags;

import gui.Tooltip;
import gui.Vocab;
import gui.views.fieldTree.subcontent.CharTileEditor;
import gui.views.fieldTree.subcontent.LayerList;
import gui.views.fieldTree.subcontent.PartyEditor;
import gui.views.fieldTree.subcontent.TileTree;
import gui.widgets.SimpleEditableList;
import lwt.LMenuInterface;
import lwt.container.LContainer;
import lwt.container.LFrame;
import lwt.container.LImage;
import lwt.container.LPanel;
import lwt.container.LFlexPanel;
import lwt.container.LStack;
import lwt.gson.GDefaultObjectEditor;
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

import java.util.function.Consumer;

public class FieldSideEditor extends GDefaultObjectEditor<Field> {

	public static final int TERRAIN = 0;
	public static final int OBSTACLE = 1;
	public static final int REGION = 2;
	public static final int CHAR = 3;
	public static final int PARTY = 4;

	public Field field;
	
	private int editor = 0;
	
	private final LayerList[] lists;
	private final TileTree[] trees;
	private final LContainer[] editors;
	private final LStack stack;
	
	private final SimpleEditableList<CharTile> lstChars;
	private final SimpleEditableList<Party> lstParties;
	public final CharTileEditor charEditor;
	public final PartyEditor partyEditor;
    private final LCombo cmbPlayerParty;
	private final LLabel lblTitle;

	public Consumer<CharTile> onSelectChar, onNewChar, onDeleteChar;
	public Consumer<Integer> onSelectTile, onSelectEditor;
	public Consumer<Layer> onSelectLayer;
	public Runnable onLayerEdit;
	public Consumer<Party> onSelectParty;

	private final static String[] titles = new String[] {
		Vocab.instance.TERRAIN,
		Vocab.instance.OBSTACLE,
		Vocab.instance.REGION,
		Vocab.instance.CHARACTER,
		Vocab.instance.PARTY
	};
	
	private final static String[] tooltips = new String[] {
		Tooltip.instance.TERRAIN,
		Tooltip.instance.OBSTACLE,
		Tooltip.instance.REGION,
		Tooltip.instance.CHARACTER,
		Tooltip.instance.PARTY
	};
	
	/**
	 * @wbp.parser.constructor
	 * @wbp.eval.method.parameter parent new lwt.dialog.LShell()
	 */
	FieldSideEditor(LContainer parent) {
		super(parent, false);
		setGridLayout(1);
		
		lblTitle = new LLabel(this, LFlags.CENTER | LFlags.EXPAND, Vocab.instance.TERRAIN);
		
		stack = new LStack(this);
		stack.getCellData().setExpand(true, true);
		
		// Terrain
		
		LFlexPanel terrain = new LFlexPanel(stack, false);
		
		LFrame grpTerrainLayers = new LFrame(terrain, Vocab.instance.LAYERS);
		grpTerrainLayers.setFillLayout(true);
		grpTerrainLayers.setHoverText(Tooltip.instance.LAYERS);
		LayerList lstTerrain = new LayerList(grpTerrainLayers);
		lstTerrain.onSelect = id -> refreshLayer(lstTerrain.getLayer(), id, 1);

		LFrame grpTerrainTiles = new LFrame(terrain, Vocab.instance.TILES);
		grpTerrainTiles.setFillLayout(false);
		grpTerrainTiles.setHoverText(Tooltip.instance.TILESET);
		TileTree selTerrain = new TileTree(grpTerrainTiles) {
			@Override
			public LDataTree<Object> getTree() {
				return Project.current.terrains.getTree();
			}
		};

		LImage imgTerrain = new LImage(grpTerrainTiles);
		selTerrain.selector.addModifyListener(event -> {
			if (onSelectTile != null)
				onSelectTile.accept(event.newValue);
            if (event.newValue == -1) {
                imgTerrain.setImage((String) null);
                return;
            }
            Terrain terrain1 = (Terrain) Project.current.terrains.getData().get(event.newValue);
            Animation anim = (Animation) Project.current.animations.getData().get(terrain1.animID);
            if (anim == null)
                imgTerrain.setImage((String) null);
            else
                imgTerrain.setImage(anim.quad.fullPath(), anim.getCell(0, 0));
        });
		terrain.setWeights(1, 2);

		// Obstacle

		LFlexPanel obstacle = new LFlexPanel(stack, false);

		LFrame grpObstacleLayers = new LFrame(obstacle, Vocab.instance.LAYERS);
		grpObstacleLayers.setFillLayout(true);
		grpObstacleLayers.setHoverText(Tooltip.instance.LAYERS);
		LayerList lstObstacle = new LayerList(grpObstacleLayers);
		lstObstacle.onSelect = id -> refreshLayer(lstObstacle.getLayer(), id, 1);

		LFrame grpObstacleTiles = new LFrame(obstacle, Vocab.instance.TILES);
		grpObstacleTiles.setFillLayout(false);
		grpObstacleTiles.setHoverText(Tooltip.instance.TILES);
		TileTree selObstacle = new TileTree(grpObstacleTiles) {
			@Override
			public LDataTree<Object> getTree() {
				return Project.current.obstacles.getTree();
			}
		};
		LImage imgObstacle = new LImage(grpObstacleTiles);
		selObstacle.selector.addModifyListener(event -> {
			if (onSelectTile != null)
				onSelectTile.accept(event.newValue);
            if (event.newValue == -1) {
                imgObstacle.setImage((String) null);
                return;
            }
            Obstacle obstacle1 = (Obstacle) Project.current.obstacles.getData().get(event.newValue);
            imgObstacle.setImage(obstacle1.image.fullPath(), obstacle1.image.getRectangle());
        });
		obstacle.setWeights(1, 2);

		// Region

		LFlexPanel region = new LFlexPanel(stack, false);
		region.getCellData().setExpand(true, false);
		region.getCellData().setAlignment(LFlags.CENTER);

		LFrame grpRegionLayers = new LFrame(region, Vocab.instance.LAYERS);
		grpRegionLayers.setFillLayout(true);
		grpRegionLayers.setHoverText(Tooltip.instance.LAYERS);
		LayerList lstRegion = new LayerList(grpRegionLayers);
		lstRegion.onSelect = id -> refreshLayer(lstRegion.getLayer(), id, 2);

		LFrame grpRegionTiles = new LFrame(region, Vocab.instance.TILES);
		grpRegionTiles.setFillLayout(false);
		grpRegionTiles.setHoverText(Tooltip.instance.TILES);
		TileTree selRegion = new TileTree(grpRegionTiles) {
			@Override
			public LDataTree<Object> getTree() {
				return Project.current.regions.getList().toTree();
			}
		};
		LLabel lblRegion = new LLabel(grpRegionTiles, "");
		selRegion.selector.addModifyListener(event -> {
			if (onSelectTile != null)
				onSelectTile.accept(event.newValue);
            if (event.newValue == -1) {
                lblRegion.setText("");
                return;
            }
            lblRegion.setText(Project.current.regions.getList().get(event.newValue).toString());
        });
		region.setWeights(1, 2);

		// Characters

		LFlexPanel character = new LFlexPanel(stack, false);

		LCollectionListener<CharTile> charListener = new LCollectionListener<>() {
			public void onInsert(LInsertEvent<CharTile> event) {
				CharTile tile = event == null ? null : event.node.data;
				if (onNewChar != null)
					onNewChar.accept(tile);
			}
			public void onDelete(LDeleteEvent<CharTile> event) {
				CharTile tile = event == null ? null : event.node.data;
				if (onDeleteChar != null)
					onDeleteChar.accept(tile);
			}
		};

		lstChars = new SimpleEditableList<>(character);
		lstChars.setMargins(5, 5);
		lstChars.getCellData().setExpand(true, true);
		lstChars.getCollectionWidget().setEditEnabled(false);
		lstChars.setIncludeID(false);
		lstChars.type = CharTile.class;
		addChild(lstChars, "characters");

		charEditor = new CharTileEditor(character);
		charEditor.setMargins(5, 5);
		charEditor.getCellData().setExpand(true, false);
		lstChars.addChild(charEditor);
		lstChars.getCollectionWidget().addInsertListener(charListener);
		lstChars.getCollectionWidget().addDeleteListener(charListener);
		lstChars.getCollectionWidget().addSelectionListener(event -> {
            CharTile tile = event == null ? null : (CharTile) event.data;
			if (onSelectChar != null)
				onSelectChar.accept(tile);
        });
		character.setWeights(1, 3);

		// Party

		LPanel party = new LPanel(stack);
		party.setGridLayout(2);
		party.getCellData().setAlignment(LFlags.CENTER);
		party.getCellData().setExpand(true, false);
		party.setMargins(5, 5);
		party.setSpacing(5, 0);

		new LLabel(party, Vocab.instance.PLAYERPARTY, Tooltip.instance.PLAYERPARTY);

		cmbPlayerParty = new LCombo(party, true);
		cmbPlayerParty.getCellData().setExpand(true, false);
		cmbPlayerParty.setIncludeID(false);
		cmbPlayerParty.setOptional(true);
		addControl(cmbPlayerParty, "playerParty");

		LFlexPanel partylist = new LFlexPanel(party, false);
		partylist.getCellData().setExpand(true, true);
		partylist.getCellData().setSpread(2, 1);

		LCollectionListener<Party> partyListener = new LCollectionListener<>() {
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
		lstParties.setMargins(0, 5);
		lstParties.getCollectionWidget().setEditEnabled(false);
		lstParties.setIncludeID(true);
		lstParties.type = Party.class;
		addChild(lstParties, "parties");
		lstParties.getCollectionWidget().addInsertListener(partyListener);
		lstParties.getCollectionWidget().addDeleteListener(partyListener);
		lstParties.getCollectionWidget().addSelectionListener(event -> {
            Party p = event == null ? null : (Party) event.data;
            if (onSelectParty != null)
				onSelectParty.accept(p);
        });

        partyEditor = new PartyEditor(partylist);
		partyEditor.onRename = n -> updatePartyNames();
		lstParties.addChild(partyEditor);

		lstTerrain.onEdit =	lstObstacle.onEdit = lstRegion.onEdit = this::onLayerEdit;
		lstTerrain.onCheck = lstObstacle.onCheck = lstRegion.onCheck = this::onLayerEdit;

		lists = new LayerList[] { lstTerrain, lstObstacle, lstRegion };
		trees = new TileTree[] { selTerrain, selObstacle, selRegion };
		editors = new LContainer[] { terrain, obstacle, region, character, party };
		
	}

	public void setMenuInterface(LMenuInterface mi) {
		for (LayerList list : lists)
			list.setMenuInterface(mi);
	}

	private void onLayerEdit(Object obj) {
		if (onLayerEdit != null)
			onLayerEdit.run();
	}

	private void refreshLayer(Layer l, int id, int type) {
		if (onSelectLayer != null)
			onSelectLayer.accept(l);
		if (field != null && id >= 0)
			Project.current.fieldTree.getData().setLastLayer(field.id, id, type);
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
			lists[0].setObject(field.layers.terrain);
			lists[1].setObject(field.layers.obstacle);
			lists[2].setObject(field.layers.region);
		}
		super.setObject(object);
		for (int i = 0; i < lists.length; i++)
			lists[i].setField(field, Project.current.fieldTree.getData().getLastLayer(field.id, i));
		selectEditor(editor);
	}

	public void selectEditor(int i) {
		editor = i;
		if (i == CHAR) {         // Character
			lstChars.onVisible();
		} else if (i == PARTY) { // Party
			lstParties.onVisible();
		} else {                 // Terrain, Obstacle, Region
			lists[i].onVisible();
			if (onSelectLayer != null)
				onSelectLayer.accept(null);
		}
		if (onSelectEditor != null)
			onSelectEditor.accept(i);
		lblTitle.setText(titles[i]);
		lblTitle.setHoverText(tooltips[i]);
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

	@Override
	public Class<?> getType() {
		return Field.class;
	}

}
