package data;

import lwt.dataestructure.LDataList;

public class Skill {

	public String name = "New Skill";
	public String description = "";
	public String type = "";
	public Quad icon = new Quad();
	public int radius = 1;
	public int range = 1;
	
	public Script script = new Script("battle\\effects\\HP Damage.lua", "(a.ATKP * 2 - b.DEFP) * math.random(0.8, 1.2)");
	public int energyCost = 10;
	public int timeCost = 100; // percentage
	public int restriction = 0;
	
	public String userLoadAnim = "";
	public String userCastAnim = "";
	
	public int loadAnimID = -1;
	public int castAnimID = -1;
	public int centerAnimID = -1;
	public int individualAnimID = -1;
	
	public LDataList<Bonus> elements = new LDataList<>(); 
	public LDataList<Tag> tags = new LDataList<>();

	public String toString() {
		return name;
	}
	
}
