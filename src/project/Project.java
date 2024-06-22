package project;

import gui.helper.FieldHelper;
import gui.helper.TilePainter;

import java.util.Arrays;

import data.*;
import data.config.*;
import gson.GObjectListSerializer;
import gson.GObjectSerializer;
import gson.GObjectTreeSerializer;
import lui.base.data.LDataTree;
import lui.base.serialization.LFileManager;
import lui.base.serialization.LSerializer;

public class Project implements LSerializer {
	
	private static class ProjectSettings {
		public String dataPath = "data/";
		public String imagePath = "images/";
		public String audioPath = "audio/";
		public int dataVersion = 0;
	}
	
	private final ProjectSettings settings;
	
	// Database
	public GObjectTreeSerializer animations;
	public GObjectTreeSerializer battlers;
	public GObjectTreeSerializer characters;
	public GObjectTreeSerializer events;
	public GObjectTreeSerializer jobs;
	public GObjectTreeSerializer items;
	public GObjectTreeSerializer obstacles;
	public GObjectTreeSerializer skills;
	public GObjectTreeSerializer status;
	public GObjectTreeSerializer terrains;
	public GObjectTreeSerializer troops;
	
	// System
	public GObjectSerializer<Config> config;
	public GObjectListSerializer attributes;
	public GObjectListSerializer languages;
	public GObjectListSerializer elements;
	public GObjectListSerializer equipTypes;
	public GObjectListSerializer plugins;
	public GObjectListSerializer regions;
	
	public FieldTreeSerializer fieldTree;
	
	private LSerializer[] allData;
	private final GObjectTreeSerializer[] database;
	
	public static Project current = null;
	public String path;
	
	public Project(String path) {
		GObjectSerializer<ProjectSettings> settings = new GObjectSerializer<>(path, ProjectSettings.class);
		if (settings.load()) {
			this.path = LFileManager.getDirectory(path);
			this.settings = settings.getData();
		} else {
			this.path = path;
			this.settings = new ProjectSettings();
		}
		
		// Database
		animations = new GObjectTreeSerializer(dataPath() + "animations", Animation.class);
		battlers = new GObjectTreeSerializer(dataPath() + "battlers", Battler.class);
		characters = new GObjectTreeSerializer(dataPath() + "characters", GameCharacter.class);
		events = new GObjectTreeSerializer(dataPath() + "events", EventSheet.class);
		jobs = new GObjectTreeSerializer(dataPath() + "jobs", Job.class);
		items = new GObjectTreeSerializer(dataPath() + "items", Item.class);
		obstacles = new GObjectTreeSerializer(dataPath() + "obstacles", Obstacle.class);
		skills = new GObjectTreeSerializer(dataPath() + "skills", Skill.class);
		status = new GObjectTreeSerializer(dataPath() + "status", Status.class);
		terrains = new GObjectTreeSerializer(dataPath() + "terrains", Terrain.class);
		troops = new GObjectTreeSerializer(dataPath() + "troops", Troop.class);
		
		// System
		config = new GObjectSerializer<>(systemPath() + "config", Config.class);
		attributes = new GObjectListSerializer(systemPath() + "attributes", Attribute.class);
		languages = new GObjectListSerializer(systemPath() + "languages", Data.class);
		elements = new GObjectListSerializer(systemPath() + "elements", Element.class);
		equipTypes = new GObjectListSerializer(systemPath() + "equipTypes", EquipType.class);
		plugins = new GObjectListSerializer(systemPath() + "plugins", Plugin.class);
		regions = new GObjectListSerializer(systemPath() + "regions", Region.class);
		
		fieldTree = new FieldTreeSerializer(fieldPath());
		
		database = new GObjectTreeSerializer[] { animations, battlers, characters, 
				events, jobs, items, obstacles, skills, status, terrains, troops };
		
		allData = new LSerializer[] { config, fieldTree, attributes, languages, elements, equipTypes, plugins, regions };
		allData = Arrays.copyOf(allData, allData.length + database.length);
		System.arraycopy(database, 0, allData, allData.length - database.length, database.length);
	}
	
	public String dataPath() {
		return path + settings.dataPath;
	}
	
	public String systemPath() {
		return dataPath() + "system/";
	}
	
	public String fieldPath() {
		return dataPath() + "fields/";
	}
	
	public String imagePath() {
		return path + settings.imagePath;
	}
	
	public String scriptPath() {
		return path + "scripts/custom/";
	}
	
	public String rulePath() {
		return scriptPath() + "rule/";
	}
	
	public String audioPath() {
		return path + settings.audioPath;
	}

	public String fontPath() {
		return path + "fonts/";
	}

	public int dataVersion() {
		return settings.dataVersion;
	}
	
	@Override
	public void initialize() {
		current = this;
		for (LSerializer data : allData) {
			data.initialize();
		}
		FieldHelper.reloadMath();
		TilePainter.reload();
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
		current = this;
		for (LSerializer data : allData) {
			if (!data.load())
				return false;
		}
		for (GObjectTreeSerializer s : database) {
			startData(s.getTree(), s.getTree());
		}
		FieldHelper.reloadMath();
		TilePainter.reload();
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
	
	private static void startData(LDataTree<Object> root, LDataTree<Object> node) {
		for (LDataTree<Object> child : node.children) {
			Data data = (Data) child.data;
			data.onStart(root, child);
			startData(root, child);
		}
	}

}
