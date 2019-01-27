package gui.shell;

import gui.Vocab;
import gui.widgets.FileSelector;

import java.io.File;

import lwt.widget.LSpinner;

import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.SashForm;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Shell;

import project.Project;

import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Label;

import data.subcontent.Audio;

public class AudioShell extends ObjectShell<Audio> {
	
	protected FileSelector selFile;
	
	/**
	 * @wbp.parser.constructor
	 */
	public AudioShell(Shell parent) {
		this(parent, "", 1);
	}
	
	public AudioShell(Shell parent, String folder, int optional) {
		super(parent);

		setMinimumSize(400, 400);
		contentEditor.setLayout(new FillLayout(SWT.HORIZONTAL));
		SashForm form = new SashForm(contentEditor, SWT.NONE);
		selFile = new FileSelector(form, optional) {
			@Override
			protected String rootPath() {
				return Project.current.audioPath();
			}
			@Override
			protected boolean isValidFile(File f) {
				String name = f.getName();
				return name.endsWith(".ogg") || name.endsWith(".mp3") || name.endsWith(".wav");
			}
		};
		selFile.setFolder(folder);
		
		Composite composite = new Composite(form, SWT.NONE);
		composite.setLayout(new GridLayout(2, false));
		composite.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true, 1, 1));
		
		Label lblVolume = new Label(composite, SWT.NONE);
		lblVolume.setText(Vocab.instance.VOLUME);
		
		LSpinner spnVolume = new LSpinner(composite, SWT.NONE);
		spnVolume.setMaximum(1000);
		spnVolume.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1));
		addControl(spnVolume, "volume");
		
		Label lblPitch = new Label(composite, SWT.NONE);
		lblPitch.setText(Vocab.instance.PITCH);
		
		LSpinner spnPitch = new LSpinner(composite, SWT.NONE);
		spnPitch.setMaximum(1000);
		spnPitch.setMinimum(1);
		spnPitch.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1));
		addControl(spnPitch, "pitch");
		
		Label lblDelay = new Label(composite, SWT.NONE);
		lblDelay.setText(Vocab.instance.TIME);
		
		LSpinner spnTime = new LSpinner(composite, SWT.NONE);
		spnTime.setMaximum(10000);
		spnTime.setMinimum(0);
		spnTime.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, false, false, 1, 1));
		addControl(spnTime, "time");
		
		form.setWeights(new int[] { 1, 1 });
		
		pack();
	}
	
	public void open(Audio initial) {
		super.open(initial);
		selFile.setSelectedFile(initial.name);
	}

	@Override
	protected Audio createResult(Audio initial) {
		Audio audio = (Audio) contentEditor.getObject();
		audio.name = selFile.getSelectedFile();
		if (audio.name == null)
			audio.name = "";
		return super.createResult(initial);
	}
	
}
