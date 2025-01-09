package data.field;

import gui.ApplicationWindow;
import lui.base.data.LDataTree;

public class FieldTree extends LDataTree<FieldNode> {
	
	public int getFieldID(FieldNode node) {
		LDataTree<FieldNode> child = findNode(node);
		return child == null ? -1 : child.id;
	}
	
	public FieldNode getFieldNode(int id) {
		LDataTree<FieldNode> child = findNode(id);
		return child == null ? null : child.data;
	}
	
	public void setLastLayer(int fieldID, int layerID, int layerType) {
		LDataTree<FieldNode> child = findNode(fieldID);
		if (child != null)
			ApplicationWindow.current.setPreference("field", "lastLayer" + fieldID + "_" + layerType, layerID);
	}

	public int getLastLayer(int fieldID, int layerType) {
		LDataTree<FieldNode> child = findNode(fieldID);
		if (child == null)
			return 0;
		Integer i = ApplicationWindow.current.getPreference("field", "lastLayer" + fieldID + "_" + layerType, Integer.class);
		return i == null ? 0 : i;
	}
	
}
