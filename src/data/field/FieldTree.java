package data.field;

import lwt.dataestructure.LDataTree;

public class FieldTree extends LDataTree<FieldNode> {
	
	private static final long serialVersionUID = 1971762099996146816L;
	
	public int lastField = -1;

	public FieldTree() {
		LDataTree<FieldNode> node = new LDataTree<FieldNode>(new FieldNode());
		node.id = 0;
		addChild(node);
	}
	
	public void setLastTerrainLayer(int fieldID, int layerID) {
		FieldNode node = findNode(fieldID).data;
		node.lastTerrainLayer = layerID;
	}

	public int getLastTerrainLayer(int fieldID) {
		FieldNode node = findNode(fieldID).data;
		return node.lastTerrainLayer;
	}

}
