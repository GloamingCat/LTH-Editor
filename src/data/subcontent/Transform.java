package data.subcontent;

public class Transform {

	// Space transformation
	public int offsetX = 0;
	public int offsetY = 0;
	public int offsetDepth = 0;
	public int scaleX = 100;
	public int scaleY = 100;
	public int rotation = 0; // in degrees
	
	// Color transformation
	public int red = 255;
	public int green = 255;
	public int blue = 255;
	public int alpha = 255;
	public int hue = 0;
	public int saturation = 100;
	public int brightness = 100;
	
	public static Transform neutral() {
		Transform t = new Transform();
		t.offsetY = 0;
		return t;
	}
	
}
