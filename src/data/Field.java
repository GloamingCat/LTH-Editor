package data;

import lwt.dataestructure.LDataList;

public class Field {

	public int id;
	public int sizeX;
	public int sizeY;
	public Prefs prefs = new Prefs();
	public LDataList<Layer> layers = new LDataList<>();

	public Field() {}
	
	public Field(int id, int sizeX, int sizeY) {
		this.id = id;
		this.sizeX = sizeX;
		this.sizeY = sizeY;
		layers.add(new Layer(sizeX, sizeY));
	}
	
	public static class Prefs {
		public String name = "New Field";
		public String background = "";
		public int tilesetID = 0;
		public int defaultRegion = 0;
		public int partyCount = 2;
		public Script onStart = new Script();
		public LDataList<Transition> transitions = new LDataList<>(); 
		public LDataList<Tag> tags = new LDataList<>();
		
		public String toString() {
			return name;
		}
	}
	
	public String toString() {
		return prefs.toString();
	}
	
}
