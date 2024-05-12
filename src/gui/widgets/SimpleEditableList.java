package gui.widgets;

import lui.base.LPrefs;
import lui.container.LContainer;
import lui.gson.GDefaultListEditor;
import lui.base.data.LDataList;

public class SimpleEditableList<T> extends GDefaultListEditor<T> {

	public Class<?> type;
	
	public SimpleEditableList(LContainer parent) {
		super(parent);
		getCollectionWidget().setEditEnabled(true);
		getCollectionWidget().setInsertNewEnabled(true);
		getCollectionWidget().setDuplicateEnabled(true);
		getCollectionWidget().setDeleteEnabled(true);
		getCollectionWidget().setDragEnabled(true);
		getCollectionWidget().setCopyEnabled(true);
		getCollectionWidget().setPasteEnabled(true);
		getCellData().setRequiredSize(LPrefs.LISTWIDTH, LPrefs.LISTHEIGHT);
	}
	
	public SimpleEditableList(LContainer parent, Class<?> type) {
		this(parent);
		this.type = type;
	}
	
	@Override
	public LDataList<T> getDataCollection() {
		return (LDataList<T>) currentObject;
	}

	@Override
	public void refreshDataCollection() {}

	@Override
	public Class<?> getType() {
		return type;
	}
	@Override
	public void setChecked(T c, boolean checked) {}
	
}
