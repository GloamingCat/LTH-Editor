package data;

import org.eclipse.swt.graphics.Image;

import gui.helper.TilePainter;
import lwt.dataestructure.LDataList;
import lwt.datainterface.LGraphical;

public class Tileset {

	public String name = "";
	public LDataList<TerrainTile> terrains = new LDataList<>();
	public LDataList<ObstacleTile> obstacles = new LDataList<>();
	public LDataList<CharTile> characters = new LDataList<>();
	public LDataList<RegionTile> regions = new LDataList<>();
	
	public String toString() {
		return name;
	}
	
	public static class BasicTile {
		public int id;
		public LDataList<Tag> tags = new LDataList<>();
	}
	
	public static class TerrainTile extends BasicTile implements LGraphical {
		@Override
		public Image toImage() {
			return TilePainter.getTerrainTile(id);
		}
	}
	
	public static class ObstacleTile extends BasicTile implements LGraphical {
		@Override
		public Image toImage() {
			return TilePainter.getObstacleTile(id);
		}
	}
	
	public static class CharTile extends BasicTile implements LGraphical {
		public int animID = 0;
		public int direction = 315;
		public int startID = -1;
		public int collisionID = -1;
		public int interactID = -1;
		public CharTile() {}
		public CharTile(int id, int animID, int direction) {
			this.id = id;
			this.animID = animID;
			this.direction = direction;
		}
		@Override
		public Image toImage() {
			return TilePainter.getCharacterTile(id, animID, direction);
		}
		public String getKey() {
			return id + "," + animID + "," + direction;
		}
	}
	
	public static class RegionTile extends BasicTile implements LGraphical {
		@Override
		public Image toImage() {
			return TilePainter.getRegionTile(id);
		}
	}
	
}
