package gui.views.database.subcontent;

import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Shell;

import com.google.gson.Gson;

import lwt.dataestructure.LDataCollection;
import lwt.dataestructure.LDataList;
import lwt.datainterface.LGraphical;
import lwt.dialog.LObjectShell;
import lwt.dialog.LShellFactory;
import lwt.editor.LDefaultGridEditor;

public class TileList<T extends LGraphical> extends LDefaultGridEditor<T> {

	private static Gson gson = new Gson();
	
	protected LDataList<T> currentList;
	
	public TileList(Composite parent, int style) {
		super(parent, style);
		TileList<T> self = this;
		setShellFactory(new LShellFactory<T>() {
			@Override
			public LObjectShell<T> createShell(Shell parent) {
				return self.createShell(parent);
			}
		});
	}
	
	public void setObject(Object object) {
		if (object == null) {
			super.setObject(null);
		} else {
			Object value = getFieldValue(object, attributeName());
			super.setObject(value);
		}
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
	
	@Override
	public T createNewData() {
		return gson.fromJson("{}", getType());
	}

	@Override
	public T duplicateData(T original) {
		return gson.fromJson(gson.toJson(original), getType());
	}

	protected String attributeName() { return ""; }
	protected Class<T> getType() { return null; } 
	protected LObjectShell<T> createShell(Shell parent) { return null; }

}
