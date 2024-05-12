package data.subcontent;

import data.field.FieldNode;
import project.Project;

public class Position implements Cloneable {
	
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
		String name = node == null ? ("NULL " + fieldID) : node.name;
		String dir = direction == -1 ? "" : ", " + direction + "º";
		return name + " (" + x + "," + y + "), Layer " + h + dir;
	}
	
	public boolean equals(Object original) {
		if (original instanceof Position p) {
            return p.fieldID == fieldID && p.direction == direction
					&& p.x == x && p.y == y && p.h == h;
		} else {
			return false;
		}
	}

	@Override
	public Position clone() {
        try {
            Position clone = (Position) super.clone();
			clone.fieldID = fieldID;
			clone.x = x;
			clone.y = y;
			clone.h = h;
			clone.direction = direction;
			return clone;
        } catch (CloneNotSupportedException e) {
            throw new RuntimeException(e);
        }

	}
	
}