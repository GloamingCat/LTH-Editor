package gui.views.database.subcontent;

import gui.shell.ObstacleTileShell;
import gui.views.common.SimpleEditableList;

import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Shell;

import data.Obstacle.Tile;
import lwt.dialog.LObjectShell;
import lwt.dialog.LShellFactory;

public class TileList extends SimpleEditableList<Tile> {

	public TileList(Composite parent, int style) {
		super(parent, style);
		type = Tile.class;
		attributeName = "tiles";
		setIncludeID(false);
		setShellFactory(new LShellFactory<Tile>() {
			@Override
			public LObjectShell<Tile> createShell(Shell parent) {
				return new ObstacleTileShell(parent);
			}
		});
	}

}
