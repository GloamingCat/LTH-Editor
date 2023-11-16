package data;

import java.util.Arrays;

import project.Project;
import data.subcontent.Property;
import data.subcontent.Icon;
import data.subcontent.Tag;
import lwt.dataestructure.LDataList;

public class Skill extends Data {

	// General
	public String description = "";
	public Icon icon = new Icon();
	public String script = "";
	
	// Affected characters
	public int type = 0; // 0 => Offensive; 1 => Supportive; 2 => General
	public boolean allParties = false; // Affects any character regardless of type
	public String effectCondition = "target.battler:isAlive()"; // Required state to be affected by skill
	
	// Selection and navigation
	public int selection = 1; // 0 => Any tile, 1 => affected only; 2 => reachable only; 3 => affected and reachable only
	public boolean autoPath = true;
	public boolean freeNavigation = true;
	
	// Use conditions
	public String condition = ""; // Required state to use the skill
	public int restriction = 0; // 0 => Anywhere, 1 => Battle only, 2 => Field only
	public LDataList<Tag> costs = new LDataList<>();
	
	// Elements
	public LDataList<Property> elements = new LDataList<>(); 
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
	
	// Range
	public Mask effectMask = new Mask();
	public boolean rotateEffect = false;
	public Mask castMask = new Mask();
	
	public Skill() {
		effects.add(new Effect());
		costs.add(new Tag("sp", "10"));
	}
	
	public static class Effect {
		
		public String key = "hp";
		public String basicResult = "action:defaultPhysicalDamage(user, target, a, b)";
		public String successRate = "action:defaultSuccessRate(user, target, a, b)";
		public boolean heal = false;
		public boolean absorb = false;
		public int statusID = -1;
		
		public String toString() {
			Status s = (Status) (statusID == -1 ? null : Project.current.status.getData().get(statusID));
			String op = heal ? " += " : " -= ";
			String points = key + op + basicResult + " (" + successRate + ")";
			if (s == null) {
				if (key.isEmpty()) {
					return "No effect";
				} else {
					return points;
				}
			} else {
				op = heal ? "-" : "+";
				if (key.isEmpty()) {
					return op + s.name + " (" + successRate + ")";
				} else {
					
					return op + s.name + ", " + points;
				}
			}
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

}
