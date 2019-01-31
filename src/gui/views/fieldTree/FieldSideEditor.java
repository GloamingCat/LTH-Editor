package gui.views.fieldTree;

import gui.Vocab;
import gui.helper.TilePainter;
import gui.views.fieldTree.subcontent.CharTileEditor;
import gui.views.fieldTree.subcontent.LayerList;
import gui.views.fieldTree.subcontent.PartyEditor;
import gui.views.fieldTree.subcontent.TileTree;
import gui.widgets.SimpleEditableList;
import lwt.dataestructure.LDataList;
import lwt.dataestructure.LDataTree;
import lwt.editor.LObjectEditor;
import lwt.event.LDeleteEvent;
import lwt.event.LInsertEvent;
import lwt.event.LSelectionEvent;
import lwt.event.listener.LCollectionListener;
import lwt.event.listener.LSelectionListener;
import lwt.widget.LCombo;
import lwt.widget.LImage;

import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.SashForm;
import org.eclipse.swt.custom.StackLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Group;
import org.eclipse.swt.widgets.Label;

import project.Project;
import data.field.CharTile;
import data.field.Field;
import data.field.Layer;
import data.field.Party;

import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.layout.GridData;

public class FieldSideEditor extends LObjectEditor {

	public static FieldSideEditor instance;
	
	public Field field;
	
	private StackLayout stack = new StackLayout();
	private int editor = 0;
	
	private LayerList[] lists;
	private TileTree[] trees;
	private Composite editors;
	
	private SimpleEditableList<CharTile> lstChars;
	private SimpleEditableList<Party> lstParties;
	private CharTileEditor charEditor;
	private PartyEditor partyEditor;
	private LCombo cmbPlayerParty;
	private Label lblTitle;
	
	private static String[] titles = new String[] { 
		Vocab.instance.TERRAIN,
		Vocab.instance.OBSTACLE,
		Vocab.instance.REGION,
		Vocab.instance.CHARACTER,
		Vocab.instance.PARTY
	};
	
