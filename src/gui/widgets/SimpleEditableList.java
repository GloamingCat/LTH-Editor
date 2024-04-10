package gui.widgets;

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
	}
	
	public SimpleEditableList(LContainer parent, Class<?> type) {
		this(parent);
		this.type = type;
	}
	
	@Override
	public LDataList<T> getDataCollection() {
		return (LDataList<T>) currentObject;
	}
	
	public void refreshDataCollection() {}
	
	public Class<?> getType() {
		return type;
	}
	
}
