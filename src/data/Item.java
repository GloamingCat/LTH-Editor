package data;

import data.Skill.Effect;
import data.subcontent.Bonus;
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
	public LDataList<Effect> effects = new LDataList<>();
	public LDataList<Bonus> statusAdd = new LDataList<>();
	public LDataList<Bonus> elementAdd = new LDataList<>();
	
	// Equip
	public String slot = "";
	public boolean allSlots = false;
	public LDataList<String> blocked = new LDataList<>();
	public LDataList<Bonus> status = new LDataList<>();
	public LDataList<Bonus> elements = new LDataList<>();
	public LDataList<Attribute> attributes = new LDataList<>();
	
	public static class Attribute {
		
		public String key = "atk";
		public int add = 0;
		public int mul = 0;
		
		public String toString() {
			return key + " * " + (mul / 100 + 1) + " + " + add;
		}
		
	}

}
