package gui.views.database;

import gui.Vocab;
import gui.views.database.content.*;
import lwt.editor.LViewFolder;

import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Composite;

import project.GObjectListSerializer;
import project.Project;

public class DatabaseEditor extends LViewFolder {

	/**
	 * Create the composite.
	 * @param parent
	 * @param style
	 */
	public DatabaseEditor(Composite parent, int style) {
		super(parent, style);

		ItemTab itemTab = new ItemTab(tabFolder, SWT.NONE);
		addTab(Vocab.instance.ITEMS, itemTab);
		
		SkillTab skillTab = new SkillTab(tabFolder, SWT.NONE);
		addTab(Vocab.instance.SKILLS, skillTab);
		
		SkillDagTab skillDagTab = new SkillDagTab(tabFolder, SWT.NONE);
		addTab(Vocab.instance.SKILLDAGS, skillDagTab);
		
		BattlerTab battlerTab = new BattlerTab(tabFolder, SWT.NONE);
		addTab(Vocab.instance.BATTLERS, battlerTab);
		
		LViewFolder objectFolder = new LViewFolder(tabFolder, SWT.NONE);
		addTab(Vocab.instance.OBJECTS, objectFolder);
		
		ObstacleTab obstacleTab = new ObstacleTab(objectFolder.getTabFolder(), SWT.NONE);
		objectFolder.addTab(Vocab.instance.OBSTACLES, obstacleTab);
		
		RampTab rampTab = new RampTab(objectFolder.getTabFolder(), SWT.NONE);
		objectFolder.addTab(Vocab.instance.RAMPS, rampTab);
		
		LViewFolder characterFolder = new LViewFolder(tabFolder, SWT.NONE);
		addTab(Vocab.instance.CHARACTERS, characterFolder);
		
		CharacterTab charFieldTab = new CharacterTab(characterFolder.getTabFolder(), SWT.NONE) {
			public GObjectListSerializer getSerializer() {
				return Project.current.charField;
			}
		};
		characterFolder.addTab(Vocab.instance.CHARACTERS, charFieldTab);
		
		CharacterTab charBattleTab = new CharacterTab(characterFolder.getTabFolder(), SWT.NONE) {
			public GObjectListSerializer getSerializer() {
				return Project.current.charBattle;
			}
		};
		characterFolder.addTab(Vocab.instance.CHARACTERS, charBattleTab);
		
		CharacterTab charOtherTab = new CharacterTab(characterFolder.getTabFolder(), SWT.NONE) {
			public GObjectListSerializer getSerializer() {
				return Project.current.charOther;
			}
		};
		characterFolder.addTab(Vocab.instance.CHARACTERS, charOtherTab);
		
		LViewFolder animationFolder = new LViewFolder(tabFolder, SWT.NONE);
		addTab(Vocab.instance.ANIMATIONS, animationFolder);

		AnimationTab animCharacter = new AnimationTab(animationFolder.getTabFolder(), SWT.NONE) {
			public GObjectListSerializer getSerializer() {
				return Project.current.animCharacter;
			}
		};
		animationFolder.addTab(Vocab.instance.CHARACTER, animCharacter);
		
		AnimationTab animBattle = new AnimationTab(animationFolder.getTabFolder(), SWT.NONE) {
			public GObjectListSerializer getSerializer() {
				return Project.current.animBattle;
			}
		};
		animationFolder.addTab(Vocab.instance.BATTLE, animBattle);
		
		AnimationTab animOther = new AnimationTab(animationFolder.getTabFolder(), SWT.NONE) {
			public GObjectListSerializer getSerializer() {
				return Project.current.animOther;
			}
		};
		animationFolder.addTab(Vocab.instance.OTHER, animOther);
		
		StatusTab statusTab = new StatusTab(tabFolder, SWT.NONE);
		addTab(Vocab.instance.STATUS, statusTab);
		
		TerrainTab terrainTab = new TerrainTab(tabFolder, SWT.NONE);
		addTab(Vocab.instance.TERRAINS, terrainTab);
		
		TilesetTab tilesetTab = new TilesetTab(tabFolder, SWT.NONE);
		addTab(Vocab.instance.TILESETS, tilesetTab);
	}

}
