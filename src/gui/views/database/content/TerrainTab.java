package gui.views.database.content;

import gui.Tooltip;
import gui.Vocab;
import gui.shell.AudioPlayShell;
import gui.views.database.DatabaseTab;
import gui.views.database.subcontent.PropertyList;
import gui.widgets.IDButton;
import gui.widgets.ImageButton;
import gui.widgets.SimpleEditableList;
import lui.base.LFlags;
import lui.container.LContainer;
import lui.container.LFrame;
import lui.container.LImage;
import lui.container.LPanel;
import lui.dialog.LObjectWindow;
import lui.dialog.LWindow;
import lui.dialog.LWindowFactory;
import lui.widget.LCheckBox;
import lui.widget.LLabel;
import lui.widget.LSpinner;
import lui.widget.LText;

import data.Terrain;
import data.subcontent.Audio;
import gson.GObjectTreeSerializer;
import project.Project;

public class TerrainTab extends DatabaseTab<Terrain> {

	private final PropertyList lstJobMoveCost;
	private final IDButton btnStatus;
	
	/**
	 * @wbp.parser.constructor
	 * @wbp.eval.method.parameter parent new lwt.dialog.LShell(800, 600)
	 */
	public TerrainTab(LContainer parent) {
		super(parent);
		
		// General

		LPanel properties = new LPanel(grpGeneral);
		properties.getCellData().setSpread(2, 1);
		properties.getCellData().setAlignment(LFlags.LEFT);
		properties.setSequentialLayout(true);

		LCheckBox btnPassable = new LCheckBox(properties);
		btnPassable.setText(Vocab.instance.PASSABLE);
		btnPassable.setHoverText(Tooltip.instance.PASSABLE);
		addControl(btnPassable, "passable");
		
		// Graphics
		
		LFrame grpGraphics = new LFrame(left, Vocab.instance.GRAPHICS);
		grpGraphics.setGridLayout(1);
		grpGraphics.setHoverText(Tooltip.instance.GRAPHICS);
		grpGraphics.getCellData().setExpand(true, true);
		LImage imgGraphics = new LImage(grpGraphics);
		imgGraphics.setImage("/javax/swing/plaf/basic/icons/image-delayed.png");
		imgGraphics.getCellData().setExpand(true, true);
		imgGraphics.setAlignment(LFlags.CENTER | LFlags.MIDDLE);
		ImageButton btnAnim = new ImageButton(grpGraphics, true);
		btnAnim.setImage(imgGraphics);
		btnAnim.addMenu(grpGraphics);
		btnAnim.addMenu(imgGraphics);
		addControl(btnAnim, "animID");

		// Move Cost

		LFrame moveCost = new LFrame(right, Vocab.instance.MOVECOST);
		moveCost.setHoverText(Tooltip.instance.DEFAULTCOST);
		moveCost.getCellData().setExpand(true, true);
		moveCost.setGridLayout(2);

		LLabel lblCost = new LLabel(moveCost, LFlags.TOP, Vocab.instance.DEFAULT,
				Tooltip.instance.DEFAULTCOST);
		lblCost.getCellData().setMinimumSize(LABELWIDTH, 0);
		LSpinner spnCost = new LSpinner(moveCost);
		spnCost.getCellData().setExpand(true, false);
		spnCost.setMinimum(100);
		spnCost.setMaximum(1000);
		spnCost.addMenu(lblCost);
		addControl(spnCost, "moveCost");

		LLabel lblJobCost = new LLabel(moveCost, LFlags.TOP, Vocab.instance.JOBMOVECOST,
				Tooltip.instance.JOBMOVECOST);
		lstJobMoveCost = new PropertyList(moveCost, Vocab.instance.JOBMOVECOST);
		lstJobMoveCost.getCellData().setExpand(true, true);
		lstJobMoveCost.addMenu(lblJobCost);
		addChild(lstJobMoveCost, "jobMoveCost");

		// Audio
		LFrame grpAudio = new LFrame(right, Vocab.instance.SOUND);
		grpAudio.setFillLayout(true);
		grpAudio.setHoverText(Tooltip.instance.SOUND);
		grpAudio.getCellData().setExpand(true, true);
		SimpleEditableList<Audio> lstAudio = new SimpleEditableList<>(grpAudio);
		lstAudio.type = Audio.class;
		lstAudio.setIncludeID(false);
		lstAudio.setShellFactory(new LWindowFactory<>() {
			@Override
			public LObjectWindow<Audio> createWindow(LWindow parent) {
				return new AudioPlayShell(parent, AudioPlayShell.TIMED);
			}
		});
		lstAudio.addMenu(grpAudio);
		addChild(lstAudio, "sounds");

		// Status
		
		LFrame grpStatus = new LFrame(right, Vocab.instance.STATUS);
		grpStatus.setGridLayout(2);
		grpStatus.setHoverText(Tooltip.instance.TERRAINSTATUS);
		grpStatus.getCellData().setExpand(true, false);
		LText txtStatus = new LText(grpStatus, true);
		txtStatus.getCellData().setExpand(true, false);
		btnStatus = new IDButton(grpStatus, Vocab.instance.STATUSSHELL, true);
		btnStatus.setNameWidget(txtStatus);
		btnStatus.addMenu(grpStatus);
		addControl(btnStatus, "statusID");

		LPanel statusProperties = new LPanel(grpStatus);
		statusProperties.getCellData().setExpand(true, false);
		statusProperties.getCellData().setAlignment(LFlags.LEFT | LFlags.MIDDLE);
		statusProperties.setSequentialLayout(true);
		LCheckBox btnRemoveOnExit = new LCheckBox(statusProperties);
		btnRemoveOnExit.setText(Vocab.instance.REMOVEONEXIT);
		btnRemoveOnExit.setHoverText(Tooltip.instance.REMOVEONEXIT);
		addControl(btnRemoveOnExit, "removeOnExit");
		
	}
	
	@Override
	public void onVisible() {
		btnStatus.dataTree = Project.current.status.getTree();
		lstJobMoveCost.dataTree = Project.current.jobs.getTree();
		super.onVisible();
	}
	
	@Override
	protected GObjectTreeSerializer getSerializer() {
		return Project.current.terrains;
	}

}
