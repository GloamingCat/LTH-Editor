package gui.views.database.content;

import gui.Vocab;
import gui.views.database.DatabaseTab;
import gui.views.database.ImageButton;
import gui.views.database.subcontent.AudioEditor;
import gui.views.database.subcontent.TagEditor;

import java.util.ArrayList;

import lwt.editor.LComboView;
import lwt.widget.LCheckButton;
import lwt.widget.LSpinner;

import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Group;
import org.eclipse.swt.widgets.Label;
import org.eclipse.wb.swt.SWTResourceManager;

import project.ListSerializer;
import project.Project;

public class TerrainTab extends DatabaseTab {

	/**
	 * Create the composite.
	 * @param parent
	 * @param style
	 */
	public TerrainTab(Composite parent, int style) {
		super(parent, style);
		
		grpGeneral.setLayout(new GridLayout(3, false));
		
		LCheckButton btnPassable = new LCheckButton(grpGeneral, SWT.CHECK);
		btnPassable.setText(Vocab.instance.PASSABLE);
		addControl(btnPassable, "passable");
		
		Label lblStatus = new Label(grpGeneral, SWT.NONE);
		lblStatus.setText(Vocab.instance.STATUS);
		
		LComboView cmbStatus = new LComboView(grpGeneral, SWT.NONE) {
			public ArrayList<Object> getArray() {
				return Project.current.status.getList();
			}
		};
		cmbStatus.setOptional(true);
		cmbStatus.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1));
		addControl(cmbStatus, "statusID");
		
		LCheckButton btnRemoveOnExit = new LCheckButton(grpGeneral, SWT.NONE);
		btnRemoveOnExit.setText(Vocab.instance.REMOVEONEXIT);
		addControl(btnRemoveOnExit, "removeOnExit");
		
		Label lblLifeTime = new Label(grpGeneral, SWT.NONE);
		lblLifeTime.setText(Vocab.instance.LIFETIME);
		
		LSpinner spnLifeTime = new LSpinner(grpGeneral, SWT.NONE);
		spnLifeTime.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1));
		addControl(spnLifeTime, "lifeTime");
		
		Label lblSeconds = new Label(grpGeneral, SWT.NONE);
		lblSeconds.setText(Vocab.instance.SECONDS);
		
		Group grpGraphics = new Group(contentEditor, SWT.NONE);
		grpGraphics.setText("Graphics");
		grpGraphics.setLayout(new GridLayout(2, false));
		grpGraphics.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true, 1, 1));
		
		Label lblFrames = new Label(grpGraphics, SWT.NONE);
		lblFrames.setText(Vocab.instance.FRAMECOUNT);
		
		LSpinner spnFrameCount = new LSpinner(grpGraphics, SWT.NONE);
		spnFrameCount.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1));
		addControl(spnFrameCount, "frameCount");
		
		Label lblFrameDuration = new Label(grpGraphics, SWT.NONE);
		lblFrameDuration.setText(Vocab.instance.DURATION);
		
		LSpinner spnDuration = new LSpinner(grpGraphics, SWT.NONE);
		spnDuration.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1));
		addControl(spnDuration, "duration");
		
		ImageButton btnSelect = new ImageButton(grpGraphics, SWT.NONE);
		btnSelect.setLayoutData(new GridData(SWT.RIGHT, SWT.CENTER, false, false, 1, 1));
		
		Label imgIcon = new Label(grpGraphics, SWT.NONE);
		imgIcon.setImage(SWTResourceManager.getImage(ItemTab.class, "/javax/swing/plaf/basic/icons/image-delayed.png"));
		imgIcon.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true, 1, 1));
		
		addImageButton(btnSelect, imgIcon, "Terrain", "imagePath");

		Composite bottom = new Composite(contentEditor, SWT.NONE);
		bottom.setLayout(new GridLayout(2, false));
		bottom.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true, 1, 1));
		
		AudioEditor audioEditor = new AudioEditor(bottom, SWT.NONE);
		audioEditor.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true, 1, 1));
		addChild(audioEditor);
		
		Group grpTags = new Group(bottom, SWT.NONE);
		grpTags.setLayout(new FillLayout(SWT.HORIZONTAL));
		grpTags.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true, 1, 1));
		grpTags.setText(Vocab.instance.TAGS);
		
		TagEditor tagEditor = new TagEditor(grpTags, SWT.NONE);
		addChild(tagEditor);
	}

	@Override
	protected ListSerializer getSerializer() {
		return Project.current.terrains;
	}

}
