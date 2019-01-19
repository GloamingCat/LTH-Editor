package gui.helper;

import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.graphics.GC;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.graphics.ImageData;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.widgets.Display;

public class ImageHelper {
	
	public static Image newImage(int imgW, int imgH) {
		Image src = new Image(Display.getCurrent(), imgW, imgH);
	    GC gc = new GC(src);
	    gc.setAlpha(0);
	    gc.fillRectangle(0, 0, imgW, imgH);
	    gc.dispose();
	    ImageData imageData = src.getImageData();
	    imageData.transparentPixel = -1;
	    Image img = correctTransparency(imageData);
	    src.dispose();
	    return img;
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

	public static Image colorTransform(Image src, float _h, float _s, float _v) {
		if (_h == 0 && _s == 1 && _v == 1)
			return src;
		_h /= 60;
		ImageData data = src.getImageData();
		ImageData newdata = (ImageData) data.clone();
		for (int i = 0; i < data.data.length; i += 4) {
			float r = (data.data[i] & 0xFF) / 255f;
			float g = (data.data[i+1] & 0xFF) / 255f;
			float b = (data.data[i+2] & 0xFF) / 255f;

			if (r == 1 && g == 1 && b == 1)
				continue;
			
		    float max = Math.max(Math.max(r, g), b);
		    float min = Math.min(Math.min(r, g), b);
			
		    float h = 360 - _h;
		    if (r == max) {
		    	h += (g - b) / (max - min);
		    } else if (g == max) {
		    	h += (b - r) / (max - min) + 2;
		    } else {
		    	h += (r - g) / (max - min) + 4;
		    }
		    h = h % 6;
		    
		    float s = 0, v = 0;
		    if (max > 0) {
		    	s = Math.max(0, Math.min(1, _s * (max - min) / max));
		    	v = Math.max(0, Math.min(1, _v * max));
		    }

			if (s < 0.001) {
				r = g = b = v;
			} else {
				int hi = (int) Math.floor(h);
			    float f = h - hi;
			    float p = v * (1 - s);
			    float q = v * (1 - f * s);
			    float t = v * (1 - (1 - f) * s);
			    switch(hi) {
			        case 0: r = v; g = t; b = p; break;
			        case 1: r = q; g = v; b = p; break;
			        case 2: r = p; g = v; b = t; break;
			        case 3: r = p; g = q; b = v; break;
			        case 4: r = t; g = p; b = v; break;
			        case 5: r = v; g = p; b = q; break;
			    }
			}
			newdata.data[i] = (byte) Math.round(r * 255);
			newdata.data[i+1] = (byte) Math.round(g * 255);
			newdata.data[i+2] = (byte) Math.round(b * 255);
			newdata.data[i+3] = data.data[i+3];
		}
		src.dispose();
		return correctTransparency(newdata);
	}

}
