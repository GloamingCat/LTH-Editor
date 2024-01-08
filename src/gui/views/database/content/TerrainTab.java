package gui.views.database.content;

import gson.project.GObjectTreeSerializer;
import gui.Tooltip;
import gui.Vocab;
import gui.shell.AudioPlayShell;
import gui.views.database.DatabaseTab;
import gui.views.database.subcontent.PropertyList;
import gui.widgets.IDButton;
import gui.widgets.ImageButton;
import gui.widgets.SimpleEditableList;
import lwt.LFlags;
import lwt.container.LContainer;
import lwt.container.LFrame;
import lwt.container.LImage;
import lwt.dialog.LObjectShell;
import lwt.dialog.LShell;
import lwt.dialog.LShellFactory;
import lwt.widget.LCheckBox;
import lwt.widget.LLabel;
import lwt.widget.LSpinner;
import lwt.widget.LText;

import data.Terrain;
import data.subcontent.Audio;
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
		
		// General
		
		LLabel lblCost = new LLabel(grpGeneral, LFlags.TOP, Vocab.instance.MOVECOST,
				Tooltip.instance.MOVECOST);
		LSpinner spnCost = new LSpinner(grpGeneral);
		spnCost.setMinimum(100);
		spnCost.setMaximum(1000);
		spnCost.addMenu(lblCost);
		addControl(spnCost, "moveCost");
		
		LLabel lblJobCost = new LLabel(grpGeneral, LFlags.TOP, Vocab.instance.JOBMOVECOST,
				Tooltip.instance.JOBMOVECOST);
		lstJobMoveCost = new PropertyList(grpGeneral, Vocab.instance.JOBMOVECOST);
		lstJobMoveCost.setExpand(true, true);
		lstJobMoveCost.addMenu(lblJobCost);
		addChild(lstJobMoveCost, "jobMoveCost");		
		
		new LLabel(grpGeneral, 1, 1);
		LCheckBox btnPassable = new LCheckBox(grpGeneral);
		btnPassable.setText(Vocab.instance.PASSABLE);
		btnPassable.setHoverText(Tooltip.instance.PASSABLE);
		addControl(btnPassable, "passable");
		
		// Graphics
		
		LFrame grpGraphics = new LFrame(left, Vocab.instance.GRAPHICS);
		grpGraphics.setGridLayout(1);
		grpGraphics.setHoverText(Tooltip.instance.GRAPHICS);
		grpGraphics.setExpand(true, true);
		LImage imgGraphics = new LImage(grpGraphics);
		imgGraphics.setImage("/javax/swing/plaf/basic/icons/image-delayed.png");
		imgGraphics.setExpand(true, true);
		ImageButton btnAnim = new ImageButton(grpGraphics, true);
		btnAnim.setImage(imgGraphics);
		btnAnim.addMenu(grpGraphics);
		btnAnim.addMenu(imgGraphics);
		addControl(btnAnim, "animID");
		LFrame frame = new LFrame(right, (String) Vocab.instance.SOUND);
		frame.setFillLayout(true);
		
		// Audio
		
		LFrame grpAudio = frame;
		grpAudio.setHoverText(Tooltip.instance.SOUND);
		grpAudio.setExpand(true, true);
		SimpleEditableList<Audio> lstAudio = new SimpleEditableList<Audio>(grpAudio);
		lstAudio.type = Audio.class;
		lstAudio.setIncludeID(false);
		lstAudio.setShellFactory(new LShellFactory<Audio>() {
			@Override
			public LObjectShell<Audio> createShell(LShell parent) {
				return new AudioPlayShell(parent, AudioPlayShell.TIMED);
			}
		});
		lstAudio.addMenu(grpAudio);
		addChild(lstAudio, "sounds");

		// Status
		
		LFrame grpStatus = new LFrame(right, Vocab.instance.STATUS);
		grpStatus.setGridLayout(2);
		grpStatus.setHoverText(Tooltip.instance.TERRAINSTATUS);
		grpStatus.setExpand(true, true);
		LText txtStatus = new LText(grpStatus, true);		
		btnStatus = new IDButton(grpStatus, Vocab.instance.STATUSSHELL, true);
		btnStatus.setNameWidget(txtStatus);
		btnStatus.addMenu(grpStatus);
		addControl(btnStatus, "statusID");
		
		LCheckBox btnRemoveOnExit = new LCheckBox(grpStatus);
		btnRemoveOnExit.setText(Vocab.instance.REMOVEONEXIT);
		btnRemoveOnExit.setHoverText(Tooltip.instance.REMOVEONEXIT);
		btnRemoveOnExit.setExpand(true, false);
		btnRemoveOnExit.setAlignment(LFlags.CENTER);
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
