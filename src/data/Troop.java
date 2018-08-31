package data;

import java.util.ArrayList;

import org.eclipse.swt.graphics.Point;

import project.Project;
import lwt.dataestructure.LDataList;

public class Troop extends Data {

	public int minWidth = 1;
	public int minHeight = 1;
	public LDataList<Unit> current = new LDataList<>();
	public LDataList<Unit> backup = new LDataList<>();
	
	public static class Unit {
		public String key = "Key";
		public int battlerID = 0;
		public int charID = 0;
		public int x = 0;
		public int y = 0;
		public ArrayList<Integer> equipment;
		
		public String toString() {
			Battler b = (Battler) Project.current.battlers.getTree().get(battlerID);
			return b.name + " (" + x + ", " + y + ")";
		}
	}
	
	public Point getSize() {
		int x = minWidth;
		int y = minHeight;
		for (Unit u : current) {
			x = Math.max(x, u.x);
			y = Math.max(y, u.y);
		}
		return new Point(x, y);
	}
	
}
