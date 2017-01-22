package data;

import lwt.dataestructure.LDataList;

public class Dialogue {
	
	public static class Speech {
		
		public Quad portrait = new Quad();
		public String name = "John Smith";
		public String message = "Hi, how are you?";
		
		public String toString() {
			return name + ": " + message;
		}
	}
	
	public int id;
	public String name = "New Dialog";
	public LDataList<Speech> speeches = new LDataList<>();

	public String toString() {
		return name;
	}
	
}
