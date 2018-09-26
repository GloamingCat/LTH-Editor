package gui.views.database.content;

import gui.Vocab;
import gui.helper.FieldPainter;
import gui.views.database.DatabaseTab;
import gui.views.database.subcontent.AudioEditor;
import gui.views.database.subcontent.TagList;
import gui.widgets.QuadButton;

import java.util.ArrayList;

import lwt.editor.LComboView;
import lwt.event.LControlEvent;
import lwt.event.listener.LControlListener;
import lwt.widget.LCheckButton;
import lwt.widget.LImage;
import lwt.widget.LSpinner;

import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Group;
import org.eclipse.swt.widgets.Label;

import data.subcontent.Quad;
import project.GObjectTreeSerializer;
import project.Project;

public class TerrainTab extends DatabaseTab {

	/**
	 * Create the composite.
	 * @param parent
	 * @param style
	 */
	public TerrainTab(Composite parent, int style) {
		super(parent, style);
		
		LControlListener<Integer> intListener = new LControlListener<Integer>() {
			@Override
			public void onModify(LControlEvent<Integer> event) {
				FieldPainter.reload();
			}
		};
		LControlListener<Quad> imgListener = new LControlListener<Quad>() {
			@Override
			public void onModify(LControlEvent<Quad> event) {
				FieldPainter.reload();
			}
		};
		
		contentEditor.setLayout(new GridLayout(2, false));
		
		grpGeneral.setLayout(new GridLayout(3, false));
		grpGeneral.setLayoutData(new GridData(SWT.FILL, SWT.TOP, true, false, 1, 1));
		
		LCheckButton btnPassable = new LCheckButton(grpGeneral, SWT.CHECK);
		btnPassable.setText(Vocab.instance.PASSABLE);
		addControl(btnPassable, "passable");
		
		Label lblMoveCost = new Label(grpGeneral, SWT.NONE);
		lblMoveCost.setText(Vocab.instance.MOVECOST);
		
		LSpinner spnCost = new LSpinner(grpGeneral, SWT.NONE);
		spnCost.setLayoutData(new GridData(SWT.FILL, SWT.TOP, false, false, 2, 1));
		addControl(spnCost, "moveCost");
		
		Label lblStatus = new Label(grpGeneral, SWT.NONE);
		lblStatus.setText(Vocab.instance.STATUS);
		
		LComboView cmbStatus = new LComboView(grpGeneral, SWT.NONE) {
			public ArrayList<Object> getArray() {
				//TODO return Project.current.status.getList();
				return null;
			}
		};
		cmbStatus.setOptional(true);
		cmbStatus.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 2, 1));
		addControl(cmbStatus, "statusID");

		Composite buttons = new Composite(grpGeneral, SWT.NONE);
		GridLayout gl_buttons = new GridLayout(2, false);
		gl_buttons.marginWidth = 0;
		gl_buttons.marginHeight = 0;
		buttons.setLayout(gl_buttons);
		buttons.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 3, 1));
		
		LCheckButton btnRemoveOnExit = new LCheckButton(buttons, SWT.NONE);
		btnRemoveOnExit.setText(Vocab.instance.REMOVEONEXIT);
		addControl(btnRemoveOnExit, "removeOnExit");
		
		LCheckButton btnResetTime = new LCheckButton(buttons, SWT.NONE);
		btnResetTime.setText(Vocab.instance.RESETTIME);
		addControl(btnResetTime, "resetTime");
		
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
		spnFrameCount.addModifyListener(intListener);
		
		LImage imgGraphics = new LImage(grpGraphics, SWT.NONE);
		imgGraphics.setImage("/javax/swing/plaf/basic/icons/image-delayed.png");
		imgGraphics.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true, 1, 3));
		imgGraphics.setHorizontalAlign(SWT.CENTER);
		imgGraphics.setVerticalAlign(SWT.CENTER);
		
		Label lblFrameDuration = new Label(grpGraphics, SWT.NONE);
		lblFrameDuration.setText(Vocab.instance.DURATION);
		
		LSpinner spnDuration = new LSpinner(grpGraphics, SWT.NONE);
		spnDuration.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, false, false, 1, 1));
		addControl(spnDuration, "duration");
		
		QuadButton btnSelect = new QuadButton(grpGraphics, SWT.NONE);
		btnSelect.setLayoutData(new GridData(SWT.RIGHT, SWT.TOP, false, false, 2, 1));
		
		addQuadButton(btnSelect, imgGraphics, "Terrain", "quad");
		btnSelect.addModifyListener(imgListener);
	}

	@Override
	protected GObjectTreeSerializer getSerializer() {
		return Project.current.terrains;
	}

}
