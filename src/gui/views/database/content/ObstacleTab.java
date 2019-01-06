package gui.views.database.content;

import gson.project.GObjectTreeSerializer;
import gui.Vocab;
import gui.views.database.DatabaseTab;
import gui.views.database.subcontent.TagList;
import gui.views.database.subcontent.ObstacleTileList;
import gui.views.database.subcontent.TransformEditor;
import gui.widgets.IconButton;
import lwt.widget.LImage;

import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Group;

import project.Project;

public class ObstacleTab extends DatabaseTab {

	/**
	 * Create the composite.
	 * @param parent
	 * @param style
	 */
	public ObstacleTab(Composite parent, int style) {
		super(parent, style);

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
		btnGraphics.setImage(imgGraphics);
		addControl(btnGraphics, "image");
		
		Group grpTransform = new Group(other, SWT.NONE);
		grpTransform.setText(Vocab.instance.TRANSFORM);
		grpTransform.setLayout(new FillLayout());
		grpTransform.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, false, 1, 1));
		
		TransformEditor transformEditor = new TransformEditor(grpTransform, SWT.NONE);
		addChild(transformEditor, "transform");
	
		Group grpTiles = new Group(lateral, SWT.NONE);
		grpTiles.setLayout(new FillLayout());
		grpTiles.setText(Vocab.instance.TILES);
		
		ObstacleTileList tileList = new ObstacleTileList(grpTiles, SWT.NONE);
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
