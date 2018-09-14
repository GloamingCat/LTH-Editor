package data;

import data.subcontent.Bonus;
import data.subcontent.Quad;
import data.subcontent.Tag;
import lwt.dataestructure.LDataList;

public class Skill extends Data {

	public String description = "";
	public int type = 1;
	public Quad icon = new Quad();
	public int radius = 1;
	public int range = 1;
	
	public String script = "";
	public String successRate = "((a:PRE() * 2 - b:EVD()) / b:EVD()) * 50";
	public String basicResult = "(a:ATKP() * 2 - b:DEFP()) * math.random(0.8, 1.2)";
	
	public int energyCost = 10;
	public int timeCost = 100; // percentage
	public int restriction = 0;
	
	public String userLoadAnim = "";
	public String userCastAnim = "";
	public boolean stepOnCast = true;
	
	public int loadAnimID = -1;
	public int castAnimID = -1;
	public int centerAnimID = -1;
	public int individualAnimID = -1;
	public boolean mirror = true;
	
	public LDataList<Bonus> elements = new LDataList<>(); 
	public LDataList<Tag> tags = new LDataList<>();

}
