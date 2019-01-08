package data.field;

import org.eclipse.swt.graphics.Image;

import data.Data;
import data.subcontent.Tag;
import gui.helper.TilePainter;
import lwt.dataestructure.LDataList;
import lwt.datainterface.LGraphical;

public class Tileset extends Data {

	public LDataList<TerrainTile> terrains = new LDataList<>();
	public LDataList<ObstacleTile> obstacles = new LDataList<>();
	public LDataList<CharTile> characters = new LDataList<>();
	public LDataList<RegionTile> regions = new LDataList<>();
	
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
	
	public static class RegionTile extends BasicTile implements LGraphical {
		@Override
		public Image toImage() {
			return TilePainter.getRegionTile(id);
		}
	}
	
	public static class PartyTile extends BasicTile implements LGraphical {
		@Override
		public Image toImage() {
			return TilePainter.getPartyTile(id);
		}
	}
	
	public static LDataList<PartyTile> createPartyArray(int size) {
		LDataList<PartyTile> list = new LDataList<>();
		for(int i = 0; i < size; i++) {
			PartyTile pt = new PartyTile();
			pt.id = i;
			list.add(pt);
		}
		return list;
	}
	
}
