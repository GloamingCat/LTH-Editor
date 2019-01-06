package gui.views.config;

import gui.Vocab;
import gui.widgets.IDButton;
import gui.widgets.PositionButton;
import lwt.action.LActionStack;
import lwt.dataestructure.LDataTree;
import lwt.editor.LObjectEditor;
import lwt.editor.LView;
import lwt.widget.LCheckButton;
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

public class SystemEditor extends LView {

	private LObjectEditor editor;
	
	/**
	 * Create the composite.
	 * @param parent
	 * @param style
	 */
	public SystemEditor(Composite parent, int style) {
		super(parent, style);

		actionStack = new LActionStack(this);
		
		setLayout(new GridLayout(2, true));
		
		editor = new LObjectEditor(this, SWT.NONE);
		editor.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 2, 1));
		editor.setLayout(new GridLayout(2, false));
		addChild(editor);
		
		Composite left = new Composite(this, SWT.NONE);
		left.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true, 1, 1));
		GridLayout gl_left = new GridLayout(1, false);
		gl_left.verticalSpacing = 0;
		gl_left.marginWidth = 0;
		gl_left.marginHeight = 0;
		left.setLayout(gl_left);
		
		Composite right = new Composite(this, SWT.NONE);
		right.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true, 1, 1));
		GridLayout gl_right = new GridLayout(1, false);
		gl_right.verticalSpacing = 0;
		gl_right.marginHeight = 0;
		gl_right.marginWidth = 0;
		right.setLayout(gl_right);
		
		// Name
		
		Label lblName = new Label(editor, SWT.NONE);
		lblName.setText(Vocab.instance.PROJECTNAME);
		
		LText txtName = new LText(editor, SWT.NONE);
		txtName.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1));
		editor.addControl(txtName, "name");
		
		// Player
		
		Group grpPlayer = new Group(left, SWT.NONE);
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
		
		PositionButton btnStartPos = new PositionButton(playerEditor, SWT.NONE);
		btnStartPos.setLayoutData(new GridData(SWT.RIGHT, SWT.CENTER, false, false, 1, 1));
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
		lblTileS.setText(Vocab.instance.TILEBASE);
		
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
		
		Label lblAllNeighbors = new Label(gridEditor, SWT.NONE);
		lblAllNeighbors.setText(Vocab.instance.ALLNEIGHBORS);
		
		LCheckButton btnAllNeighbors = new LCheckButton(gridEditor, SWT.NONE);
		gridEditor.addControl(btnAllNeighbors, "allNeighbors");
		
		Label lblOverpassAllies = new Label(gridEditor, SWT.NONE);
		lblOverpassAllies.setText(Vocab.instance.OVERPASSALLIES);
		
		LCheckButton btnOverpassAllies = new LCheckButton(gridEditor, SWT.NONE);
		gridEditor.addControl(btnOverpassAllies, "overpassAllies");
		
		Label lblOverpassDeads = new Label(gridEditor, SWT.NONE);
		lblOverpassDeads.setText(Vocab.instance.OVERPASSDEADS);
		
		LCheckButton btnOverpassDeads = new LCheckButton(gridEditor, SWT.NONE);
		gridEditor.addControl(btnOverpassDeads, "overpassDeads");
		
		// Battle
		
		Group grpBattle = new Group(right, SWT.NONE);
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

		// Troop
		
		Group grpTroop = new Group(right, SWT.NONE);
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
		
		Label lblHeight = new Label(troopEditor, SWT.NONE);
		lblHeight.setText(Vocab.instance.WIDTH);
		
		LSpinner spnHeight = new LSpinner(troopEditor, SWT.NONE);
		spnHeight.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 2, 1));
		troopEditor.addControl(spnHeight, "height");
		
		Label lblWidth = new Label(troopEditor, SWT.NONE);
		lblWidth.setText(Vocab.instance.HEIGHT);
		
		LSpinner spnWidth = new LSpinner(troopEditor, SWT.NONE);
		spnWidth.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 2, 1));
		troopEditor.addControl(spnWidth, "width");
		
	}
	
	public void onVisible() {
		onChildVisible();
		editor.setObject(Project.current.config.getData());
	}
	
}
