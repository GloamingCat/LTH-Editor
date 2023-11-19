package gui.views.database.content;

import gson.project.GObjectTreeSerializer;
import gui.Vocab;
import gui.shell.database.ObstacleTileShell;
import gui.views.database.DatabaseTab;
import gui.views.database.subcontent.TransformEditor;
import gui.widgets.IconButton;
import gui.widgets.SimpleEditableList;
import lwt.container.LContainer;
import lwt.container.LFrame;
import lwt.dialog.LObjectShell;
import lwt.dialog.LShell;
import lwt.dialog.LShellFactory;
import lwt.event.LSelectionEvent;
import lwt.event.listener.LSelectionListener;
import lwt.widget.LImage;
import lwt.widget.LLabel;

import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.GridData;

import data.Animation;
import data.Obstacle;
import data.Obstacle.ObstacleTile;
import project.Project;

public class ObstacleTab extends DatabaseTab<Obstacle> {

	/**
	 * @wbp.parser.constructor
	 * @wbp.eval.method.parameter parent new lwt.dialog.LShell(800, 600)
	 */
	public ObstacleTab(LContainer parent) {
		super(parent);

		new LLabel(grpGeneral, LLabel.TOP, Vocab.instance.COLLIDERTILES);
		
		SimpleEditableList<ObstacleTile> tileList = new SimpleEditableList<ObstacleTile>(grpGeneral);
		tileList.type = ObstacleTile.class;
		tileList.setIncludeID(false);
		tileList.setShellFactory(new LShellFactory<ObstacleTile>() {
			@Override
			public LObjectShell<ObstacleTile> createShell(LShell parent) {
				return new ObstacleTileShell(parent);
			}
		});
		GridData gd_tiles = new GridData(SWT.FILL, SWT.FILL, true, true, 1, 1);
		gd_tiles.heightHint = 60;
		gd_tiles.minimumHeight = 60;
		tileList.setLayoutData(gd_tiles);
		addChild(tileList, "tiles");
		
		// Graphics
		
		LFrame grpGraphics = new LFrame(left, Vocab.instance.GRAPHICS, 1);
		grpGraphics.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true, 1, 1));

		LImage imgGraphics = new LImage(grpGraphics, SWT.NONE);
		imgGraphics.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true, 1, 1));
		
		IconButton btnGraphics = new IconButton(grpGraphics, false);
		addControl(btnGraphics, "image");
		
		// Transform
		
		LFrame grpTransform = new LFrame(right, Vocab.instance.TRANSFORM, true, true);
		grpTransform.setLayoutData(new GridData(SWT.FILL, SWT.TOP, true, true, 1, 1));
		
		TransformEditor transformEditor = new TransformEditor(grpTransform);
		addChild(transformEditor, "transform");
		transformEditor.addSelectionListener(new LSelectionListener() {
			@Override
			public void onSelect(LSelectionEvent event) {
				Obstacle o = (Obstacle) contentEditor.getObject();
				Animation a = (Animation) Project.current.animations.getData().get(o.image.id);
				if (a == null)
					return;
				transformEditor.secondaryTransform = a.transform;
			}
		});
		
		btnGraphics.setImageWidget(imgGraphics);
		btnGraphics.setTransform(transformEditor);
		transformEditor.setImage(imgGraphics);
		
		new LLabel(right, 1, 1);
	}

	@Override
	protected GObjectTreeSerializer getSerializer() {
		return Project.current.obstacles;
	}

}
