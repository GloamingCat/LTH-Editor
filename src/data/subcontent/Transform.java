package data.subcontent;

import lui.container.LImage;

public class Transform {

	public static final Transform neutral = new Transform();
	
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
	
	public Transform clone() {
		Transform t = new Transform();
		t.offsetDepth = offsetDepth;
		t.offsetX = offsetX;
		t.offsetY = offsetY;
		t.scaleX = scaleX;
		t.scaleY = scaleY;
		t.rotation = rotation;
		t.red = red;
		t.green = green;
		t.blue = blue;
		t.hue = hue;
		t.saturation = saturation;
		t.brightness = brightness;
		return t;
	}
	
	public batching.Transform convert() {
		batching.Transform t = new batching.Transform();
		t.offsetX = offsetX;
		t.offsetY = offsetY;
		t.scaleX = scaleX;
		t.scaleY = scaleY;
		t.rotation = rotation;
		t.red = red;
		t.green = green;
		t.blue = blue;
		t.hue = hue;
		t.saturation = saturation;
		t.brightness = brightness;
		return t;
	}
	
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
	
	public void setColorTransform(LImage img, Transform other) {
		img.setRGBA(red / 255f * other.red / 255f,
				green / 255f * other.green / 255f,
				blue / 255f * other.blue / 255f,
				alpha / 255f * other.alpha / 255f);
		img.setHSV(hue + other.hue,
				saturation / 100f * other.saturation / 100f,
				brightness / 100f * other.brightness / 100f);
	}
	
	public void setColorTransform(LImage img) {
		img.setRGBA(red / 255f, green / 255f, blue / 255f, alpha / 255f);
		img.setHSV(hue, saturation / 100f, brightness / 100f);
	}
	
}
