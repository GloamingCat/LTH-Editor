package gui.views.database.subcontent;

import gui.shell.database.CharTileListShell;
import lwt.dataestructure.LDataList;
import lwt.dialog.LObjectShell;
import lwt.dialog.LShellFactory;
import lwt.widget.LObjectButton;

import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Shell;

import data.GameCharacter.Tile;

public class CharTileListButton extends LObjectButton<LDataList<Tile>> {

	/**
	 * Create the composite.
	 * @param parent
	 * @param style
	 */
	public CharTileListButton(Composite parent, int style) {
		super(parent, style);
		setShellFactory(new LShellFactory<LDataList<Tile>>() {
			@Override
			public LObjectShell<LDataList<Tile>> createShell(Shell parent) {
				return new CharTileListShell(parent);
			}
		});
	}

}