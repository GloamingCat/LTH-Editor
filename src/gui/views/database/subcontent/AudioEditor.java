package gui.views.database.subcontent;

import gui.widgets.AudioButton;

import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;

import lwt.editor.LObjectEditor;
import lwt.widget.LText;

public class AudioEditor extends LObjectEditor {

	AudioButton btnSelectSound;
	
	public AudioEditor(Composite parent, int style) {
		super(parent, style);
		
		GridLayout gridLayout = new GridLayout(2, false);
		gridLayout.marginWidth = 0;
		gridLayout.marginHeight = 0;
		setLayout(gridLayout);

		LText txtSource = new LText(this, true);
		txtSource.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1));
		
		btnSelectSound = new AudioButton(this, SWT.NONE);
		btnSelectSound.setTextWidget(txtSource);
		addControl(btnSelectSound, "audio");
		
	}
	
}
