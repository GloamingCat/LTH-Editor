package gui.views.database.subcontent;

import org.eclipse.swt.widgets.Composite;

import com.google.gson.Gson;

import data.SkillDag.Node;
import lwt.dataestructure.LDataCollection;
import lwt.dataestructure.LDataList;
import lwt.editor.LDefaultListEditor;

public class SkillNodeList extends LDefaultListEditor<Node> {

	private static Gson gson = new Gson();
	
	protected LDataList<Node> currentList;
	
	public SkillNodeList(Composite parent, int style) {
		super(parent, style);
		getCollectionWidget().setEditEnabled(false);
		getCollectionWidget().setInsertNewEnabled(true);
		getCollectionWidget().setDuplicateEnabled(true);
		getCollectionWidget().setDeleteEnabled(true);
		getCollectionWidget().setDragEnabled(true);
		setIncludeID(true);
	}

	public void setObject(Object object) {
		if (object == null) {
			super.setObject(null);
		} else {
			Object value = getFieldValue(object, "nodes");
			super.setObject(value);
		}
	}

	@Override
	public void setDataCollection(LDataCollection<Node> list) {
		currentList = (LDataList<Node>) list;
	}
	
	@Override
	public LDataList<Node> getDataCollection() {
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
