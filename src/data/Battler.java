package data;

import project.Project;
import data.subcontent.Bonus;
import data.subcontent.Script;
import lwt.dataestructure.LDataList;

public class Battler extends Data {

	public boolean persistent = false;
	public Script ai = new Script();
	public int classID = 0;
	public int attackID = 0;
	public int level = 0;
	public int money = 0;
	public int exp = 0;
	public LDataList<Integer> attributes = new LDataList<>();
	
	// Initial
	public LDataList<Integer> skills = new LDataList<>();
	public LDataList<Integer> status = new LDataList<>();
	public LDataList<Bonus> elements = new LDataList<>();
	
	// Items
	public LDataList<Drop> items = new LDataList<>();
	public LDataList<Equip> equip = new LDataList<>();
	
	public static class Equip {
		
		public String key = "";
		public int id = 0;
		public int state = 0;
		
		public String toString() {
			Item item = (Item) Project.current.items.getTree().get(id);
			if (item == null)
				return key + ":        ";
			else
				return key + ": " + item.toString();
		}
		
		public boolean equals(Object other) {
			if (other instanceof Equip) {
				Equip equip = (Equip) other;
				return equip.id == id && equip.state == state && equip.key.equals(key);
			} else return false;
		}
		
		public Equip clone() {
			Equip e = new Equip();
			e.id = id;
			e.key = key;
			e.state = state;
			return e;
		}
		
	}
	
	public static class Drop extends Bonus {
		
		public int count = 1;
		
		public String toString() {
			Item item = (Item) Project.current.items.getTree().get(id);
			if (item == null)
				return "NULL";
			else
				return item.toString() + " x " + count + " (" + this.value + "%)";
		}
		
		public boolean equals(Object obj) {
			if (obj instanceof Drop) {
				Drop other = (Drop) obj;
				return other.id == id && other.count == count && other.value == value;
			} else return false;
		}
		
	}
	
}
