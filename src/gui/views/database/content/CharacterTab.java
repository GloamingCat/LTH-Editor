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
import lwt.container.LPanel;
import lwt.dataestructure.LDataTree;
import lwt.dialog.LObjectShell;
import lwt.dialog.LShell;
import lwt.dialog.LShellFactory;
import lwt.event.LEditEvent;
import lwt.event.LSelectionEvent;
import lwt.event.listener.LCollectionListener;
import lwt.event.listener.LSelectionListener;
import lwt.widget.LCheckBox;
import lwt.widget.LImage;
import lwt.widget.LLabel;
import lwt.widget.LSpinner;
import lwt.widget.LText;

import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.graphics.Rectangle;
import org.eclipse.swt.layout.GridData;
import org.eclipse.wb.swt.SWTResourceManager;

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
	
	/**
	 * @wbp.parser.constructor
	 * @wbp.eval.method.parameter parent new lwt.dialog.LShell(800, 600)
	 */
	public CharacterTab(LContainer parent) {
		super(parent);
		
		new LLabel(grpGeneral, Vocab.instance.CHARBATTLER);
		
		LPanel battler = new LPanel(grpGeneral, 2, false);
		battler.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, false, 1, 1));
		
		LText txtBattler = new LText(battler, true);
		btnBattler = new IDButton(battler, true);
		btnBattler.setNameWidget(txtBattler);
		addControl(btnBattler, "battlerID");
		
		// Shadow
		
		new LLabel(grpGeneral, Vocab.instance.SHADOW);
		
		LPanel shadow = new LPanel(grpGeneral, 2, false);
		shadow.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, false, 1, 1));
		
		LText txtShadow = new LText(shadow, true);		
		btnShadow = new IDButton(shadow, true);
		btnShadow.setNameWidget(txtShadow);
		addControl(btnShadow, "shadowID");
		
		// Tiles
		
		LPanel middle = new LPanel(left, true, true);
		middle.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true, 1, 1));
		
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
		addChild(lstTiles, "tiles");
		
		// Scripts
		
		LFrame grpScripts = new LFrame(middle, Vocab.instance.SCRIPTS, 1);
		ScriptList lstScripts = new ScriptList(grpScripts, 2 | 4 | 8);
		lstScripts.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true, 1, 1));
		addChild(lstScripts, "scripts");
		LCheckBox btnRepeat = new LCheckBox(grpScripts);
		btnRepeat.setText(Vocab.instance.REPEATCOLLISIONS);
		addControl(btnRepeat, "repeatCollisions");
		
		// KO
		
		LFrame grpKO = new LFrame(left, Vocab.instance.KOANIM, 3, false);
		grpKO.setLayoutData(new GridData(SWT.FILL, SWT.FILL, false, false, 1, 1));
		
		new LLabel(grpKO, Vocab.instance.ANIMATION);
		
		LText txtKO = new LText(grpKO, true);		
		btnKO = new IDButton(grpKO, true);
		btnKO.setNameWidget(txtKO);
		addControl(btnKO, "koAnimID");
		
		new LLabel(grpKO, Vocab.instance.FADEOUT);
		
		LSpinner spnFade = new LSpinner(grpKO, 2);
		spnFade.setMinimum(-1);
		spnFade.setMaximum(999);
		addControl(spnFade, "koFadeout");
		
		// Transform
		
		LFrame grpTransform = new LFrame(right, Vocab.instance.TRANSFORM, true, true);
		grpTransform.setLayoutData(new GridData(SWT.FILL, SWT.FILL, false, false, 1, 1));
		
		TransformEditor transformTab = new TransformEditor(grpTransform);
		addChild(transformTab, "transform");
		
		// Animations
		
		LPanel graphics = new LPanel(contentEditor, 2, true);
		graphics.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true, 2, 1));
		
		LFrame grpAnimations = new LFrame(graphics, Vocab.instance.ANIMATIONS, 2, false);
		grpAnimations.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true, 1, 1));

		NodeList lstAnim = new NodeList(grpAnimations) {
			protected LDataTree<Object> getDataTree() { 
				return Project.current.animations.getTree(); 
			}
		};
		GridData gd_anims = new GridData(SWT.FILL, SWT.FILL, false, true, 1, 1);
		gd_anims.widthHint = 128;
		lstAnim.setLayoutData(gd_anims);
		lstAnim.getCollectionWidget().setIncludeID(false);
		addChild(lstAnim, "animations");
		
		LImage imgAnim = new LImage(grpAnimations, SWT.NONE);
		imgAnim.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true, 1, 1));
		
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
		grpPortraits.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true, 1, 1));
		
		PortraitList lstPortraits = new PortraitList(grpPortraits);
		GridData gd_portraits = new GridData(SWT.FILL, SWT.FILL, false, true, 1, 1);
		gd_portraits.widthHint = 128;
		lstPortraits.setLayoutData(gd_portraits);
		addChild(lstPortraits, "portraits");
		
		LImage imgPotrait = new LImage(grpPortraits, SWT.NONE);
		imgPotrait.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true, 1, 1));

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
		
		left.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, false, 1, 1));
		right.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, false, 1, 1));
		
	}
	
	private void updateAnim(LImage img, Node node) {
		if (node != null) {
			Animation anim = (Animation) Project.current.animations.getTree().get(node.id);
			if (anim != null) {
				GameCharacter c = (GameCharacter) contentEditor.getObject();
				Rectangle rect = anim.quad.getRectangle();
				anim.transform.setColorTransform(img, c.transform);
				img.setImage(SWTResourceManager.getImage(
						Project.current.imagePath() + anim.quad.path), rect);
			} else {
				img.setImage((Image) null); 
			}
		} else {
			img.setImage((Image) null); 
		}
	}
	
	private void updatePortrait(LImage img, Portrait p) {
		if (p != null) {
			Object obj = Project.current.animations.getTree().get(p.id);
			if (obj == null) {
				img.setImage((Image) null);
				return;
			}
			GameCharacter c = (GameCharacter) contentEditor.getObject();
			Animation anim = (Animation) obj;
			anim.transform.setColorTransform(img, c.transform);
			if (anim.quad.path.isEmpty()) {
				img.setImage((Image) null);
				return;
			}
			img.setImage(Project.current.imagePath() + anim.quad.path, anim.quad.getRectangle());
		} else {
			img.setImage((Image) null);
		}
	}
	
	@Override
	public void onVisible() {
		btnBattler.dataTree = Project.current.battlers.getTree();
		btnShadow.dataTree = Project.current.animations.getTree();
		btnKO.dataTree = Project.current.animations.getTree();
		super.onVisible();
	}
	
	@Override
	protected GObjectTreeSerializer getSerializer() {
		return Project.current.characters;
	}

}
