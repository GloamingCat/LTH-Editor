package gui.views.system;

import gui.Tooltip;
import gui.Vocab;
import gui.views.database.subcontent.NodeList;
import gui.views.database.subcontent.PortraitList;
import gui.widgets.IDButton;
import gui.widgets.ImageButton;
import gui.widgets.PositionButton;
import lui.base.LFlags;
import lui.container.LContainer;
import lui.container.LFrame;
import lui.container.LPanel;
import lui.container.LView;
import lui.gson.GDefaultObjectEditor;
import lui.widget.LCheckBox;
import lui.widget.LCombo;
import lui.widget.LLabel;
import lui.widget.LSpinner;
import lui.widget.LText;

import java.lang.reflect.Type;

import data.config.Config;
import project.Project;

public class ConfigEditor extends LView {

	private final GDefaultObjectEditor<Config> editor;
	private final NodeList lstAnimations;
	private final IDButton btnInitialTroop;
	
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
		editor.setGridLayout(2);
		editor.setEqualCells(true, false);
		addChild(editor);
		
		LPanel left = new LPanel(this);
		left.setGridLayout(1);
		left.getCellData().setExpand(true, true);
		left.getCellData().setMinimumSize(180, 0);
		
		LPanel middle = new LPanel(this);
		middle.setGridLayout(1);
		middle.getCellData().setExpand(true, true);
		middle.getCellData().setMinimumSize(150, 0);
		
		LPanel right = new LPanel(this);
		right.setFillLayout(false);
		right.getCellData().setExpand(true, true);
		
		// Name
		
		LFrame grpIdentity = new LFrame(editor, Vocab.instance.IDENTITY);
		grpIdentity.setGridLayout(6);
		grpIdentity.setHoverText(Tooltip.instance.IDENTITY);
		grpIdentity.getCellData().setExpand(true, false);
		
		new LLabel(grpIdentity, Vocab.instance.PROJECTNAME, Tooltip.instance.PROJECTNAME);
		LText txtName = new LText(grpIdentity);
		txtName.getCellData().setSpread(5, 1);
		txtName.getCellData().setExpand(true, false);
		editor.addControl(txtName, "name");
		
		// Cover
		
		new LLabel(grpIdentity, Vocab.instance.COVER, Tooltip.instance.COVER);
		LText txtCover = new LText(grpIdentity, true);
		txtCover.getCellData().setExpand(true, false);
		ImageButton btnCover = new ImageButton(grpIdentity, true);
		btnCover.setNameWidget(txtCover);
		editor.addControl(btnCover, "coverID");

		// Logo

		new LLabel(grpIdentity, Vocab.instance.LOGO, Tooltip.instance.LOGO);
		LText txtLogo = new LText(grpIdentity, true);
		txtLogo.getCellData().setExpand(true, false);
		ImageButton btnLogo = new ImageButton(grpIdentity, true);
		btnLogo.setNameWidget(txtLogo);
		editor.addControl(btnLogo, "logoID");
		
		// Platform
		
		LFrame grpExecution = new LFrame(editor, Vocab.instance.EXECUTION);
		grpExecution.setGridLayout(4);
		grpExecution.setHoverText(Tooltip.instance.EXECUTION);
		grpExecution.getCellData().setExpand(true, false);
		
		String[] platforms = new String[] {
				Vocab.instance.DESKTOP,
				Vocab.instance.MOBILE,
				Vocab.instance.BROWSER,
				Vocab.instance.MOBILEBROWSER
			};
		new LLabel(grpExecution, Vocab.instance.PLATFORM, Tooltip.instance.PLATFORM);
		LCombo cmbPlatform = new LCombo(grpExecution, true);
		cmbPlatform.getCellData().setSpread(3, 1);
		cmbPlatform.getCellData().setExpand(true, false);
		cmbPlatform.setOptional(false);
		cmbPlatform.setIncludeID(false);
		cmbPlatform.setItems(platforms);
		editor.addControl(cmbPlatform, "platform");
		// TODO: export button

