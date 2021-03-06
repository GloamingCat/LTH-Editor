package gui.views.system;

import gui.shell.system.RegionShell;
import gui.widgets.SimpleEditableList;

import org.eclipse.swt.widgets.Composite;

import data.config.Region;
import lwt.dialog.LObjectShell;
import lwt.dialog.LShellFactory;

public class RegionList extends SimpleEditableList<Region> {

	public RegionList(Composite parent, int style) {
		super(parent, style);
		type = Region.class;
		setIncludeID(true);
		setShellFactory(new LShellFactory<Region>() {
			@Override
			public LObjectShell<Region> createShell(
					org.eclipse.swt.widgets.Shell parent) {
				return new RegionShell(parent);
			}
		});
	}

}
