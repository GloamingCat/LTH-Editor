package gui.views.system;

import gui.shell.system.AudioNodeShell;
import gui.widgets.SimpleEditableList;

import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Shell;

import data.subcontent.Audio;
import lwt.dialog.LObjectShell;
import lwt.dialog.LShellFactory;

public class SoundList extends SimpleEditableList<Audio.Node> {
	
	public SoundList(Composite parent, int style) {
		super(parent, SWT.NONE);
		type = Audio.Node.class;
		setIncludeID(false);
		setShellFactory(new LShellFactory<Audio.Node>() {
			@Override
			public LObjectShell<Audio.Node> createShell(Shell parent) {
				return new AudioNodeShell(parent, style);
			}
		});
	}

}
