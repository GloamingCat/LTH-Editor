package gui.views.database.subcontent;

import gui.shell.database.ObstacleTileShell;
import gui.widgets.SimpleEditableList;

import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Shell;

import data.Obstacle.Tile;
import lwt.dialog.LObjectShell;
import lwt.dialog.LShellFactory;

public class ObstacleTileList extends SimpleEditableList<Tile> {

	public ObstacleTileList(Composite parent, int style) {
		super(parent, style);
		type = Tile.class;
		setIncludeID(false);
		setShellFactory(new LShellFactory<Tile>() {
			@Override
			public LObjectShell<Tile> createShell(Shell parent) {
				return new ObstacleTileShell(parent);
			}
		});
	}

}
