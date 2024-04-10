package gui.views.database.content;

import lui.base.LFlags;
import gui.Tooltip;
import gui.Vocab;
import gui.shell.database.ObstacleTileDialog;
import gui.views.database.DatabaseTab;
import gui.views.database.subcontent.TransformEditor;
import gui.widgets.IconButton;
import gui.widgets.SimpleEditableList;
import lui.container.LContainer;
import lui.container.LFrame;
import lui.container.LImage;
import lui.dialog.LObjectDialog;
import lui.dialog.LWindow;
import lui.dialog.LWindowFactory;
import lui.widget.LLabel;
import data.Animation;
import data.Obstacle;
import data.Obstacle.ObstacleTile;
import gson.GObjectTreeSerializer;
import project.Project;

public class ObstacleTab extends DatabaseTab<Obstacle> {

	/**
	 * @wbp.parser.constructor
	 * @wbp.eval.method.parameter parent new lwt.dialog.LShell(800, 600)
	 */
	public ObstacleTab(LContainer parent) {
		super(parent);

		LLabel lblTiles = new LLabel(grpGeneral, LFlags.TOP, Vocab.instance.COLLIDERTILES,
				Tooltip.instance.COLLIDERTILES);
		SimpleEditableList<ObstacleTile> tileList = new SimpleEditableList<>(grpGeneral);
		tileList.type = ObstacleTile.class;
		tileList.setIncludeID(false);
		tileList.setShellFactory(new LWindowFactory<>() {
			@Override
			public LObjectDialog<ObstacleTile> createWindow(LWindow parent) {
				return new ObstacleTileDialog(parent);
			}
		});
		tileList.getCellData().setExpand(true, true);
		tileList.getCellData().setMinimumSize(0, 60);
		tileList.addMenu(lblTiles);
		addChild(tileList, "tiles");
		
		// Graphics
		
		LFrame grpGraphics = new LFrame(left, Vocab.instance.GRAPHICS);
		grpGraphics.setGridLayout(1);
		grpGraphics.setHoverText(Tooltip.instance.GRAPHICS);
		grpGraphics.getCellData().setExpand(true, true);
		LImage imgGraphics = new LImage(grpGraphics);
		imgGraphics.getCellData().setExpand(true, true);
		imgGraphics.setAlignment(LFlags.TOP | LFlags.LEFT);
		IconButton btnGraphics = new IconButton(grpGraphics, false);
		btnGraphics.addMenu(grpGraphics);
		btnGraphics.addMenu(imgGraphics);
		btnGraphics.setImageWidget(imgGraphics);
		addControl(btnGraphics, "image");

		// Transform

		LFrame grpTransform = new LFrame(right, Vocab.instance.TRANSFORM);
		grpTransform.setFillLayout(true);
		grpTransform.setHoverText(Tooltip.instance.TRANSFORM);
		grpTransform.getCellData().setExpand(true, false);
		TransformEditor transformEditor = new TransformEditor(grpTransform);
		transformEditor.addMenu(grpTransform);
		addChild(transformEditor, "transform");
		transformEditor.addSelectionListener(event -> {
            Obstacle o = contentEditor.getObject();
            if (o == null)
                return;
            Animation a = (Animation) Project.current.animations.getData().get(o.image.id);
            if (a == null)
                return;
            transformEditor.secondaryTransform = a.transform;
        });
		transformEditor.setImage(imgGraphics);
		btnGraphics.setTransform(transformEditor);

		new LLabel(right, 1, 1);
	}

	@Override
	protected GObjectTreeSerializer getSerializer() {
		return Project.current.obstacles;
	}

}
