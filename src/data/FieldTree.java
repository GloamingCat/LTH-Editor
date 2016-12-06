package data;

import java.util.HashMap;

import lwt.dataestructure.LDataTree;

// ==================================================================================================
//	The data stored in HD
// ==================================================================================================

public class FieldTree {
	
	public int lastField = 0;
	private HashMap<Integer, Integer> lastLayers;
	public LDataTree<Node> root = new LDataTree<>();
	
	public FieldTree() {
		lastLayers = new HashMap<Integer, Integer>();
	}
	
	public int getLastLayer(int fieldID) {
		Integer id = lastLayers.get(fieldID);
		if (id == null)
			return 0;
		return id;
	}

	public void setLastLayer(int fieldID, int layerID) {
		lastLayers.put(fieldID, layerID);
	}
	
}