		// FPS

		LLabel lblFPS = new LLabel(grpExecution, Vocab.instance.FPSLIMIT, Tooltip.instance.FPSLIMIT);
		lblFPS.getCellData().setAlignment(LFlags.CENTER);
		
		LSpinner spnFpsMin = new LSpinner(grpExecution);
		spnFpsMin.getCellData().setExpand(true, false);
		spnFpsMin.setMinimum(1);
		spnFpsMin.setMaximum(9999);
		editor.addControl(spnFpsMin, "fpsMin");

		new LLabel(grpExecution, " ~ ");
		
		LSpinner spnFpsMax = new LSpinner(grpExecution);
		spnFpsMax.getCellData().setExpand(true, false);
		spnFpsMax.setMinimum(1);
		spnFpsMax.setMaximum(9999);
		editor.addControl(spnFpsMax, "fpsMax");

		// Screen
		
		LFrame grpScreen = new LFrame(left, Vocab.instance.SCREEN);
		grpScreen.setFillLayout(true);
		grpScreen.setHoverText(Tooltip.instance.SCREEN);
		grpScreen.getCellData().setExpand(true, true);
		grpScreen.getCellData().setMinimumSize(0, -1);
		
		ScreenEditor screenEditor = new ScreenEditor(grpScreen, true);
		editor.addChild(screenEditor, "screen");
		screenEditor.setGridLayout(3);
		
		new LLabel(screenEditor, Vocab.instance.NATIVESIZE, Tooltip.instance.NATIVESIZE);
		
		LPanel nativeSize = new LPanel(screenEditor);
		nativeSize.setGridLayout(3);
		nativeSize.getCellData().setExpand(true, false);
		nativeSize.getCellData().setSpread(2, 1);
		
		LSpinner spnNativeWidth = new LSpinner(nativeSize);
		spnNativeWidth.getCellData().setExpand(true, false);
		spnNativeWidth.setMinimum(1);
		spnNativeWidth.setMaximum(9999);
		screenEditor.addControl(spnNativeWidth, "nativeWidth");

		new LLabel(nativeSize, "x");

		LSpinner spnNativeHeight = new LSpinner(nativeSize);
		spnNativeHeight.getCellData().setExpand(true, false);
		spnNativeHeight.setMinimum(1);
		spnNativeHeight.setMaximum(9999);
		screenEditor.addControl(spnNativeHeight, "nativeHeight");

		new LLabel(screenEditor, Vocab.instance.SCALEFACTOR, Tooltip.instance.SCALEFACTOR);
		
		LPanel scaleFactor = new LPanel(screenEditor);
		scaleFactor.setGridLayout(3);
		scaleFactor.getCellData().setExpand(true, false);
		scaleFactor.getCellData().setSpread(2, 1);
		
		LSpinner spnWidthScale = new LSpinner(scaleFactor);
		spnWidthScale.getCellData().setExpand(true, false);
		spnWidthScale.setMinimum(1);
		spnWidthScale.setMaximum(9999);
		screenEditor.addControl(spnWidthScale, "widthScale");

		new LLabel(scaleFactor, "x");

		LSpinner spnHeightScale = new LSpinner(scaleFactor);
		spnHeightScale.getCellData().setExpand(true, false);
		spnHeightScale.setMinimum(1);
		spnHeightScale.setMaximum(9999);
		screenEditor.addControl(spnHeightScale, "heightScale");

		String[] scaleTypes = new String[] {
			Vocab.instance.NOSCALE,
			Vocab.instance.INTEGERONLY,
			Vocab.instance.KEEPRATIO,
			Vocab.instance.FREESCALE
		};
		
		new LLabel(screenEditor, Vocab.instance.SCALETYPE, Tooltip.instance.SCALETYPE);
		