	/**
	 * Create the composite.
	 * @param parent
	 * @param style
	 */
	public FieldSideEditor(Composite parent, int style) {
		super(parent, style);
		instance = this;
		
		setLayout(new GridLayout());
		
		lblTitle = new Label(this, SWT.NONE);
		lblTitle.setAlignment(SWT.CENTER);
		lblTitle.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1));
		lblTitle.setText(Vocab.instance.TERRAIN);
		
		editors = new Composite(this, SWT.NONE);
		editors.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true, 1, 1));
		editors.setLayout(stack);
		
		// Terrain
		
		SashForm terrain = new SashForm(editors, SWT.VERTICAL);
		
		Group grpTerrainLayers = new Group(terrain, SWT.NONE);
		grpTerrainLayers.setLayout(new FillLayout(SWT.HORIZONTAL));
		grpTerrainLayers.setText(Vocab.instance.LAYERS);
		LayerList lstTerrain = new LayerList(grpTerrainLayers, 0) {
			public LDataList<Layer> getLayerList(Field field) {
				return field.layers.terrain;
			}
		};
		lstTerrain.setEditor(this);
		
		Group grpTerrainTiles = new Group(terrain, SWT.NONE);
		grpTerrainTiles.setLayout(new FillLayout(SWT.VERTICAL));
		grpTerrainTiles.setText(Vocab.instance.TILES);
		TileTree selTerrain = new TileTree(grpTerrainTiles, SWT.NONE) {
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
		
		SashForm obstacle = new SashForm(editors, SWT.VERTICAL);
		
		Group grpObstacleLayers = new Group(obstacle, SWT.NONE);
		grpObstacleLayers.setLayout(new FillLayout(SWT.HORIZONTAL));
		grpObstacleLayers.setText(Vocab.instance.LAYERS);
		LayerList lstObstacle = new LayerList(grpObstacleLayers, 1) {
			public LDataList<Layer> getLayerList(Field field) {
				return field.layers.obstacle;
			}
		};
		lstObstacle.setEditor(this);
		
		Group grpObstacleTiles = new Group(obstacle, SWT.NONE);
		grpObstacleTiles.setLayout(new FillLayout(SWT.VERTICAL));
		grpObstacleTiles.setText(Vocab.instance.TILES);
		TileTree selObstacle = new TileTree(grpObstacleTiles, SWT.NONE) {
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
		
		SashForm region = new SashForm(editors, SWT.VERTICAL);
		region.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1));
		
		Group grpRegionLayers = new Group(region, SWT.NONE);
		grpRegionLayers.setLayout(new FillLayout(SWT.HORIZONTAL));
		grpRegionLayers.setText(Vocab.instance.LAYERS);
		LayerList lstRegion = new LayerList(grpRegionLayers, 2) {
			public LDataList<Layer> getLayerList(Field field) {
				return field.layers.region;
			}
		};
		lstRegion.setEditor(this);
		
		Group grpRegionTiles = new Group(region, SWT.NONE);
		grpRegionTiles.setLayout(new FillLayout(SWT.VERTICAL));
		grpRegionTiles.setText(Vocab.instance.TILES);
		TileTree selRegion = new TileTree(grpRegionTiles, SWT.NONE) {
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
		
		Composite character = new Composite(editors, SWT.NONE);
		character.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1));
		GridLayout gl_character = new GridLayout(1, false);
		gl_character.marginHeight = 0;
		gl_character.marginWidth = 0;
		character.setLayout(gl_character);
		
		LCollectionListener<CharTile> charListener = new LCollectionListener<CharTile>() {
			public void onInsert(LInsertEvent<CharTile> event) {
				if (event != null && event.node != null)
					FieldEditor.instance.canvas.updateTileImage(event.node.data.x, event.node.data.y);
			}
			public void onDelete(LDeleteEvent<CharTile> event) {
				if (event != null && event.node != null)
					FieldEditor.instance.canvas.updateTileImage(event.node.data.x, event.node.data.y);
			}
		};
		
		lstChars = new SimpleEditableList<>(character, SWT.NONE);
		lstChars.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true, 1, 1));
		lstChars.getCollectionWidget().setEditEnabled(false);
		lstChars.setIncludeID(false);
		lstChars.type = CharTile.class;
		addChild(lstChars, "characters");
		lstChars.getCollectionWidget().addInsertListener(charListener);
		lstChars.getCollectionWidget().addSelectionListener(new LSelectionListener() {
			@Override
			public void onSelect(LSelectionEvent event) {
				if (event == null || event.data == null)
					return;
				CharTile tile = (CharTile) event.data;
				FieldEditor.instance.canvas.setHeight(tile.h);
			}
		});
		
		charEditor = new CharTileEditor(character, SWT.NONE);
		charEditor.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, false, 1, 1));
		lstChars.addChild(charEditor);
		
		// Party
		
		Composite party = new Composite(editors, SWT.NONE);
		party.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1));
		GridLayout gl_party = new GridLayout(2, false);
		gl_party.marginWidth = 0;
		gl_party.marginHeight = 0;
		party.setLayout(gl_party);
		
		Label lblPlayerParty = new Label(party, SWT.NONE);
		lblPlayerParty.setText(Vocab.instance.PLAYERPARTY);
		
		cmbPlayerParty = new LCombo(party, SWT.NONE);
		cmbPlayerParty.setIncludeID(false);
		cmbPlayerParty.setOptional(true);
		cmbPlayerParty.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, false, false, 1, 1));
		addControl(cmbPlayerParty, "playerParty");
		
		Composite partylist = new Composite(party, SWT.NONE);
		partylist.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true, 2, 1));
		partylist.setLayout(new FillLayout(SWT.VERTICAL));
		
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
		
		lstParties = new SimpleEditableList<>(partylist, SWT.NONE);
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
		
		partyEditor = new PartyEditor(partylist, SWT.NONE);
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
			FieldEditor.instance.canvas.setMode(0);
		} else {
			selectLayer(null);
			if (i == lists.length) { 	// Character
				lstChars.onVisible();
				FieldEditor.instance.canvas.setParty(null);
				FieldEditor.instance.canvas.setMode(1);
			} else { 					// Party
				lstParties.onVisible();
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
