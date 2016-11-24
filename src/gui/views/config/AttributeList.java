package gui.views.config;

import gui.shell.AttributeShell;

import org.eclipse.swt.widgets.Composite;

import data.Attribute;
import lwt.dataestructure.LDataList;
import lwt.dialog.LObjectShell;
import lwt.dialog.LShellFactory;
import lwt.editor.LDefaultListEditor;

public class AttributeList extends LDefaultListEditor<Attribute> {
	
	protected LDataList<Attribute> currentList;
	
	public AttributeList(Composite parent, int style) {
		super(parent, style);
		getCollection().setEditEnabled(true);
		getCollection().setInsertNewEnabled(true);
		getCollection().setDuplicateEnabled(true);
		getCollection().setDeleteEnabled(true);
		getCollection().setDragEnabled(true);
		setIncludeID(true);

		setShellFactory(new LShellFactory<Attribute>() {
			@Override
			public LObjectShell<Attribute> createShell(
					org.eclipse.swt.widgets.Shell parent) {
				return new AttributeShell(parent);
			}
		});
	}

	public void setObject(Object object) {
		if (object == null) {
			super.setObject(null);
		} else {
			Object value = getFieldValue(object, "attributes");
			super.setObject(value);
		}
	}
	
	@Override
	public void setList(LDataList<Attribute> list) {
		currentList = list;
	}
	
	@Override
	public LDataList<Attribute> getList() {
		return currentList;
	}

	@Override
	public Attribute createNewData() {
		return new Attribute();
	}

	@Override
	public Attribute duplicateData(Attribute original) {
		Attribute att = new Attribute();
		att.name = original.name + "2";
		att.shortName = original.shortName + "2";
		att.mutable = original.mutable;
		att.script = original.script;
		return att;
	}

}
