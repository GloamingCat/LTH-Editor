package data.subcontent;

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
	
	public boolean equals(Node node) {
		if (node instanceof Node) {
			Node n = (Node) node;
			return n.id == id;
		}
		return false;
	}
	
}
