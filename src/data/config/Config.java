package data.config;

import data.subcontent.Audio;
import data.subcontent.Script;
import lui.base.data.LDataList;
import data.GameCharacter.Portrait;
import data.subcontent.Node;
import data.subcontent.Position;

public class Config {
	
	// General
	public String name = "New Project";
	public int fpsMax = 120;
	public int fpsMin = 15;
	public int coverID = -1;
	public int logoID = -1;
	public int platform = 0;
	
	public Player player = new Player();
	public Battle battle = new Battle();
	public Troop troop = new Troop();
	public Grid grid = new Grid();
	public Screen screen = new Screen();

	public LDataList<Node> animations = new LDataList<>();
	public LDataList<Portrait> icons = new LDataList<>();
	public LDataList<Audio> sounds = new LDataList<>();
	
	// System
	
	public static class Player {
		public int walkSpeed = 104;
		public int dashSpeed = 200;
		public int diagThreshold = 15;
		public Position startPos = new Position();
		public Script loadScript = new Script("LoadPlayer.lua", false);
	}

	public static class Battle {
		public int maxLevel = 99;
		public String attHP = "hp";
		public String attSP = "sp";
		public String attStep = "mov";
		public String attJump = "jmp";
		public int itemSkillID = 0;
		public boolean battleEndRevive = true;
		public boolean keepParties = true;
		public int charSpeed = 150;
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
		public int depthPerHeight = 18;
		public int depthPerY = 11;
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
		public int mobileScaleType = 2;
		public boolean pixelPerfect = true;
		public boolean vsync = false;
	}
	
}