		LCombo cmbScale = new LCombo(screenEditor, true);
		cmbScale.getCellData().setExpand(true, false);
		cmbScale.getCellData().setSpread(2, 1);
		cmbScale.setOptional(false);
		cmbScale.setIncludeID(false);
		cmbScale.setItems(scaleTypes);
		screenEditor.addControl(cmbScale, "scaleType");
		
		new LLabel(screenEditor, Vocab.instance.MOBILESCALETYPE, Tooltip.instance.MOBILESCALETYPE);
		
		LCombo cmbScaleMobile = new LCombo(screenEditor, true);
		cmbScaleMobile.getCellData().setExpand(true, false);
		cmbScaleMobile.getCellData().setSpread(2, 1);
		cmbScaleMobile.setOptional(false);
		cmbScaleMobile.setIncludeID(false);
		cmbScaleMobile.setItems(scaleTypes);
		screenEditor.addControl(cmbScaleMobile, "mobileScaleType");
		
		LPanel checkScreen = new LPanel(screenEditor);
		checkScreen.setSequentialLayout(true);
		checkScreen.getCellData().setSpread(3, 1);
		
		LCheckBox btnPixelPerfect = new LCheckBox(checkScreen);
		btnPixelPerfect.setText(Vocab.instance.PIXELPERFECT);
		btnPixelPerfect.setHoverText(Tooltip.instance.PIXELPERFECT);
		screenEditor.addControl(btnPixelPerfect, "pixelPerfect");
		
		LCheckBox btnVSync = new LCheckBox(checkScreen);
		btnVSync.setText(Vocab.instance.VSYNC);
		btnVSync.setHoverText(Tooltip.instance.VSYNC);
		screenEditor.addControl(btnVSync, "vsync");
		
		// Player
		
		LFrame grpPlayer = new LFrame(middle, Vocab.instance.PLAYER);
		grpPlayer.setHoverText(Tooltip.instance.PLAYER);
		grpPlayer.setFillLayout(true);
		grpPlayer.getCellData().setExpand(true, true);

		PlayerEditor playerEditor = new PlayerEditor(grpPlayer, true);
		playerEditor.setGridLayout(3);
		editor.addChild(playerEditor, "player");

		new LLabel(playerEditor, Vocab.instance.WALKSPEED, Tooltip.instance.WALKSPEED);
		LSpinner spnWalkSpeed = new LSpinner(playerEditor);
		spnWalkSpeed.getCellData().setExpand(true, false);
		spnWalkSpeed.getCellData().setSpread(2, 1);
		playerEditor.addControl(spnWalkSpeed, "walkSpeed");
		spnWalkSpeed.setMaximum(9999);
		
		new LLabel(playerEditor, Vocab.instance.DASHSPEED, Tooltip.instance.DASHSPEED);
		LSpinner spnDashSpeed = new LSpinner(playerEditor);
		spnDashSpeed.getCellData().setExpand(true, false);
		playerEditor.addControl(spnDashSpeed, "dashSpeed");
		spnDashSpeed.setMaximum(9999);
		
		new LLabel(playerEditor, "%");
		new LLabel(playerEditor, Vocab.instance.DIAGTHRESHOLD, Tooltip.instance.DIAGTHRESHOLD);
		
		LSpinner spnDiagThreshold = new LSpinner(playerEditor);
		spnDiagThreshold.getCellData().setExpand(true, false);
		playerEditor.addControl(spnDiagThreshold, "diagThreshold");
		
		new LLabel(playerEditor, "%");
		new LLabel(playerEditor, Vocab.instance.STARTPOS, Tooltip.instance.STARTPOS);
		
		LText txtPos = new LText(playerEditor, true);
		txtPos.getCellData().setExpand(true, false);
		PositionButton btnStartPos = new PositionButton(playerEditor);
		btnStartPos.setTextWidget(txtPos);
		playerEditor.addControl(btnStartPos, "startPos");
		
		// Grid
		
		LFrame grpGrid = new LFrame(left, Vocab.instance.GRID);
		grpGrid.setFillLayout(true);
		grpGrid.setHoverText(Tooltip.instance.GRID);
		grpGrid.getCellData().setExpand(true, true);
		grpGrid.getCellData().setMinimumSize(0, -1);
		
