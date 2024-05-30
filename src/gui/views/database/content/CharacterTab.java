package gui.views.database.content;

import gui.Tooltip;
import gui.Vocab;
import gui.shell.ScriptDialog;
import gui.shell.database.CharTileDialog;
import gui.views.database.DatabaseTab;
import gui.views.database.subcontent.NodeList;
import gui.views.database.subcontent.PortraitList;
import gui.views.database.subcontent.TransformEditor;
import gui.views.fieldTree.subcontent.ScriptList;
import gui.widgets.IDButton;
import gui.widgets.ImageButton;
import gui.widgets.SimpleEditableList;
import lui.base.LFlags;
import lui.base.LPrefs;
import lui.base.event.listener.LCollectionListener;
import lui.container.LContainer;
import lui.container.LFrame;
import lui.container.LImage;
import lui.container.LPanel;
import lui.dialog.LObjectDialog;
import lui.dialog.LWindow;
import lui.dialog.LWindowFactory;
import lui.base.event.LEditEvent;
import lui.widget.LCheckBox;
import lui.widget.LLabel;
import lui.widget.LSpinner;
import lui.widget.LText;

import data.Animation;
import data.GameCharacter;
import data.GameCharacter.Portrait;
import data.subcontent.Node;
import data.subcontent.Tile;
import gson.GObjectTreeSerializer;
import project.Project;

public class CharacterTab extends DatabaseTab<GameCharacter> {

	private IDButton btnBattler;
	private NodeList lstAnim;
	
	/**
	 * @wbp.parser.constructor
	 * @wbp.eval.method.parameter parent new lwt.dialog.LShell(800, 600)
	 */
	public CharacterTab(LContainer parent) {
		super(parent);
	}

