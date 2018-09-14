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

import data.subcontent.Audio;

public class AudioShell extends FileShell<Audio> {

	private Spinner spnVolume;
	private Spinner spnPitch;
	private Spinner spnTime;
	
	/**
	 * @wbp.parser.constructor
	 */
	public AudioShell(Shell parent) {
		this(parent, "", true);
	}
	
	public AudioShell(Shell parent, String folder, boolean optional) {
		super(parent, folder, optional);

		setMinimumSize(400, 400);
		
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
		
		Label lblDelay = new Label(composite, SWT.NONE);
		lblDelay.setText(Vocab.instance.TIME);
		
		spnTime = new Spinner(composite, SWT.BORDER);
		spnTime.setMaximum(10000);
		spnTime.setMinimum(0);
		spnTime.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, false, false, 1, 1));
		sashForm.setWeights(new int[] {5, 4});
	}
	
	public void open(Audio initial) {
		super.open(initial);
		int i = indexOf(initial.path);
		list.select(i);
		spnVolume.setSelection(initial.volume);
		spnPitch.setSelection(initial.pitch);
		spnTime.setSelection(initial.time);
	}

	@Override
	protected Audio createResult(Audio initial) {
		int i = list.getSelectionIndex();
		if (i < 0)
			return null;
		String newPath = folder + "/" + list.getItem(i);
		if (newPath.equals(initial) && initial.pitch == spnPitch.getSelection() 
				&& initial.volume == spnVolume.getSelection()
				&& initial.time == spnTime.getSelection()) {
			return null;
		} else {
			return new Audio(newPath, 
					spnVolume.getSelection(), 
					spnPitch.getSelection(),
					spnTime.getSelection());
		}
	}

	protected boolean isValidFile(File file) {
		String name = file.getName();
		return name.endsWith(".ogg") || name.endsWith(".mp3") || name.endsWith(".wav");
	}
	
	protected String rootPath() {
		return Project.current.audioPath();
	}
	
}
