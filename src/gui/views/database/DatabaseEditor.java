package gui.views.database;

import gui.Vocab;
import gui.views.database.content.*;
import lwt.container.LContainer;
import lwt.container.LViewFolder;

public class DatabaseEditor extends LViewFolder {

	/**
	 * @wbp.parser.constructor
	 * @wbp.eval.method.parameter parent new lwt.dialog.LShell(800, 600)
	 */
	public DatabaseEditor(LContainer parent) {
		super(parent, false);

		AnimationTab animationTab = new AnimationTab(this);
		addTab(Vocab.instance.ANIMATIONS, animationTab);
		
		ObstacleTab obstacleTab = new ObstacleTab(this);
		addTab(Vocab.instance.OBSTACLES, obstacleTab);
		
		StatusTab statusTab = new StatusTab(this);
		addTab(Vocab.instance.STATUS, statusTab);
		
		SkillTab skillTab = new SkillTab(this);
		addTab(Vocab.instance.SKILLS, skillTab);
		
		ItemTab itemTab = new ItemTab(this);
		addTab(Vocab.instance.ITEMS, itemTab);
		
		JobTab jobTab = new JobTab(this);
		addTab(Vocab.instance.JOBS, jobTab);
		
		BattlerTab battlerTab = new BattlerTab(this);
		addTab(Vocab.instance.BATTLERS, battlerTab);
		
		CharacterTab characterTab = new CharacterTab(this);
		addTab(Vocab.instance.CHARACTERS, characterTab);
		
		TroopTab troopTab = new TroopTab(this);
		addTab(Vocab.instance.TROOPS, troopTab);
		
		TerrainTab terrainTab = new TerrainTab(this);
		addTab(Vocab.instance.TERRAINS, terrainTab);

		EventTab eventTab = new EventTab(this);
		addTab(Vocab.instance.EVENTSHEETS, eventTab);
		
	}

}
