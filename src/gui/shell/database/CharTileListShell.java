package gui.shell.database;

import gui.views.database.subcontent.CharTileList;

import org.eclipse.swt.widgets.Shell;

import lwt.dataestructure.LDataList;
import lwt.dialog.LObjectShell;

import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.FillLayout;

import data.GameCharacter.Tile;
import org.eclipse.swt.layout.GridData;

public class CharTileListShell extends LObjectShell<LDataList<Tile>> {
	
	private CharTileList lstTiles;
	private LDataList<Tile> tiles;
	
	public CharTileListShell(Shell parent) {
		super(parent);
		GridData gridData = (GridData) content.getLayoutData();
		gridData.verticalAlignment = SWT.FILL;
		gridData.grabExcessVerticalSpace = true;
		
		content.setLayout(new FillLayout());

		lstTiles = new CharTileList(content, SWT.NONE) {
			public LDataList<Tile> getDataCollection() {
				return tiles;
			}
		};

		pack();
	}
	
	public void open(LDataList<Tile> initial) {
		super.open(initial);
		tiles = new LDataList<>();
		for (Tile i : initial) {
			tiles.add(new Tile(i));
		}
		lstTiles.onVisible();
	}

	@Override
	protected LDataList<Tile> createResult(LDataList<Tile> initial) {
		if (tiles.equals(initial)) {
			return null;
		} else {
			return tiles;
		}
	}

}
