package gui.views.database.content;

import gui.Vocab;
import gui.shell.AudioShell;
import gui.shell.ImageShell;
import gui.views.database.DatabaseTab;
import gui.views.database.subcontent.TransformEditor;
import lwt.dialog.LObjectShell;
import lwt.dialog.LShellFactory;
import lwt.widget.LSpinner;
import lwt.widget.LStringButton;

import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Group;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Text;
import org.eclipse.wb.swt.SWTResourceManager;

import project.ListSerializer;
import project.Project;

public class AnimationTab extends DatabaseTab {

	/**
	 * Create the composite.
	 * @param parent
	 * @param style
	 */
	public AnimationTab(Composite parent, int style) {
		super(parent, style);
		
		grpGeneral.setLayout(new GridLayout(3, false));
		txtName.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, true, 2, 1));
		
		Label lblColumns = new Label(grpGeneral, SWT.NONE);
		lblColumns.setText(Vocab.instance.COLUMNS);
		
		LSpinner spnCols = new LSpinner(grpGeneral, SWT.NONE);
		spnCols.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1));
		contentEditor.addControl(spnCols, "cols");
		
		Composite imageComp = new Composite(grpGeneral, SWT.NONE);
		imageComp.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true, 1, 3));
		GridLayout gl_imageComp = new GridLayout(2, false);
		gl_imageComp.marginWidth = 0;
		gl_imageComp.marginHeight = 0;
		imageComp.setLayout(gl_imageComp);
		
		LStringButton btnSelectImage = new LStringButton(imageComp, SWT.NONE) {
			@Override
			protected String getImagePath() {
				return Project.current.imagePath();
			}
		};
		btnSelectImage.setLayoutData(new GridData(SWT.LEFT, SWT.CENTER, false, true, 1, 1));
		
		Label image = new Label(imageComp, SWT.NONE);
		GridData gd_image = new GridData(SWT.FILL, SWT.FILL, true, true, 1, 1);
		gd_image.heightHint = 96;
		image.setLayoutData(gd_image);
		image.setImage(SWTResourceManager.getImage(AnimationTab.class, "/javax/swing/plaf/basic/icons/image-delayed.png"));
		
		btnSelectImage.setLabel(image);
		btnSelectImage.setShellFactory(new LShellFactory<String>() {
			@Override
			public LObjectShell<String> createShell(Shell parent) {
				return new ImageShell(parent, "Animation");
			}
		});
		
		contentEditor.addControl(btnSelectImage, "imagePath");
		
		Label lblRows = new Label(grpGeneral, SWT.NONE);
		lblRows.setText(Vocab.instance.ROWS);
		
		LSpinner spnRows = new LSpinner(grpGeneral, SWT.NONE);
		spnRows.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1));
		contentEditor.addControl(spnRows, "rows");

		Label lblFrameDuration = new Label(grpGeneral, SWT.NONE);
		lblFrameDuration.setLayoutData(new GridData(SWT.LEFT, SWT.TOP, false, false, 1, 1));
		lblFrameDuration.setText(Vocab.instance.FRAMEDURATION);

		LSpinner spnFrameDuration = new LSpinner(grpGeneral, SWT.NONE);
		spnFrameDuration.setLayoutData(new GridData(SWT.FILL, SWT.TOP, true, false, 1, 1));
		contentEditor.addControl(spnFrameDuration, "frameDuration");
		
		TransformEditor transformTab = new TransformEditor(contentEditor, SWT.NONE);
		transformTab.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1));
		contentEditor.addChild(transformTab);

		Group grpAudio = new Group(contentEditor, SWT.NONE);
		GridLayout gl_grpAudio = new GridLayout(3, false);
		grpAudio.setLayout(gl_grpAudio);
		grpAudio.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1));
		grpAudio.setText(Vocab.instance.SOUND);
		
		Label lblSource = new Label(grpAudio, SWT.NONE);
		lblSource.setLayoutData(new GridData(SWT.RIGHT, SWT.CENTER, false, false, 1, 1));
		lblSource.setText(Vocab.instance.PATH);
		
		Text txtSource = new Text(grpAudio, SWT.NONE | SWT.READ_ONLY);
		txtSource.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1));
		
		LStringButton btnSelectSound = new LStringButton(grpAudio, SWT.NONE);
		
		btnSelectImage.setText(txtSource);
		btnSelectImage.setShellFactory(new LShellFactory<String>() {
			@Override
			public LObjectShell<String> createShell(Shell parent) {
				return new AudioShell(parent, "sfx");
			}
		});
		contentEditor.addControl(btnSelectSound, "soundPath");
		
		Composite soundConfig = new Composite(grpAudio, SWT.NONE);
		GridLayout gl_soundConfig = new GridLayout(4, false);
		gl_soundConfig.marginWidth = 0;
		gl_soundConfig.marginHeight = 0;
		soundConfig.setLayout(gl_soundConfig);
		soundConfig.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 3, 1));
		
		Label lblVolume = new Label(soundConfig, SWT.NONE);
		lblVolume.setText(Vocab.instance.VOLUME);
		
		LSpinner spnVolume = new LSpinner(soundConfig, SWT.NONE);
		spnVolume.setMaximum(200);
		spnVolume.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1));
		contentEditor.addControl(spnVolume, "volume");
		
		Label lblPitch = new Label(soundConfig, SWT.NONE);
		lblPitch.setText(Vocab.instance.PITCH);
		
		LSpinner spnPitch = new LSpinner(soundConfig, SWT.NONE);
		spnPitch.setMaximum(200);
		spnPitch.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1));
		contentEditor.addControl(spnPitch, "pitch");
	}

	@Override
	protected ListSerializer getSerializer() {
		return null;
	}

}
