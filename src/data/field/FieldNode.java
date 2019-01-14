package data.field;

public class FieldNode {

	public String name = "New Field";
	
	public int[] lastLayers = new int[] {-1, -1, -1, -1};
	
	public FieldNode clone() {
		FieldNode copy = new FieldNode();
		copy.lastLayers = lastLayers.clone();
		copy.name = name;
		return copy;
	}
	
	public String toString() {
		return name;
	}
	
}