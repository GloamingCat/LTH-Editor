package data;

import project.Project;
import data.subcontent.Property;
import data.subcontent.Icon;
import data.subcontent.Rule;
import lwt.dataestructure.LDataList;

public class Battler extends Data {

	public boolean persistent = false;
	public LDataList<Rule> ai = new LDataList<>();
	public int jobID = 0;
	public int level = 0;
	public int money = 0;
	public int exp = 0;
	public LDataList<Integer> attributes = new LDataList<>();
	
	// Initial
	public LDataList<Integer> skills = new LDataList<>();
	public LDataList<Integer> status = new LDataList<>();
	public LDataList<Property> elements = new LDataList<>();
	
	// Items
	public LDataList<Drop> items = new LDataList<>();
	public LDataList<Equip> equip = new LDataList<>();
	
	// Recruit
	public boolean recruit = false;
	public Icon icon = new Icon();
	public String description = "";
	
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
	
	public static class Drop extends Property {
		
		public int count = 1;
		
		public String toString() {
			Item item = (Item) Project.current.items.getTree().get(id);
			String name = item == null ? ("NULL " + id) : item.toString();
			return name + " x " + count + " (" + this.value + "%)";
		}
		
		public boolean equals(Object obj) {
			if (obj instanceof Drop) {
				Drop other = (Drop) obj;
				return other.id == id && other.count == count && other.value == value;
			} else return false;
		}
		
	}
	
}
