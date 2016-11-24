package gui.views.database.subcontent;

import java.util.ArrayList;

import gui.shell.BonusShell;

import org.eclipse.swt.widgets.Composite;

import com.google.gson.Gson;

import data.Bonus;
import lwt.dataestructure.LDataList;
import lwt.dialog.LObjectShell;
import lwt.dialog.LShellFactory;
import lwt.editor.LDefaultListEditor;

public class BonusList extends LDefaultListEditor<Bonus> {

	private static Gson gson = new Gson();
	
	protected LDataList<Bonus> currentList;
	
	public BonusList(Composite parent, int style) {
		super(parent, style);
		getCollection().setEditEnabled(true);
		getCollection().setInsertNewEnabled(true);
		getCollection().setDuplicateEnabled(true);
		getCollection().setDeleteEnabled(true);
		getCollection().setDragEnabled(true);
		setIncludeID(true);
		
		setShellFactory(new LShellFactory<Bonus>() {
			@Override
			public LObjectShell<Bonus> createShell(
					org.eclipse.swt.widgets.Shell parent) {
				return new BonusShell(parent) {
					public ArrayList<?> getArray() {
						return comboArray();
					};
				};
			}
		});
	}
	
	protected ArrayList<?> comboArray() { return null; }
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
	public void setList(LDataList<Bonus> list) {
		currentList = list;
	}
	
	@Override
	public LDataList<Bonus> getList() {
		return currentList;
	}

	@Override
	public Bonus createNewData() {
		return gson.fromJson("{}", Bonus.class);
	}

	@Override
	public Bonus duplicateData(Bonus original) {
		return gson.fromJson(gson.toJson(original), Bonus.class);
	}

}
