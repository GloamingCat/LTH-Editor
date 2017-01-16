package gui.views.database.subcontent;

import java.util.ArrayList;

import gui.shell.BonusShell;

import org.eclipse.swt.widgets.Composite;

import com.google.gson.Gson;

import data.Bonus;
import lwt.dataestructure.LDataCollection;
import lwt.dataestructure.LDataList;
import lwt.dialog.LObjectShell;
import lwt.dialog.LShellFactory;
import lwt.editor.LDefaultListEditor;

public class BonusList extends LDefaultListEditor<Bonus> {

	private static Gson gson = new Gson();
	
	protected LDataList<Bonus> currentList;
	public String attributeName = "";
	
	public BonusList(Composite parent, int style) {
		super(parent, style);
		getCollectionWidget().setEditEnabled(true);
		getCollectionWidget().setInsertNewEnabled(true);
		getCollectionWidget().setDuplicateEnabled(true);
		getCollectionWidget().setDeleteEnabled(true);
		getCollectionWidget().setDragEnabled(true);
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
	
	public void setObject(Object object) {
		if (object == null) {
			super.setObject(null);
		} else {
			Object value = getFieldValue(object, attributeName);
			super.setObject(value);
		}
	}

	@Override
	public void setDataCollection(LDataCollection<Bonus> list) {
		currentList = (LDataList<Bonus>) list;
	}
	
	@Override
	public LDataList<Bonus> getDataCollection() {
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
