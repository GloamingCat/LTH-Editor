package gui.views.database.content;

import java.util.ArrayList;

import lwt.dialog.LObjectShell;
import gui.Vocab;
import gui.shell.database.BasicTileShell;
import gui.shell.database.TilesetCharShell;
import gui.views.GraphicalList;
import gui.views.database.DatabaseTab;

import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Group;
import org.eclipse.swt.widgets.Shell;

import data.Config;
import data.Tileset.CharTile;
import data.Tileset.ObstacleTile;
import data.Tileset.RegionTile;
import data.Tileset.TerrainTile;

import project.GObjectTreeSerializer;
import project.Project;

import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.layout.GridData;

public class TilesetTab extends DatabaseTab {

	/**
	 * Create the composite.
	 * @param parent
	 * @param style
	 */
	public TilesetTab(Composite parent, int style) {
		super(parent, style);
		
		Composite composite = new Composite(contentEditor, SWT.NONE);
		composite.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true, 1, 1));
		GridLayout gl_composite = new GridLayout(2, true);
		gl_composite.marginHeight = 0;
		gl_composite.marginWidth = 0;
		gl_composite.verticalSpacing = 0;
		composite.setLayout(gl_composite);
		
		Group grpTerrains = new Group(composite, SWT.NONE);
		grpTerrains.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true, 1, 1));
		grpTerrains.setLayout(new FillLayout());
		grpTerrains.setText(Vocab.instance.TERRAINS);
		
		GraphicalList<TerrainTile> lstTerrains = new GraphicalList<TerrainTile>(grpTerrains, SWT.NONE) {
			protected String attributeName() { return "terrains"; }
			protected Class<TerrainTile> getType() { return TerrainTile.class; } 
			protected LObjectShell<TerrainTile> createShell(Shell parent) { 
				return new BasicTileShell<TerrainTile>(parent) {
					protected TerrainTile createNew() {
						TerrainTile t = new TerrainTile();
						t.id = cmbID.getSelectionIndex();
						t.tags = tags;
						return t;
					}
					protected ArrayList<?> getArray() {
						//TODO return Project.current.terrains.getList();
						return null;
					}
				};
			}
		};
		addChild(lstTerrains);
		
		Group grpObstacles = new Group(composite, SWT.NONE);
		grpObstacles.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true, 1, 1));
		grpObstacles.setLayout(new FillLayout());
		grpObstacles.setText(Vocab.instance.OBSTACLES);
		
		GraphicalList<ObstacleTile> lstObstacles = new GraphicalList<ObstacleTile>(grpObstacles, SWT.NONE) {
			protected String attributeName() { return "obstacles"; }
			protected Class<ObstacleTile> getType() { return ObstacleTile.class; } 
			protected LObjectShell<ObstacleTile> createShell(Shell parent) { 
				return new BasicTileShell<ObstacleTile>(parent) {
					protected ObstacleTile createNew() {
						ObstacleTile t = new ObstacleTile();
						t.id = cmbID.getSelectionIndex();
						t.tags = tags;
						return t;
					}
					protected ArrayList<?> getArray() {
						//TODO return Project.current.obstacles.getList();
						return null;
					}
				};
			}
		};
		addChild(lstObstacles);
		
		Group grpChars = new Group(composite, SWT.NONE);
		grpChars.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true, 1, 1));
		grpChars.setLayout(new FillLayout());
		grpChars.setText(Vocab.instance.CHARACTERS);
		
		GraphicalList<CharTile> lstCharacters = new GraphicalList<CharTile>(grpChars, SWT.NONE) {
			protected String attributeName() { return "characters"; }
			protected Class<CharTile> getType() { return CharTile.class; } 
			protected LObjectShell<CharTile> createShell(Shell parent) { 
				return new TilesetCharShell(parent); 
			}
		};
		addChild(lstCharacters);
		
		Group grpRegions = new Group(composite, SWT.NONE);
		grpRegions.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true, 1, 1));
		grpRegions.setLayout(new FillLayout());
		grpRegions.setText(Vocab.instance.REGIONS);
		
		GraphicalList<RegionTile> lstRegions = new GraphicalList<RegionTile>(grpRegions, SWT.NONE) {
			protected String attributeName() { return "regions"; }
			protected Class<RegionTile> getType() { return RegionTile.class; } 
			protected LObjectShell<RegionTile> createShell(Shell parent) { 
				return new BasicTileShell<RegionTile>(parent) {
					protected RegionTile createNew() {
						RegionTile t = new RegionTile();
						t.id = cmbID.getSelectionIndex();
						t.tags = tags;
						return t;
					}
					protected ArrayList<?> getArray() {
						Config conf = (Config) Project.current.config.getData();
						return conf.regions;
					}
				};
			}
		};
		addChild(lstRegions);
	}

	@Override
	protected GObjectTreeSerializer getSerializer() {
		return Project.current.tilesets;
	}

}
