package gui.views.database.content;

import gui.Vocab;
import gui.views.ImageButton;
import gui.views.ScriptButton;
import gui.views.database.DatabaseTab;
import gui.views.database.subcontent.AudioEditor;
import gui.views.database.subcontent.TransformEditor;
import lwt.widget.LCheckButton;
import lwt.widget.LSpinner;

import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Group;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Text;
import org.eclipse.wb.swt.SWTResourceManager;

import project.GObjectListSerializer;

public class AnimationTab extends DatabaseTab {

	/**
	 * Create the composite.
	 * @param parent
	 * @param style
	 */
	public AnimationTab(Composite parent, int style) {
		super(parent, style);
		
		contentEditor.setLayout(new GridLayout(2, false));
		grpGeneral.setLayout(new GridLayout(2, false));
		grpGeneral.setLayoutData(new GridData(SWT.FILL, SWT.TOP, false, false, 1, 1));
		txtName.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, true, 1, 1));
		
		Label lblScript = new Label(grpGeneral,  SWT.NONE);
		lblScript.setText(Vocab.instance.SCRIPT);

		Composite script = new Composite(grpGeneral,  SWT.NONE);
		script.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, false, false, 1, 1));
		GridLayout gl_script = new GridLayout(2, false);
		gl_script.marginWidth = 0;
		gl_script.marginHeight = 0;
		script.setLayout(gl_script);
		
		Text txtScript = new Text(script, SWT.BORDER | SWT.READ_ONLY);
		txtScript.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1));
		
		ScriptButton btnScript = new ScriptButton(script, SWT.NONE);
		btnScript.optional = true;
		btnScript.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, false, false, 1, 1));
		addScriptButton(btnScript, txtScript, null, "animation", "script");
		
		Group grpImg = new Group(contentEditor, SWT.NONE);
		grpImg.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true, 1, 5));
		GridLayout gl_grpImg = new GridLayout(1, false);
		gl_grpImg.marginWidth = 0;
		gl_grpImg.marginHeight = 0;
		grpImg.setLayout(gl_grpImg);
		grpImg.setText(Vocab.instance.GRAPHICS);
		
		Label lblColumns = new Label(grpGeneral, SWT.NONE);
		lblColumns.setText(Vocab.instance.COLUMNS);
		
		LSpinner spnCols = new LSpinner(grpGeneral, SWT.NONE);
		spnCols.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1));
		addControl(spnCols, "cols");
		
		ImageButton btnSelectImage = new ImageButton(grpImg, SWT.NONE);
		btnSelectImage.setLayoutData(new GridData(SWT.LEFT, SWT.TOP, false, false, 1, 1));
		
		Label image = new Label(grpImg, SWT.NONE);
		GridData gd_image = new GridData(SWT.FILL, SWT.FILL, true, true, 1, 1);
		gd_image.heightHint = 99;
		image.setLayoutData(gd_image);
		image.setImage(SWTResourceManager.getImage(AnimationTab.class, "/javax/swing/plaf/basic/icons/image-delayed.png"));
		
		addImageButton(btnSelectImage, image, "Animation/" + getFolder(), "imagePath");
		
		Label lblRows = new Label(grpGeneral, SWT.NONE);
		lblRows.setText(Vocab.instance.ROWS);
		
		LSpinner spnRows = new LSpinner(grpGeneral, SWT.NONE);
		spnRows.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1));
		addControl(spnRows, "rows");

		Label lblDuration = new Label(grpGeneral, SWT.NONE);
		lblDuration.setText(Vocab.instance.DURATION);

		LSpinner spnDuration = new LSpinner(grpGeneral, SWT.NONE);
		spnDuration.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1));
		addControl(spnDuration, "duration");
		
		Label lblSound = new Label(grpGeneral, SWT.NONE);
		lblSound.setText(Vocab.instance.SOUND);
		
		AudioEditor audioEditor = new AudioEditor(grpGeneral, SWT.NONE);
		audioEditor.setFolder("sfx");
		audioEditor.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, false, false, 1, 1));
		addChild(audioEditor);
		
		Composite buttons = new Composite(grpGeneral, SWT.NONE);
		buttons.setLayoutData(new GridData(SWT.LEFT, SWT.CENTER, false, false, 2, 1));
		GridLayout gl_buttons = new GridLayout(2, false);
		gl_buttons.marginHeight = 0;
		gl_buttons.marginWidth = 0;
		buttons.setLayout(gl_buttons);

		LCheckButton btnLoop = new LCheckButton(buttons, SWT.NONE);
		btnLoop.setText(Vocab.instance.LOOP);
		addControl(btnLoop, "loop");
		
		LCheckButton btnAllRows = new LCheckButton(buttons, SWT.NONE);
		btnAllRows.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, false, false, 1, 1));
		btnAllRows.setText(Vocab.instance.ALLROWS);
		addControl(btnAllRows, "allRows");
		
		TransformEditor transformTab = new TransformEditor(contentEditor, SWT.NONE);
		GridData gd_transformTab = new GridData(SWT.FILL, SWT.CENTER, false, false, 1, 1);
		gd_transformTab.widthHint = 252;
		transformTab.setLayoutData(gd_transformTab);
		addChild(transformTab);
		new Label(contentEditor, SWT.NONE);
		new Label(contentEditor, SWT.NONE);
		new Label(contentEditor, SWT.NONE);

	}
	
	protected String getFolder() {
		return "";
	}

	@Override
	protected GObjectListSerializer getSerializer() {
		return null;
	}

}
