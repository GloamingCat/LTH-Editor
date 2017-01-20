package gui.views.database.content;

import gui.Vocab;
import gui.views.common.ImageButton;
import gui.views.database.DatabaseTab;
import gui.views.database.subcontent.AudioEditor;
import gui.views.database.subcontent.TransformEditor;
import lwt.widget.LSpinner;

import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Label;
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
		
		grpGeneral.setLayout(new GridLayout(3, false));
		txtName.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, true, 1, 1));

		Composite imageComp = new Composite(grpGeneral, SWT.NONE);
		imageComp.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true, 1, 5));
		GridLayout gl_imageComp = new GridLayout(2, false);
		gl_imageComp.marginWidth = 0;
		gl_imageComp.marginHeight = 0;
		imageComp.setLayout(gl_imageComp);
		
		Label lblColumns = new Label(grpGeneral, SWT.NONE);
		lblColumns.setText(Vocab.instance.COLUMNS);
		
		LSpinner spnCols = new LSpinner(grpGeneral, SWT.NONE);
		spnCols.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1));
		addControl(spnCols, "cols");
		
		ImageButton btnSelectImage = new ImageButton(imageComp, SWT.NONE);
		btnSelectImage.setLayoutData(new GridData(SWT.LEFT, SWT.CENTER, false, true, 1, 1));
		
		Label image = new Label(imageComp, SWT.NONE);
		GridData gd_image = new GridData(SWT.FILL, SWT.FILL, true, true, 1, 1);
		gd_image.heightHint = 99;
		image.setLayoutData(gd_image);
		image.setImage(SWTResourceManager.getImage(AnimationTab.class, "/javax/swing/plaf/basic/icons/image-delayed.png"));
		
		addImageButton(btnSelectImage, image, "Animation", "imagePath");
		
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
		audioEditor.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, false, false, 1, 1));
		addChild(audioEditor);
		
		TransformEditor transformTab = new TransformEditor(contentEditor, SWT.NONE);
		transformTab.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1));
		addChild(transformTab);

	}

	@Override
	protected GObjectListSerializer getSerializer() {
		return null;
	}

}
