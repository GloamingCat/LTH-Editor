package data.config;

import project.Project;
import lwt.dataestructure.LDataList;
import data.Animation;
import data.GameCharacter.Portrait;
import data.subcontent.Node;
import data.subcontent.Position;

public class Config {
	
	// General
	public String name = "New Project";
	public Player player = new Player();
	public Battle battle = new Battle();
	public Troop troop = new Troop();
	public Grid grid = new Grid();
	public Screen screen = new Screen();
	
	public static class AnimNode extends Node {
		public String toString() {
			Animation anim = (Animation) Project.current.animations.getTree().get(id);
			return name + ": " + (anim == null ? "NULL" : anim.toString());
		}
	}
	
	public static class IconNode extends Portrait {
		public String toString() {
			Animation anim = (Animation) Project.current.animations.getTree().get(id);
			return name + ": " + (anim == null ? "NULL" : anim.toString());
		}
	}
	
	public LDataList<AnimNode> animations = new LDataList<>();
	public LDataList<IconNode> icons = new LDataList<>();
	
	// System
	
	public static class Player {
		public int walkSpeed = 104;
		public int dashSpeed = 208;
		public Position startPos = new Position();
	}

	public static class Battle {
		public int maxLevel = 99;
		public String attHP = "hp";
		public String attSP = "sp";
		public String attStep = "mov";
		public String attJump = "jmp";
		public int itemSkillID = 0;
	}
	
	public static class Troop {
		public int initialTroopID = 1;
		public int maxMembers = 6;
		public int width = 5;
		public int height = 3;
	}
	
	public static class Grid {
		public int tileW = 36;
		public int tileH = 22;
		public int tileB = 16;
		public int tileS = 0;
		public int pixelsPerHeight = 8;
		public int depthPerHeight = 24;
		public boolean allNeighbors = false;
		public boolean overpassAllies = false;
		public boolean overpassDeads = true;
	}
	
	public static class Screen {
		public int nativeWidth = 400;
		public int nativeHeight = 225;
		public int widthScale = 200;
		public int heightScale = 200;
		public int scaleType = 1;
		public int fpsLimit = 120;
		public int coverID = 211;
	}
	
}
