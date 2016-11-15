package data;

public class FieldNode {
	
	public String name = "New";
	public int id = 0;
	
	public FieldNode() {}
	
	public FieldNode(int id, String name) {
		this.id = id;
		this.name = name;
	}
	
	public String toString() {
		return name;
	}
	
}
