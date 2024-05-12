package gui.views.system;

import data.subcontent.Audio;
import gui.shell.system.AudioDialog;
import gui.widgets.SimpleEditableList;

import lui.container.LContainer;
import lui.dialog.LObjectDialog;
import lui.dialog.LWindow;
import lui.dialog.LWindowFactory;

public class SoundList extends SimpleEditableList<Audio> {
	
	public SoundList(LContainer parent) {
		super(parent);
		type = Audio.class;
		setIncludeID(false);
		setShellFactory(new LWindowFactory<Audio>() {
			@Override
			public LObjectDialog<Audio> createWindow(LWindow parent) {
				return new AudioDialog(parent, AudioDialog.OPTIONAL);
			}
		});
	}

}
