package project;

import gui.helper.FieldHelper;
import gui.helper.TilePainter;
import data.*;
import lwt.dataserialization.LSerializer;

public class Project implements LSerializer {
	
	public ListSerializer battlers;
	public ListSerializer items;
	public ListSerializer skills;
	public ListSerializer skillDags;
	public ListSerializer status;
	public ListSerializer animBattle;
	public ListSerializer animCharacter;
	public ListSerializer animOther;
	public ListSerializer terrains;
	public ListSerializer obstacles;
	public ListSerializer ramps;
	public ListSerializer characters;
	public ListSerializer tilesets;
	public FieldTreeSerializer fieldTree;
	public ObjectSerializer config;
	
	private LSerializer[] allData;
	
	public static Project current = null;
	public String path;
	
	public Project(String path) {
		this.path = path;
		
		battlers = new ListSerializer(dataPath() + "battlers", Battler.class);
		items = new ListSerializer(dataPath() + "items", Item.class);
		skills = new ListSerializer(dataPath() + "skills", Skill.class);
		skillDags = new ListSerializer(dataPath() + "skillDags", SkillDag.class);
		status = new ListSerializer(dataPath() + "status", Status.class);
		animCharacter = new ListSerializer(dataPath() + "animCharacter", Animation.class);
		animBattle = new ListSerializer(dataPath() + "animBattle", Animation.class);
		animOther = new ListSerializer(dataPath() + "animOther", Animation.class);
		terrains = new ListSerializer(dataPath() + "terrains", Terrain.class);
		obstacles = new ListSerializer(dataPath() + "obstacles", Obstacle.class);
		ramps = new ListSerializer(dataPath() + "ramps", Ramp.class);
		characters = new ListSerializer(dataPath() + "characters", GameCharacter.class);
		tilesets = new ListSerializer(dataPath() + "tilesets", Tileset.class);
		fieldTree = new FieldTreeSerializer(dataPath() + "fieldTree");
		config = new ObjectSerializer(dataPath() + "config", Config.class);
		
		allData = new LSerializer[] {items, skills, skillDags, battlers, status, 
				animCharacter, animBattle, animOther, terrains, obstacles, ramps, 
				characters, tilesets, config, fieldTree};
	}
	
	public String dataPath() {
		return path + "data/";
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
		FieldHelper.reloadMath();
		TilePainter.reloadConfig();
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
