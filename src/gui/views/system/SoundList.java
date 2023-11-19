package gui.views.system;

import gui.shell.system.AudioNodeShell;
import gui.widgets.SimpleEditableList;

import data.subcontent.Audio;
import lwt.container.LContainer;
import lwt.dialog.LObjectShell;
import lwt.dialog.LShell;
import lwt.dialog.LShellFactory;

public class SoundList extends SimpleEditableList<Audio.Node> {
	
	public SoundList(LContainer parent) {
		super(parent);
		type = Audio.Node.class;
		setIncludeID(false);
		setShellFactory(new LShellFactory<Audio.Node>() {
			@Override
			public LObjectShell<Audio.Node> createShell(LShell parent) {
				return new AudioNodeShell(parent, 0);
			}
		});
	}

}
