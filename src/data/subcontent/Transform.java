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
	
	public Transform combine(Transform t) {
		offsetX += t.offsetX;
		offsetY += t.offsetY;
		offsetDepth += t.offsetDepth;
		scaleX = (scaleX * t.scaleX) / 100;
		scaleY = (scaleY * t.scaleY) / 100;
		rotation = (rotation + t.rotation + 360) % 360;
		red = (red * t.red) / 255;
		green = (green * t.green) / 255;
		blue = (blue * t.blue) / 255;
		alpha = (alpha * t.alpha) / 255;
		hue = (hue + t.hue) % 360;
		saturation = (saturation * t.saturation) / 100;
		brightness = (brightness * t.brightness) / 100;
		return this;
	}
	
}
