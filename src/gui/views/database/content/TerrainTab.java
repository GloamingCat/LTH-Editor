package gui.views.database.content;

import gui.Vocab;
import gui.views.ImageButton;
import gui.views.database.DatabaseTab;
import gui.views.database.subcontent.AudioEditor;
import gui.views.database.subcontent.TagList;

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

import project.GObjectListSerializer;
import project.Project;

public class TerrainTab extends DatabaseTab {

	/**
	 * Create the composite.
	 * @param parent
	 * @param style
	 */
	public TerrainTab(Composite parent, int style) {
		super(parent, style);
		
		contentEditor.setLayout(new GridLayout(2, false));
		
		grpGeneral.setLayout(new GridLayout(3, false));
		grpGeneral.setLayoutData(new GridData(SWT.FILL, SWT.TOP, true, false, 1, 1));
		
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
		
		Label lblAudio = new Label(grpGeneral, SWT.NONE);
		lblAudio.setText(Vocab.instance.SOUND);
		
		AudioEditor audioEditor = new AudioEditor(grpGeneral, SWT.NONE);
		audioEditor.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, true, 2, 1));
		addChild(audioEditor);
		
		Group grpTags = new Group(contentEditor, SWT.NONE);
		grpTags.setLayout(new FillLayout(SWT.HORIZONTAL));
		grpTags.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, false, 1, 1));
		grpTags.setText(Vocab.instance.TAGS);
		
		TagList tagEditor = new TagList(grpTags, SWT.NONE);
		addChild(tagEditor);
		
		Group grpGraphics = new Group(contentEditor, SWT.NONE);
		grpGraphics.setText("Graphics");
		grpGraphics.setLayout(new GridLayout(3, false));
		grpGraphics.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true, 2, 1));
		
		Label lblFrames = new Label(grpGraphics, SWT.NONE);
		lblFrames.setText(Vocab.instance.FRAMECOUNT);
		
		LSpinner spnFrameCount = new LSpinner(grpGraphics, SWT.NONE);
		GridData gd_spnFrameCount = new GridData(SWT.FILL, SWT.CENTER, false, false, 1, 1);
		gd_spnFrameCount.widthHint = 68;
		spnFrameCount.setLayoutData(gd_spnFrameCount);
		addControl(spnFrameCount, "frameCount");
		
		Label imgIcon = new Label(grpGraphics, SWT.NONE);
		imgIcon.setImage(SWTResourceManager.getImage(ItemTab.class, "/javax/swing/plaf/basic/icons/image-delayed.png"));
		imgIcon.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true, 1, 3));
		
		Label lblFrameDuration = new Label(grpGraphics, SWT.NONE);
		lblFrameDuration.setText(Vocab.instance.DURATION);
		
		LSpinner spnDuration = new LSpinner(grpGraphics, SWT.NONE);
		spnDuration.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, false, false, 1, 1));
		addControl(spnDuration, "duration");
		
		ImageButton btnSelect = new ImageButton(grpGraphics, SWT.NONE);
		btnSelect.setLayoutData(new GridData(SWT.RIGHT, SWT.TOP, false, false, 2, 1));
		
		addImageButton(btnSelect, imgIcon, "Terrain", "imagePath");
	}

	@Override
	protected GObjectListSerializer getSerializer() {
		return Project.current.terrains;
	}

}
