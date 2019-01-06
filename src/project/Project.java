package project;

import gson.project.GObjectListSerializer;
import gson.project.GObjectSerializer;
import gson.project.GObjectTreeSerializer;
import data.*;
import data.config.*;
import data.subcontent.Constant;
import lwt.dataserialization.LSerializer;

public class Project implements LSerializer {
	
	// Database
	public GObjectTreeSerializer animations;
	public GObjectTreeSerializer battlers;
	public GObjectTreeSerializer characters;
	public GObjectTreeSerializer classes;
	public GObjectTreeSerializer items;
	public GObjectTreeSerializer obstacles;
	public GObjectTreeSerializer scripts;
	public GObjectTreeSerializer skills;
	public GObjectTreeSerializer status;
	public GObjectTreeSerializer terrains;
	public GObjectTreeSerializer tilesets;
	public GObjectTreeSerializer troops;
	
	// System
	public GObjectSerializer<Config> config;
	public GObjectListSerializer attributes;
	public GObjectListSerializer constants;
	public GObjectListSerializer elements;
	public GObjectListSerializer equipTypes;
	public GObjectListSerializer plugins;
	public GObjectListSerializer regions;
	
	public FieldTreeSerializer fieldTree;
	
	
	private LSerializer[] allData;
	
	public static Project current = null;
	public String path;
	
	public Project(String path) {
		this.path = path;
		
		// Database
		animations = new GObjectTreeSerializer(dataPath() + "animations", Animation.class);
		battlers = new GObjectTreeSerializer(dataPath() + "battlers", Battler.class);
		characters = new GObjectTreeSerializer(dataPath() + "characters", GameCharacter.class);
		classes = new GObjectTreeSerializer(dataPath() + "classes", BattleClass.class);
		items = new GObjectTreeSerializer(dataPath() + "items", Item.class);
		obstacles = new GObjectTreeSerializer(dataPath() + "obstacles", Obstacle.class);
		scripts = new GObjectTreeSerializer(dataPath() + "scripts", Script.class);
		skills = new GObjectTreeSerializer(dataPath() + "skills", Skill.class);
		status = new GObjectTreeSerializer(dataPath() + "status", Status.class);
		terrains = new GObjectTreeSerializer(dataPath() + "terrains", Terrain.class);
		tilesets = new GObjectTreeSerializer(dataPath() + "tilesets", Tileset.class);
		troops = new GObjectTreeSerializer(dataPath() + "troops", Troop.class);
		
		// System
		config = new GObjectSerializer<Config>(systemPath() + "config", Config.class);
		attributes = new GObjectListSerializer(systemPath() + "attributes", Attribute.class);
		constants = new GObjectListSerializer(systemPath() + "constants", Constant.class);
		elements = new GObjectListSerializer(systemPath() + "elements", Data.class);
		equipTypes = new GObjectListSerializer(systemPath() + "equipTypes", EquipType.class);
		plugins = new GObjectListSerializer(systemPath() + "plugins", Plugin.class);
		regions = new GObjectListSerializer(systemPath() + "regions", Region.class);
		
		fieldTree = new FieldTreeSerializer(dataPath());
		
		allData = new LSerializer[] { animations, battlers, characters, classes,
				items, obstacles, scripts, skills, status, terrains, troops, 
				config, attributes, constants, elements, equipTypes, plugins, regions };
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
