package gui.shell;

import gui.Vocab;

import java.io.File;

import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Shell;

import project.Project;

import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Spinner;

import data.Audio;

public class AudioShell extends FileShell<Audio> {

	private Spinner spnVolume;
	private Spinner spnPitch;
	private Spinner spnSpeed;
	
	/**
	 * @wbp.parser.constructor
	 */
	public AudioShell(Shell parent) {
		this(parent, "", true);
	}
	
	public AudioShell(Shell parent, String folder, boolean optional) {
		super(parent, folder, optional);

		Composite composite = new Composite(sashForm, SWT.NONE);
		composite.setLayout(new GridLayout(2, false));
		composite.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true, 1, 1));
		
		Label lblVolume = new Label(composite, SWT.NONE);
		lblVolume.setText(Vocab.instance.VOLUME);
		
		spnVolume = new Spinner(composite, SWT.BORDER);
		spnVolume.setMaximum(1000);
		spnVolume.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1));
		
		Label lblPitch = new Label(composite, SWT.NONE);
		lblPitch.setText(Vocab.instance.PITCH);
		
		spnPitch = new Spinner(composite, SWT.BORDER);
		spnPitch.setMaximum(1000);
		spnPitch.setMinimum(1);
		spnPitch.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1));
		
		Label lblSpeed = new Label(composite, SWT.NONE);
		lblSpeed.setText(Vocab.instance.SPEED);
		
		spnSpeed = new Spinner(composite, SWT.BORDER);
		spnSpeed.setMaximum(1000);
		spnSpeed.setMinimum(1);
		spnSpeed.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, false, false, 1, 1));
	}
	
	public void open(Audio initial) {
		super.open(initial);
		int i = indexOf(initial.path);
		list.select(i);
		spnVolume.setSelection(initial.volume);
		spnPitch.setSelection(initial.pitch);
		spnSpeed.setSelection(initial.speed);
	}

	@Override
	protected Audio createResult(Audio initial) {
		int i = list.getSelectionIndex();
		if (i < 0)
			return null;
		String newPath = folder + "/" + list.getItem(i);
		if (newPath.equals(initial) && initial.pitch == spnPitch.getSelection() 
				&& initial.volume == spnVolume.getSelection()
				&& initial.speed == spnSpeed.getSelection()) {
			return null;
		} else {
			return new Audio(newPath, 
					spnVolume.getSelection(), 
					spnPitch.getSelection(),
					spnSpeed.getSelection());
		}
	}

	protected boolean isValidFile(File file) {
		String name = file.getName();
		return name.endsWith(".ogg") || name.endsWith(".mp3") || name.endsWith(".mid");
	}
	
	protected String rootPath() {
		return Project.current.audioPath();
	}
	
}
