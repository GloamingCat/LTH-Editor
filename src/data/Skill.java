package data;

import java.util.Arrays;

import project.Project;
import data.subcontent.Property;
import data.subcontent.Icon;
import data.subcontent.Tag;
import lui.base.data.LInitializable;
import lui.base.data.LDataList;
import lui.base.data.LDataTree;

public class Skill extends Data implements LInitializable {

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
	
	public AnimInfo animInfo = new AnimInfo();
	
	// Legacy
	public String userLoadAnim;
	public String userCastAnim;
	public boolean stepOnCast;
	public int loadAnimID;
	public int castAnimID;
	public int individualAnimID;
	public boolean mirror;
	public String introTime;
	public String castTime;
	public String centerTime;
	public String targetTime;
	public String finishTime;
	public boolean damageAnim;
	
	@Override
	public void onStart(LDataTree<Object> root, LDataTree<Object> node) {
		if (userLoadAnim != null) {
			animInfo.userLoad = userLoadAnim;
			animInfo.userCast = userCastAnim;
			animInfo.stepOnCast = stepOnCast;
			animInfo.loadID = loadAnimID;
			animInfo.castID = castAnimID;
			animInfo.individualID = individualAnimID;
			animInfo.mirror = mirror;
			animInfo.introTime = introTime.isEmpty() ? -1 : Integer.parseInt(introTime);
			animInfo.castTime = castTime.isEmpty() ? -1 : Integer.parseInt(castTime);
			animInfo.centerTime = centerTime.isEmpty() ? -1 : Integer.parseInt(centerTime);
			animInfo.targetTime = targetTime.isEmpty() ? -1 : Integer.parseInt(targetTime);
			animInfo.finishTime = finishTime.isEmpty() ? -1 : Integer.parseInt(finishTime);
			animInfo.damageAnim = damageAnim;
			userLoadAnim = null;
			userCastAnim = null;
			introTime = null;
			castTime = null;
			centerTime = null;
			targetTime = null;
			finishTime = null;
		}
	}
	
	// Effects
	public LDataList<Effect> effects = new LDataList<>();
	
	// Range
	public Mask effectMask = new Mask();
	public boolean rotateEffect = false;
	public Mask castMask = new Mask();
	
	public Skill() {}


	public void initialize() {
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

	public static class AnimInfo {		
		// User Animations
		public String userLoad = "";
		public String userCast = "";
		public boolean stepOnCast = true;
		
		// Battle Animations
		public int loadID = -1;
		public int castID = -1;
		public int individualID = -1;
		public boolean mirror = true;
		
		// Animation Options
		public int introTime = -1;
		public int castTime = -1;
		public int centerTime = -1;
		public int targetTime = -1;
		public int finishTime = -1;
		public boolean damageAnim = true;
	}
}
