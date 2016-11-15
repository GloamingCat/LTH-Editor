package data;

import lwt.dataestructure.LDataList;

public class Skill {

	public String name = "New Skill";
	public String description = "";
	public String icon = "";
	public int radius = 1;
	public int range = 1;
	
	public String effect = "battle\\effects\\HP Damage.lua";
	public String param = "(a.ATKP * 2 - b.DEFP) * math.random(0.8, 1.2)";
	public int energyCost = 10;
	public int timeCost = 100; // percentage
	
	public String userAnim = "";
	public int castAnimID;
	public int centerAnimID;
	public int individualAnimID;
	
	public LDataList<Tag> tags = new LDataList<>();

	public String toString() {
		return name;
	}
	
}
