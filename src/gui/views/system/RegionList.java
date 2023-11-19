package gui.views.system;

import gui.shell.system.RegionShell;
import gui.widgets.SimpleEditableList;

import data.config.Region;
import lwt.container.LContainer;
import lwt.dialog.LObjectShell;
import lwt.dialog.LShell;
import lwt.dialog.LShellFactory;

public class RegionList extends SimpleEditableList<Region> {

	public RegionList(LContainer parent) {
		super(parent);
		type = Region.class;
		setIncludeID(true);
		setShellFactory(new LShellFactory<Region>() {
			@Override
			public LObjectShell<Region> createShell(LShell parent) {
				return new RegionShell(parent);
			}
		});
	}

}
