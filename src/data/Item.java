package data;

import project.Project;
import data.Skill.Effect;
import data.subcontent.Element;
import data.subcontent.Icon;
import lwt.dataestructure.LDataList;

public class Item extends Data {

	// General
	public String description = "";
	public Icon icon = new Icon();
	public int price = 10;
	public boolean sellable = true;
	
	// Use
	public int skillID = -1;
	public boolean consume = true;
	public boolean needsUser = false;
	public LDataList<Effect> effects = new LDataList<>();
	public LDataList<Attribute> attributes = new LDataList<>();
	
	// Equip
	public String slot = "";
	public boolean allSlots = false;
	public LDataList<String> blocked = new LDataList<>();
	public LDataList<EquipStatus> equipStatus = new LDataList<>();
	public LDataList<Element> elements = new LDataList<>();
	public LDataList<Attribute> equipAttributes = new LDataList<>();
	
	public static class EquipStatus {
		
		public int id = -1;
		public boolean battle = false;
		
		public String toString() {
			Status s = (Status) Project.current.status.getData().get(id);
			String name = s == null ? "NULL" : s.name;
			if (battle)
				name += " (battle)";
			return name;
		}
		
	}
	
	public static class Attribute {
		
		public String key = "atk";
		public int add = 0;
		public int mul = 0;
		
		public String toString() {
			return key + " * " + (mul / 100f + 1) + " + " + add;
		}
		
		public boolean equals(Object obj) {
			if (obj instanceof Attribute) {
				Attribute other = (Attribute) obj;
				return other.add == add && other.mul == mul && other.key.equals(key);
			} else return false;
		}
		
	}

}
