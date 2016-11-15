package gui.views.database;

import org.eclipse.swt.widgets.Composite;

import data.Tag;
import lwt.dataestructure.LDataList;
import lwt.editor.LDefaultListEditor;

public class TagEditor extends LDefaultListEditor<Tag> {

	protected LDataList<Tag> currentList;
	
	public TagEditor(Composite parent, int style) {
		super(parent, style);
		setEditEnabled(true);
		setInsertNewEnabled(true);
		setDuplicateEnabled(true);
		setDeleteEnabled(true);
		setDragEnabled(true);
	}
	
	public void setObject(Object object) {
		if (object == null) {
			super.setObject(null);
		} else {
			Object value = getFieldValue(object, "tags");
			super.setObject(value);
		}
	}

	@Override
	protected void setList(LDataList<Tag> list) {
		currentList = list;
	}
	
	@Override
	public LDataList<Tag> getList() {
		return currentList;
	}

	@Override
	public Tag createNewData() {
		return new Tag();
	}

	@Override
	public Tag duplicateData(Tag original) {
		Tag tag = new Tag();
		tag.name = original.name;
		tag.value = original.value;
		return tag;
	}

}
