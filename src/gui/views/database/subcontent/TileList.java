package gui.views.database.subcontent;

import gui.shell.ObstacleTileShell;

import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Shell;

import com.google.gson.Gson;

import data.Obstacle.Tile;
import lwt.dataestructure.LDataCollection;
import lwt.dataestructure.LDataList;
import lwt.dialog.LObjectShell;
import lwt.dialog.LShellFactory;
import lwt.editor.LDefaultListEditor;

public class TileList extends LDefaultListEditor<Tile> {

	private static Gson gson = new Gson();
	
	protected LDataList<Tile> currentList;
	
	public TileList(Composite parent, int style) {
		super(parent, style);
		getCollectionWidget().setEditEnabled(true);
		getCollectionWidget().setInsertNewEnabled(true);
		getCollectionWidget().setDuplicateEnabled(true);
		getCollectionWidget().setDeleteEnabled(true);
		getCollectionWidget().setDragEnabled(true);
		setIncludeID(false);
		setShellFactory(new LShellFactory<Tile>() {
			@Override
			public LObjectShell<Tile> createShell(Shell parent) {
				return new ObstacleTileShell(parent);
			}
		});
	}
	
	public void setObject(Object object) {
		if (object == null) {
			super.setObject(null);
		} else {
			Object value = getFieldValue(object, "tiles");
			super.setObject(value);
		}
	}

	@Override
	public void setDataCollection(LDataCollection<Tile> list) {
		currentList = (LDataList<Tile>) list;
		super.setDataCollection(list);
	}
	
	@Override
	public LDataList<Tile> getDataCollection() {
		return currentList;
	}

	@Override
	public Tile createNewData() {
		return new Tile();
	}

	@Override
	public Tile duplicateData(Tile original) {
		return gson.fromJson(gson.toJson(original), Tile.class);
	}

}