	@Override
	protected void createContent() {
		contentEditor.right.getCellData().setExpand(true, false);
		contentEditor.right.getCellData().setAlignment(LFlags.FILL);
		contentEditor.left.getCellData().setExpand(true, false);
		contentEditor.left.getCellData().setAlignment(LFlags.FILL);

		LLabel lblBattler = new LLabel(contentEditor.grpGeneral, Vocab.instance.CHARBATTLER, Tooltip.instance.CHARBATTLER);
		LPanel battler = new LPanel(contentEditor.grpGeneral);
		battler.setGridLayout(2);
		battler.getCellData().setExpand(true, false);
		LText txtBattler = new LText(battler, true);
		txtBattler.getCellData().setExpand(true, false);
		btnBattler = new IDButton(battler, Vocab.instance.BATTLERSHELL, true);
		btnBattler.setNameWidget(txtBattler);
		btnBattler.addMenu(lblBattler);
		btnBattler.addMenu(txtBattler);
		addControl(btnBattler, "battlerID");
		
		// Shadow
		
		LLabel lblShadow = new LLabel(contentEditor.grpGeneral, Vocab.instance.SHADOW, Tooltip.instance.SHADOW);
		LPanel shadow = new LPanel(contentEditor.grpGeneral);
		shadow.setGridLayout(2);
		shadow.getCellData().setExpand(true, false);
		LText txtShadow = new LText(shadow, true);
		txtShadow.getCellData().setExpand(true, false);
		ImageButton btnShadow = new ImageButton(shadow, true);
		btnShadow.setNameWidget(txtShadow);
		btnShadow.addMenu(lblShadow);
		btnShadow.addMenu(txtShadow);
		addControl(btnShadow, "shadowID");

		// Transform

		LFrame grpTransform = new LFrame(contentEditor.right, Vocab.instance.TRANSFORM);
		grpTransform.setFillLayout(true);
		grpTransform.setHoverText(Tooltip.instance.TRANSFORM);
		grpTransform.getCellData().setExpand(true, false);
		grpTransform.getCellData().setAlignment(LFlags.FILL);
		TransformEditor transform = new TransformEditor(grpTransform);
		transform.addMenu(grpTransform);
		addChild(transform, "transform");

		// Tiles + Scripts

		LPanel middle = new LPanel(contentEditor.left);
		middle.setFillLayout(true);
		middle.setSpacing(LPrefs.FRAMEMARGIN);
		middle.getCellData().setExpand(true, true);
		middle.getCellData().setTargetSize(0, 0);

		// Tiles

		LFrame grpTiles = new LFrame(middle, Vocab.instance.COLLIDERTILES);
		grpTiles.setFillLayout(true);
		grpTiles.setHoverText(Tooltip.instance.COLLIDERTILES);
		SimpleEditableList<Tile> lstTiles = new SimpleEditableList<>(grpTiles);
		lstTiles.setFillLayout(true);
		lstTiles.type = Tile.class;
		lstTiles.setIncludeID(false);
		lstTiles.setShellFactory(new LWindowFactory<>() {
			@Override
			public LObjectDialog<Tile> createWindow(LWindow parent) {
				return new CharTileDialog(parent);
			}
		});
		lstTiles.addMenu(grpTiles);
		lstTiles.getCellData().setRequiredSize(0, 0);
		addChild(lstTiles, "tiles");

		// Scripts

		LFrame grpScripts = new LFrame(middle, Vocab.instance.SCRIPTS);
		grpScripts.setGridLayout(1);
		grpScripts.setHoverText(Tooltip.instance.SCRIPTS);
		ScriptList lstScripts = new ScriptList(grpScripts,
				ScriptDialog.ONLOAD | ScriptDialog.ONCOLLIDE | ScriptDialog.ONINTERACT);
		lstScripts.getCellData().setExpand(true, true);
		lstScripts.getCellData().setAlignment(LFlags.FILL);
		lstScripts.getCellData().setRequiredSize(0, 0);
		lstScripts.addMenu(grpScripts);
		addChild(lstScripts, "scripts");

		LCheckBox btnRepeat = new LCheckBox(grpScripts);
		btnRepeat.setText(Vocab.instance.REPEATCOLLISIONS);
		btnRepeat.setHoverText(Tooltip.instance.REPEATCOLLISIONS);
		btnRepeat.getCellData().setAlignment(LFlags.LEFT);
		addControl(btnRepeat, "repeatCollisions");

		// KO

		LFrame grpKO = new LFrame(contentEditor.left, Vocab.instance.KOEFFECT);
		grpKO.setGridLayout(3);
		grpKO.setHoverText(Tooltip.instance.KOEFFECT);
		grpKO.getCellData().setExpand(true, false);
		grpKO.getCellData().setAlignment(LFlags.FILL);

		LLabel lblKO = new LLabel(grpKO, Vocab.instance.ANIMATION, Tooltip.instance.KOANIM);
		lblKO.getCellData().setTargetSize(LPrefs.LABELWIDTH, LPrefs.WIDGETHEIGHT);
		LText txtKO = new LText(grpKO, true);
		txtKO.getCellData().setExpand(true, false);
		ImageButton btnKO = new ImageButton(grpKO, true);
		btnKO.setNameWidget(txtKO);
		btnKO.addMenu(lblKO);
		addControl(btnKO, "koAnimID");

		LLabel lblFade = new LLabel(grpKO, Vocab.instance.FADEOUT, Tooltip.instance.KOFADE);
		LSpinner spnFade = new LSpinner(grpKO);
		spnFade.getCellData().setExpand(true, false);
		spnFade.getCellData().setSpread(2, 1);
		spnFade.setMinimum(-1);
		spnFade.addMenu(lblFade);
		addControl(spnFade, "koFadeout");

		// Animations
		
		LPanel graphics = new LPanel(contentEditor);
		graphics.setFillLayout(true);
		graphics.setSpacing(LPrefs.FRAMEMARGIN, 0);
		graphics.getCellData().setExpand(true, true);
		graphics.getCellData().setSpread(2, 2);
		
		LFrame grpAnimations = new LFrame(graphics, Vocab.instance.ANIMATIONS);
		grpAnimations.setGridLayout(2);
		grpAnimations.setHoverText(Tooltip.instance.CHARANIMS);
		lstAnim = new NodeList(grpAnimations, Vocab.instance.ANIMSHELL);
		lstAnim.getCollectionWidget().setIncludeID(false);
		lstAnim.getCellData().setRequiredSize(128, 0);
		lstAnim.getCellData().setExpand(false, true);
		lstAnim.addMenu(grpAnimations);
		addChild(lstAnim, "animations");
		LImage imgAnim = new LImage(grpAnimations);
		imgAnim.getCellData().setExpand(true, true);
		imgAnim.getCellData().setSpread(1, 2);
		imgAnim.setAlignment(LFlags.CENTER | LFlags.MIDDLE);
		lstAnim.getCollectionWidget().addSelectionListener(event -> updateAnim(imgAnim, (Node) event.data));
		lstAnim.getCollectionWidget().addEditListener(new LCollectionListener<>() {
			@Override
			public void onEdit(LEditEvent<Node> e) {
				updateAnim(imgAnim, e.newData);
			}
		});

		LCheckBox btnAnim = new LCheckBox(grpAnimations);
		btnAnim.setText(Vocab.instance.APPLYTRANSFORM);
		btnAnim.setHoverText(Tooltip.instance.TRANSFORMANIM);
		btnAnim.getCellData().setAlignment(LFlags.LEFT);
		addControl(btnAnim, "transformAnimations");

		LFrame grpPortraits = new LFrame(graphics, Vocab.instance.PORTRAITS);
		grpPortraits.setGridLayout(2);
		grpPortraits.setHoverText(Tooltip.instance.CHARICONS);
		PortraitList lstPortraits = new PortraitList(grpPortraits);
		lstPortraits.getCellData().setRequiredSize(128, 0);
		lstPortraits.getCellData().setExpand(false, true);
		lstPortraits.addMenu(grpPortraits);
		addChild(lstPortraits, "portraits");
		LImage imgPortrait = new LImage(grpPortraits);
		imgPortrait.getCellData().setExpand(true, true);
		imgPortrait.getCellData().setSpread(1, 2);
		imgPortrait.setAlignment(LFlags.CENTER | LFlags.MIDDLE);
		lstPortraits.getCollectionWidget().addSelectionListener(event -> updatePortrait(imgPortrait, (Portrait) event.data));
		lstPortraits.getCollectionWidget().addEditListener(new LCollectionListener<>() {
			@Override
			public void onEdit(LEditEvent<Portrait> e) {
				updatePortrait(imgPortrait, e.newData);
			}
		});

		LCheckBox btnIcon = new LCheckBox(grpPortraits);
		btnIcon.setText(Vocab.instance.APPLYTRANSFORM);
		btnIcon.setHoverText(Tooltip.instance.TRANSFORMICON);
		btnIcon.getCellData().setAlignment(LFlags.LEFT);
		addControl(btnIcon, "transformPortraits");

		transform.onChange = t -> {
			updateAnim(imgAnim, lstAnim.getCollectionWidget().getSelectedObject());
			updatePortrait(imgPortrait, lstPortraits.getCollectionWidget().getSelectedObject());
		};

	}
	
