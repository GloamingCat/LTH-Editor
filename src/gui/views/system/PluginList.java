package gui.views.system;

import gui.shell.system.PluginShell;
import gui.widgets.SimpleEditableList;

import data.config.Plugin;
import lwt.container.LContainer;
import lwt.dialog.LObjectShell;
import lwt.dialog.LShell;
import lwt.dialog.LShellFactory;

public class PluginList extends SimpleEditableList<Plugin> {
	
	public PluginList(LContainer parent) {
		super(parent);
		type = Plugin.class;
		setIncludeID(false);
		setShellFactory(new LShellFactory<Plugin>() {
			@Override
			public LObjectShell<Plugin> createShell(LShell parent) {
				return new PluginShell(parent);
			}
		});
	}

}
