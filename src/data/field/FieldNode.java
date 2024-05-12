package data.field;

public class FieldNode {

	public String name = "New Field";
	
	public int[] lastLayers = new int[] {-1, -1, -1};
	
	public FieldNode clone() {
        try {
			FieldNode copy = (FieldNode) super.clone();
			copy.lastLayers = lastLayers.clone();
			copy.name = name;
			return copy;
        } catch (CloneNotSupportedException e) {
            throw new RuntimeException(e);
        }
	}
	
	public String toString() {
		return name;
	}
	
}