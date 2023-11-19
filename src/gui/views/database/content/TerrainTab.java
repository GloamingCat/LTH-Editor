package gui.views.database.content;

import gson.project.GObjectTreeSerializer;
import gui.Vocab;
import gui.shell.AudioShell;
import gui.views.database.DatabaseTab;
import gui.views.database.subcontent.PropertyList;
import gui.widgets.IDButton;
import gui.widgets.SimpleEditableList;
import lwt.container.LContainer;
import lwt.container.LFrame;
import lwt.container.LPanel;
import lwt.dataestructure.LDataTree;
import lwt.dialog.LObjectShell;
import lwt.dialog.LShell;
import lwt.dialog.LShellFactory;
import lwt.widget.LCheckBox;
import lwt.widget.LImage;
import lwt.widget.LLabel;
import lwt.widget.LSpinner;
import lwt.widget.LText;

import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.GridData;

import data.Terrain;
import data.subcontent.Audio;
import project.Project;

public class TerrainTab extends DatabaseTab<Terrain> {

	private PropertyList lstJobMoveCost;
	private IDButton btnAnim;
	private IDButton btnStatus;
	
	/**
	 * @wbp.parser.constructor
	 * @wbp.eval.method.parameter parent new lwt.dialog.LShell(800, 600)
	 */
	public TerrainTab(LContainer parent) {
		super(parent);
		
		// General
		
		new LLabel(grpGeneral, LLabel.TOP, Vocab.instance.MOVECOST);
		
		LPanel moveCost = new LPanel(grpGeneral, 2, false);
		moveCost.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, false, false, 1, 1));
		
		LSpinner spnCost = new LSpinner(moveCost);
		spnCost.setMinimum(100);
		spnCost.setMaximum(1000);
		addControl(spnCost, "moveCost");
		
		LCheckBox btnPassable = new LCheckBox(moveCost);
		btnPassable.setText(Vocab.instance.PASSABLE);
		addControl(btnPassable, "passable");
		
		lstJobMoveCost = new PropertyList(moveCost);
		lstJobMoveCost.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true, 2, 1));
		addChild(lstJobMoveCost, "jobMoveCost");
		
		// Graphics
		
		LFrame grpGraphics = new LFrame(left, Vocab.instance.GRAPHICS, 1);
		grpGraphics.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true, 1, 1));
		
		LImage imgGraphics = new LImage(grpGraphics, SWT.NONE);
		imgGraphics.setImage("/javax/swing/plaf/basic/icons/image-delayed.png");
		imgGraphics.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true, 1, 1));
		imgGraphics.setHorizontalAlign(SWT.CENTER);
		imgGraphics.setVerticalAlign(SWT.CENTER);
		
		btnAnim = new IDButton(grpGraphics, true) {
			public LDataTree<Object> getDataTree() {
				return Project.current.animations.getTree();
			}
		};
		addControl(btnAnim, "animID");
		btnAnim.setImage(imgGraphics);
		
		// Audio
		
		LFrame grpAudio = new LFrame(right, Vocab.instance.SOUND, true, true);
		grpAudio.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true, 1, 1));
		SimpleEditableList<Audio> lstAudio = new SimpleEditableList<Audio>(grpAudio);
		lstAudio.type = Audio.class;
		lstAudio.setIncludeID(false);
		lstAudio.setShellFactory(new LShellFactory<Audio>() {
			@Override
			public LObjectShell<Audio> createShell(LShell parent) {
				return new AudioShell(parent, false);
			}
		});
		addChild(lstAudio, "sounds");

		// Status
		
		LFrame grpStatus = new LFrame(right, Vocab.instance.STATUS, 2, false);
		grpStatus.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true, 1, 1));
		LText txtStatus = new LText(grpStatus, true);		
		btnStatus = new IDButton(grpStatus, true) {
			public LDataTree<Object> getDataTree() {
				return Project.current.status.getTree();
			}
		};
		btnStatus.setNameWidget(txtStatus);
		addControl(btnStatus, "statusID");
		LCheckBox btnRemoveOnExit = new LCheckBox(grpStatus);
		btnRemoveOnExit.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1));
		btnRemoveOnExit.setText(Vocab.instance.REMOVEONEXIT);
		addControl(btnRemoveOnExit, "removeOnExit");
		
	}
	
	@Override
	public void onVisible() {
		btnStatus.dataTree = Project.current.status.getTree();
		btnAnim.dataTree = Project.current.animations.getTree();
		lstJobMoveCost.dataTree = Project.current.jobs.getTree();
		super.onVisible();
	}
	
	@Override
	protected GObjectTreeSerializer getSerializer() {
		return Project.current.terrains;
	}

}
