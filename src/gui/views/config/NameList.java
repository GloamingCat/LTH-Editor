package gui.views.config;

import gui.shell.NameShell;

import org.eclipse.swt.widgets.Composite;

import lwt.dataestructure.LDataCollection;
import lwt.dataestructure.LDataList;
import lwt.dialog.LObjectShell;
import lwt.dialog.LShellFactory;
import lwt.editor.LDefaultListEditor;

public class NameList extends LDefaultListEditor<String> {
	
	protected LDataList<String> currentList;
	
	public NameList(Composite parent, int style) {
		super(parent, style);
		getCollectionWidget().setEditEnabled(true);
		getCollectionWidget().setInsertNewEnabled(true);
		getCollectionWidget().setDuplicateEnabled(true);
		getCollectionWidget().setDeleteEnabled(true);
		getCollectionWidget().setDragEnabled(true);
		setIncludeID(true);

		setShellFactory(new LShellFactory<String>() {
			@Override
			public LObjectShell<String> createShell(
					org.eclipse.swt.widgets.Shell parent) {
				return new NameShell(parent);
			}
		});
	}
	
	protected String attributeName() { return ""; }

	public void setObject(Object object) {
		if (object == null) {
			super.setObject(null);
		} else {
			Object value = getFieldValue(object, attributeName());
			super.setObject(value);
		}
	}
	
	@Override
	public void setDataCollection(LDataCollection<String> list) {
		currentList = (LDataList<String>) list;
	}
	
	@Override
	public LDataList<String> getDataCollection() {
		return currentList;
	}

	@Override
	public String createNewData() {
		return "New";
	}

	@Override
	public String duplicateData(String original) {
		return original;
	}

}
