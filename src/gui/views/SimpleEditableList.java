package gui.views;

import java.lang.reflect.Type;

import org.eclipse.swt.widgets.Composite;

import editor.GDefaultListEditor;
import lwt.dataestructure.LDataCollection;
import lwt.dataestructure.LDataList;

public class SimpleEditableList<T> extends GDefaultListEditor<T> {

	public String attributeName;
	public Type type;
	protected LDataList<T> currentList;
	
	public SimpleEditableList(Composite parent, int style) {
		super(parent, style);
		getCollectionWidget().setEditEnabled(true);
		getCollectionWidget().setInsertNewEnabled(true);
		getCollectionWidget().setDuplicateEnabled(true);
		getCollectionWidget().setDeleteEnabled(true);
		getCollectionWidget().setDragEnabled(true);
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

	@Override
	public void setObject(Object object) {
		if (object == null || attributeName == null || 
				attributeName.isEmpty()) {
			super.setObject(object);
			return;
		}
		Object value = getFieldValue(object, attributeName);
		super.setObject(value);
	}
	
}