		GridEditor gridEditor = new GridEditor(grpGrid, true);
		gridEditor.setGridLayout(3);
		editor.addChild(gridEditor, "grid");

		new LLabel(gridEditor, Vocab.instance.TILEWIDTH, Tooltip.instance.TILEWIDTH);
		LSpinner spnTileW = new LSpinner(gridEditor);
		spnTileW.getCellData().setExpand(true, false);
		spnTileW.getCellData().setSpread(2, 1);
		gridEditor.addControl(spnTileW, "tileW");
		
		new LLabel(gridEditor, Vocab.instance.TILEHEIGHT, Tooltip.instance.TILEHEIGHT);
		LSpinner spnTileH = new LSpinner(gridEditor);
		spnTileH.getCellData().setExpand(true, false);
		spnTileH.getCellData().setSpread(2, 1);
		gridEditor.addControl(spnTileH, "tileH");
		
		new LLabel(gridEditor, Vocab.instance.TILEBASE, Tooltip.instance.TILEBASE);
		LSpinner spnTileB = new LSpinner(gridEditor);
		spnTileB.getCellData().setExpand(true, false);
		spnTileB.getCellData().setSpread(2, 1);
		gridEditor.addControl(spnTileB, "tileB");
		
		new LLabel(gridEditor, Vocab.instance.TILESIDE, Tooltip.instance.TILESIDE);
		LSpinner spnTileS = new LSpinner(gridEditor);
		spnTileS.getCellData().setExpand(true, false);
		spnTileS.getCellData().setSpread(2, 1);
		gridEditor.addControl(spnTileS, "tileS");
		
		new LLabel(gridEditor, Vocab.instance.PIXELHEIGHT, Tooltip.instance.PIXELHEIGHT);
		LSpinner spnPixelsPerHeight = new LSpinner(gridEditor);
		spnPixelsPerHeight.getCellData().setExpand(true, false);
		spnPixelsPerHeight.getCellData().setSpread(2, 1);
		gridEditor.addControl(spnPixelsPerHeight, "pixelsPerHeight");
		
		new LLabel(gridEditor, Vocab.instance.DEPTHHEIGHT, Tooltip.instance.DEPTHHEIGHT);
		LSpinner spnDepthPerHeight = new LSpinner(gridEditor);
		spnDepthPerHeight.getCellData().setExpand(true, false);
		spnDepthPerHeight.getCellData().setSpread(2, 1);
		gridEditor.addControl(spnDepthPerHeight, "depthPerHeight");
		
		new LLabel(gridEditor, Vocab.instance.DEPTHY, Tooltip.instance.DEPTHY);
		LSpinner spnDepthPerY = new LSpinner(gridEditor);
		spnDepthPerY.getCellData().setExpand(true, false);
		spnDepthPerY.getCellData().setSpread(2, 1);
		gridEditor.addControl(spnDepthPerY, "depthPerY");
		
		LPanel gridOptions = new LPanel(gridEditor);
		gridOptions.setSequentialLayout(true);
		gridOptions.getCellData().setAlignment(LFlags.CENTER);
		gridOptions.getCellData().setExpand(true, false);
		gridOptions.getCellData().setSpread(3, 1);
		
		LCheckBox btnAllNeighbors = new LCheckBox(gridOptions);
		btnAllNeighbors.setText(Vocab.instance.ALLNEIGHBORS);
		btnAllNeighbors.setHoverText(Tooltip.instance.ALLNEIGHBORS);
		gridEditor.addControl(btnAllNeighbors, "allNeighbors");

		LCheckBox btnOverpassAllies = new LCheckBox(gridOptions);
		btnOverpassAllies.setText(Vocab.instance.OVERPASSALLIES);
		btnOverpassAllies.setHoverText(Tooltip.instance.OVERPASSALLIES);
		gridEditor.addControl(btnOverpassAllies, "overpassAllies");

