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
import gui.widgets.TransformedImage;
import lui.base.LFlags;
import lui.base.LPrefs;
import lui.base.event.listener.LCollectionListener;
import lui.container.LContainer;
import lui.container.LFrame;
import lui.container.LPanel;
import lui.dialog.LObjectDialog;
import lui.dialog.LWindow;
import lui.dialog.LWindowFactory;
import lui.base.event.LEditEvent;
import lui.widget.LCheckBox;
import lui.widget.LLabel;
import lui.widget.LSpinner;
import lui.widget.LText;

import data.GameCharacter;
import data.GameCharacter.Portrait;
import data.subcontent.Node;
import data.subcontent.Tile;
import gson.GObjectTreeSerializer;
import project.Project;

public class CharacterTab extends DatabaseTab<GameCharacter> {

	private IDButton btnBattler;
	private NodeList lstAnim;
	private PortraitList lstPortraits;
	
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
		TransformEditor transformEditor = new TransformEditor(grpTransform);
		transformEditor.addMenu(grpTransform);
		addChild(transformEditor, "transform");

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
		TransformedImage imgAnim = new TransformedImage(grpAnimations);
		imgAnim.getCellData().setExpand(true, true);
		imgAnim.getCellData().setSpread(1, 2);
		imgAnim.setAlignment(LFlags.CENTER | LFlags.MIDDLE);
		lstAnim.getCollectionWidget().addSelectionListener(e -> {
			GameCharacter c = contentEditor.getObject();
			if (c != null)
				imgAnim.update((Node) e.data, c.transform, !c.transformAnimations);
		});
		lstAnim.getCollectionWidget().addEditListener(new LCollectionListener<>() {
			@Override
			public void onEdit(LEditEvent<Node> e) {
				GameCharacter c = contentEditor.getObject();
				if (c != null)
					imgAnim.update(e.newData, c.transform, !c.transformAnimations);
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
		lstPortraits = new PortraitList(grpPortraits);
		lstPortraits.getCellData().setRequiredSize(128, 0);
		lstPortraits.getCellData().setExpand(false, true);
		lstPortraits.addMenu(grpPortraits);
		addChild(lstPortraits, "portraits");
		TransformedImage imgPortrait = new TransformedImage(grpPortraits);
		imgPortrait.getCellData().setExpand(true, true);
		imgPortrait.getCellData().setSpread(1, 2);
		imgPortrait.setAlignment(LFlags.CENTER | LFlags.MIDDLE);
		lstPortraits.getCollectionWidget().addSelectionListener(e -> {
			GameCharacter c = contentEditor.getObject();
			if (c != null)
				imgPortrait.update((Portrait) e.data, c.transform, !c.transformPortraits);
		});
		lstPortraits.getCollectionWidget().addEditListener(new LCollectionListener<>() {
			@Override
			public void onEdit(LEditEvent<Portrait> e) {
				GameCharacter c = contentEditor.getObject();
				if (c != null)
					imgPortrait.update(e.newData, c.transform, !c.transformPortraits);
			}
		});

		LCheckBox btnIcon = new LCheckBox(grpPortraits);
		btnIcon.setText(Vocab.instance.APPLYTRANSFORM);
		btnIcon.setHoverText(Tooltip.instance.TRANSFORMICON);
		btnIcon.getCellData().setAlignment(LFlags.LEFT);
		addControl(btnIcon, "transformPortraits");

		transformEditor.onOffsetChange = offset -> {
			GameCharacter c = contentEditor.getObject();
			if (c.transformAnimations)
				imgAnim.updateOffset(selectedAnimId(), offset);
			if (c.transformPortraits)
				imgPortrait.updateOffset(selectedPortraitId(), offset);
		};
		transformEditor.onScaleChange = scale -> {
			GameCharacter c = contentEditor.getObject();
			if (c.transformAnimations)
				imgAnim.updateScale(selectedAnimId(), scale);
			if (c.transformPortraits)
				imgPortrait.updateScale(selectedPortraitId(), scale);
		};
		transformEditor.onRotationChange = angle -> {
			GameCharacter c = contentEditor.getObject();
			if (c.transformAnimations)
				imgAnim.updateRotation(selectedAnimId(), angle);
			if (c.transformPortraits)
				imgPortrait.updateRotation(selectedPortraitId(), angle);
		};
		transformEditor.onRGBAChange = color -> {
			imgAnim.updateRGBA(selectedAnimId(), color);
			imgPortrait.updateRGBA(selectedPortraitId(), color);
		};
		transformEditor.onHSVChange = color -> {
			imgAnim.updateHSV(selectedAnimId(), color);
			imgPortrait.updateHSV(selectedPortraitId(), color);
		};
	}

	private int selectedAnimId() {
		Node n = lstAnim.getCollectionWidget().getSelectedObject();
		return n == null ? -1 : n.id;
	}

	private int selectedPortraitId() {
		Portrait n = lstPortraits.getCollectionWidget().getSelectedObject();
		return n == null ? -1 : n.id;
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
