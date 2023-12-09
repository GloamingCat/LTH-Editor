package gui.views.database.content;

import gson.project.GObjectTreeSerializer;
import gui.Vocab;
import gui.shell.database.ObstacleTileShell;
import gui.views.database.DatabaseTab;
import gui.views.database.subcontent.TransformEditor;
import gui.widgets.IconButton;
import gui.widgets.SimpleEditableList;
import lwt.LFlags;
import lwt.container.LContainer;
import lwt.container.LFrame;
import lwt.container.LImage;
import lwt.dialog.LObjectShell;
import lwt.dialog.LShell;
import lwt.dialog.LShellFactory;
import lwt.event.LSelectionEvent;
import lwt.event.listener.LSelectionListener;
import lwt.widget.LLabel;

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

		new LLabel(grpGeneral, LFlags.TOP, Vocab.instance.COLLIDERTILES);
		
		SimpleEditableList<ObstacleTile> tileList = new SimpleEditableList<ObstacleTile>(grpGeneral);
		tileList.type = ObstacleTile.class;
		tileList.setIncludeID(false);
		tileList.setShellFactory(new LShellFactory<ObstacleTile>() {
			@Override
			public LObjectShell<ObstacleTile> createShell(LShell parent) {
				return new ObstacleTileShell(parent);
			}
		});

		tileList.setExpand(true, true);
		tileList.setMinimumHeight(60);
		addChild(tileList, "tiles");
		
		// Graphics
		
		LFrame grpGraphics = new LFrame(left, Vocab.instance.GRAPHICS, 1);
		grpGraphics.setExpand(true, true);

		LImage imgGraphics = new LImage(grpGraphics);
		imgGraphics.setExpand(true, true);
		imgGraphics.setAlignment(LFlags.TOP & LFlags.LEFT);
		
		IconButton btnGraphics = new IconButton(grpGraphics, false);
		addControl(btnGraphics, "image");
		
		// Transform
		
		LFrame grpTransform = new LFrame(right, Vocab.instance.TRANSFORM, true, true);
		grpTransform.setExpand(true, false);
		
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