	private void updateAnim(LImage img, Node node) {
		if (node != null) {
			Animation anim = (Animation) Project.current.animations.getTree().get(node.id);
			if (anim != null) {
				GameCharacter c = contentEditor.getObject();
				img.resetTransform();
				anim.transform.applyTo(img);
				if (c.transformAnimations)
					c.transform.applyTo(img);
				else
					c.transform.applyColorTo(img);
				img.setImage(anim.quad.fullPath(), anim.quad);
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
			img.resetTransform();
			GameCharacter c = contentEditor.getObject();
			Animation anim = (Animation) obj;
			anim.transform.applyTo(img);
			if (c.transformPortraits)
				c.transform.applyTo(img);
			else
				c.transform.applyColorTo(img);
			if (anim.quad.path.isEmpty()) {
				img.setImage((String) null);
				return;
			}
			img.setImage(Project.current.imagePath() + anim.quad.path, anim.quad);
		} else {
			img.setImage((String) null);
		}
	}
	
	@Override
	public void onVisible() {
		btnBattler.dataTree = Project.current.battlers.getTree();
		lstAnim.dataTree = Project.current.animations.getTree();
		super.onVisible();
	}
	
	@Override
	protected GObjectTreeSerializer getSerializer() {
		return Project.current.characters;
	}

}
