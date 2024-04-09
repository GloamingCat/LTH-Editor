package gui.views.system;

import gui.shell.system.PluginShell;
import gui.widgets.SimpleEditableList;

import data.config.Plugin;
import lwt.container.LContainer;
import lwt.dialog.LObjectWindow;
import lwt.dialog.LWindow;
import lwt.dialog.LWindowFactory;

public class PluginList extends SimpleEditableList<Plugin> {
	
	public PluginList(LContainer parent) {
		super(parent);
		type = Plugin.class;
		setIncludeID(false);
		setShellFactory(new LWindowFactory<Plugin>() {
			@Override
			public LObjectWindow<Plugin> createWindow(LWindow parent) {
				return new PluginShell(parent);
			}
		});
	}

}
