package gui.views.system;

import gui.shell.system.PluginShell;
import gui.widgets.SimpleEditableList;

import data.config.Plugin;
import lui.container.LContainer;
import lui.dialog.LObjectWindow;
import lui.dialog.LWindow;
import lui.dialog.LWindowFactory;

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
