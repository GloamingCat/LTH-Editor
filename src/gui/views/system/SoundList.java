package gui.views.system;

import gui.shell.system.AudioShell;
import gui.widgets.SimpleEditableList;

import data.subcontent.Audio;
import lui.container.LContainer;
import lui.dialog.LObjectWindow;
import lui.dialog.LWindow;
import lui.dialog.LWindowFactory;

public class SoundList extends SimpleEditableList<Audio.Node> {
	
	public SoundList(LContainer parent) {
		super(parent);
		type = Audio.Node.class;
		setIncludeID(false);
		setShellFactory(new LWindowFactory<Audio.Node>() {
			@Override
			public LObjectWindow<Audio.Node> createWindow(LWindow parent) {
				return new AudioShell(parent, AudioShell.OPTIONAL);
			}
		});
	}

}
