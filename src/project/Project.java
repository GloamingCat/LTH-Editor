package project;

import gui.helper.FieldHelper;
import gui.helper.TilePainter;
import data.*;
import lwt.dataserialization.LSerializer;

public class Project implements LSerializer {
	
	public GObjectListSerializer battlers;
	public GObjectListSerializer troops;
	public GObjectListSerializer items;
	public GObjectListSerializer skills;
	public GObjectListSerializer skillDags;
	public GObjectListSerializer status;
	public GObjectListSerializer animBattle;
	public GObjectListSerializer animCharacter;
	public GObjectListSerializer animOther;
	public GObjectListSerializer terrains;
	public GObjectListSerializer obstacles;
	public GObjectListSerializer ramps;
	public GObjectListSerializer charBattle;
	public GObjectListSerializer charField;
	public GObjectListSerializer charOther;
	public GObjectListSerializer tilesets;
	
	public FieldTreeSerializer fieldTree;
	public GObjectSerializer<Config> config;
	public DialogueTreeSerializer dialogueTree;
	
	private LSerializer[] allData;
	
	public static Project current = null;
	public String path;
	
	public Project(String path) {
		this.path = path;
		
		battlers = new GObjectListSerializer(dataPath() + "battlers", Battler.class);
		troops = new GObjectListSerializer(dataPath() + "troops", Troop.class);
		items = new GObjectListSerializer(dataPath() + "items", Item.class);
		skills = new GObjectListSerializer(dataPath() + "skills", Skill.class);
		skillDags = new GObjectListSerializer(dataPath() + "skillDags", SkillDag.class);
		status = new GObjectListSerializer(dataPath() + "status", Status.class);
		animCharacter = new GObjectListSerializer(dataPath() + "animCharacter", Animation.class);
		animBattle = new GObjectListSerializer(dataPath() + "animBattle", Animation.class);
		animOther = new GObjectListSerializer(dataPath() + "animOther", Animation.class);
		terrains = new GObjectListSerializer(dataPath() + "terrains", Terrain.class);
		obstacles = new GObjectListSerializer(dataPath() + "obstacles", Obstacle.class);
		ramps = new GObjectListSerializer(dataPath() + "ramps", Ramp.class);
		charField = new GObjectListSerializer(dataPath() + "charField", GameCharacter.class);
		charBattle = new GObjectListSerializer(dataPath() + "charBattle", GameCharacter.class);
		charOther = new GObjectListSerializer(dataPath() + "charOther", GameCharacter.class);
		tilesets = new GObjectListSerializer(dataPath() + "tilesets", Tileset.class);
		
		fieldTree = new FieldTreeSerializer(dataPath());
		dialogueTree = new DialogueTreeSerializer(dataPath());
		
		config = new GObjectSerializer<Config>(dataPath() + "config", Config.class);
		
		allData = new LSerializer[] {items, skills, skillDags, battlers, status, 
				animCharacter, animBattle, animOther, terrains, obstacles, ramps, 
				charField, charBattle, charOther, tilesets, config, fieldTree, dialogueTree };
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

	public String fontPath() {
		return path + "fonts/";
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
