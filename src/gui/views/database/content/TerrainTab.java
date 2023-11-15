package gui.views.database.content;

import gson.project.GObjectTreeSerializer;
import gui.Vocab;
import gui.shell.AudioShell;
import gui.views.database.DatabaseTab;
import gui.views.database.subcontent.BonusList;
import gui.widgets.IDButton;
import gui.widgets.SimpleEditableList;
import lwt.dataestructure.LDataTree;
import lwt.dialog.LObjectShell;
import lwt.dialog.LShellFactory;
import lwt.widget.LCheckBox;
import lwt.widget.LImage;
import lwt.widget.LLabel;
import lwt.widget.LSpinner;
import lwt.widget.LText;

import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Group;
import org.eclipse.swt.widgets.Shell;

import data.Terrain;
import data.subcontent.Audio;
import project.Project;

public class TerrainTab extends DatabaseTab<Terrain> {

	/**
	 * Create the composite.
	 * @param parent
	 */
	public TerrainTab(Composite parent) {
		super(parent);;
		
		// General
		
		LLabel label = new LLabel(grpGeneral, Vocab.instance.MOVECOST);
		label.setLayoutData(new GridData(SWT.LEFT, SWT.TOP, false, false, 1, 1));
		
		Composite moveCost = new Composite(grpGeneral, SWT.NONE);
		moveCost.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, false, false, 1, 1));
		GridLayout gl_moveCost = new GridLayout(2, false);
		gl_moveCost.marginHeight = 0;
		gl_moveCost.marginWidth = 0;
		moveCost.setLayout(gl_moveCost);
		
		LSpinner spnCost = new LSpinner(moveCost, SWT.NONE);
		spnCost.setLayoutData(new GridData(SWT.FILL, SWT.TOP, true, false, 1, 1));
		spnCost.setMinimum(100);
		spnCost.setMaximum(1000);
		addControl(spnCost, "moveCost");
		
		LCheckBox btnPassable = new LCheckBox(moveCost, SWT.CHECK);
		btnPassable.setText(Vocab.instance.PASSABLE);
		addControl(btnPassable, "passable");
		
		BonusList lstJobMoveCost = new BonusList(moveCost, SWT.NONE) {
			@Override
			protected LDataTree<Object> getDataTree() {
				return Project.current.jobs.getTree();
			}
		};
		lstJobMoveCost.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true, 2, 1));
		addChild(lstJobMoveCost, "jobMoveCost");
		
		// Status
		
		Group grpStatus = new Group(left, SWT.NONE);
		grpStatus.setLayout(new GridLayout(1, false));
		grpStatus.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, false, 1, 1));
		grpStatus.setText(Vocab.instance.STATUS);
			
		Composite status = new Composite(grpStatus, SWT.NONE);
		status.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1));
		GridLayout gl_status = new GridLayout(2, false);
		gl_status.marginHeight = 0;
		gl_status.marginWidth = 0;
		status.setLayout(gl_status);
			
		LText txtStatus = new LText(status, true);
		txtStatus.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1));
		
		IDButton btnStatus = new IDButton(status, 1) {
			public LDataTree<Object> getDataTree() {
				return Project.current.status.getTree();
			}
		};
		btnStatus.setNameWidget(txtStatus);
		addControl(btnStatus, "statusID");
		
		LCheckBox btnRemoveOnExit = new LCheckBox(grpStatus, SWT.NONE);
		btnRemoveOnExit.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1));
		btnRemoveOnExit.setText(Vocab.instance.REMOVEONEXIT);
		addControl(btnRemoveOnExit, "removeOnExit");
		
		// Graphics
		
		Group grpGraphics = new Group(left, SWT.NONE);
		grpGraphics.setLayout(new GridLayout(1, false));
		grpGraphics.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true, 1, 1));
		grpGraphics.setText(Vocab.instance.GRAPHICS);
		
		LImage imgGraphics = new LImage(grpGraphics, SWT.NONE);
		imgGraphics.setImage("/javax/swing/plaf/basic/icons/image-delayed.png");
		imgGraphics.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true, 1, 1));
		imgGraphics.setHorizontalAlign(SWT.CENTER);
		imgGraphics.setVerticalAlign(SWT.CENTER);
		
		IDButton btnAnim = new IDButton(grpGraphics, 1) {
			public LDataTree<Object> getDataTree() {
				return Project.current.animations.getTree();
			}
		};
		addControl(btnAnim, "animID");
		btnAnim.setImage(imgGraphics);
		
		// Audio
		
		Group grpAudio = new Group(right, SWT.NONE);
		grpAudio.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true, 1, 1));
		grpAudio.setLayout(new FillLayout(SWT.HORIZONTAL));
		grpAudio.setText(Vocab.instance.SOUND);
		
		SimpleEditableList<Audio> lstAudio = new SimpleEditableList<Audio>(grpAudio, SWT.NONE);
		lstAudio.type = Audio.class;
		lstAudio.setIncludeID(false);
		lstAudio.setShellFactory(new LShellFactory<Audio>() {
			@Override
			public LObjectShell<Audio> createShell(Shell parent) {
				return new AudioShell(parent, 0);
			}
		});
		addChild(lstAudio, "sounds");
		
		new LLabel(right, "").setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true, 1, 1));

	}

	@Override
	protected GObjectTreeSerializer getSerializer() {
		return Project.current.terrains;
	}

}
