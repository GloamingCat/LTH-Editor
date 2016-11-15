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

public abstract class BonusEditor extends LDefaultListEditor<Bonus> {

	private static Gson gson = new Gson();
	
	protected class Shell extends BonusShell {
		
		public Shell(org.eclipse.swt.widgets.Shell parent) {
			super(parent);
		}
		
		@Override
		protected ArrayList<?> getIDArray() {
			return getArray();
		}
		
	}
	
	protected LDataList<Bonus> currentList;
	
	public BonusEditor(Composite parent, int style) {
		super(parent, style);
		setEditEnabled(true);
		setInsertNewEnabled(true);
		setDuplicateEnabled(true);
		setDeleteEnabled(true);
		setDragEnabled(true);
		setIncludeID(true);
		setShellFactory(new LShellFactory<Bonus>() {
			@Override
			public LObjectShell<Bonus> createShell(
					org.eclipse.swt.widgets.Shell parent) {
				return new Shell(parent);
			}
		});
	}
	
	protected abstract ArrayList<?> getArray();
	protected abstract String attributeName();
	
	public void setObject(Object object) {
		if (object == null) {
			super.setObject(null);
		} else {
			Object value = getFieldValue(object, attributeName());
			super.setObject(value);
		}
	}

	@Override
	protected void setList(LDataList<Bonus> list) {
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
