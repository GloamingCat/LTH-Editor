package data.field;

import project.Project;
import lwt.dataestructure.LDataList;

public class Party {

	// How member's characters are generated.
	// 0 => They are not. The characters from the field are used.
	// 1 => Using the troop's grid.
	public int memberGen = 1;

	// Position of the top-left corner of the party (or left corner, in isometric/hexagonal grid).
	public int x = 0;
	public int y = 0;
	public int h = 0;

	// The initial direction of the characters.
	// The troop's grid is also rotated to reposition them, in case they are generated from grid.
	public int direction = 0;
	
	// The possible troops to occupate this party spot.
	public LDataList<Integer> troops = new LDataList<>();
	
	public String toString() {
		return "";
	}
	
	public int maxX() {
		if (direction % 2 == 0)
			return x + Project.current.config.getData().troop.width - 1;
		else
			return x + Project.current.config.getData().troop.height - 1;
	}
	
	public int maxY() {
		if (direction % 2 == 0)
			return y + Project.current.config.getData().troop.height - 1;
		else
			return y + Project.current.config.getData().troop.width - 1;
	}

}
