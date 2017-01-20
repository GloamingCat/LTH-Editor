package data;

import lwt.dataestructure.LDataList;

public class Dialog {
	
	public static class Speech {
		public Quad portrait = new Quad();
		public String name = "John Smith";
		public String msg = "Hi, how are you?";
	}
	
	public int id;
	public String name = "";
	public LDataList<Speech> speeches = new LDataList<>();

	public String toString() {
		return name;
	}
	
}
