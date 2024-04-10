package gui.views.system;

import gui.shell.system.RegionDialog;
import gui.widgets.SimpleEditableList;

import data.config.Region;
import lui.container.LContainer;
import lui.dialog.LObjectDialog;
import lui.dialog.LWindow;
import lui.dialog.LWindowFactory;

public class RegionList extends SimpleEditableList<Region> {

	public RegionList(LContainer parent) {
		super(parent);
		type = Region.class;
		setIncludeID(true);
		setShellFactory(new LWindowFactory<Region>() {
			@Override
			public LObjectDialog<Region> createWindow(LWindow parent) {
				return new RegionDialog(parent);
			}
		});
	}

}
