package gui.views.database.subcontent;

import gui.shell.database.CharTileShell;
import gui.views.SimpleEditableList;

import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Shell;

import data.GameCharacter.Tile;
import lwt.dialog.LObjectShell;
import lwt.dialog.LShellFactory;

public class CharTileList extends SimpleEditableList<Tile> {

	public String folderName = "";
	public boolean optional = false;
	
	public CharTileList(Composite parent, int style) {
		super(parent, style);
		type = Tile.class;
		setIncludeID(false);
		setShellFactory(new LShellFactory<Tile>() {
			@Override
			public LObjectShell<Tile> createShell(Shell parent) {
				return new CharTileShell(parent);
			}
		});
	}

}
