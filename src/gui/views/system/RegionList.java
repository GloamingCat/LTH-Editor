package gui.views.system;

import gui.shell.system.RegionShell;
import gui.widgets.SimpleEditableList;

import data.config.Region;
import lui.container.LContainer;
import lui.dialog.LObjectWindow;
import lui.dialog.LWindow;
import lui.dialog.LWindowFactory;

public class RegionList extends SimpleEditableList<Region> {

	public RegionList(LContainer parent) {
		super(parent);
		type = Region.class;
		setIncludeID(true);
		setShellFactory(new LWindowFactory<Region>() {
			@Override
			public LObjectWindow<Region> createWindow(LWindow parent) {
				return new RegionShell(parent);
			}
		});
	}

}
