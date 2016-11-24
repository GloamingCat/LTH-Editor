package gui.views.database.subcontent;

import gui.shell.TagShell;

import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Shell;

import data.Tag;
import lwt.dataestructure.LDataList;
import lwt.dialog.LObjectShell;
import lwt.dialog.LShellFactory;
import lwt.editor.LDefaultListEditor;

public class TagList extends LDefaultListEditor<Tag> {

	protected LDataList<Tag> currentList;
	
	public TagList(Composite parent, int style) {
		super(parent, style);
		getCollection().setEditEnabled(true);
		getCollection().setInsertNewEnabled(true);
		getCollection().setDuplicateEnabled(true);
		getCollection().setDeleteEnabled(true);
		getCollection().setDragEnabled(true);
		setIncludeID(false);
		setShellFactory(new LShellFactory<Tag>() {
			@Override
			public LObjectShell<Tag> createShell(Shell parent) {
				return new TagShell(parent);
			}
		});
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
	public void setList(LDataList<Tag> list) {
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