		LCheckBox btnOverpassDeads = new LCheckBox(gridOptions);
		btnOverpassDeads.setText(Vocab.instance.OVERPASSDEADS);
		btnOverpassDeads.setHoverText(Tooltip.instance.OVERPASSDEADS);
		gridEditor.addControl(btnOverpassDeads, "overpassDeads");

		// Battle
		
		LFrame grpBattle = new LFrame(middle, Vocab.instance.BATTLE);		
		grpBattle.setFillLayout(true);
		grpBattle.setHoverText(Tooltip.instance.BATTLE);
		grpBattle.getCellData().setExpand(true, true);
		grpBattle.getCellData().setMinimumSize(0, -1);
		
		BattleEditor battleEditor = new BattleEditor(grpBattle, true);
		editor.addChild(battleEditor, "battle");
		battleEditor.setGridLayout(3);
		
		new LLabel(battleEditor, Vocab.instance.FINALLEVEL, Tooltip.instance.FINALLEVEL);
		
		LSpinner spnMaxLevel = new LSpinner(battleEditor);
		spnMaxLevel.getCellData().setExpand(true, false);
		spnMaxLevel.getCellData().setSpread(2, 1);;
		battleEditor.addControl(spnMaxLevel, "maxLevel");
		spnMaxLevel.setMaximum(9999);
		
		new LLabel(battleEditor, Vocab.instance.ATTHP, Tooltip.instance.ATTHP);
		
		LText txtAttHP = new LText(battleEditor);
		txtAttHP.getCellData().setExpand(true, false);
		txtAttHP.getCellData().setSpread(2, 1);
		battleEditor.addControl(txtAttHP, "attHP");
		
		new LLabel(battleEditor, Vocab.instance.ATTSP, Tooltip.instance.ATTSP);
		
		LText txtAttSP = new LText(battleEditor);
		txtAttSP.getCellData().setExpand(true, false);
		txtAttSP.getCellData().setSpread(2, 1);
		battleEditor.addControl(txtAttSP, "attSP");
		
		new LLabel(battleEditor, Vocab.instance.ATTSTEP, Tooltip.instance.ATTSTEP);
		
		LText txtAttStep = new LText(battleEditor);
		txtAttStep.getCellData().setExpand(true, false);
		txtAttStep.getCellData().setSpread(2, 1);
		battleEditor.addControl(txtAttStep, "attStep");
		
		new LLabel(battleEditor, Vocab.instance.ATTJUMP, Tooltip.instance.ATTJUMP);
		
		LText txtAttJump = new LText(battleEditor);
		txtAttJump.getCellData().setExpand(true, false);
		txtAttJump.getCellData().setSpread(2, 1);
		battleEditor.addControl(txtAttJump, "attJump");
		
		new LLabel(battleEditor, Vocab.instance.CHARSPEED, Tooltip.instance.CHARSPEED);
		
		LSpinner spnCharSpeed = new LSpinner(battleEditor);
		spnCharSpeed.getCellData().setExpand(true, false);
		spnCharSpeed.setMaximum(9999);
		battleEditor.addControl(spnCharSpeed, "charSpeed");

		new LLabel(battleEditor, "%");
		
		LPanel checkBattle = new LPanel(battleEditor);
		checkBattle.setSequentialLayout(true);
		checkBattle.getCellData().setExpand(true, false);
		checkBattle.getCellData().setSpread(2, 1);

		LCheckBox btnRevive = new LCheckBox(checkBattle);
		btnRevive.setText(Vocab.instance.BATTLEENDREVIVE);
		battleEditor.addControl(btnRevive, "battleEndRevive");

		LCheckBox btnKeepParties = new LCheckBox(checkBattle);
		btnKeepParties.setText(Vocab.instance.KEEPPARTIES);
		battleEditor.addControl(btnKeepParties, "keepParties");
		
		// Troop
		
