package data;

import org.eclipse.swt.graphics.Point;

import data.Battler.Drop;
import data.Battler.Equip;
import data.config.Config;
import project.Project;
import lwt.dataestructure.LDataList;

public class Troop extends Data {

	public int scriptID = -1;
	public boolean persistent = false;
	public int money = 100;
	public LDataList<Drop> items = new LDataList<>();
	
	public LDataList<Unit> current = new LDataList<>();
	public LDataList<Unit> backup = new LDataList<>();
	
	public static class Unit {
		
		public String key = "Key";
		public int charID = 0;
		public int battlerID = -1;
		public int x = 1;
		public int y = 1;
		public LDataList<Equip> equip = new LDataList<>();
		
		public String toString() {
			int id = battlerID;
			if (id < 0) {
				GameCharacter gc = (GameCharacter) Project.current.characters.getData().get(charID);
				id = gc.battlerID;
			}
			Battler b = (Battler) Project.current.battlers.getData().get(id);
			String name = " (" + (b == null ? "NULL" : b.name) + ")";
			String pos = " (" + x + ", " + y + ")";
			return key + name + pos;
		}
		
	}
	
	public Point getSize() {
		Config.Troop conf = Project.current.config.getData().troop;
		int x = conf.width;
		int y = conf.height;
		for (Unit u : current) {
			x = Math.max(x, u.x);
			y = Math.max(y, u.y);
		}
		return new Point(x, y);
	}
	
}
