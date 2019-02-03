package data.subcontent;

import data.field.FieldNode;
import project.Project;

public class Position {
	
	public int fieldID = 0;
	public int x = 0;
	public int y = 0;
	public int h = 0;
	public int direction = -1;
	
	public Position() {}
	
	public Position(Position copy) {
		this.fieldID = copy.fieldID;
		this.x = copy.x;
		this.y = copy.y;
		this.h = copy.h;
		this.direction = copy.direction;
	}
	
	public String toString() {
		FieldNode node = Project.current.fieldTree.getData().get(fieldID);
		String name = node == null ? "NULL" : node.name;
		String dir = direction == -1 ? "" : ", " + direction + "°";
		return name + " (" + x + "," + y + "), Layer " + h + dir;
	}
	
	public boolean equals(Object original) {
		if (original instanceof Position) {
			Position p = (Position) original;
			return p.fieldID == fieldID && p.direction == direction
					&& p.x == x && p.y == y && p.h == h;
		} else {
			return false;
		}
	}
	
	public Position clone() {
		return new Position(this);
	}
	
}