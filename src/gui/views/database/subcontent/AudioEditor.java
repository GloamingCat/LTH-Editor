package gui.views.database.subcontent;

import gui.shell.AudioShell;

import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Text;

import data.Audio;
import lwt.dialog.LObjectShell;
import lwt.dialog.LShellFactory;
import lwt.editor.LObjectEditor;
import lwt.event.LControlEvent;
import lwt.event.listener.LControlListener;
import lwt.widget.LObjectButton;

public class AudioEditor extends LObjectEditor {

	public AudioEditor(Composite parent, int style) {
		super(parent, style);
		
		GridLayout gridLayout = new GridLayout(2, false);
		gridLayout.marginWidth = 0;
		gridLayout.marginHeight = 0;
		setLayout(gridLayout);

		Text txtSource = new Text(this, SWT.BORDER | SWT.READ_ONLY);
		txtSource.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1));
		
		LObjectButton<Audio> btnSelectSound = new LObjectButton<>(this, SWT.NONE);

		addAudioButton(btnSelectSound, txtSource, "sfx");
	}

	protected void addAudioButton(LObjectButton<Audio> button, Text text, String folderName) {
		button.setShellFactory(new LShellFactory<Audio>() {
			@Override
			public LObjectShell<Audio> createShell(Shell parent) {
				return new AudioShell(parent, folderName);
			}
		});
		button.addModifyListener(new LControlListener<Audio> () {
			@Override
			public void onModify(LControlEvent<Audio> event) {
				text.setText(event.newValue.path);
			}
		});
		addControl(button, "audio");
	}
	
}
