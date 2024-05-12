package gui.views.database.content;

import gui.Tooltip;
import gui.Vocab;
import gui.shell.AudioPlayDialog;
import gui.views.database.DatabaseTab;
import gui.views.database.subcontent.PropertyList;
import gui.widgets.CheckBoxPanel;
import gui.widgets.IDButton;
import gui.widgets.ImageButton;
import gui.widgets.SimpleEditableList;
import lui.base.LFlags;
import lui.container.LContainer;
import lui.container.LFrame;
import lui.container.LImage;
import lui.container.LPanel;
import lui.dialog.LObjectDialog;
import lui.dialog.LWindow;
import lui.dialog.LWindowFactory;
import lui.widget.LCheckBox;
import lui.widget.LLabel;
import lui.widget.LSpinner;
import lui.widget.LText;

import data.Terrain;
import data.subcontent.AudioPlay;
import gson.GObjectTreeSerializer;
import project.Project;

public class TerrainTab extends DatabaseTab<Terrain> {

	private PropertyList lstJobMoveCost;
	private IDButton btnStatus;

	/**
	 * @wbp.parser.constructor
	 * @wbp.eval.method.parameter parent new lwt.dialog.LShell(800, 600)
	 */
	public TerrainTab(LContainer parent) {
		super(parent);
	}

	@Override
	protected void createContent() {
		
		// General

		LPanel check = new CheckBoxPanel(contentEditor.grpGeneral);
		check.getCellData().setSpread(2, 1);

		LCheckBox btnPassable = new LCheckBox(check);
		btnPassable.setText(Vocab.instance.PASSABLE);
		btnPassable.setHoverText(Tooltip.instance.PASSABLE);
		addControl(btnPassable, "passable");

		// Graphics

		LFrame grpGraphics = new LFrame(contentEditor.left, Vocab.instance.GRAPHICS);
		grpGraphics.setGridLayout(1);
		grpGraphics.setHoverText(Tooltip.instance.GRAPHICS);
		grpGraphics.getCellData().setExpand(true, true);
		LImage imgGraphics = new LImage(grpGraphics);
		imgGraphics.getCellData().setExpand(true, true);
		imgGraphics.setAlignment(LFlags.MIDDLE | LFlags.CENTER);
		ImageButton btnGraphics = new ImageButton(grpGraphics, true);
		btnGraphics.addMenu(grpGraphics);
		btnGraphics.addMenu(imgGraphics);
		btnGraphics.setImage(imgGraphics);
		addControl(btnGraphics, "animID");

		// Move Cost

		LFrame moveCost = new LFrame(contentEditor.right, Vocab.instance.MOVECOST);
		moveCost.setHoverText(Tooltip.instance.DEFAULTCOST);
		moveCost.getCellData().setExpand(true, true);
		moveCost.setGridLayout(2);

		LLabel lblCost = new LLabel(moveCost, LFlags.TOP, Vocab.instance.DEFAULT,
				Tooltip.instance.DEFAULTCOST);
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
		LFrame grpAudio = new LFrame(contentEditor.right, Vocab.instance.SOUND);
		grpAudio.setFillLayout(true);
		grpAudio.setHoverText(Tooltip.instance.SOUND);
		grpAudio.getCellData().setExpand(true, true);
		SimpleEditableList<AudioPlay> lstAudio = new SimpleEditableList<>(grpAudio);
		lstAudio.type = AudioPlay.class;
		lstAudio.setIncludeID(false);
		lstAudio.setShellFactory(new LWindowFactory<>() {
			@Override
			public LObjectDialog<AudioPlay> createWindow(LWindow parent) {
				return new AudioPlayDialog(parent, AudioPlayDialog.TIMED);
			}
		});
		lstAudio.addMenu(grpAudio);
		addChild(lstAudio, "sounds");

		// Status

		LFrame grpStatus = new LFrame(contentEditor.right, Vocab.instance.STATUS);
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
