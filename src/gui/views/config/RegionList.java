package gui.views.config;

import gui.shell.RegionShell;

import org.eclipse.swt.widgets.Composite;

import com.google.gson.Gson;

import data.Region;
import lwt.dataestructure.LDataCollection;
import lwt.dataestructure.LDataList;
import lwt.dialog.LObjectShell;
import lwt.dialog.LShellFactory;
import lwt.editor.LDefaultListEditor;

public class RegionList extends LDefaultListEditor<Region> {
	
	private static Gson gson = new Gson();
	protected LDataList<Region> currentList;
	
	public RegionList(Composite parent, int style) {
		super(parent, style);
		getCollectionWidget().setEditEnabled(true);
		getCollectionWidget().setInsertNewEnabled(true);
		getCollectionWidget().setDuplicateEnabled(true);
		getCollectionWidget().setDeleteEnabled(true);
		getCollectionWidget().setDragEnabled(true);
		setIncludeID(true);

		setShellFactory(new LShellFactory<Region>() {
			@Override
			public LObjectShell<Region> createShell(
					org.eclipse.swt.widgets.Shell parent) {
				return new RegionShell(parent);
			}
		});
	}

	public void setObject(Object object) {
		if (object == null) {
			super.setObject(null);
		} else {
			Object value = getFieldValue(object, "regions");
			super.setObject(value);
		}
	}
	
	@Override
	public void setDataCollection(LDataCollection<Region> list) {
		currentList = (LDataList<Region>) list;
	}
	
	@Override
	public LDataList<Region> getDataCollection() {
		return currentList;
	}

	@Override
	public Region createNewData() {
		return new Region();
	}

	@Override
	public Region duplicateData(Region original) {
		System.out.println(original.toString());
		new Exception().printStackTrace();
		return gson.fromJson(gson.toJson(original), Region.class);
	}

}
