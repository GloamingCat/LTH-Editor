package data;

import project.Project;
import data.subcontent.Bonus;
import lwt.dataestructure.LDataList;

public class Battler extends Data {

	public boolean persistent = false;
	public int scriptID = 0;
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
	public LDataList<Bonus> items = new LDataList<>();
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
		
	}
	
}
