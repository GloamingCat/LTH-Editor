package data;

public class Node {
	
	public String name = "New";
	public int id = 0;
	
	public Node() {}
	
	public Node(int id, String name) {
		this.id = id;
		this.name = name;
	}
	
	public String toString() {
		return name;
	}
	
}
