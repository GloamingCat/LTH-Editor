package gui.views.system;

import gui.shell.system.AudioDialog;
import gui.widgets.SimpleEditableList;

import data.subcontent.Audio;
import lui.container.LContainer;
import lui.dialog.LObjectDialog;
import lui.dialog.LWindow;
import lui.dialog.LWindowFactory;

public class SoundList extends SimpleEditableList<Audio.Node> {
	
	public SoundList(LContainer parent) {
		super(parent);
		type = Audio.Node.class;
		setIncludeID(false);
		setShellFactory(new LWindowFactory<Audio.Node>() {
			@Override
			public LObjectDialog<Audio.Node> createWindow(LWindow parent) {
				return new AudioDialog(parent, AudioDialog.OPTIONAL);
			}
		});
	}

}
