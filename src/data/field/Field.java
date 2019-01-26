package data.field;

import data.Data;
import data.Script;
import data.subcontent.Quad;
import lwt.dataestructure.LDataList;

public class Field {

	public int id;
	public int sizeX;
	public int sizeY;
	
	public Prefs prefs = new Prefs();
	public Layers layers = new Layers();
	public LDataList<CharTile> characters = new LDataList<>();
	public LDataList<Party> parties = new LDataList<>();
	public int playerParty = -1;
	
	public Field() {}
	
	public Field(int id, int sizeX, int sizeY) {
		this.id = id;
		this.sizeX = sizeX;
		this.sizeY = sizeY;
		layers.terrain.add(new Layer(sizeX, sizeY));
	}
	
	public static class Prefs extends Data {
		
		public int defaultRegion = 0;
		public int maxHeight = 4;
		public Script loadScript = new Script();
		public Quad background = new Quad();
		public Quad parallax = new Quad();
		public LDataList<Transition> transitions = new LDataList<>(); 
		
	}
	
	public static class Layers {
		
		public LDataList<Layer> terrain = new LDataList<>();
		public LDataList<Layer> obstacle = new LDataList<>();
		public LDataList<Layer> region = new LDataList<>();
		
		public int maxHeight() {
			int maxH = 0;
			for (Layer l : terrain)
				maxH = Math.max(maxH, l.info.height);
			for (Layer l : obstacle)
				maxH = Math.max(maxH, l.info.height);
			for (Layer l : region)
				maxH = Math.max(maxH, l.info.height);
			return maxH;
		}
		
	}
	
	public CharTile findCharacter(int x, int y, int h) {
		for (CharTile t : characters) {
			if (t.x == x && t.y == y && t.h == h)
				return t;
		}
		return null;
	}
	
}
