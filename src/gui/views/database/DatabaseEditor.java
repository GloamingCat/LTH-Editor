package gui.views.database;

import gui.Vocab;
import gui.views.database.content.*;
import lwt.editor.LViewFolder;

import org.eclipse.swt.widgets.Composite;

public class DatabaseEditor extends LViewFolder {

	/**
	 * Create the composite.
	 * @param parent
	 * @param style
	 */
	public DatabaseEditor(Composite parent) {
		super(parent);

		AnimationTab animationTab = new AnimationTab(tabFolder);
		addTab(Vocab.instance.ANIMATIONS, animationTab);
		
		ObstacleTab obstacleTab = new ObstacleTab(tabFolder);
		addTab(Vocab.instance.OBSTACLES, obstacleTab);
		
		StatusTab statusTab = new StatusTab(tabFolder);
		addTab(Vocab.instance.STATUS, statusTab);
		
		SkillTab skillTab = new SkillTab(tabFolder);
		addTab(Vocab.instance.SKILLS, skillTab);
		
		ItemTab itemTab = new ItemTab(tabFolder);
		addTab(Vocab.instance.ITEMS, itemTab);
		
		JobTab jobTab = new JobTab(tabFolder);
		addTab(Vocab.instance.JOBS, jobTab);
		
		BattlerTab battlerTab = new BattlerTab(tabFolder);
		addTab(Vocab.instance.BATTLERS, battlerTab);
		
		CharacterTab characterTab = new CharacterTab(tabFolder);
		addTab(Vocab.instance.CHARACTERS, characterTab);
		
		TroopTab troopTab = new TroopTab(tabFolder);
		addTab(Vocab.instance.TROOPS, troopTab);
		
		TerrainTab terrainTab = new TerrainTab(tabFolder);
		addTab(Vocab.instance.TERRAINS, terrainTab);

		EventTab eventTab = new EventTab(tabFolder);
		addTab(Vocab.instance.EVENTSHEETS, eventTab);
		
	}

}
