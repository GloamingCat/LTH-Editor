package data.subcontent;

public class Node {
	
	public String name = "New";
	public int id = 0;
	
	public Node() {}
	
	public Node(int id, String name) {
		this.id = id;
		this.name = name;
	}

	@Override
	public String toString() {
		return name;
	}

	@Override
	public boolean equals(Object node) {
		if (node instanceof Node n) {
			return n.id == id && name.equals(n.name);
		}
		return false;
	}
	
}
