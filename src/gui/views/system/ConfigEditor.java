package gui.views.system;

import gui.Vocab;
import gui.views.database.subcontent.NodeList;
import gui.views.database.subcontent.PortraitList;
import gui.widgets.IDButton;
import gui.widgets.PositionButton;
import lwt.LFlags;
import lwt.action.LActionStack;
import lwt.container.LContainer;
import lwt.container.LFrame;
import lwt.container.LPanel;
import lwt.container.LView;
import lwt.dataestructure.LDataTree;
import lwt.widget.LCheckBox;
import lwt.widget.LCombo;
import lwt.widget.LLabel;
import lwt.widget.LSpinner;
import lwt.widget.LText;

import data.config.Config;
import gson.editor.GDefaultObjectEditor;
import project.Project;

public class ConfigEditor extends LView {

	private GDefaultObjectEditor<Config> editor;
	
	/**
	 * @wbp.parser.constructor
	 * @wbp.eval.method.parameter parent new lwt.dialog.LShell()
	 */
	public ConfigEditor(LContainer parent) {
		super(parent, 4, false, true);

		actionStack = new LActionStack(this);

		editor = new GDefaultObjectEditor<Config>(this, false);
		editor.setAlignment(LFlags.CENTER);
		editor.setSpread(4, 1);
		editor.setExpand(true, false);
		editor.setGridLayout(2, true);
		addChild(editor);
		
		LPanel left = new LPanel(this, 1);
		left.setExpand(true, true);
		left.setMinimumWidth(200);
		
		LPanel middle = new LPanel(this, 1);
		middle.setExpand(true, true);
		middle.setMinimumWidth(180);
		
		LPanel right = new LPanel(this, false);
		right.setExpand(true, true);
		
		// Name
		
		LFrame grpIdentity = new LFrame(editor, Vocab.instance.IDENTITY, 6, false);
		grpIdentity.setExpand(true, false);
		
		new LLabel(grpIdentity, Vocab.instance.PROJECTNAME);
		LText txtName = new LText(grpIdentity, 5);
		editor.addControl(txtName, "name");
		
		// Cover
		
		new LLabel(grpIdentity, Vocab.instance.COVER);
		LText txtCover = new LText(grpIdentity, true);		
		IDButton btnCover = new IDButton(grpIdentity, true) {
			@Override
			public LDataTree<Object> getDataTree() {
				return Project.current.animations.getTree();
			}
		};
		btnCover.setNameWidget(txtCover);
		editor.addControl(btnCover, "coverID");
		
		// Logo
		
		new LLabel(grpIdentity, Vocab.instance.LOGO);
		LText txtLogo = new LText(grpIdentity, true);
		IDButton btnLogo = new IDButton(grpIdentity, true) {
			@Override
			public LDataTree<Object> getDataTree() {
				return Project.current.animations.getTree();
			}
		};
		btnLogo.setNameWidget(txtLogo);
		editor.addControl(btnLogo, "logoID");
		
		// Platform
		
		LFrame grpPlatform = new LFrame(editor, Vocab.instance.EXECUTION, 4, false);
		grpPlatform.setExpand(true, false);
		
		String[] platforms = new String[] {
				Vocab.instance.DESKTOP,
				Vocab.instance.MOBILE,
				Vocab.instance.BROWSER,
				Vocab.instance.MOBILEBROWSER
			};
		new LLabel(grpPlatform, Vocab.instance.PLATFORM);
		LCombo cmbPlatform = new LCombo(grpPlatform, 3, true);
		cmbPlatform.setOptional(false);
		cmbPlatform.setIncludeID(false);
		cmbPlatform.setItems(platforms);
		editor.addControl(cmbPlatform, "platform");
		// TODO: export button

		// FPS

		LLabel lblFPS = new LLabel(grpPlatform, Vocab.instance.FPSLIMIT);
		lblFPS.setAlignment(LFlags.CENTER);
		
		LSpinner spnFpsMin = new LSpinner(grpPlatform);
		editor.addControl(spnFpsMin, "fpsMin");
		spnFpsMin.setMinimum(1);
		spnFpsMin.setMaximum(9999);
		
		new LLabel(grpPlatform, " ~ ");
		
		LSpinner spnFpsMax = new LSpinner(grpPlatform);
		editor.addControl(spnFpsMax, "fpsMax");
		spnFpsMax.setMinimum(1);
		spnFpsMax.setMaximum(9999);
		
		// Screen
		
		LFrame grpScreen = new LFrame(left, Vocab.instance.SCREEN, true, true);
		grpScreen.setExpand(true, true);
		
		GDefaultObjectEditor<Config.Screen> screenEditor = new GDefaultObjectEditor<Config.Screen>(grpScreen, true);
		editor.addChild(screenEditor, "screen");
		screenEditor.setGridLayout(3, false);
		
		new LLabel(screenEditor, Vocab.instance.NATIVESIZE);
		
		LPanel nativeSize = new LPanel(screenEditor, 3, false);
		nativeSize.setAlignment(LFlags.CENTER);
		nativeSize.setSpread(2, 1);
		
		LSpinner spnNativeWidth = new LSpinner(nativeSize);
		screenEditor.addControl(spnNativeWidth, "nativeWidth");
		spnNativeWidth.setMinimum(1);
		spnNativeWidth.setMaximum(9999);
		
		new LLabel(nativeSize, "x");
		
		LSpinner spnNativeHeight = new LSpinner(nativeSize);
		screenEditor.addControl(spnNativeHeight, "nativeHeight");
		spnNativeHeight.setMinimum(1);
		spnNativeHeight.setMaximum(9999);
		
		new LLabel(screenEditor, Vocab.instance.SCALEFACTOR);
		
		LPanel scaleFactor = new LPanel(screenEditor, 3, false);
		scaleFactor.setAlignment(LFlags.CENTER);
		scaleFactor.setSpread(2, 1);
		
		LSpinner spnWidthScale = new LSpinner(scaleFactor);
		screenEditor.addControl(spnWidthScale, "widthScale");
		spnWidthScale.setMinimum(1);
		spnWidthScale.setMaximum(9999);
		
		new LLabel(scaleFactor, "x");
		
		LSpinner spnHeightScale = new LSpinner(scaleFactor);
		screenEditor.addControl(spnHeightScale, "heightScale");
		spnHeightScale.setMinimum(1);
		spnHeightScale.setMaximum(9999);
		
		String[] scaleTypes = new String[] {
			Vocab.instance.NOSCALE,
			Vocab.instance.INTEGERONLY,
			Vocab.instance.KEEPRATIO,
			Vocab.instance.FREESCALE
		};
		
		new LLabel(screenEditor, Vocab.instance.SCALETYPE);
		
		LCombo cmbScale = new LCombo(screenEditor, 2, true);
		cmbScale.setOptional(false);
		cmbScale.setIncludeID(false);
		cmbScale.setItems(scaleTypes);
		screenEditor.addControl(cmbScale, "scaleType");
		
		new LLabel(screenEditor, Vocab.instance.MOBILESCALETYPE);
		
		LCombo cmbScaleMobile = new LCombo(screenEditor, 2, true);
		cmbScaleMobile.setOptional(false);
		cmbScaleMobile.setIncludeID(false);
		cmbScaleMobile.setItems(scaleTypes);
		screenEditor.addControl(cmbScaleMobile, "mobileScaleType");
		
		LPanel checkScreen = new LPanel(screenEditor, true, false);
		checkScreen.setSpread(3, 1);
		
		LCheckBox btnPixelPerfect = new LCheckBox(checkScreen);
		btnPixelPerfect.setText(Vocab.instance.PIXELPERFECT);
		screenEditor.addControl(btnPixelPerfect, "pixelPerfect");
		
		LCheckBox btnVSync = new LCheckBox(checkScreen);
		btnVSync.setText(Vocab.instance.VSYNC);
		screenEditor.addControl(btnVSync, "vsync");
		
		// Player
		
		LFrame grpPlayer = new LFrame(middle, Vocab.instance.PLAYER, true, true);
		grpPlayer.setExpand(true, true);
		
		GDefaultObjectEditor<Config.Player> playerEditor = new GDefaultObjectEditor<Config.Player>(grpPlayer, true);
		editor.addChild(playerEditor, "player");
		playerEditor.setGridLayout(3, false);
		
		new LLabel(playerEditor, Vocab.instance.WALKSPEED);
		
		LSpinner spnWalkSpeed = new LSpinner(playerEditor, 2);
		playerEditor.addControl(spnWalkSpeed, "walkSpeed");
		spnWalkSpeed.setMaximum(9999);
		
		new LLabel(playerEditor, Vocab.instance.DASHSPEED);
		
		LSpinner spnDashSpeed = new LSpinner(playerEditor);
		playerEditor.addControl(spnDashSpeed, "dashSpeed");
		spnDashSpeed.setMaximum(9999);
		
		new LLabel(playerEditor, "%");
		new LLabel(playerEditor, Vocab.instance.DIAGTHRESHOLD);
		
		LSpinner spnDiagThreshold = new LSpinner(playerEditor);
		playerEditor.addControl(spnDiagThreshold, "diagThreshold");
		
		new LLabel(playerEditor, "%");
		new LLabel(playerEditor, Vocab.instance.STARTPOS);
		
		LText txtPos = new LText(playerEditor, true);
		PositionButton btnStartPos = new PositionButton(playerEditor);
		btnStartPos.setTextWidget(txtPos);
		playerEditor.addControl(btnStartPos, "startPos");
		
		// Grid
		
		LFrame grpGrid = new LFrame(left, Vocab.instance.GRID, true, true);
		grpGrid.setExpand(true, true);
		
		GDefaultObjectEditor<Config.Grid> gridEditor = new GDefaultObjectEditor<Config.Grid>(grpGrid, true);
		editor.addChild(gridEditor, "grid");
		gridEditor.setGridLayout(3, false);
		
		new LLabel(gridEditor, Vocab.instance.TILEWIDTH);
		LSpinner spnTileW = new LSpinner(gridEditor, 2);
		gridEditor.addControl(spnTileW, "tileW");
		
		new LLabel(gridEditor, Vocab.instance.TILEHEIGHT);
		LSpinner spnTileH = new LSpinner(gridEditor, 2);
		gridEditor.addControl(spnTileH, "tileH");
		
		new LLabel(gridEditor, Vocab.instance.TILEBASE);
		LSpinner spnTileB = new LSpinner(gridEditor, 2);
		gridEditor.addControl(spnTileB, "tileB");
		
		new LLabel(gridEditor, Vocab.instance.TILESIDE);
		LSpinner spnTileS = new LSpinner(gridEditor, 2);
		gridEditor.addControl(spnTileS, "tileS");
		
		new LLabel(gridEditor, Vocab.instance.PIXELHEIGHT);
		LSpinner spnPixelsPerHeight = new LSpinner(gridEditor, 2);
		gridEditor.addControl(spnPixelsPerHeight, "pixelsPerHeight");
		
		new LLabel(gridEditor, Vocab.instance.DEPTHHEIGHT);
		LSpinner spnDepthPerHeight = new LSpinner(gridEditor, 2);
		gridEditor.addControl(spnDepthPerHeight, "depthPerHeight");
		
		new LLabel(gridEditor, Vocab.instance.DEPTHY);
		LSpinner spnDepthPerY = new LSpinner(gridEditor, 2);
		gridEditor.addControl(spnDepthPerY, "depthPerY");
		
		LPanel gridOptions = new LPanel(gridEditor, 2, false);
		gridOptions.setAlignment(LFlags.CENTER);
		gridOptions.setExpand(true, false);
		gridOptions.setSpread(3, 1);
		
		LCheckBox btnAllNeighbors = new LCheckBox(gridOptions);
		btnAllNeighbors.setText(Vocab.instance.ALLNEIGHBORS);
		gridEditor.addControl(btnAllNeighbors, "allNeighbors");

		LCheckBox btnOverpassAllies = new LCheckBox(gridOptions);
		btnOverpassAllies.setText(Vocab.instance.OVERPASSALLIES);
		gridEditor.addControl(btnOverpassAllies, "overpassAllies");

		LCheckBox btnOverpassDeads = new LCheckBox(gridOptions);
		btnOverpassDeads.setText(Vocab.instance.OVERPASSDEADS);
		gridEditor.addControl(btnOverpassDeads, "overpassDeads");
		
		// Battle
		
		LFrame grpBattle = new LFrame(middle, Vocab.instance.BATTLE, true, true);
		grpBattle.setExpand(true, true);
		
		GDefaultObjectEditor<Config.Battle> battleEditor = new GDefaultObjectEditor<Config.Battle>(grpBattle, true);
		editor.addChild(battleEditor, "battle");
		battleEditor.setGridLayout(3, false);
		
		new LLabel(battleEditor, Vocab.instance.MAXLEVEL);
		
		LSpinner spnMaxLevel = new LSpinner(battleEditor, 2);
		battleEditor.addControl(spnMaxLevel, "maxLevel");
		spnMaxLevel.setMaximum(9999);
		
		new LLabel(battleEditor, Vocab.instance.ATTHP);
		
		LText txtAttHP = new LText(battleEditor, 2);
		battleEditor.addControl(txtAttHP, "attHP");
		
		new LLabel(battleEditor, Vocab.instance.ATTSP);
		
		LText txtAttSP = new LText(battleEditor, 2);
		battleEditor.addControl(txtAttSP, "attSP");
		
		new LLabel(battleEditor, Vocab.instance.ATTSTEP);
		
		LText txtAttStep = new LText(battleEditor, 2);
		battleEditor.addControl(txtAttStep, "attStep");
		
		new LLabel(battleEditor, Vocab.instance.ATTJUMP);
		
		LText txtAttJump = new LText(battleEditor, 2);
		battleEditor.addControl(txtAttJump, "attJump");
		
		new LLabel(battleEditor, Vocab.instance.CHARSPEED);
		
		LSpinner spnCharSpeed = new LSpinner(battleEditor);
		battleEditor.addControl(spnCharSpeed, "charSpeed");
		spnCharSpeed.setMaximum(9999);
		
		new LLabel(battleEditor, "%");
		
		LPanel checkBattle = new LPanel(battleEditor, true, false);
		checkBattle.setExpand(true, false);
		checkBattle.setSpread(2, 1);

		LCheckBox btnRevive = new LCheckBox(checkBattle);
		btnRevive.setText(Vocab.instance.BATTLEENDREVIVE);
		battleEditor.addControl(btnRevive, "battleEndRevive");

		LCheckBox btnKeepParties = new LCheckBox(checkBattle);
		btnKeepParties.setText(Vocab.instance.KEEPPARTIES);
		battleEditor.addControl(btnKeepParties, "keepParties");	
		
		// Troop
		
		LFrame grpTroop = new LFrame(middle, Vocab.instance.TROOP, true, true);
		grpTroop.setExpand(true, true);
		
		GDefaultObjectEditor<Config.Troop> troopEditor = new GDefaultObjectEditor<Config.Troop>(grpTroop, true);
		editor.addChild(troopEditor, "troop");
		troopEditor.setGridLayout(3, false);
		
		new LLabel(troopEditor, Vocab.instance.INITIALTROOP);
		
		LText txtInitialTroop = new LText(troopEditor, true);
		IDButton btnInitialTroop = new IDButton(troopEditor, false) {
			@Override
			public LDataTree<Object> getDataTree() {
				return Project.current.troops.getTree();
			}
		};
		btnInitialTroop.setNameWidget(txtInitialTroop);
		troopEditor.addControl(btnInitialTroop, "initialTroopID");		
		
		new LLabel(troopEditor, Vocab.instance.MAXMEMBERS);
		
		LSpinner spnMaxMembers = new LSpinner(troopEditor, 2);
		troopEditor.addControl(spnMaxMembers, "maxMembers");
		
		new LLabel(troopEditor, Vocab.instance.TROOPSIZE);
		
		LPanel troopSize = new LPanel(troopEditor, 3, false);
		troopSize.setAlignment(LFlags.CENTER);
		troopSize.setSpread(2, 1);
		
		LSpinner spnWidth = new LSpinner(troopSize);
		troopEditor.addControl(spnWidth, "width");
		
		new LLabel(troopSize, "x");
		
		LSpinner spnHeight = new LSpinner(troopSize);
		troopEditor.addControl(spnHeight, "height");
		
		// Animations
		
		LFrame grpAnimations = new LFrame(right, Vocab.instance.ANIMATIONS, true, true);
		NodeList lstAnimations = new NodeList(grpAnimations) {
			@Override
			protected LDataTree<Object> getDataTree() {
				return Project.current.animations.getTree();
			}
		};
		lstAnimations.setIncludeID(false);
		editor.addChild(lstAnimations, "animations");
		
		// Icons
		
		LFrame grpIcons = new LFrame(right, Vocab.instance.ICONS, true, true);
		PortraitList lstIcons = new PortraitList(grpIcons);
		editor.addChild(lstIcons, "icons");
		
		// Sounds
		
		LFrame grpSounds = new LFrame(this, Vocab.instance.SOUNDS, true, true);
		grpSounds.setExpand(true, true);
		SoundList lstSounds = new SoundList(grpSounds);
		editor.addChild(lstSounds, "sounds");
		
	}
	
	public void onVisible() {
		super.onVisible();
		editor.setObject(Project.current.config.getData());
	}
	
}
