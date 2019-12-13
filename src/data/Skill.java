package data;

import java.util.Arrays;

import project.Project;
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
	
	// Elements
	public LDataList<Bonus> elements = new LDataList<>(); 
	public boolean userElements = true;
	
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
	public String introTime = "";
	public String castTime = "";
	public String centerTime = "";
	public String targetTime = "";
	public String finishTime = "";
	public boolean damageAnim = true;
	
	// Effects
	public LDataList<Effect> effects = new LDataList<>();
	public LDataList<SkillStatus> statusAdd = new LDataList<>();
	public LDataList<SkillStatus> statusRemove = new LDataList<>();
	
	// Range
	public Mask effectMask = new Mask();
	public Mask castMask = new Mask();
	
	public Skill() {
		effects.add(new Effect());
		costs.add(new Tag("sp", "10"));
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
	
	public static class Mask {
		
		public int centerX = 1;
		public int centerY = 1;
		public int centerH = 1;
		public boolean[][][] grid = new boolean[][][] {{{true}}};

		@Override
		public boolean equals(Object obj) {
			if (this == obj) return true;
			if (obj == null) return false;
			if (getClass() != obj.getClass()) return false;
			Mask other = (Mask) obj;
			return centerH == other.centerH && centerX == other.centerX && 
					centerY == other.centerY && Arrays.deepEquals(grid, other.grid);
		}
		
	}
	
	public static class SkillStatus {
		
		public int id = 0;
		public String rate = "100";
		
		public String toString() {
			Status s = (Status) Project.current.status.getData().get(id);
			return (s == null ? "NULL" : s.name) + " (" + rate + ")";
		}

	}

}
