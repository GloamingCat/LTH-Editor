package gui.views.database.subcontent;

import java.util.ArrayList;

import gui.shell.NodeShell;

import org.eclipse.swt.widgets.Composite;

import com.google.gson.Gson;

import data.Node;
import lwt.dataestructure.LDataList;
import lwt.dialog.LObjectShell;
import lwt.dialog.LShellFactory;
import lwt.editor.LDefaultListEditor;

public class NodeEditor extends LDefaultListEditor<Node> {

	private static Gson gson = new Gson();
	
	protected class Shell extends NodeShell {
		
		public Shell(org.eclipse.swt.widgets.Shell parent) {
			super(parent);
		}
		
		@Override
		protected ArrayList<?> getIDArray() {
			return getArray();
		}
		
	}
	
	protected LDataList<Node> currentList;
	
	public NodeEditor(Composite parent, int style) {
		super(parent, style);
		setEditEnabled(true);
		setInsertNewEnabled(true);
		setDuplicateEnabled(true);
		setDeleteEnabled(true);
		setDragEnabled(true);
		setIncludeID(true);
		setShellFactory(new LShellFactory<Node>() {
			@Override
			public LObjectShell<Node> createShell(
					org.eclipse.swt.widgets.Shell parent) {
				return new Shell(parent);
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
	protected void setList(LDataList<Node> list) {
		currentList = list;
	}
	
	@Override
	public LDataList<Node> getList() {
		return currentList;
	}

	@Override
	public Node createNewData() {
		return gson.fromJson("{}", Node.class);
	}

	@Override
	public Node duplicateData(Node original) {
		return gson.fromJson(gson.toJson(original), Node.class);
	}
	
	protected ArrayList<?> getArray() { return null; }
	protected String attributeName() { return ""; }

}
