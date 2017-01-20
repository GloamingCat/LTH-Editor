package gui.views.database.subcontent;

import gui.views.common.AudioButton;

import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Text;

import lwt.editor.LObjectEditor;

public class AudioEditor extends LObjectEditor {

	public AudioEditor(Composite parent, int style) {
		super(parent, style);
		
		GridLayout gridLayout = new GridLayout(2, false);
		gridLayout.marginWidth = 0;
		gridLayout.marginHeight = 0;
		setLayout(gridLayout);

		Text txtSource = new Text(this, SWT.BORDER | SWT.READ_ONLY);
		txtSource.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1));
		
		AudioButton btnSelectSound = new AudioButton(this, SWT.NONE);
		btnSelectSound.setText(txtSource);
		addControl(btnSelectSound, "audio");
		
	}
	
}
