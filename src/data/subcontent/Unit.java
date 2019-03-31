package data.subcontent;

import lwt.dataestructure.LDataList;
import project.Project;
import data.Battler;
import data.GameCharacter;
import data.Battler.Equip;

public class Unit {
	
	public String key = "Key";
	public int charID = 0;
	public int battlerID = -1;
	public int x = 1;
	public int y = 1;
	public LDataList<Equip> equip = new LDataList<>();
	public boolean backup = false;
	
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
					other.charID == charID && other.battlerID == battlerID &&
					other.equip.equals(equip);
		} else return false;
	}
	
}