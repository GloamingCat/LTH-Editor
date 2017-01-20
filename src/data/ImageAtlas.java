package data;

public class ImageAtlas {

	public int width;
	public int height;
	public String imagePath = "";
	
	public static class Entry {
		public ImageAtlas atlas;
		public int x;
		public int y;
	}
	
	public String toString() {
		return imagePath;
	}

}
