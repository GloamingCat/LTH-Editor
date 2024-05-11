package gui.views.system;

import gui.Tooltip;
import gui.Vocab;
import gui.views.database.subcontent.NodeList;
import gui.views.database.subcontent.PortraitList;
import lui.base.LFlags;
import lui.container.LContainer;
import lui.container.LFrame;
import lui.container.LPanel;
import lui.container.LView;
import lui.gson.GDefaultObjectEditor;

import data.config.Config;
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
		setGridLayout(4);
		setEqualCells(true, false);

		createMenuInterface();

		editor = new MainEditor(this, false);
		editor.getCellData().setAlignment(LFlags.CENTER);
		editor.getCellData().setSpread(4, 1);
		editor.getCellData().setExpand(true, false);
		addChild(editor);

		LPanel left = new LPanel(this);
		left.setGridLayout(1);
		left.getCellData().setExpand(true, true);
		left.getCellData().setRequiredSize(180, 0);

		LPanel middle = new LPanel(this);
		middle.setGridLayout(1);
		middle.getCellData().setExpand(true, true);
		middle.getCellData().setRequiredSize(150, 0);

		LPanel right = new LPanel(this);
		right.setFillLayout(false);
		right.getCellData().setExpand(true, true);

		// Screen
		
		LFrame grpScreen = new LFrame(left, Vocab.instance.SCREEN);
		grpScreen.setFillLayout(true);
		grpScreen.setHoverText(Tooltip.instance.SCREEN);
		grpScreen.getCellData().setExpand(true, true);
		grpScreen.getCellData().setRequiredSize(0, -1);
		
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
		grpGrid.getCellData().setRequiredSize(0, -1);
		
		GridEditor gridEditor = new GridEditor(grpGrid, true);
		editor.addChild(gridEditor, "grid");

		// Battle
		
		LFrame grpBattle = new LFrame(middle, Vocab.instance.BATTLE);		
		grpBattle.setFillLayout(true);
		grpBattle.setHoverText(Tooltip.instance.BATTLE);
		grpBattle.getCellData().setExpand(true, true);
		grpBattle.getCellData().setRequiredSize(0, -1);
		
		BattleEditor battleEditor = new BattleEditor(grpBattle, true);
		editor.addChild(battleEditor, "battle");
		
		// Troop
		
		LFrame grpTroop = new LFrame(middle, Vocab.instance.TROOP);
		grpTroop.setFillLayout(true);
		grpTroop.setHoverText(Tooltip.instance.TROOP);
		grpTroop.getCellData().setExpand(true, true);
		grpTroop.getCellData().setRequiredSize(0, -1);
		
		troopEditor = new TroopEditor(grpTroop, true);
		editor.addChild(troopEditor, "troop");

		// Animations
		
		LFrame grpAnimations = new LFrame(right, Vocab.instance.ANIMATIONS);
		grpAnimations.setFillLayout(true);
		grpAnimations.setHoverText(Tooltip.instance.ANIMATIONS);
		lstAnimations = new NodeList(grpAnimations, Vocab.instance.ANIMSHELL);
		lstAnimations.setIncludeID(false);
		editor.addChild(lstAnimations, "animations");
		
		// Icons

		LFrame grpIcons = new LFrame(right, Vocab.instance.ICONS);
		grpIcons.setFillLayout(true);
		grpIcons.setHoverText(Tooltip.instance.ICONS);
		PortraitList lstIcons = new PortraitList(grpIcons);
		editor.addChild(lstIcons, "icons");
		
		// Sounds

		LFrame grpSounds = new LFrame(this, Vocab.instance.SOUNDS);
		grpSounds.setFillLayout(true);
		grpSounds.setHoverText(Tooltip.instance.SOUNDS);
		grpSounds.getCellData().setExpand(true, true);
		SoundList lstSounds = new SoundList(grpSounds);
		editor.addChild(lstSounds, "sounds");

	}
	
	public void onVisible() {
		lstAnimations.dataTree = Project.current.animations.getTree();
		troopEditor.btnInitialTroop.dataTree = Project.current.troops.getTree();
		super.onVisible();
		editor.setObject(Project.current.config.getData());
	}

}
