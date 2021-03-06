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
import lwt.widget.LCheckButton;
import lwt.widget.LCombo;
import lwt.widget.LSpinner;
import lwt.widget.LText;

import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Group;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Text;

import project.Project;

public class ConfigEditor extends LView {

	private LObjectEditor editor;
	
	/**
	 * Create the composite.
	 * @param parent
	 * @param style
	 */
	public ConfigEditor(Composite parent, int style) {
		super(parent, style);

		actionStack = new LActionStack(this);
		
		setLayout(new GridLayout(3, false));
		
		editor = new LObjectEditor(this, SWT.NONE);
		editor.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 3, 1));
		editor.setLayout(new GridLayout(2, false));
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
		
		Composite right = new Composite(this, SWT.NONE);
		right.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true, 1, 1));
		right.setLayout(new FillLayout(SWT.VERTICAL));
		
		// Name
		
		Label lblName = new Label(editor, SWT.NONE);
		lblName.setText(Vocab.instance.PROJECTNAME);
		
		LText txtName = new LText(editor, SWT.NONE);
		txtName.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1));
		editor.addControl(txtName, "name");
		
		// Screen
		
		Group grpScreen = new Group(left, SWT.NONE);
		grpScreen.setLayout(new FillLayout(SWT.HORIZONTAL));
		grpScreen.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true, 1, 1));
		grpScreen.setText(Vocab.instance.SCREEN);
		
		LObjectEditor screenEditor = new LObjectEditor(grpScreen, SWT.NONE);
		editor.addChild(screenEditor, "screen");
		screenEditor.setLayout(new GridLayout(3, false));
		
		Label lblCover = new Label(screenEditor, SWT.NONE);
		lblCover.setText(Vocab.instance.COVER);
		
		Text txtCover = new Text(screenEditor, SWT.BORDER | SWT.READ_ONLY);
		txtCover.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1));
		
		IDButton btnCover = new IDButton(screenEditor, 0) {
			@Override
			public LDataTree<Object> getDataTree() {
				return Project.current.animations.getTree();
			}
		};
		btnCover.setNameText(txtCover);
		screenEditor.addControl(btnCover, "coverID");
		
		Label lblFps = new Label(screenEditor, SWT.NONE);
		lblFps.setText(Vocab.instance.FPSLIMIT);
		
		LSpinner spnFps = new LSpinner(screenEditor, SWT.NONE);
		spnFps.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 2, 1));
		screenEditor.addControl(spnFps, "fpsLimit");
		spnFps.setMinimum(1);
		spnFps.setMaximum(9999);
		
		Label lblNativeSize = new Label(screenEditor, SWT.NONE);
		lblNativeSize.setText(Vocab.instance.NATIVESIZE);
		
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
		
		Label lblX = new Label(nativeSize, SWT.NONE);
		lblX.setText("x");
		
		LSpinner spnNativeHeight = new LSpinner(nativeSize, SWT.NONE);
		spnNativeHeight.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1));
		screenEditor.addControl(spnNativeHeight, "nativeHeight");
		spnNativeHeight.setMinimum(1);
		spnNativeHeight.setMaximum(9999);
		
		Label lblScaleFactor = new Label(screenEditor, SWT.NONE);
		lblScaleFactor.setText(Vocab.instance.SCALEFACTOR);
		
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
		
		Label lblX2 = new Label(scaleFactor, SWT.NONE);
		lblX2.setText("x");
		
		LSpinner spnHeightScale = new LSpinner(scaleFactor, SWT.NONE);
		spnHeightScale.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1));
		screenEditor.addControl(spnHeightScale, "heightScale");
		spnHeightScale.setMinimum(1);
		spnHeightScale.setMaximum(9999);
		
		Label lblScaleType = new Label(screenEditor, SWT.NONE);
		lblScaleType.setText(Vocab.instance.SCALETYPE);
		
		LCombo cmbScale = new LCombo(screenEditor, SWT.NONE);
		cmbScale.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 2, 1));
		cmbScale.setOptional(false);
		cmbScale.setIncludeID(false);
		cmbScale.setItems(new String[] {
			Vocab.instance.NOSCALE,
			Vocab.instance.INTEGERONLY,
			Vocab.instance.KEEPRATIO,
			Vocab.instance.FREESCALE
		});
		screenEditor.addControl(cmbScale, "scaleType");
		
		// Player
		
		Group grpPlayer = new Group(middle, SWT.NONE);
		grpPlayer.setLayout(new FillLayout(SWT.HORIZONTAL));
		grpPlayer.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true, 1, 1));
		grpPlayer.setText(Vocab.instance.PLAYER);
		
		LObjectEditor playerEditor = new LObjectEditor(grpPlayer, SWT.NONE);
		editor.addChild(playerEditor, "player");
		playerEditor.setLayout(new GridLayout(3, false));
		
		Label lblWalkSpeed = new Label(playerEditor, SWT.NONE);
		lblWalkSpeed.setText(Vocab.instance.WALKSPEED);
		
		LSpinner spnWalkSpeed = new LSpinner(playerEditor, SWT.NONE);
		spnWalkSpeed.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 2, 1));
		playerEditor.addControl(spnWalkSpeed, "walkSpeed");
		spnWalkSpeed.setMaximum(9999);
		
		Label lblDashSpeed = new Label(playerEditor, SWT.NONE);
		lblDashSpeed.setText(Vocab.instance.DASHSPEED);
		
		LSpinner spnDashSpeed = new LSpinner(playerEditor, SWT.NONE);
		spnDashSpeed.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 2, 1));
		playerEditor.addControl(spnDashSpeed, "dashSpeed");
		spnDashSpeed.setMaximum(9999);
		
		Label lblStartPos = new Label(playerEditor, SWT.NONE);
		lblStartPos.setText(Vocab.instance.STARTPOS);
		
		Text txtPos = new Text(playerEditor, SWT.BORDER | SWT.READ_ONLY);
		txtPos.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1));
		
		PositionButton btnStartPos = new PositionButton(playerEditor);
		btnStartPos.setLayoutData(new GridData(SWT.RIGHT, SWT.CENTER, false, false, 1, 1));
		btnStartPos.setText(txtPos);
		playerEditor.addControl(btnStartPos, "startPos");
		
		// Grid
		
		Group grpGrid = new Group(left, SWT.NONE);
		grpGrid.setLayout(new FillLayout(SWT.HORIZONTAL));
		grpGrid.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true, 1, 1));
		grpGrid.setText(Vocab.instance.GRID);
		
		LObjectEditor gridEditor = new LObjectEditor(grpGrid, SWT.NONE);
		editor.addChild(gridEditor, "grid");
		gridEditor.setLayout(new GridLayout(2, false));
		
		Label lblTileW = new Label(gridEditor, SWT.NONE);
		lblTileW.setText(Vocab.instance.TILEWIDTH);
		
		LSpinner spnTileW = new LSpinner(gridEditor, SWT.NONE);
		spnTileW.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1));
		gridEditor.addControl(spnTileW, "tileW");
		
		Label lblTileH = new Label(gridEditor, SWT.NONE);
		lblTileH.setText(Vocab.instance.TILEHEIGHT);
		
		LSpinner spnTileH = new LSpinner(gridEditor, SWT.NONE);
		spnTileH.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1));
		gridEditor.addControl(spnTileH, "tileH");
		
		Label lblTileB = new Label(gridEditor, SWT.NONE);
		lblTileB.setText(Vocab.instance.TILEBASE);
		
		LSpinner spnTileB = new LSpinner(gridEditor, SWT.NONE);
		spnTileB.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1));
		gridEditor.addControl(spnTileB, "tileB");
		
		Label lblTileS = new Label(gridEditor, SWT.NONE);
		lblTileS.setText(Vocab.instance.TILESIDE);
		
		LSpinner spnTileS = new LSpinner(gridEditor, SWT.NONE);
		spnTileS.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1));
		gridEditor.addControl(spnTileS, "tileS");
		
		Label lblPixelsPerHeight = new Label(gridEditor, SWT.NONE);
		lblPixelsPerHeight.setText(Vocab.instance.PIXELHEIGHT);
		
		LSpinner spnPixelsPerHeight = new LSpinner(gridEditor, SWT.NONE);
		spnPixelsPerHeight.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1));
		gridEditor.addControl(spnPixelsPerHeight, "pixelsPerHeight");
		
		Label lblDepthPerHeight = new Label(gridEditor, SWT.NONE);
		lblDepthPerHeight.setText(Vocab.instance.DEPTHHEIGHT);
		
		LSpinner spnDepthPerHeight = new LSpinner(gridEditor, SWT.NONE);
		spnDepthPerHeight.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1));
		gridEditor.addControl(spnDepthPerHeight, "depthPerHeight");
		
		Label lblDepthPerY = new Label(gridEditor, SWT.NONE);
		lblDepthPerY.setText(Vocab.instance.DEPTHY);
		
		LSpinner spnDepthPerY = new LSpinner(gridEditor, SWT.NONE);
		spnDepthPerY.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1));
		gridEditor.addControl(spnDepthPerY, "depthPerY");
		
		Composite gridOptions = new Composite(gridEditor, SWT.NONE);
		gridOptions.setLayout(new GridLayout(2, false));
		gridOptions.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 2, 1));
		
		LCheckButton btnAllNeighbors = new LCheckButton(gridOptions, SWT.NONE);
		btnAllNeighbors.setText(Vocab.instance.ALLNEIGHBORS);
		gridEditor.addControl(btnAllNeighbors, "allNeighbors");

		LCheckButton btnOverpassAllies = new LCheckButton(gridOptions, SWT.NONE);
		btnOverpassAllies.setText(Vocab.instance.OVERPASSALLIES);
		gridEditor.addControl(btnOverpassAllies, "overpassAllies");

		LCheckButton btnOverpassDeads = new LCheckButton(gridOptions, SWT.NONE);
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
		
		Label lblMaxLevel = new Label(battleEditor, SWT.NONE);
		lblMaxLevel.setText(Vocab.instance.MAXLEVEL);
		
		LSpinner spnMaxLevel = new LSpinner(battleEditor, SWT.NONE);
		spnMaxLevel.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1));
		battleEditor.addControl(spnMaxLevel, "maxLevel");
		spnMaxLevel.setMaximum(9999);
		
		Label lblAttHP = new Label(battleEditor, SWT.NONE);
		lblAttHP.setText(Vocab.instance.ATTHP);
		
		LText txtAttHP = new LText(battleEditor, SWT.NONE);
		txtAttHP.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, false, false, 1, 1));
		battleEditor.addControl(txtAttHP, "attHP");
		
		Label lblAttSP = new Label(battleEditor, SWT.NONE);
		lblAttSP.setText(Vocab.instance.ATTSP);
		
		LText txtAttSP = new LText(battleEditor, SWT.NONE);
		txtAttSP.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, false, false, 1, 1));
		battleEditor.addControl(txtAttSP, "attSP");
		
		Label lblAttStep = new Label(battleEditor, SWT.NONE);
		lblAttStep.setText(Vocab.instance.ATTSTEP);
		
		LText txtAttStep = new LText(battleEditor, SWT.NONE);
		txtAttStep.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, false, false, 1, 1));
		battleEditor.addControl(txtAttStep, "attStep");
		
		Label lblAttJump = new Label(battleEditor, SWT.NONE);
		lblAttJump.setText(Vocab.instance.ATTSTEP);
		
		LText txtAttJump = new LText(battleEditor, SWT.NONE);
		txtAttJump.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, false, false, 1, 1));
		battleEditor.addControl(txtAttJump, "attJump");
		
		LCheckButton btnRevive = new LCheckButton(battleEditor);
		btnRevive.setLayoutData(new GridData(SWT.LEFT, SWT.CENTER, false, false, 2, 1));
		btnRevive.setText(Vocab.instance.BATTLEENDREVIVE);
		battleEditor.addControl(btnRevive, "battleEndRevive");

		// Troop
		
		Group grpTroop = new Group(middle, SWT.NONE);
		grpTroop.setLayout(new FillLayout(SWT.HORIZONTAL));
		grpTroop.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true, 1, 1));
		grpTroop.setText(Vocab.instance.TROOP);
		
		LObjectEditor troopEditor = new LObjectEditor(grpTroop, SWT.NONE);
		editor.addChild(troopEditor, "troop");
		troopEditor.setLayout(new GridLayout(3, false));
		
		Label lblInitialTroop = new Label(troopEditor, SWT.NONE);
		lblInitialTroop.setText(Vocab.instance.INITIALTROOP);
		
		Text txtInitialTroop = new Text(troopEditor, SWT.BORDER | SWT.READ_ONLY);
		txtInitialTroop.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1));
		
		IDButton btnInitialTroop = new IDButton(troopEditor, 0) {
			@Override
			public LDataTree<Object> getDataTree() {
				return Project.current.troops.getTree();
			}
		};
		btnInitialTroop.setNameText(txtInitialTroop);
		troopEditor.addControl(btnInitialTroop, "initialTroopID");		
		
		Label lblMaxMembers = new Label(troopEditor, SWT.NONE);
		lblMaxMembers.setText(Vocab.instance.MAXMEMBERS);
		
		LSpinner spnMaxMembers = new LSpinner(troopEditor, SWT.NONE);
		spnMaxMembers.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 2, 1));
		troopEditor.addControl(spnMaxMembers, "maxMembers");
		
		Label lblSize = new Label(troopEditor, SWT.NONE);
		lblSize.setText(Vocab.instance.TROOPSIZE);
		
		Composite troopSize = new Composite(troopEditor, SWT.NONE);
		GridLayout gl_troopSize = new GridLayout(3, false);
		gl_troopSize.marginHeight = 0;
		gl_troopSize.marginWidth = 0;
		troopSize.setLayout(gl_troopSize);
		troopSize.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, false, false, 2, 1));
		
		LSpinner spnWidth = new LSpinner(troopSize, SWT.NONE);
		spnWidth.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1));
		troopEditor.addControl(spnWidth, "width");
		
		Label lblX3 = new Label(troopSize, SWT.NONE);
		lblX3.setText("x");
		
		LSpinner spnHeight = new LSpinner(troopSize, SWT.NONE);
		spnHeight.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1));
		troopEditor.addControl(spnHeight, "height");
		
		// Animations
		
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
		
	}
	
	public void onVisible() {
		onChildVisible();
		editor.setObject(Project.current.config.getData());
	}
}
