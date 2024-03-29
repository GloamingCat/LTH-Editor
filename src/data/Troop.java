package data;

import data.Battler.Drop;
import data.config.Config;
import project.Project;
import lwt.dataestructure.LDataList;

public class Troop extends Data {

	public String ai = "";
	public boolean persistent = false;
	public int money = 0;
	public int exp = 0;
	public LDataList<Drop> items = new LDataList<>();
	public LDataList<Unit> members = new LDataList<>();
	
	public int[] getSize() {
		Config.Troop conf = Project.current.config.getData().troop;
		int x = conf.width;
		int y = conf.height;
		for (Unit u : members) {
			if (u.list == 0) {
				x = Math.max(x, u.x);
				y = Math.max(y, u.y);
			}
		}
		return new int[] {x, y};
	}
	
	public class Unit {
		
		public String key = "Key";
		public int charID = 0;
		public int battlerID = -1;
		public int x = 1;
		public int y = 1;
		public int list; // 0 => Current party, 1 => Backup, 2 => Hidden
		
		public String toString() {
			int id = battlerID;
			if (id < 0) {
				GameCharacter gc = (GameCharacter) Project.current.characters.getData().get(charID);
				id = gc == null ? -1 : gc.battlerID;
			}
			Battler b = (Battler) Project.current.battlers.getData().get(id);
			String name = " (" + (b == null ? "NULL" : b.name) + ")";
			String pos = " (" + x + ", " + y + ")";
			return key + name + pos;
		}
		
		public boolean equals(Object obj) {
			if (obj instanceof Unit) {
				Unit other = (Unit) obj;
				return other.key.equals(key) && other.x == x && other.y == y &&
						other.charID == charID && other.battlerID == battlerID;
			} else return false;
		}
		
	}
	
}
