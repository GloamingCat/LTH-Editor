package gui.views.database.content;

import gson.project.GObjectTreeSerializer;
import gui.Vocab;
import gui.shell.database.CharTileShell;
import gui.views.database.DatabaseTab;
import gui.views.database.subcontent.NodeList;
import gui.views.database.subcontent.PortraitList;
import gui.views.database.subcontent.TransformEditor;
import gui.views.fieldTree.subcontent.ScriptList;
import gui.widgets.IDButton;
import gui.widgets.SimpleEditableList;
import lwt.container.LContainer;
import lwt.container.LFrame;
import lwt.container.LImage;
import lwt.container.LPanel;
import lwt.dialog.LObjectShell;
import lwt.dialog.LShell;
import lwt.dialog.LShellFactory;
import lwt.event.LEditEvent;
import lwt.event.LSelectionEvent;
import lwt.event.listener.LCollectionListener;
import lwt.event.listener.LSelectionListener;
import lwt.widget.LCheckBox;
import lwt.widget.LLabel;
import lwt.widget.LSpinner;
import lwt.widget.LText;

import data.Animation;
import data.GameCharacter;
import data.GameCharacter.Portrait;
import data.subcontent.Node;
import data.subcontent.Tile;
import project.Project;

public class CharacterTab extends DatabaseTab<GameCharacter> {

	private IDButton btnBattler;
	private IDButton btnShadow;
	private IDButton btnKO;
	private NodeList lstAnim;
	
	/**
	 * @wbp.parser.constructor
	 * @wbp.eval.method.parameter parent new lwt.dialog.LShell(800, 600)
	 */
	public CharacterTab(LContainer parent) {
		super(parent);
		
		LLabel lblBattler = new LLabel(grpGeneral, Vocab.instance.CHARBATTLER);
		LPanel battler = new LPanel(grpGeneral, 2, false);
		battler.setExpand(true, false);
		LText txtBattler = new LText(battler, true);
		btnBattler = new IDButton(battler, true);
		btnBattler.setNameWidget(txtBattler);
		btnBattler.addMenu(lblBattler);
		btnBattler.addMenu(txtBattler);
		addControl(btnBattler, "battlerID");
		
		// Shadow
		
		LLabel lblShadow = new LLabel(grpGeneral, Vocab.instance.SHADOW);
		LPanel shadow = new LPanel(grpGeneral, 2, false);
		shadow.setExpand(true, false);
		LText txtShadow = new LText(shadow, true);		
		btnShadow = new IDButton(shadow, true);
		btnShadow.setNameWidget(txtShadow);
		btnShadow.addMenu(lblShadow);
		btnShadow.addMenu(txtShadow);
		addControl(btnShadow, "shadowID");
		
		// Tiles
		
		LPanel middle = new LPanel(left, true, true);
		middle.setExpand(true, true);
		LFrame grpTiles = new LFrame(middle, Vocab.instance.COLLIDERTILES, true, true);
		SimpleEditableList<Tile> lstTiles = new SimpleEditableList<Tile>(grpTiles);
		lstTiles.type = Tile.class;
		lstTiles.setIncludeID(false);
		lstTiles.setShellFactory(new LShellFactory<Tile>() {
			@Override
			public LObjectShell<Tile> createShell(LShell parent) {
				return new CharTileShell(parent);
			}
		});
		lstTiles.addMenu(grpTiles);
		addChild(lstTiles, "tiles");
		
		// Scripts
		
		LFrame grpScripts = new LFrame(middle, Vocab.instance.SCRIPTS, 1);
		ScriptList lstScripts = new ScriptList(grpScripts, 2 | 4 | 8);
		lstScripts.setExpand(true, true);
		lstScripts.addMenu(grpScripts);
		addChild(lstScripts, "scripts");
		
		LCheckBox btnRepeat = new LCheckBox(grpScripts);
		btnRepeat.setText(Vocab.instance.REPEATCOLLISIONS);
		addControl(btnRepeat, "repeatCollisions");
		
		// KO
		
		LFrame grpKO = new LFrame(left, Vocab.instance.KOANIM, 3, false);
		grpKO.initGridData();
		
		LLabel lblKO = new LLabel(grpKO, Vocab.instance.ANIMATION);
		LText txtKO = new LText(grpKO, true);		
		btnKO = new IDButton(grpKO, true);
		btnKO.setNameWidget(txtKO);
		btnKO.addMenu(lblKO);
		addControl(btnKO, "koAnimID");
		
		LLabel lblFade = new LLabel(grpKO, Vocab.instance.FADEOUT);
		LSpinner spnFade = new LSpinner(grpKO, 2);
		spnFade.setMinimum(-1);
		spnFade.setMaximum(999);
		spnFade.addMenu(lblFade);
		addControl(spnFade, "koFadeout");
		
		// Transform
		
		LFrame grpTransform = new LFrame(right, Vocab.instance.TRANSFORM, true, true);
		grpTransform.setExpand(true, false);
		TransformEditor transform = new TransformEditor(grpTransform);
		transform.addMenu(grpTransform);
		addChild(transform, "transform");
		
		// Animations
		
		LPanel graphics = new LPanel(contentEditor, 2, true);
		graphics.setExpand(true, true);
		graphics.setSpread(2, 1);
		
		LFrame grpAnimations = new LFrame(graphics, Vocab.instance.ANIMATIONS, 2, false);
		grpAnimations.setExpand(true, true);
		lstAnim = new NodeList(grpAnimations);
		lstAnim.getCollectionWidget().setIncludeID(false);
		lstAnim.setMinimumWidth(128);
		lstAnim.setExpand(false, true);
		lstAnim.addMenu(grpAnimations);
		addChild(lstAnim, "animations");
		LImage imgAnim = new LImage(grpAnimations);
		imgAnim.setExpand(true, true);
		lstAnim.getCollectionWidget().addSelectionListener(new LSelectionListener() {
			@Override
			public void onSelect(LSelectionEvent event) {
				updateAnim(imgAnim, (Node) event.data); 
			}
		});
		lstAnim.getCollectionWidget().addEditListener(new LCollectionListener<Node>() {
			@Override
			public void onEdit(LEditEvent<Node> e) {
				updateAnim(imgAnim, e.newData);
			}
		});
		
		LFrame grpPortraits = new LFrame(graphics, Vocab.instance.PORTRAITS, 2, false);
		grpPortraits.setExpand(true, true);
		PortraitList lstPortraits = new PortraitList(grpPortraits);
		lstPortraits.setMinimumWidth(128);
		lstPortraits.setExpand(false, true);
		lstPortraits.addMenu(grpPortraits);
		addChild(lstPortraits, "portraits");
		LImage imgPotrait = new LImage(grpPortraits);
		imgPotrait.setExpand(true, true);
		lstPortraits.getCollectionWidget().addSelectionListener(new LSelectionListener() {
			@Override
			public void onSelect(LSelectionEvent event) {
				updatePortrait(imgPotrait, (Portrait) event.data);
			}
		});
		lstPortraits.getCollectionWidget().addEditListener(new LCollectionListener<Portrait>() {
			@Override
			public void onEdit(LEditEvent<Portrait> e) {
				updatePortrait(imgPotrait, e.newData);
			}
		});
		
		left.setExpand(true, false);
		right.setExpand(true, false);
		
	}
	
