package data;

import lwt.dataestructure.LDataList;

public class Dialogue {
	
	public static class Speech {
		
		public Quad portrait = new Quad();
		public int portraitSide = 0;
		
		public String name = "John Smith";
		public int nameSide = 0;
		
		public String message = "Hi, how are you?";
		
		// Position
		public int xType = 0;
		public int yType = 0;
		public int x = -1;
		public int y = -1;
		
		// Size
		public int widthType = 0;
		public int heightType = 0;
		public int width = -1;
		public int height = -1;
		public int lineCount = -1;
		
		public LDataList<Tag> tags = new LDataList<>();
		
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
