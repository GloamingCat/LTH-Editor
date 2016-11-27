package data;

import org.eclipse.swt.graphics.Image;

import gui.helper.ImageHelper;
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
	
	public static class TerrainTile implements LGraphical {
		public int id;
		public TerrainTile(int i) {
			// TODO Auto-generated constructor stub
		}
		@Override
		public Image toImage() {
			return ImageHelper.getTerrainTile(id);
		}
	}
	
	public static class ObstacleTile implements LGraphical {
		public int id;
		@Override
		public Image toImage() {
			return ImageHelper.getObstacleTile(id);
		}
	}
	
	public static class CharTile implements LGraphical {
		public int id;
		@Override
		public Image toImage() {
			return ImageHelper.getCharacterTile(id);
		}
	}
	
	public static class RegionTile implements LGraphical {
		public int id;
		@Override
		public Image toImage() {
			return ImageHelper.getRegionTile(id);
		}
	}
	
	
}
