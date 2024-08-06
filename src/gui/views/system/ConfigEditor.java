package gui.views.system;

import gui.Tooltip;
import gui.Vocab;
import gui.views.database.subcontent.NodeList;
import gui.views.database.subcontent.PortraitList;
import lui.base.LFlags;
import lui.base.LPrefs;
import lui.container.LContainer;
import lui.container.LFrame;
import lui.container.LPanel;
import lui.container.LView;
import lui.gson.GDefaultObjectEditor;

import data.config.Config;
import lui.widget.LLabel;
import project.Project;

public class ConfigEditor extends LView {

	private final GDefaultObjectEditor<Config> editor;
	private final NodeList lstAnimations;

	private final TroopEditor troopEditor;
	
	/**
	 * @wbp.parser.constructor
	 * @wbp.eval.method.parameter parent new lwt.dialog.LShell()
	 */
	public ConfigEditor(LContainer parent) {
		super(parent, true);
		setGridLayout(3);
		setEqualCells(true, false);

		createMenuInterface();

		editor = new MainEditor(this, false);
		editor.getCellData().setSpread(3, 1);
		editor.getCellData().setExpand(true, false);
		addChild(editor);

		LPanel left = new LPanel(this);
		left.setGridLayout(1);
		left.getCellData().setExpand(true, true);
		left.getCellData().setRequiredSize(180, 0);

		LPanel middle = new LPanel(this);
		middle.setGridLayout(1);
		middle.getCellData().setExpand(true, true);
		middle.getCellData().setRequiredSize(180, 0);

		LPanel right = new LPanel(this);
		right.setFillLayout(false);
		right.getCellData().setExpand(true, true);

		// Screen
		
		LFrame grpScreen = new LFrame(left, Vocab.instance.SCREEN);
		grpScreen.setFillLayout(true);
		grpScreen.setHoverText(Tooltip.instance.SCREEN);
		grpScreen.getCellData().setExpand(true, true);
		
		ScreenEditor screenEditor = new ScreenEditor(grpScreen, true);
		editor.addChild(screenEditor, "screen");
		
		// Player
		
		LFrame grpPlayer = new LFrame(middle, Vocab.instance.PLAYER);
		grpPlayer.setHoverText(Tooltip.instance.PLAYER);
		grpPlayer.setFillLayout(true);
		grpPlayer.getCellData().setExpand(true, true);

		PlayerEditor playerEditor = new PlayerEditor(grpPlayer, true);
		editor.addChild(playerEditor, "player");
		
		// Grid
		
		LFrame grpGrid = new LFrame(left, Vocab.instance.GRID);
		grpGrid.setFillLayout(true);
		grpGrid.setHoverText(Tooltip.instance.GRID);
		grpGrid.getCellData().setExpand(true, true);
		
		GridEditor gridEditor = new GridEditor(grpGrid, true);
		editor.addChild(gridEditor, "grid");

		// Battle
		
		LFrame grpBattle = new LFrame(middle, Vocab.instance.BATTLE);		
		grpBattle.setFillLayout(true);
		grpBattle.setHoverText(Tooltip.instance.BATTLE);
		grpBattle.getCellData().setExpand(true, true);
		
		BattleEditor battleEditor = new BattleEditor(grpBattle, true);
		editor.addChild(battleEditor, "battle");
		
		// Troop
		
		LFrame grpTroop = new LFrame(left, Vocab.instance.TROOP);
		grpTroop.setFillLayout(true);
		grpTroop.setHoverText(Tooltip.instance.TROOP);
		grpTroop.getCellData().setExpand(true, true);
		
		troopEditor = new TroopEditor(grpTroop, true);
		editor.addChild(troopEditor, "troop");

		// Animations / Icons
		
		LFrame grpGraphics = new LFrame(right, Vocab.instance.GRAPHICS, Tooltip.instance.GRAPHICS);
		grpGraphics.setGridLayout(2);
		grpGraphics.setSpacing(LPrefs.GRIDSPACING, 0);
		new LLabel(grpGraphics, Vocab.instance.ANIMATIONS, Tooltip.instance.ANIMATIONS).setAlignment(LFlags.CENTER);
		new LLabel(grpGraphics, Vocab.instance.ICONS, Tooltip.instance.ICONS).setAlignment(LFlags.CENTER);
		lstAnimations = new NodeList(grpGraphics, Vocab.instance.ANIMSHELL);
		lstAnimations.setIncludeID(false);
		lstAnimations.getCellData().setExpand(true, true);
		editor.addChild(lstAnimations, "animations");
		PortraitList lstIcons = new PortraitList(grpGraphics);
		lstIcons.getCellData().setExpand(true, true);
		editor.addChild(lstIcons, "icons");

		// Icon Config

		LFrame grpInput = new LFrame(right, Vocab.instance.INPUT);
		grpInput.setGridLayout(2);
		grpInput.setSpacing(LPrefs.GRIDSPACING, 0);
		//new LLabel(grpInput, Vocab.instance.KEYS, Tooltip.instance.KEYS).setAlignment(LFlags.CENTER);
		//new LLabel(grpInput, Vocab.instance.KEYMAPS, Tooltip.instance.KEYMAPS).setAlignment(LFlags.CENTER);

	}
	
	public void onVisible() {
		lstAnimations.dataTree = Project.current.animations.getTree();
		troopEditor.btnInitialTroop.dataTree = Project.current.troops.getTree();
		super.onVisible();
		editor.setObject(Project.current.config.getData());
	}

}
