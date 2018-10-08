package gui.views.database;

import gui.Vocab;
import gui.views.database.content.*;
import lwt.editor.LViewFolder;

import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Composite;

public class DatabaseEditor extends LViewFolder {

	/**
	 * Create the composite.
	 * @param parent
	 * @param style
	 */
	public DatabaseEditor(Composite parent, int style) {
		super(parent, style);

		AnimationTab animationTab = new AnimationTab(tabFolder, SWT.NONE);
		addTab(Vocab.instance.ANIMATIONS, animationTab);
		
		ObstacleTab obstacleTab = new ObstacleTab(tabFolder, SWT.NONE);
		addTab(Vocab.instance.OBSTACLES, obstacleTab);
		
		StatusTab statusTab = new StatusTab(tabFolder, SWT.NONE);
		addTab(Vocab.instance.STATUS, statusTab);
		
		SkillTab skillTab = new SkillTab(tabFolder, SWT.NONE);
		addTab(Vocab.instance.SKILLS, skillTab);
		
		ItemTab itemTab = new ItemTab(tabFolder, SWT.NONE);
		addTab(Vocab.instance.ITEMS, itemTab);
		
		BattlerTab battlerTab = new BattlerTab(tabFolder, SWT.NONE);
		addTab(Vocab.instance.BATTLERS, battlerTab);
		
		CharacterTab characterTab = new CharacterTab(tabFolder, SWT.NONE);
		addTab(Vocab.instance.CHARACTERS, characterTab);
		
		TerrainTab terrainTab = new TerrainTab(tabFolder, SWT.NONE);
		addTab(Vocab.instance.TERRAINS, terrainTab);
		
	}

}
