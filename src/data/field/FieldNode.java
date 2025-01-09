package data.field;

public class FieldNode implements Cloneable {

	public String name = "New Field";
	
	public int[] lastLayers = new int[] {-1, -1, -1};
	
	public FieldNode clone() {
		FieldNode copy;
        try {
			copy = (FieldNode) super.clone();
			return copy;
        } catch (CloneNotSupportedException e) {
            copy = new FieldNode();
        }
		copy.lastLayers = lastLayers.clone();
		copy.name = name;
		return copy;
	}
	
	public String toString() {
		return name;
	}
	
}