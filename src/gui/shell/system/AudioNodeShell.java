package gui.shell.system;

import gui.Vocab;
import gui.shell.ObjectShell;
import gui.widgets.FileSelector;

import java.io.File;

import lwt.LSoundPlayer;
import lwt.widget.LLabel;
import lwt.widget.LSpinner;
import lwt.widget.LText;

import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.SashForm;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Shell;

import project.Project;

import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.widgets.Composite;

import data.subcontent.Audio;

import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;

public class AudioNodeShell extends ObjectShell<Audio.Node> {
	
	protected FileSelector selFile;
	protected boolean isBGM = false;
	
	public static final int BGM = 0x01;
	
	/**
	 * @wbp.parser.constructor
	 */
	public AudioNodeShell(Shell parent) {
		this(parent, 1);
	}
	
	public AudioNodeShell(Shell parent, int style) {
		super(parent);

		setMinimumSize(400, 400);
		contentEditor.setLayout(new FillLayout(SWT.HORIZONTAL));
		SashForm form = new SashForm(contentEditor, SWT.NONE);
		selFile = new FileSelector(form, style) {
			@Override
			protected boolean isValidFile(File f) {
				String name = f.getName();
				return name.endsWith(".ogg") || name.endsWith(".mp3") || name.endsWith(".wav");
			}
		};
		selFile.setFolder(Project.current.audioPath());
		
		isBGM = style / 2 == 1;
		
		Composite composite = new Composite(form, SWT.NONE);
		composite.setLayout(new GridLayout(2, false));
		composite.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true, 1, 1));

		new LLabel(composite, Vocab.instance.KEY);
		
		LText txtKey = new LText(composite);;
		txtKey.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1));
		addControl(txtKey, "key");
		
		new LLabel(composite, Vocab.instance.VOLUME);
		
		LSpinner spnVolume = new LSpinner(composite, SWT.NONE);
		spnVolume.setMaximum(1000);
		spnVolume.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1));
		addControl(spnVolume, "volume");
		
		new LLabel(composite, Vocab.instance.PITCH);
		
		LSpinner spnPitch = new LSpinner(composite, SWT.NONE);
		spnPitch.setMaximum(1000);
		spnPitch.setMinimum(1);
		spnPitch.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1));
		addControl(spnPitch, "pitch");
		
		new LLabel(composite, Vocab.instance.TIME);
		
		LSpinner spnTime = new LSpinner(composite, SWT.NONE);
		spnTime.setMaximum(10000);
		spnTime.setMinimum(0);
		spnTime.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, false, false, 1, 1));
		addControl(spnTime, "time");
		
		Composite reproduction = new Composite(composite, SWT.NONE);
		GridLayout gl_reproduction = new GridLayout(2, false);
		gl_reproduction.marginWidth = 0;
		gl_reproduction.marginHeight = 0;
		reproduction.setLayout(gl_reproduction);
		reproduction.setLayoutData(new GridData(SWT.RIGHT, SWT.BOTTOM, false, true, 2, 1));
		
		Button btnPlay = new Button(reproduction, SWT.NONE);
		btnPlay.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				String name = selFile.getSelectedFile();
				if (name == null || name.isEmpty())
					return;
				String path = Project.current.audioPath() + name;
				float volume = spnVolume.getValue() * 0.1f;
				float pitch = spnPitch.getValue() * 0.1f;
				if (isBGM)
					LSoundPlayer.playBGM(path, volume, pitch);
				else
					LSoundPlayer.playSFX(path, volume, pitch);
			}
		});
		btnPlay.setText(Vocab.instance.PLAY);
		
		Button btnStop = new Button(reproduction, SWT.NONE);
		btnStop.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				LSoundPlayer.stop();
			}
		});
		btnStop.setText(Vocab.instance.STOP);
		
		form.setWeights(new int[] { 1, 1 });
		
		pack();
	}
	
	public void open(Audio.Node initial) {
		super.open(initial);
		selFile.setSelectedFile(initial.name);
	}

	@Override
	protected Audio.Node createResult(Audio.Node initial) {
		Audio audio = (Audio) contentEditor.getObject();
		audio.name = selFile.getSelectedFile();
		if (audio.name == null) {
			audio.name = "";
		}
		return super.createResult(initial);
	}
	
}
