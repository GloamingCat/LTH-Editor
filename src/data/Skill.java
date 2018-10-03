package data;

import data.subcontent.Bonus;
import data.subcontent.Icon;
import data.subcontent.Tag;
import lwt.dataestructure.LDataList;

public class Skill extends Data {

	// General
	public String description = "";
	public Icon icon = new Icon();
	public String script = "";
	public int type = 1; // 0 => Offensive, 1 => Supportive, 2 => General
	public int targetType = 0; // 0 => Any tile, 1 => Any character, 2 => Living only, 3 => Dead only
	public int restriction = 0; // 0 => Anywhere, 1 => Battle only, 2 => Field only
	public LDataList<Tag> costs = new LDataList<>();
	
	// User Animations
	public String userLoadAnim = "";
	public String userCastAnim = "";
	public boolean stepOnCast = true;
	
	// Battle Animations
	public int loadAnimID = -1;
	public int castAnimID = -1;
	public int individualAnimID = -1;
	public boolean mirror = true;
	
	// Animation Options
	public boolean damageAnim = true;
	public String castTime = "";
	
	// Effects
	public LDataList<Effect> effects = new LDataList<>();
	public LDataList<Bonus> elements = new LDataList<>(); 
	public LDataList<Bonus> status = new LDataList<>(); 
	public boolean userElements = true;
	
	// Range
	public Range effectRange = new Range();
	public Range castRange = new Range();
	
	// Tags
	public LDataList<Tag> tags = new LDataList<>();
	
	public Skill() {
		effects.add(new Effect());
		costs.add(new Tag("sp", "10"));
	}
	
	public static class Range {
		
		public int minh = 0;
		public int maxh = 0;
		public int far = 1;
		public int near = 0;
		
		public String toString() {
			return "Circle [" + near + ", " + far + "], Height [" + minh + ", " + maxh + "]";
		}
		
	}
	
	public static class Effect {
		
		public String key = "hp";
		public String basicResult = "100";
		public String successRate = "100";
		public boolean heal = false;
		public boolean absorb = false;
		
		public String toString() {
			return "\"" + key + "\": " + basicResult;
		}
		
	}

}
