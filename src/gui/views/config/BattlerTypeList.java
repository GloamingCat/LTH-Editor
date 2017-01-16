package gui.views.config;

import gui.shell.BattlerTypeShell;

import org.eclipse.swt.widgets.Composite;

import data.BattlerType;
import lwt.dataestructure.LDataCollection;
import lwt.dataestructure.LDataList;
import lwt.dialog.LObjectShell;
import lwt.dialog.LShellFactory;
import lwt.editor.LDefaultListEditor;

public class BattlerTypeList extends LDefaultListEditor<BattlerType> {
	
	protected LDataList<BattlerType> currentList;
	
	public BattlerTypeList(Composite parent, int style) {
		super(parent, style);
		getCollectionWidget().setEditEnabled(true);
		getCollectionWidget().setInsertNewEnabled(true);
		getCollectionWidget().setDuplicateEnabled(true);
		getCollectionWidget().setDeleteEnabled(true);
		getCollectionWidget().setDragEnabled(true);
		setIncludeID(true);

		setShellFactory(new LShellFactory<BattlerType>() {
			@Override
			public LObjectShell<BattlerType> createShell(
					org.eclipse.swt.widgets.Shell parent) {
				return new BattlerTypeShell(parent);
			}
		});
	}

	public void setObject(Object object) {
		if (object == null) {
			super.setObject(null);
		} else {
			Object value = getFieldValue(object, "battlerTypes");
			super.setObject(value);
		}
	}
	
	@Override
	public void setDataCollection(LDataCollection<BattlerType> list) {
		currentList = (LDataList<BattlerType>) list;
	}
	
	@Override
	public LDataList<BattlerType> getDataCollection() {
		return currentList;
	}

	@Override
	public BattlerType createNewData() {
		return new BattlerType();
	}

	@Override
	public BattlerType duplicateData(BattlerType original) {
		BattlerType t = new BattlerType();
		t.name = original.name;
		t.code = original.code;
		return t;
	}

}