		LFrame grpTroop = new LFrame(middle, Vocab.instance.TROOP);
		grpTroop.setFillLayout(true);
		grpTroop.setHoverText(Tooltip.instance.TROOP);
		grpTroop.getCellData().setExpand(true, true);
		grpTroop.getCellData().setMinimumSize(0, -1);
		
		TroopEditor troopEditor = new TroopEditor(grpTroop, true);
		troopEditor.setGridLayout(3);
		editor.addChild(troopEditor, "troop");

		new LLabel(troopEditor, Vocab.instance.INITIALTROOP, Tooltip.instance.INITIALTROOP);
		LText txtInitialTroop = new LText(troopEditor, true);
		txtInitialTroop.getCellData().setExpand(true, false);
		btnInitialTroop = new IDButton(troopEditor, Vocab.instance.TROOPSHELL, false);
		btnInitialTroop.setNameWidget(txtInitialTroop);
		troopEditor.addControl(btnInitialTroop, "initialTroopID");		
		
		new LLabel(troopEditor, Vocab.instance.MAXMEMBERS, Tooltip.instance.MAXMEMBERS);
		LSpinner spnMaxMembers = new LSpinner(troopEditor);
		spnMaxMembers.getCellData().setExpand(true, false);
		spnMaxMembers.getCellData().setSpread(2, 1);
		troopEditor.addControl(spnMaxMembers, "maxMembers");
		
		new LLabel(troopEditor, Vocab.instance.TROOPSIZE, Tooltip.instance.TROOPSIZE);
		LPanel troopSize = new LPanel(troopEditor);
		troopSize.setGridLayout(3);
		troopSize.getCellData().setExpand(true, false);
		troopSize.getCellData().setSpread(2, 1);
		
		LSpinner spnWidth = new LSpinner(troopSize);
		spnWidth.getCellData().setExpand(true, false);
		troopEditor.addControl(spnWidth, "width");

		new LLabel(troopSize, "x");

		LSpinner spnHeight = new LSpinner(troopSize);
		spnHeight.getCellData().setExpand(true, false);
		troopEditor.addControl(spnHeight, "height");

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
		btnInitialTroop.dataTree = Project.current.troops.getTree();
		super.onVisible();
		editor.setObject(Project.current.config.getData());
	}
	
	private static class MainEditor extends GDefaultObjectEditor<Config> {
		public MainEditor(LContainer parent, boolean doubleBuffered) {
			super(parent, doubleBuffered);
		}
		@Override
		public Type getType() {
			return Config.class;
		}
	}
	
	private static class GridEditor extends GDefaultObjectEditor<Config.Grid> {
		public GridEditor(LContainer parent, boolean doubleBuffered) {
			super(parent, doubleBuffered);
		}
		@Override
		public Type getType() {
			return Config.Grid.class;
		}
	}
	
	private static class ScreenEditor extends GDefaultObjectEditor<Config.Screen> {
		public ScreenEditor(LContainer parent, boolean doubleBuffered) {
			super(parent, doubleBuffered);
		}
		@Override
		public Type getType() {
			return Config.Screen.class;
		}
	}
	
	private static class PlayerEditor extends GDefaultObjectEditor<Config.Player> {
		public PlayerEditor(LContainer parent, boolean doubleBuffered) {
			super(parent, doubleBuffered);
		}
		@Override
		public Type getType() {
			return Config.Player.class;
		}
	}
	
	private static class BattleEditor extends GDefaultObjectEditor<Config.Battle> {
		public BattleEditor(LContainer parent, boolean doubleBuffered) {
			super(parent, doubleBuffered);
		}
		@Override
		public Type getType() {
			return Config.Battle.class;
		}
	}
	
	private static class TroopEditor extends GDefaultObjectEditor<Config.Troop> {
		public TroopEditor(LContainer parent, boolean doubleBuffered) {
			super(parent, doubleBuffered);
		}
		@Override
		public Type getType() {
			return Config.Troop.class;
		}
	}
	
}
