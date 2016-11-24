package gui.views.database.subcontent;

import java.util.ArrayList;

import gui.shell.NodeShell;

import org.eclipse.swt.widgets.Composite;

import project.Project;

import com.google.gson.Gson;

import data.Node;
import lwt.dataestructure.LDataList;
import lwt.dialog.LObjectShell;
import lwt.dialog.LShellFactory;
import lwt.editor.LDefaultListEditor;

public class NodeList extends LDefaultListEditor<Node> {

	private static Gson gson = new Gson();
	
	protected LDataList<Node> currentList;
	
	public NodeList(Composite parent, int style) {
		super(parent, style);
		getCollection().setEditEnabled(true);
		getCollection().setInsertNewEnabled(true);
		getCollection().setDuplicateEnabled(true);
		getCollection().setDeleteEnabled(true);
		getCollection().setDragEnabled(true);
		setIncludeID(true);

		setShellFactory(new LShellFactory<Node>() {
			@Override
			public LObjectShell<Node> createShell(
					org.eclipse.swt.widgets.Shell parent) {
				return new NodeShell(parent) {
					public ArrayList<?> getArray() {
						return Project.current.skills.getList();
					}
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
	public void setList(LDataList<Node> list) {
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

}
