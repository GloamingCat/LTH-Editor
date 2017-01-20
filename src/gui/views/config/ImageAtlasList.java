package gui.views.config;

import gui.shell.ImageAtlasShell;

import org.eclipse.swt.widgets.Composite;

import com.google.gson.Gson;

import data.ImageAtlas;
import lwt.dataestructure.LDataCollection;
import lwt.dataestructure.LDataList;
import lwt.dialog.LObjectShell;
import lwt.dialog.LShellFactory;
import lwt.editor.LDefaultListEditor;

public class ImageAtlasList extends LDefaultListEditor<ImageAtlas> {
	
	private static Gson gson = new Gson();
	protected LDataList<ImageAtlas> currentList;
	
	public ImageAtlasList(Composite parent, int style) {
		super(parent, style);
		getCollectionWidget().setEditEnabled(true);
		getCollectionWidget().setInsertNewEnabled(true);
		getCollectionWidget().setDuplicateEnabled(true);
		getCollectionWidget().setDeleteEnabled(true);
		getCollectionWidget().setDragEnabled(true);
		setIncludeID(true);

		setShellFactory(new LShellFactory<ImageAtlas>() {
			@Override
			public LObjectShell<ImageAtlas> createShell(
					org.eclipse.swt.widgets.Shell parent) {
				return new ImageAtlasShell(parent);
			}
		});
	}

	public void setObject(Object object) {
		if (object == null) {
			super.setObject(null);
		} else {
			Object value = getFieldValue(object, "atlases");
			super.setObject(value);
		}
	}
	
	@Override
	public void setDataCollection(LDataCollection<ImageAtlas> list) {
		currentList = (LDataList<ImageAtlas>) list;
	}
	
	@Override
	public LDataList<ImageAtlas> getDataCollection() {
		return currentList;
	}

	@Override
	public ImageAtlas createNewData() {
		return new ImageAtlas();
	}

	@Override
	public ImageAtlas duplicateData(ImageAtlas original) {
		return gson.fromJson(gson.toJson(original), ImageAtlas.class);
	}

}
