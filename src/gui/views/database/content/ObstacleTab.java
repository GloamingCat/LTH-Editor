package gui.views.database.content;

import gson.project.GObjectTreeSerializer;
import gui.Vocab;
import gui.shell.database.ObstacleTileShell;
import gui.views.database.DatabaseTab;
import gui.views.database.subcontent.TagList;
import gui.views.database.subcontent.TransformEditor;
import gui.widgets.IconButton;
import gui.widgets.SimpleEditableList;
import lwt.dialog.LObjectShell;
import lwt.dialog.LShellFactory;
import lwt.widget.LImage;

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

public class ObstacleTab extends DatabaseTab {

	/**
	 * Create the composite.
	 * @param parent
	 */
	public ObstacleTab(Composite parent) {
		super(parent);

		Composite lateral = new Composite(contentEditor, SWT.NONE);
		lateral.setLayout(new FillLayout(SWT.VERTICAL));
		lateral.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true, 1, 2));

		Composite other = new Composite(contentEditor, SWT.NONE);
		other.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true, 1, 1));
		GridLayout gl_other = new GridLayout(1, false);
		gl_other.marginWidth = 0;
		gl_other.marginHeight = 0;
		other.setLayout(gl_other);
		
		Group grpGraphics = new Group(other, SWT.NONE);
		grpGraphics.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true, 1, 1));
		grpGraphics.setText(Vocab.instance.GRAPHICS);
		grpGraphics.setLayout(new GridLayout());

		LImage imgGraphics = new LImage(grpGraphics, SWT.NONE);
		imgGraphics.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true, 1, 1));
		
		IconButton btnGraphics = new IconButton(grpGraphics, 0);
		btnGraphics.setLayoutData(new GridData(SWT.LEFT, SWT.FILL, false, false, 1, 1));
		addControl(btnGraphics, "image");
		
		Group grpTransform = new Group(other, SWT.NONE);
		grpTransform.setText(Vocab.instance.TRANSFORM);
		grpTransform.setLayout(new FillLayout());
		grpTransform.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, false, 1, 1));
		
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
	
		Group grpTiles = new Group(lateral, SWT.NONE);
		grpTiles.setLayout(new FillLayout());
		grpTiles.setText(Vocab.instance.TILES);
		
		SimpleEditableList<ObstacleTile> tileList = new SimpleEditableList<ObstacleTile>(grpTiles, SWT.NONE);
		tileList.type = ObstacleTile.class;
		tileList.setIncludeID(false);
		tileList.setShellFactory(new LShellFactory<ObstacleTile>() {
			@Override
			public LObjectShell<ObstacleTile> createShell(Shell parent) {
				return new ObstacleTileShell(parent);
			}
		});
		addChild(tileList, "tiles");
		
		Group grpTags = new Group(lateral, SWT.NONE);
		grpTags.setLayout(new FillLayout());
		grpTags.setText(Vocab.instance.TAGS);
		
		TagList lstTags = new TagList(grpTags, SWT.NONE);
		addChild(lstTags, "tags");
		
	}

	@Override
	protected GObjectTreeSerializer getSerializer() {
		return Project.current.obstacles;
	}

}
