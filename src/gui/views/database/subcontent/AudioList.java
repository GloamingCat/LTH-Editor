package gui.views.database.subcontent;

import gui.shell.AudioShell;
import gui.views.SimpleEditableList;

import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Shell;

import data.subcontent.Audio;
import lwt.dialog.LObjectShell;
import lwt.dialog.LShellFactory;

public class AudioList extends SimpleEditableList<Audio> {
	
	public String folder = "";
	
	public AudioList(Composite parent, int style) {
		super(parent, style);
		type = Audio.class;
		attributeName = "audio";
		setIncludeID(false);
		setShellFactory(new LShellFactory<Audio>() {
			@Override
			public LObjectShell<Audio> createShell(Shell parent) {
				return new AudioShell(parent, folder, false);
			}
		});
	}

}
