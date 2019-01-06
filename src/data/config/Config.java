package data.config;

import data.Data;
import data.subcontent.Audio;
import data.subcontent.Constant;
import data.subcontent.Icon;
import data.subcontent.Position;
import lwt.dataestructure.LDataList;

public class Config {
	
	// General
	public String name = "New Project";
	
	public Player player = new Player();
	public Battle battle = new Battle();
	public Troop troop = new Troop();
	public Grid grid = new Grid();
	
	public Animations animations = new Animations();
	public Icons icons = new Icons();
	public Screen screen = new Screen();
	
	public Sounds sounds = new Sounds();
	
	// Lists
	
	public LDataList<Attribute> attributes = new LDataList<>();
	public LDataList<Data> elements = new LDataList<>();
	public LDataList<EquipType> equipTypes = new LDataList<>();
	public LDataList<Region> regions = new LDataList<>();
	public LDataList<Plugin> plugins = new LDataList<>();
	public LDataList<Constant> constants = new LDataList<>();
	
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
	
	// Graphics
	
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
	}
	
	public static class Icons {
		public Icon gold = new Icon();
		public Icon location = new Icon();
		public Icon time = new Icon();
	}
	
	public static class Screen {
		public int nativeWidth = 400;
		public int nativeHeight = 225;
		public int widthScale = 2;
		public int heightScale = 200;
		public int scaleType = 1;
		public int fpsLimit = 120;
		public int coverID = 211;
	}
	
	// Audio
	
	public static class Sounds {
		// GUI
		public Audio buttonConfirm = new Audio();
		public Audio buttonCancel = new Audio();
		public Audio buttonError = new Audio();
		public Audio buttonSelect = new Audio();
		public Audio text = new Audio();
		public Audio menu = new Audio();
		public Audio equip = new Audio();
		public Audio unequip = new Audio();
		public Audio buy = new Audio();
		public Audio sell = new Audio();
		public Audio save = new Audio();
		public Audio load = new Audio();
		public Audio exp = new Audio();
		public Audio levelup = new Audio();
		// Battle
		public Audio allyKO = new Audio();
		public Audio enemyKO = new Audio();
		public Audio battleIntro = new Audio();
		// Themes
		public Audio titleTheme = new Audio();
		public Audio battleTheme = new Audio();
		public Audio victoryTheme = new Audio();
		public Audio gameoverTheme = new Audio();
		
	}
	
}
