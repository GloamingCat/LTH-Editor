package gui.views.database.subcontent;

import gui.shell.ScriptShell;

import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Shell;

import data.Script;
import lwt.dataestructure.LDataCollection;
import lwt.dataestructure.LDataList;
import lwt.dialog.LObjectShell;
import lwt.dialog.LShellFactory;
import lwt.editor.LDefaultListEditor;

public class ScriptList extends LDefaultListEditor<Script> {

	protected LDataList<Script> currentList;
	
	public ScriptList(Composite parent, int style) {
		super(parent, style);
		getCollectionWidget().setEditEnabled(true);
		getCollectionWidget().setInsertNewEnabled(true);
		getCollectionWidget().setDuplicateEnabled(true);
		getCollectionWidget().setDeleteEnabled(true);
		getCollectionWidget().setDragEnabled(true);
		setIncludeID(false);
		setShellFactory(new LShellFactory<Script>() {
			@Override
			public LObjectShell<Script> createShell(Shell parent) {
				return new ScriptShell(parent, folderName);
			}
		});
	}
	
	public String folderName = "";
	public String attributeName = "script";
	
	public void setObject(Object object) {
		if (object == null) {
			super.setObject(null);
		} else {
			Object value = getFieldValue(object, attributeName);
			super.setObject(value);
		}
	}

	@Override
	public void setDataCollection(LDataCollection<Script> list) {
		currentList = (LDataList<Script>) list;
		super.setDataCollection(list);
	}
	
	@Override
	public LDataList<Script> getDataCollection() {
		return currentList;
	}

	@Override
	public Script createNewData() {
		return new Script();
	}

	@Override
	public Script duplicateData(Script original) {
		Script script = new Script();
		script.path = original.path;
		script.param = original.param;
		return script;
	}

}
