package data;

import java.util.HashMap;

import org.eclipse.swt.graphics.RGB;

import lwt.dataestructure.LDataList;

public class Config {
	
	// General
	public String name = "New Project";
	public Player player = new Player();
	public Party party = new Party();
	public Battle battle = new Battle();
	public GUI gui = new GUI();
	
	// Grid
	public int tileW = 36;
	public int tileH = 22;
	public int tileB = 16;
	public int tileS = 0;
	public int pixelsPerHeight = 16;
	public boolean allNeighbors = false;
	
	public HashMap<String, FontData> fonts = new HashMap<>();
	public HashMap<String, Audio> sounds = new HashMap<>();
	
	// Types
	public LDataList<Attribute> attributes = new LDataList<>();
	public LDataList<String> elements = new LDataList<>();
	public LDataList<String> itemTypes = new LDataList<>();
	public LDataList<Region> regions = new LDataList<>();
	public LDataList<ImageAtlas> atlases = new LDataList<>();
	public LDataList<Tag> tags = new LDataList<>();
	
	public Config() {
		fonts.put("dialog", new FontData("Arial", 12, "\\c{#000000}"));
		fonts.put("button", new FontData("Arial", 12, "\\c{#000000}"));
		fonts.put("popup", new FontData("Arial", 14, "\\c{#0000FF}"));
		sounds.put("cursor", new Audio("cursor.wav", 100, 100, 100));
		sounds.put("confirm", new Audio("confirm.wav", 100, 100, 100));
		sounds.put("cancel", new Audio("cancel.wav", 100, 100, 100));
		sounds.put("dialogue", new Audio("cursor.wav", 100, 75, 150));
	}
	
	public static class Player {
		public boolean pixelMovement = false;
		public int walkSpeed = 120;
		public int dashSpeed = 240;
		public boolean stopOnCollision = true;
		public Script script = new Script("character/Player.lua", "");
		public Position startPos = new Position();
	}
	
	public static class Party {
		public LDataList<Integer> initialMembers = new LDataList<>();
		public int maxBattleMembers = 6;
	}
	
	public static class Battle {
		public LDataList<BattlerType> battlerTypes = new LDataList<>();
		public int turnLimit = 2000;
		public boolean individualTurn = true;
		public boolean groupEscape = true;
		public boolean partyTileEscape = true;
		public boolean instantTurnTransition = false;
	}
	
	public static class GUI {
		public int cursorAnimID = 0;
		public RGB disabledColor = new RGB(128, 128, 128);
	}
	
}
