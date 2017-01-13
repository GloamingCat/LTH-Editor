package gui.views.database.content;

import gui.Vocab;
import gui.views.database.DatabaseTab;
import gui.views.database.subcontent.QuadEditor;
import gui.views.database.subcontent.TagList;
import gui.views.database.subcontent.TileList;
import gui.views.database.subcontent.TransformEditor;

import lwt.widget.LSpinner;

import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Group;
import org.eclipse.swt.widgets.Label;

import project.ListSerializer;
import project.Project;

public class ObstacleTab extends DatabaseTab {

	/**
	 * Create the composite.
	 * @param parent
	 * @param style
	 */
	public ObstacleTab(Composite parent, int style) {
		super(parent, style);
		
		contentEditor.setLayout(new GridLayout(2, false));
		
		Composite lateral = new Composite(contentEditor, SWT.NONE);
		lateral.setLayout(new FillLayout(SWT.VERTICAL));
		lateral.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true, 1, 2));
		
		Label lblHeight = new Label(grpGeneral, SWT.NONE);
		lblHeight.setText(Vocab.instance.HEIGHT);
		
		LSpinner spnHeight = new LSpinner(grpGeneral, SWT.NONE);
		spnHeight.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, false, false, 1, 1));
		addControl(spnHeight, "colliderHeight");

		Composite other = new Composite(contentEditor, SWT.NONE);
		other.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true, 1, 1));
		GridLayout gl_other = new GridLayout(1, false);
		gl_other.marginWidth = 0;
		gl_other.marginHeight = 0;
		other.setLayout(gl_other);
		
		Group grpGraphics = new Group(other, SWT.NONE);
		grpGraphics.setLayoutData(new GridData(SWT.FILL, SWT.FILL, false, true, 1, 1));
		grpGraphics.setText("Graphics");
		grpGraphics.setLayout(new FillLayout());

		QuadEditor quadComp = new QuadEditor(grpGraphics, 0) {
			@Override
			protected String getFolder() {
				return "Obstacle";
			}
		};
		addChild(quadComp);
		
		Group grpTags = new Group(lateral, SWT.NONE);
		grpTags.setLayout(new FillLayout());
		grpTags.setText(Vocab.instance.TAGS);
		
		TagList tagEditor = new TagList(grpTags, SWT.NONE);
		addChild(tagEditor);
		
		TransformEditor transformEditor = new TransformEditor(other, SWT.NONE);
		transformEditor.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, false, 1, 1));
		addChild(transformEditor);
		transformEditor.setLabel(quadComp.getLabel());
	
		Group grpTiles = new Group(lateral, SWT.NONE);
		grpTiles.setLayout(new FillLayout());
		grpTiles.setText(Vocab.instance.TILES);
		
		TileList tileList = new TileList(grpTiles, SWT.NONE);
		addChild(tileList);
		
	}

	@Override
	protected ListSerializer getSerializer() {
		return Project.current.obstacles;
	}

}
