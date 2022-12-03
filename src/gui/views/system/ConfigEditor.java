package gui.views.system;

import gui.Vocab;
import gui.views.database.subcontent.NodeList;
import gui.views.database.subcontent.PortraitList;
import gui.widgets.IDButton;
import gui.widgets.PositionButton;
import lwt.action.LActionStack;
import lwt.dataestructure.LDataTree;
import lwt.editor.LObjectEditor;
import lwt.editor.LView;
import lwt.widget.LCheckBox;
import lwt.widget.LCombo;
import lwt.widget.LLabel;
import lwt.widget.LSpinner;
import lwt.widget.LText;

import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Group;

import project.Project;

public class ConfigEditor extends LView {

	private LObjectEditor editor;
	
	/**
	 * Create the composite.
	 * @param parent
	 */
	public ConfigEditor(Composite parent) {
		super(parent);

		actionStack = new LActionStack(this);
		
		setLayout(new GridLayout(4, false));
		
		editor = new LObjectEditor(this, SWT.NONE);
		editor.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 4, 1));
		editor.setLayout(new GridLayout(4, false));
		addChild(editor);
		
		Composite left = new Composite(this, SWT.NONE);
		left.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true, 1, 1));
		GridLayout gl_left = new GridLayout(1, false);
		gl_left.verticalSpacing = 0;
		gl_left.marginWidth = 0;
		gl_left.marginHeight = 0;
		left.setLayout(gl_left);
		
		Composite middle = new Composite(this, SWT.NONE);
		middle.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true, 1, 1));
		GridLayout gl_middle = new GridLayout(1, false);
		gl_middle.verticalSpacing = 0;
		gl_middle.marginHeight = 0;
		gl_middle.marginWidth = 0;
		middle.setLayout(gl_middle);
		
		// Name
		
		new LLabel(editor, Vocab.instance.PROJECTNAME);
		
		LText txtName = new LText(editor);
		GridData gd_txtName = new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1);
		gd_txtName.widthHint = 240;
		txtName.setLayoutData(gd_txtName);
		editor.addControl(txtName, "name");
		
		// Platform
		
		String[] platforms = new String[] {
				Vocab.instance.DESKTOP,
				Vocab.instance.MOBILE,
				Vocab.instance.BROWSER,
				Vocab.instance.MOBILEBROWSER
			};
		
		new LLabel(editor, Vocab.instance.PLATFORM);
		
		LCombo cmbPlatform = new LCombo(editor, SWT.NONE);
		cmbPlatform.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1));
		cmbPlatform.setOptional(false);
		cmbPlatform.setIncludeID(false);
		cmbPlatform.setItems(platforms);
		editor.addControl(cmbPlatform, "platform");
		// TODO: export button
		
		// Cover / Logo
		
		Composite cover = new Composite(editor, SWT.NONE);
		cover.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 2, 1));
		GridLayout gl_cover = new GridLayout(6, false);
		gl_cover.marginWidth = 0;
		gl_cover.marginHeight = 0;
		cover.setLayout(gl_cover);
		
		new LLabel(cover, Vocab.instance.COVER);
		
		LText txtCover = new LText(cover, true);
		txtCover.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1));
		
		IDButton btnCover = new IDButton(cover, 1) {
			@Override
			public LDataTree<Object> getDataTree() {
				return Project.current.animations.getTree();
			}
		};
		btnCover.setNameWidget(txtCover);
		editor.addControl(btnCover, "coverID");
		
		new LLabel(cover, Vocab.instance.LOGO);
		
		LText txtLogo = new LText(cover, true);
		txtLogo.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1));
		
		IDButton btnLogo = new IDButton(cover, 1) {
			@Override
			public LDataTree<Object> getDataTree() {
				return Project.current.animations.getTree();
			}
		};
		btnLogo.setNameWidget(txtLogo);
		editor.addControl(btnLogo, "logoID");

		// FPS

		Composite fpsLimits = new Composite(editor, SWT.NONE);
		fpsLimits.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 2, 1));
		GridLayout gl_fpsLimits = new GridLayout(4, false);
		gl_fpsLimits.marginWidth = 0;
		gl_fpsLimits.marginHeight = 0;
		fpsLimits.setLayout(gl_fpsLimits);
		
		LLabel lblFPS = new LLabel(fpsLimits, Vocab.instance.FPSLIMIT);
		lblFPS.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, false, false, 1, 1));
	
		
		LSpinner spnFpsMin = new LSpinner(fpsLimits, SWT.NONE);
		spnFpsMin.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1));
		editor.addControl(spnFpsMin, "fpsMin");
		spnFpsMin.setMinimum(1);
		spnFpsMin.setMaximum(9999);
		
		new LLabel(fpsLimits, " ~ ");
		
		LSpinner spnFpsMax = new LSpinner(fpsLimits, SWT.NONE);
		spnFpsMax.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1));
		editor.addControl(spnFpsMax, "fpsMax");
		spnFpsMax.setMinimum(1);
		spnFpsMax.setMaximum(9999);
		
		// Screen
		
		Group grpScreen = new Group(left, SWT.NONE);
		grpScreen.setLayout(new FillLayout(SWT.HORIZONTAL));
		grpScreen.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true, 1, 1));
		grpScreen.setText(Vocab.instance.SCREEN);
		
		LObjectEditor screenEditor = new LObjectEditor(grpScreen, SWT.NONE);
		editor.addChild(screenEditor, "screen");
		screenEditor.setLayout(new GridLayout(3, false));
		
		new LLabel(screenEditor, Vocab.instance.NATIVESIZE);
		
		Composite nativeSize = new Composite(screenEditor, SWT.NONE);
		GridLayout gl_nativeSize = new GridLayout(3, false);
		gl_nativeSize.marginHeight = 0;
		gl_nativeSize.marginWidth = 0;
		nativeSize.setLayout(gl_nativeSize);
		nativeSize.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, false, false, 2, 1));
		
		LSpinner spnNativeWidth = new LSpinner(nativeSize, SWT.NONE);
		spnNativeWidth.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1));
		screenEditor.addControl(spnNativeWidth, "nativeWidth");
		spnNativeWidth.setMinimum(1);
		spnNativeWidth.setMaximum(9999);
		
		new LLabel(nativeSize, "x");
		
		LSpinner spnNativeHeight = new LSpinner(nativeSize, SWT.NONE);
		spnNativeHeight.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1));
		screenEditor.addControl(spnNativeHeight, "nativeHeight");
		spnNativeHeight.setMinimum(1);
		spnNativeHeight.setMaximum(9999);
		
		new LLabel(screenEditor, Vocab.instance.SCALEFACTOR);
		
		Composite scaleFactor = new Composite(screenEditor, SWT.NONE);
		GridLayout gl_scaleFactor = new GridLayout(3, false);
		gl_scaleFactor.marginHeight = 0;
		gl_scaleFactor.marginWidth = 0;
		scaleFactor.setLayout(gl_scaleFactor);
		scaleFactor.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, false, false, 2, 1));
		
		LSpinner spnWidthScale = new LSpinner(scaleFactor, SWT.NONE);
		spnWidthScale.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1));
		screenEditor.addControl(spnWidthScale, "widthScale");
		spnWidthScale.setMinimum(1);
		spnWidthScale.setMaximum(9999);
		
		new LLabel(scaleFactor, "x");
		
		LSpinner spnHeightScale = new LSpinner(scaleFactor, SWT.NONE);
		spnHeightScale.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1));
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
		
		LCombo cmbScale = new LCombo(screenEditor, SWT.NONE);
		cmbScale.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 2, 1));
		cmbScale.setOptional(false);
		cmbScale.setIncludeID(false);
		cmbScale.setItems(scaleTypes);
		screenEditor.addControl(cmbScale, "scaleType");
		
		new LLabel(screenEditor, Vocab.instance.MOBILESCALETYPE);
		
		LCombo cmbScaleMobile = new LCombo(screenEditor, SWT.NONE);
		cmbScaleMobile.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 2, 1));
		cmbScaleMobile.setOptional(false);
		cmbScaleMobile.setIncludeID(false);
		cmbScaleMobile.setItems(scaleTypes);
		screenEditor.addControl(cmbScaleMobile, "mobileScaleType");
		
		LCheckBox btnPixelPerfect = new LCheckBox(screenEditor, SWT.NONE);
		btnPixelPerfect.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 3, 1));
		btnPixelPerfect.setText(Vocab.instance.PIXELPERFECT);
		screenEditor.addControl(btnPixelPerfect, "pixelPerfect");
		
		// Player
		
		Group grpPlayer = new Group(middle, SWT.NONE);
		grpPlayer.setLayout(new FillLayout(SWT.HORIZONTAL));
		grpPlayer.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true, 1, 1));
		grpPlayer.setText(Vocab.instance.PLAYER);
		
		LObjectEditor playerEditor = new LObjectEditor(grpPlayer, SWT.NONE);
		editor.addChild(playerEditor, "player");
		playerEditor.setLayout(new GridLayout(3, false));
		
		new LLabel(playerEditor, Vocab.instance.WALKSPEED);
		
		LSpinner spnWalkSpeed = new LSpinner(playerEditor, SWT.NONE);
		spnWalkSpeed.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 2, 1));
		playerEditor.addControl(spnWalkSpeed, "walkSpeed");
		spnWalkSpeed.setMaximum(9999);
		
		new LLabel(playerEditor, Vocab.instance.DASHSPEED);
		
		LSpinner spnDashSpeed = new LSpinner(playerEditor, SWT.NONE);
		spnDashSpeed.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 2, 1));
		playerEditor.addControl(spnDashSpeed, "dashSpeed");
		spnDashSpeed.setMaximum(9999);
		
		new LLabel(playerEditor, Vocab.instance.STARTPOS);
		
		LText txtPos = new LText(playerEditor, true);
		txtPos.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1));
		
		PositionButton btnStartPos = new PositionButton(playerEditor);
		btnStartPos.setLayoutData(new GridData(SWT.RIGHT, SWT.CENTER, false, false, 1, 1));
		btnStartPos.setTextWidget(txtPos);
		playerEditor.addControl(btnStartPos, "startPos");
		
		// Grid
		
		Group grpGrid = new Group(left, SWT.NONE);
		grpGrid.setLayout(new FillLayout(SWT.HORIZONTAL));
		grpGrid.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true, 1, 1));
		grpGrid.setText(Vocab.instance.GRID);
		
		LObjectEditor gridEditor = new LObjectEditor(grpGrid, SWT.NONE);
		editor.addChild(gridEditor, "grid");
		gridEditor.setLayout(new GridLayout(2, false));
		
		new LLabel(gridEditor, Vocab.instance.TILEWIDTH);
		
		LSpinner spnTileW = new LSpinner(gridEditor, SWT.NONE);
		spnTileW.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1));
		gridEditor.addControl(spnTileW, "tileW");
		
		new LLabel(gridEditor, Vocab.instance.TILEHEIGHT);
		
		LSpinner spnTileH = new LSpinner(gridEditor, SWT.NONE);
		spnTileH.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1));
		gridEditor.addControl(spnTileH, "tileH");
		
		new LLabel(gridEditor, Vocab.instance.TILEBASE);
		
		LSpinner spnTileB = new LSpinner(gridEditor, SWT.NONE);
		spnTileB.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1));
		gridEditor.addControl(spnTileB, "tileB");
		
		new LLabel(gridEditor, Vocab.instance.TILESIDE);
		
		LSpinner spnTileS = new LSpinner(gridEditor, SWT.NONE);
		spnTileS.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1));
		gridEditor.addControl(spnTileS, "tileS");
		
		new LLabel(gridEditor, Vocab.instance.PIXELHEIGHT);
		
		LSpinner spnPixelsPerHeight = new LSpinner(gridEditor, SWT.NONE);
		spnPixelsPerHeight.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1));
		gridEditor.addControl(spnPixelsPerHeight, "pixelsPerHeight");
		
		new LLabel(gridEditor, Vocab.instance.DEPTHHEIGHT);
		
		LSpinner spnDepthPerHeight = new LSpinner(gridEditor, SWT.NONE);
		spnDepthPerHeight.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1));
		gridEditor.addControl(spnDepthPerHeight, "depthPerHeight");
		
		new LLabel(gridEditor, Vocab.instance.DEPTHY);
		
		LSpinner spnDepthPerY = new LSpinner(gridEditor, SWT.NONE);
		spnDepthPerY.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1));
		gridEditor.addControl(spnDepthPerY, "depthPerY");
		
		Composite gridOptions = new Composite(gridEditor, SWT.NONE);
		gridOptions.setLayout(new GridLayout(2, false));
		gridOptions.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 2, 1));
		
		LCheckBox btnAllNeighbors = new LCheckBox(gridOptions, SWT.NONE);
		btnAllNeighbors.setText(Vocab.instance.ALLNEIGHBORS);
		gridEditor.addControl(btnAllNeighbors, "allNeighbors");

		LCheckBox btnOverpassAllies = new LCheckBox(gridOptions, SWT.NONE);
		btnOverpassAllies.setText(Vocab.instance.OVERPASSALLIES);
		gridEditor.addControl(btnOverpassAllies, "overpassAllies");

		LCheckBox btnOverpassDeads = new LCheckBox(gridOptions, SWT.NONE);
		btnOverpassDeads.setLayoutData(new GridData(SWT.LEFT, SWT.CENTER, false, false, 2, 1));
		btnOverpassDeads.setText(Vocab.instance.OVERPASSDEADS);
		gridEditor.addControl(btnOverpassDeads, "overpassDeads");
		
		// Battle
		
		Group grpBattle = new Group(middle, SWT.NONE);
		grpBattle.setLayout(new FillLayout(SWT.HORIZONTAL));
		grpBattle.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true, 1, 1));
		grpBattle.setText(Vocab.instance.BATTLE);
		
		LObjectEditor battleEditor = new LObjectEditor(grpBattle, SWT.NONE);
		editor.addChild(battleEditor, "battle");
		battleEditor.setLayout(new GridLayout(2, false));
		
		new LLabel(battleEditor, Vocab.instance.MAXLEVEL);
		
		LSpinner spnMaxLevel = new LSpinner(battleEditor, SWT.NONE);
		spnMaxLevel.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1));
		battleEditor.addControl(spnMaxLevel, "maxLevel");
		spnMaxLevel.setMaximum(9999);
		
		new LLabel(battleEditor, Vocab.instance.ATTHP);
		
		LText txtAttHP = new LText(battleEditor);
		txtAttHP.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, false, false, 1, 1));
		battleEditor.addControl(txtAttHP, "attHP");
		
		new LLabel(battleEditor, Vocab.instance.ATTSP);
		
		LText txtAttSP = new LText(battleEditor);
		txtAttSP.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, false, false, 1, 1));
		battleEditor.addControl(txtAttSP, "attSP");
		
		new LLabel(battleEditor, Vocab.instance.ATTSTEP);
		
		LText txtAttStep = new LText(battleEditor);
		txtAttStep.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, false, false, 1, 1));
		battleEditor.addControl(txtAttStep, "attStep");
		
		new LLabel(battleEditor, Vocab.instance.ATTJUMP);
		
		LText txtAttJump = new LText(battleEditor);
		txtAttJump.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, false, false, 1, 1));
		battleEditor.addControl(txtAttJump, "attJump");
		
		LCheckBox btnRevive = new LCheckBox(battleEditor);
		btnRevive.setLayoutData(new GridData(SWT.LEFT, SWT.CENTER, false, false, 2, 1));
		btnRevive.setText(Vocab.instance.BATTLEENDREVIVE);
		battleEditor.addControl(btnRevive, "battleEndRevive");

		LCheckBox btnKeepParties = new LCheckBox(battleEditor);
		btnKeepParties.setLayoutData(new GridData(SWT.LEFT, SWT.CENTER, false, false, 2, 1));
		btnKeepParties.setText(Vocab.instance.KEEPPARTIES);
		battleEditor.addControl(btnKeepParties, "keepParties");	
	
		// Troop
		
		Group grpTroop = new Group(middle, SWT.NONE);
		grpTroop.setLayout(new FillLayout(SWT.HORIZONTAL));
		grpTroop.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true, 1, 1));
		grpTroop.setText(Vocab.instance.TROOP);
		
		LObjectEditor troopEditor = new LObjectEditor(grpTroop, SWT.NONE);
		editor.addChild(troopEditor, "troop");
		troopEditor.setLayout(new GridLayout(3, false));
		
		new LLabel(troopEditor, Vocab.instance.INITIALTROOP);
		
		LText txtInitialTroop = new LText(troopEditor, true);
		txtInitialTroop.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1));
		
		IDButton btnInitialTroop = new IDButton(troopEditor, 0) {
			@Override
			public LDataTree<Object> getDataTree() {
				return Project.current.troops.getTree();
			}
		};
		btnInitialTroop.setNameWidget(txtInitialTroop);
		troopEditor.addControl(btnInitialTroop, "initialTroopID");		
		
		new LLabel(troopEditor, Vocab.instance.MAXMEMBERS);
		
		LSpinner spnMaxMembers = new LSpinner(troopEditor, SWT.NONE);
		spnMaxMembers.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 2, 1));
		troopEditor.addControl(spnMaxMembers, "maxMembers");
		
		new LLabel(troopEditor, Vocab.instance.TROOPSIZE);
		
		Composite troopSize = new Composite(troopEditor, SWT.NONE);
		GridLayout gl_troopSize = new GridLayout(3, false);
		gl_troopSize.marginHeight = 0;
		gl_troopSize.marginWidth = 0;
		troopSize.setLayout(gl_troopSize);
		troopSize.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, false, false, 2, 1));
		
		LSpinner spnWidth = new LSpinner(troopSize, SWT.NONE);
		spnWidth.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1));
		troopEditor.addControl(spnWidth, "width");
		
		new LLabel(troopSize, "x");
		
		LSpinner spnHeight = new LSpinner(troopSize, SWT.NONE);
		spnHeight.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1));
		troopEditor.addControl(spnHeight, "height");
		
		// Animations
		
		Composite right = new Composite(this, SWT.NONE);
		right.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true, 1, 1));
		right.setLayout(new FillLayout(SWT.VERTICAL));
		
		Group grpAnimations = new Group(right, SWT.NONE);
		grpAnimations.setText(Vocab.instance.ANIMATIONS);
		grpAnimations.setLayout(new FillLayout(SWT.HORIZONTAL));
		
		NodeList lstAnimations = new NodeList(grpAnimations, SWT.NONE) {
			@Override
			protected LDataTree<Object> getDataTree() {
				return Project.current.animations.getTree();
			}
		};
		lstAnimations.setIncludeID(false);
		editor.addChild(lstAnimations, "animations");
		
		// Icons
		
		Group grpIcons = new Group(right, SWT.NONE);
		grpIcons.setText(Vocab.instance.ICONS);
		grpIcons.setLayout(new FillLayout(SWT.HORIZONTAL));
		
		PortraitList lstIcons = new PortraitList(grpIcons, SWT.NONE);
		editor.addChild(lstIcons, "icons");
		
		// Sounds
		
		Group grpSounds = new Group(this, SWT.NONE);
		grpSounds.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true, 1, 1));
		grpSounds.setText(Vocab.instance.SOUNDS);
		grpSounds.setLayout(new FillLayout(SWT.HORIZONTAL));
		
		SoundList lstSounds = new SoundList(grpSounds, SWT.NONE);
		editor.addChild(lstSounds, "sounds");
		
		
	}
	
	public void onVisible() {
		onChildVisible();
		editor.setObject(Project.current.config.getData());
	}
}
