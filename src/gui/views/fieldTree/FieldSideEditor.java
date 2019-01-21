package gui.views.fieldTree;

import java.util.ArrayList;

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
import lwt.editor.LState;
import lwt.event.LDeleteEvent;
import lwt.event.LInsertEvent;
import lwt.event.listener.LCollectionListener;
import lwt.widget.LCombo;
import lwt.widget.LImage;

import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.SashForm;
import org.eclipse.swt.custom.StackLayout;
import org.eclipse.swt.widgets.Composite;
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
	
	private SimpleEditableList<CharTile> lstChars;
	private SimpleEditableList<Party> lstParties;
	private CharTileEditor charEditor;
	private PartyEditor partyEditor;
	private LCombo cmbPlayerParty;
	
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
			public LDataList<Layer> getLayerList(Field field) {
				return field.layers.terrain;
			}
		};
		lstTerrain.setEditor(this);
		addChild(lstTerrain);
		
		TileTree selTerrain = new TileTree(terrain, SWT.NONE) {
			@Override
			public LDataTree<Object> getTree() {
				return Project.current.terrains.getTree();
			}
			@Override
			public void updateImage(Object obj, int id) {
				image.setImage(TilePainter.getTerrainTile(id, false));
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
			public LDataList<Layer> getLayerList(Field field) {
				return field.layers.obstacle;
			}
		};
		lstObstacle.setEditor(this);
		addChild(lstObstacle);
		
		TileTree selObstacle = new TileTree(obstacle, SWT.NONE) {
			@Override
			public LDataTree<Object> getTree() {
				return Project.current.obstacles.getTree();
			}
			@Override
			public void updateImage(Object obj, int id) {
				image.setImage(TilePainter.getObstacleTile(id));
			}
		};
		addChild(selObstacle);
		
		LImage imgObstacle = new LImage(obstacle, SWT.NONE);
		imgObstacle.setHorizontalAlign(SWT.CENTER);
		imgObstacle.setVerticalAlign(SWT.CENTER);
		selObstacle.image = imgObstacle;
		
		// Region
		
		SashForm region = new SashForm(this, SWT.VERTICAL);
		LayerList lstRegion = new LayerList(region, 1) {
			public LDataList<Layer> getLayerList(Field field) {
				return field.layers.region;
			}
		};
		lstRegion.setEditor(this);
		addChild(lstRegion);
		
		TileTree selRegion = new TileTree(region, SWT.NONE) {
			@Override
			public LDataTree<Object> getTree() {
				return Project.current.regions.getList().toTree();
			}
			@Override
			public void updateImage(Object obj, int id) {
				image.setImage(TilePainter.getRegionTile(id, true));
			}
		};
		addChild(selRegion);
		
		LImage imgRegion = new LImage(region, SWT.NONE);
		imgRegion.setHorizontalAlign(SWT.CENTER);
		imgRegion.setVerticalAlign(SWT.CENTER);
		selRegion.image = imgRegion;
		
		// Characters
		
		Composite character = new Composite(this, SWT.NONE);
		character.setLayout(new GridLayout(1, false));
		
		lstChars = new SimpleEditableList<>(character, SWT.NONE);
		lstChars.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true, 1, 1));
		lstChars.getCollectionWidget().setEditEnabled(false);
		lstChars.setIncludeID(false);
		lstChars.type = CharTile.class;
		addChild(lstChars, "characters");
		
		charEditor = new CharTileEditor(character, SWT.NONE);
		charEditor.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, false, 1, 1));
		lstChars.addChild(charEditor);
		
		// Party
		
		Composite party = new Composite(this, SWT.NONE);
		party.setLayout(new GridLayout(2, false));
		
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
		
		LCollectionListener<Party> listener = new LCollectionListener<Party>() {
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
		lstParties.getCollectionWidget().addInsertListener(listener);
		lstParties.getCollectionWidget().addDeleteListener(listener);
		
		partyEditor = new PartyEditor(partylist, SWT.NONE);
		lstParties.addChild(partyEditor);
		
		lists = new LayerList[] { lstTerrain, lstObstacle, lstRegion };
		trees = new TileTree[] { selTerrain, selObstacle, selRegion };
	}

	@Override
	public void setObject(Object object) {
		field = (Field) object;
		cmbPlayerParty.setItems(field.parties);
		charEditor.setField(field);
		super.setObject(object);
	}
	
	public void selectLayer(Layer layer) {
		FieldEditor.instance.selectLayer(layer);
		if (layer == null) {
			stack.topControl = getChildren()[0];
			editor = 0;
			layout();
		}
	}

	public void selectEditor(int i) {
		editor = i;
		if (i < lists.length){ // Terrain, Obstacle, Region
			lists[editor].onVisible();
			selectLayer(lists[i].getLayer());
		} else // Character, Party
			selectLayer(null);
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
		selectEditor(editor);
	}
	
	public ArrayList<LState> getChildrenStates() {
		ArrayList<LState> states = super.getChildrenStates();
		final int editor = this.editor;
		states.add(0, new LState() {
			@Override
			public void reset() {
				selectEditor(editor);
			}
		});
		return states;
	}

}
