package data;

import lwt.dataestructure.LDataList;

public class Config {
	
	// General
	public String name = "New Project";
	public Player player = new Player();
	public GUI gui = new GUI();
	public Battle battle = new Battle();
	public Grid grid = new Grid();
	
	// Types
	public LDataList<Integer> initialMembers = new LDataList<>();
	public LDataList<BattlerType> battlerTypes = new LDataList<>();
	public LDataList<Attribute> attributes = new LDataList<>();
	public LDataList<String> elements = new LDataList<>();
	public LDataList<String> itemTypes = new LDataList<>();
	public LDataList<Region> regions = new LDataList<>();
	public LDataList<ImageAtlas> atlases = new LDataList<>();
	public LDataList<Tag> tags = new LDataList<>();
	
	public static class Player {
		public boolean pixelMovement = false;
		public int walkSpeed = 120;
		public int dashSpeed = 240;
		public boolean stopOnCollision = true;
		public Script script = new Script("character/Player.lua", "");
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
	
	public static class GUI {
		public int cursorAnimID = 0;
		public int battleCursorAnimID = 1;
		public int tileAnimID = 2;
		public int tileHLAnimID = 3;
	}
	
}
