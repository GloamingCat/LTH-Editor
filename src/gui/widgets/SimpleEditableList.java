package gui.widgets;

import gson.editor.GDefaultListEditor;
import lwt.container.LContainer;
import lwt.dataestructure.LDataCollection;
import lwt.dataestructure.LDataList;

public class SimpleEditableList<T> extends GDefaultListEditor<T> {

	public Class<?> type;
	protected LDataList<T> currentList;
	
	public SimpleEditableList(LContainer parent) {
		super(parent);
		getCollectionWidget().setEditEnabled(true);
		getCollectionWidget().setInsertNewEnabled(true);
		getCollectionWidget().setDuplicateEnabled(true);
		getCollectionWidget().setDeleteEnabled(true);
		getCollectionWidget().setDragEnabled(true);
		getCollectionWidget().setCopyEnabled(true);
		getCollectionWidget().setPasteEnabled(true);
		pack();
	}
	
	public SimpleEditableList(LContainer parent, Class<?> type) {
		this(parent);
		this.type = type;
	}
	
	@Override
	public void setDataCollection(LDataCollection<T> list) {
		currentList = (LDataList<T>) list;
		super.setDataCollection(list);
	}
	
	@Override
	public LDataList<T> getDataCollection() {
		return currentList;
	}
	
	public Class<?> getType() {
		return type;
	}
	
}
