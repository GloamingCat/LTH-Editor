package gui.views.system;

import gui.shell.system.PluginShell;
import gui.widgets.SimpleEditableList;

import org.eclipse.swt.widgets.Composite;

import data.config.Plugin;
import lwt.dataestructure.LDataList;
import lwt.dialog.LObjectShell;
import lwt.dialog.LShellFactory;

public class PluginList extends SimpleEditableList<Plugin> {
	
	protected LDataList<Plugin> currentList;
	
	public PluginList(Composite parent, int style) {
		super(parent, style);
		type = Plugin.class;
		setIncludeID(false);
		setShellFactory(new LShellFactory<Plugin>() {
			@Override
			public LObjectShell<Plugin> createShell(
					org.eclipse.swt.widgets.Shell parent) {
				return new PluginShell(parent, 0);
			}
		});
	}

}
