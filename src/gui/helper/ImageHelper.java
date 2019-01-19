package gui.helper;

import lwt.LHelper;
import lwt.widget.LImage;

import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.graphics.GC;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.graphics.ImageData;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.widgets.Display;

import data.subcontent.Transform;

public class ImageHelper {
	
	public static Image newImage(int imgW, int imgH) {
		Image src = new Image(Display.getCurrent(), imgW, imgH);
	    GC gc = new GC(src);
	    gc.setAlpha(0);
	    gc.fillRectangle(0, 0, imgW, imgH);
	    gc.dispose();
	    //ImageData imageData = src.getImageData();
	    //imageData.transparentPixel = -1;
	    
	    //Image img = correctTransparency(imageData);
	    //src.dispose();
	    //return img;
	    return src;
	}
	
	public static Image correctTransparency(Image image) {
		Image img = correctTransparency(image.getImageData());
		image.dispose();
		return img;
	}
	
	public static Image correctTransparency(ImageData imageData) {
		int len = imageData.width * imageData.height;
		imageData.transparentPixel = -1;
		imageData.alphaData = new byte[len];
		for (int idx = 0; idx < len; idx++) {
	        final int coord = (idx * 4) + 3;
	        imageData.alphaData[idx] = imageData.data[coord];
	    }
		return new Image(Display.getCurrent(), imageData);
	}
	
	public static Image getStringImage(String s, int w, int h, Color background, boolean borders) {
		Image image = new Image(Display.getCurrent(), w, h);
		GC gc = new GC(image);
		if (background != null) {
			gc.setBackground(background);
			gc.fillRectangle(0, 0, w, h);
		}
		gc.setAlpha(255);
		Point size = gc.stringExtent(s);
		int x = (w - size.x) / 2;
		int y = (h - size.y) / 2;
		gc.setForeground(image.getDevice().getSystemColor(SWT.COLOR_BLACK));
		gc.drawText(s, x, y);
		if (borders) {
			gc.drawRectangle(2, 2, w - 5, h - 5);
		}
		gc.dispose();
	    ImageData imageData = image.getImageData();
	    imageData.transparentPixel = imageData.getPixel(0, 0);
	    image.dispose();
		return new Image(Display.getCurrent(), imageData);
	}
	
	public static Image getStringImage(String s, int w, int h, Color background) {
		return getStringImage(s, w, h, background, false);
	}

	public static Image colorTransform(Image src, 
			float r, float g, float b, float a,
			float h, float s, float v) {
		if (r == 1 && g == 1 && b == 1 && a == 1 && 
				h == 0 && s == 1 && v == 1)
			return src;
		ImageData newdata = LHelper.colorTransform(src.getImageData(), 
				r, g, b, a, h, s, v);
		src.dispose();
		return correctTransparency(newdata);
	}
	
	public static void setColorTransform(LImage img, Transform t1, Transform t2) {
		img.setRGBA(t1.red / 255f * t2.red / 255f,
				t1.green / 255f * t2.green / 255f,
				t1.blue / 255f * t2.blue / 255f,
				t1.alpha / 255f * t2.alpha / 255f);
		img.setHSV(t1.hue + t2.hue,
				t1.saturation / 100f * t2.saturation / 100f,
				t1.brightness / 100f * t2.brightness / 100f);
	}
	
	public static void setColorTransform(LImage img, Transform t) {
		img.setRGBA(t.red / 255f, t.green / 255f, t.blue / 255f, t.alpha / 255f);
		img.setHSV(t.hue, t.saturation / 100f, t.brightness / 100f);
	}

}
