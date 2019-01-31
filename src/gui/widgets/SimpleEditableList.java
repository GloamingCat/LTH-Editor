package gui.widgets;

import java.lang.reflect.Type;

import org.eclipse.swt.widgets.Composite;

import gson.editor.GDefaultListEditor;
import lwt.dataestructure.LDataCollection;
import lwt.dataestructure.LDataList;

public class SimpleEditableList<T> extends GDefaultListEditor<T> {

	public Type type;
	protected LDataList<T> currentList;
	
	public SimpleEditableList(Composite parent, int style) {
		super(parent, style);
		getCollectionWidget().setEditEnabled(true);
		getCollectionWidget().setInsertNewEnabled(true);
		getCollectionWidget().setDuplicateEnabled(true);
		getCollectionWidget().setDeleteEnabled(true);
		getCollectionWidget().setDragEnabled(true);
		getCollectionWidget().setCopyEnabled(true);
		getCollectionWidget().setPasteEnabled(true);
	}
	
	public SimpleEditableList(Composite parent, Type type) {
		this(parent, 0);
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
	
	public Type getType() {
		return type;
	}
	
}
