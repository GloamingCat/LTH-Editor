package data;

import org.eclipse.swt.graphics.Point;

import project.Project;
import lwt.dataestructure.LDataList;

public class Troop {

	public String name = "New Troop";
	public int minWidth = 1;
	public int minHeight = 1;
	public LDataList<Unit> units = new LDataList<>();
	
	public static class Unit {
		public int battleID = 0;
		public int x = 0;
		public int y = 0;
		
		public String toString() {
			Battler b = (Battler) Project.current.battlers.getList().get(battleID);
			return b.name + " (" + x + ", " + y + ")";
		}
	}
	
	public Point getSize() {
		int x = minWidth;
		int y = minHeight;
		for (Unit u : units) {
			x = Math.max(x, u.x);
			y = Math.max(y, u.y);
		}
		return new Point(x, y);
	}
	
}
