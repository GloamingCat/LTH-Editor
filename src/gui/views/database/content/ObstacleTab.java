package gui.views.database.content;

import gson.project.GObjectTreeSerializer;
import gui.Vocab;
import gui.shell.database.ObstacleTileShell;
import gui.views.database.DatabaseTab;
import gui.views.database.subcontent.TransformEditor;
import gui.widgets.IconButton;
import gui.widgets.SimpleEditableList;
import lwt.dialog.LObjectShell;
import lwt.dialog.LShellFactory;
import lwt.widget.LImage;
import lwt.widget.LLabel;

import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Group;
import org.eclipse.swt.widgets.Shell;

import data.Animation;
import data.Obstacle;
import data.Obstacle.ObstacleTile;
import data.subcontent.Transform;
import project.Project;

public class ObstacleTab extends DatabaseTab<Obstacle> {

	/**
	 * Create the composite.
	 * @param parent
	 */
	public ObstacleTab(Composite parent) {
		super(parent);

		new LLabel(grpGeneral, Vocab.instance.COLLIDERTILES).
			setLayoutData(new GridData(SWT.LEFT, SWT.TOP, false, false, 1, 1));
		
		SimpleEditableList<ObstacleTile> tileList = new SimpleEditableList<ObstacleTile>(grpGeneral, SWT.NONE);
		tileList.type = ObstacleTile.class;
		tileList.setIncludeID(false);
		tileList.setShellFactory(new LShellFactory<ObstacleTile>() {
			@Override
			public LObjectShell<ObstacleTile> createShell(Shell parent) {
				return new ObstacleTileShell(parent);
			}
		});
		tileList.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true, 1, 1));
		addChild(tileList, "tiles");
		
		// Graphics
		
		Group grpGraphics = new Group(left, SWT.NONE);
		grpGraphics.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true, 1, 1));
		grpGraphics.setText(Vocab.instance.GRAPHICS);
		grpGraphics.setLayout(new GridLayout());

		LImage imgGraphics = new LImage(grpGraphics, SWT.NONE);
		imgGraphics.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true, 1, 1));
		
		IconButton btnGraphics = new IconButton(grpGraphics, 0);
		btnGraphics.setLayoutData(new GridData(SWT.LEFT, SWT.FILL, false, false, 1, 1));
		addControl(btnGraphics, "image");
		
		// Transform
		
		Group grpTransform = new Group(right, SWT.NONE);
		grpTransform.setText(Vocab.instance.TRANSFORM);
		grpTransform.setLayout(new FillLayout());
		grpTransform.setLayoutData(new GridData(SWT.FILL, SWT.TOP, true, true, 1, 1));
		
		TransformEditor transformEditor = new TransformEditor(grpTransform, SWT.NONE) {
			public Transform secondaryTransform() {
				Obstacle o = (Obstacle) contentEditor.getObject();
				Animation a = (Animation) Project.current.animations.getData().get(o.image.id);
				if (a == null)
					return null;
				return a.transform;
			}
		};
		addChild(transformEditor, "transform");
		
		btnGraphics.setImageWidget(imgGraphics);
		btnGraphics.setTransform(transformEditor);
		transformEditor.setImage(imgGraphics);
		
		new LLabel(right, "").setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true, 1, 1));
		
	}

	@Override
	protected GObjectTreeSerializer getSerializer() {
		return Project.current.obstacles;
	}

}
