package data.field;

import lbase.data.LDataTree;

public class FieldTree extends LDataTree<FieldNode> {
	
	private static final long serialVersionUID = 1971762099996146816L;
	
	public int lastField = -1;
	
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
			child.data.lastLayers[layerType] = layerID;
	}

	public int getLastLayer(int fieldID, int layerType) {
		LDataTree<FieldNode> child = findNode(fieldID);
		return child == null ? 0 : child.data.lastLayers[layerType];
	}
	
}