	private void updateAnim(LImage img, Node node) {
		if (node != null) {
			Animation anim = (Animation) Project.current.animations.getTree().get(node.id);
			if (anim != null) {
				GameCharacter c = (GameCharacter) contentEditor.getObject();
				anim.transform.setColorTransform(img, c.transform);
				img.setImage(anim.quad.fullPath(), anim.quad.getRect());
			} else {
				img.setImage((String) null); 
			}
		} else {
			img.setImage((String) null); 
		}
	}
	
	private void updatePortrait(LImage img, Portrait p) {
		if (p != null) {
			Object obj = Project.current.animations.getTree().get(p.id);
			if (obj == null) {
				img.setImage((String) null);
				return;
			}
			GameCharacter c = (GameCharacter) contentEditor.getObject();
			Animation anim = (Animation) obj;
			anim.transform.setColorTransform(img, c.transform);
			if (anim.quad.path.isEmpty()) {
				img.setImage((String) null);
				return;
			}
			img.setImage(Project.current.imagePath() + anim.quad.path, anim.quad.getRect());
		} else {
			img.setImage((String) null);
		}
	}
	
	@Override
	public void onVisible() {
		btnBattler.dataTree = Project.current.battlers.getTree();
		btnShadow.dataTree = Project.current.animations.getTree();
		btnKO.dataTree = Project.current.animations.getTree();
		lstAnim.dataTree = Project.current.animations.getTree();
		super.onVisible();
	}
	
	@Override
	protected GObjectTreeSerializer getSerializer() {
		return Project.current.characters;
	}

}
