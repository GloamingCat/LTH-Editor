package project;

import data.*;
import data.config.Config;
import lwt.dataserialization.LSerializer;

public class Project implements LSerializer {
	
	public GObjectTreeSerializer animations;
	
	public GObjectTreeSerializer battlers;
	public GObjectTreeSerializer characters;
	public GObjectTreeSerializer classes;
	public GObjectTreeSerializer items;
	public GObjectTreeSerializer obstacles;
	public GObjectTreeSerializer skills;
	public GObjectTreeSerializer status;
	public GObjectTreeSerializer terrains;
	public GObjectTreeSerializer tilesets;
	public GObjectTreeSerializer troops;
	
	public FieldTreeSerializer fieldTree;
	public GObjectSerializer<Config> config;
	
	private LSerializer[] allData;
	
	public static Project current = null;
	public String path;
	
	public Project(String path) {
		this.path = path;
		
		animations = new GObjectTreeSerializer(dataPath() + "animations", Animation.class);
		battlers = new GObjectTreeSerializer(dataPath() + "battlers", Battler.class);
		characters = new GObjectTreeSerializer(dataPath() + "characters", GameCharacter.class);
		classes = new GObjectTreeSerializer(dataPath() + "classes", BattleClass.class);
		items = new GObjectTreeSerializer(dataPath() + "items", Item.class);
		obstacles = new GObjectTreeSerializer(dataPath() + "obstacles", Obstacle.class);
		skills = new GObjectTreeSerializer(dataPath() + "skills", Skill.class);
		status = new GObjectTreeSerializer(dataPath() + "status", Status.class);
		terrains = new GObjectTreeSerializer(dataPath() + "terrains", Terrain.class);
		tilesets = new GObjectTreeSerializer(dataPath() + "tilesets", Tileset.class);
		troops = new GObjectTreeSerializer(dataPath() + "troops", Troop.class);
		
		fieldTree = new FieldTreeSerializer(dataPath());
		
		config = new GObjectSerializer<Config>(systemPath() + "config", Config.class);
		
		allData = new LSerializer[] { animations, characters, obstacles, status, skills, 
				terrains, config };
	}
	
	public String dataPath() {
		return path + "data/";
	}
	
	public String systemPath() {
		return dataPath() + "system/";
	}
	
	public String fieldPath() {
		return dataPath() + "fields/";
	}
	
	public String imagePath() {
		return path + "images/";
	}
	
	public String scriptPath() {
		return path + "scripts/custom/";
	}
	
	public String audioPath() {
		return path + "audio/";
	}

	public String fontPath() {
		return path + "fonts/";
	}
	
	@Override
	public void initialize() {
		for (LSerializer data : allData) {
			data.initialize();
		}
		//FieldHelper.reloadMath();
		//TilePainter.reloadConfig();
		current = this;
	}	

	@Override
	public boolean save() {
		for (LSerializer data : allData) {
			if (!data.save())
				return false;
		}
		return true;
	}

	@Override
	public boolean load() {
		for (LSerializer data : allData) {
			if (!data.load())
				return false;
		}
		current = this;
		//FieldHelper.reloadMath();
		//TilePainter.reloadConfig();
		return true;
	}

	@Override
	public boolean isDataFolder(String path) {
		for(LSerializer data : allData) {
			if (data.isDataFolder(path)) {
				return true;
			}
		}
		return false;
	}

}
