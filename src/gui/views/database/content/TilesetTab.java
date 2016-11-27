package gui.views.database.content;

import lwt.dialog.LObjectShell;
import gui.Vocab;
import gui.views.database.DatabaseTab;
import gui.views.database.subcontent.TileList;

import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Group;
import org.eclipse.swt.widgets.Shell;

import data.Tileset.CharTile;
import data.Tileset.ObstacleTile;
import data.Tileset.RegionTile;
import data.Tileset.TerrainTile;
import project.ListSerializer;
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
		grpTerrains.setSize(59, 24);
		grpTerrains.setLayout(new FillLayout());
		grpTerrains.setText(Vocab.instance.TERRAINS);
		
		TileList<TerrainTile> lstTerrains = new TileList<TerrainTile>(grpTerrains, SWT.NONE) {
			protected String attributeName() { return "terrains"; }
			protected Class<TerrainTile> getType() { return TerrainTile.class; } 
			protected LObjectShell<TerrainTile> createShell(Shell parent) { 
				return null; 
			}
		};
		addChild(lstTerrains);
		
		Group grpObstacles = new Group(composite, SWT.NONE);
		grpObstacles.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true, 1, 1));
		grpObstacles.setSize(69, 24);
		grpObstacles.setLayout(new FillLayout());
		grpObstacles.setText(Vocab.instance.OBSTACLES);
		
		TileList<ObstacleTile> lstObstacles = new TileList<ObstacleTile>(grpObstacles, SWT.NONE) {
			protected String attributeName() { return "obstacles"; }
			protected Class<ObstacleTile> getType() { return ObstacleTile.class; } 
			protected LObjectShell<ObstacleTile> createShell(Shell parent) { 
				return null; 
			}
		};
		addChild(lstObstacles);
		
		Group grpChars = new Group(composite, SWT.NONE);
		grpChars.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true, 1, 1));
		grpChars.setSize(74, 24);
		grpChars.setLayout(new FillLayout());
		grpChars.setText(Vocab.instance.CHARACTERS);
		
		TileList<CharTile> lstCharacters = new TileList<CharTile>(grpChars, SWT.NONE) {
			protected String attributeName() { return "characters"; }
			protected Class<CharTile> getType() { return CharTile.class; } 
			protected LObjectShell<CharTile> createShell(Shell parent) { 
				return null; 
			}
		};
		addChild(lstCharacters);
		
		Group grpRegions = new Group(composite, SWT.NONE);
		grpRegions.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true, 1, 1));
		grpRegions.setSize(60, 24);
		grpRegions.setLayout(new FillLayout());
		grpRegions.setText(Vocab.instance.REGIONS);
		
		TileList<RegionTile> lstRegions = new TileList<RegionTile>(grpRegions, SWT.NONE) {
			protected String attributeName() { return "regions"; }
			protected Class<RegionTile> getType() { return RegionTile.class; } 
			protected LObjectShell<RegionTile> createShell(Shell parent) { 
				return null; 
			}
		};
		addChild(lstRegions);
	}

	@Override
	protected ListSerializer getSerializer() {
		return Project.current.tilesets;
	}

}
