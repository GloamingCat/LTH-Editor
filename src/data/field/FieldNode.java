package data.field;

public class FieldNode {

	public String name = "New Field";
	
	public int lastTerrainLayer = -1;
	public int lastObstacleLayer = -1;
	public int lastRegionLayer = -1;
	public int lastPartyLayer = -1;
	
	public FieldNode clone() {
		return this; // TODO
	}
	
}