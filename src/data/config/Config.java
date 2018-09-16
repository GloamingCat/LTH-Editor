package data.config;

import data.subcontent.BattlerType;
import data.subcontent.Position;
import data.subcontent.Tag;
import lwt.dataestructure.LDataList;

public class Config {
	
	// General
	public String name = "New Project";
	public Player player = new Player();
	public Animations animations = new Animations();
	public Battle battle = new Battle();
	public Grid grid = new Grid();
	
	// Types
	public LDataList<Integer> initialMembers = new LDataList<>();
	public LDataList<BattlerType> battlerTypes = new LDataList<>();
	public LDataList<Attribute> attributes = new LDataList<>();
	public LDataList<String> elements = new LDataList<>();
	public LDataList<String> itemTypes = new LDataList<>();
	public LDataList<Region> regions = new LDataList<>();
	public LDataList<Tag> tags = new LDataList<>();
	
	public static class Player {
		public boolean pixelMovement = false;
		public int walkSpeed = 120;
		public int dashSpeed = 240;
		public boolean stopOnCollision = true;
		public Position startPos = new Position();
	}

	public static class Battle {
		public int tradeSkillID = 0;
		public int attHPID = 0;
		public int attSPID = 0;
		public int attTurnID = 0;
		public int attStepID = 0;
		public int attJumpID = 0;
	}
	
	public static class Grid {
		public int tileW = 36;
		public int tileH = 22;
		public int tileB = 16;
		public int tileS = 0;
		public int pixelsPerHeight = 16;
		public boolean allNeighbors = false;
	}
	
	public static class Animations {
		public int windowSkinID = 20;
		public int frameID = 21;
		public int cursorID = 22;
		public int highlightID = 23;
		public int battleCursorID = 24;
		public int tileCursorID = 25;
		public int tileID = 26;
		public int gaugeFrameID = 28;
		public int gaugeBarID = 29;
		public int arrowID = 210;
		
		public LDataList<String> sets = new LDataList<>();
		
		public Animations() {
			sets.add("Sleep");
		}
	}
	
	public static class Screen {
		public int nativeWidth = 225;
		public int nativeHeight = 400;
		public int scaleX = 200;
		public int scaleY = 200;
		public int scaleType = 1;
	}
	
}
