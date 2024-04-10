package gui.views.system;

import gui.shell.system.PluginDialog;
import gui.widgets.SimpleEditableList;

import data.config.Plugin;
import lui.container.LContainer;
import lui.dialog.LObjectDialog;
import lui.dialog.LWindow;
import lui.dialog.LWindowFactory;

public class PluginList extends SimpleEditableList<Plugin> {
	
	public PluginList(LContainer parent) {
		super(parent);
		type = Plugin.class;
		setIncludeID(false);
		setShellFactory(new LWindowFactory<Plugin>() {
			@Override
			public LObjectDialog<Plugin> createWindow(LWindow parent) {
				return new PluginDialog(parent);
			}
		});
	}

}
