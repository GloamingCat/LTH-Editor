package gui.views.database.subcontent;

import gui.Vocab;
import gui.shell.AudioShell;

import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Group;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Text;

import lwt.dialog.LObjectShell;
import lwt.dialog.LShellFactory;
import lwt.editor.LObjectEditor;
import lwt.widget.LSpinner;
import lwt.widget.LStringButton;

public class AudioEditor extends LObjectEditor {

	public AudioEditor(Composite parent, int style) {
		super(parent, style);
		
		setLayout(new FillLayout());
		
		Group grpAudio = new Group(this, SWT.NONE);
		GridLayout gl_grpAudio = new GridLayout(3, false);
		grpAudio.setLayout(gl_grpAudio);
		
		grpAudio.setText(Vocab.instance.SOUND);
		
		Label lblSource = new Label(grpAudio, SWT.NONE);
		lblSource.setLayoutData(new GridData(SWT.RIGHT, SWT.CENTER, false, false, 1, 1));
		lblSource.setText(Vocab.instance.PATH);
		
		Text txtSource = new Text(grpAudio, SWT.BORDER | SWT.READ_ONLY);
		txtSource.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1));
		
		LStringButton btnSelectSound = new LStringButton(grpAudio, SWT.NONE);

		addAudioButton(btnSelectSound, txtSource, "sfx", "soundPath");
		
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
		addControl(spnVolume, "volume");
		
		Label lblPitch = new Label(soundConfig, SWT.NONE);
		lblPitch.setText(Vocab.instance.PITCH);
		
		LSpinner spnPitch = new LSpinner(soundConfig, SWT.NONE);
		spnPitch.setMaximum(200);
		spnPitch.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1));
		addControl(spnPitch, "pitch");
		
	}
	
	public void setObject(Object obj) {
		if (obj == null) {
			super.setObject(null);
		} else {
			Object value = getFieldValue(obj, "audio");
			super.setObject(value);
		}
	}
	
	protected void addAudioButton(LStringButton button, Text text, String folderName, String attName) {
		button.setText(text);
		button.setShellFactory(new LShellFactory<String>() {
			@Override
			public LObjectShell<String> createShell(Shell parent) {
				return new AudioShell(parent, folderName);
			}
		});
		addControl(button, attName);
	}
	
}
