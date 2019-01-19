package gui.helper;

import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.graphics.GC;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.graphics.ImageData;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.graphics.Transform;
import org.eclipse.swt.widgets.Display;

public class ImageHelper {
	
	public static Image newImage(int imgW, int imgH) {
		Image src = new Image(Display.getCurrent(), imgW, imgH);   
	    GC gc = new GC(src);
	    gc.setAlpha(0);
	    gc.fillRectangle(0, 0, imgW, imgH);
	    gc.dispose();
	    ImageData imageData = src.getImageData();
	    imageData.transparentPixel = imageData.getPixel(0, 0);
	    return correctTransparency(src);
	}
	
	public static Image correctTransparency(Image src) {
		final ImageData imageData = src.getImageData();
		final int len = src.getBounds().width * src.getBounds().height;
		imageData.alphaData = new byte[len];
		for (int idx = 0; idx < len; idx++) {
	        final int coord = (idx * 4) + 3;
	        imageData.alphaData[idx] = imageData.data[coord];
	    }
		src.dispose();
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
		//gc.setTextAntialias(0);
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

	public static Image transform(data.subcontent.Transform t, Image original) {
		return transform(t.offsetX, t.offsetY, t.rotation, t.scaleX, t.scaleY, 
				t.red, t.green, t.blue, t.alpha, original);
	}
	
	public static Image transform(int ox, int oy, int rot, int sx, int sy, 
			int r, int g, int b, int a, Image original) {
		int w = original.getBounds().x;
		int W = w * sx / 100;
		int h = original.getBounds().y;
		int H = h * sx / 100;
		int d = (int) Math.sqrt(W * W + H * H) + 1;
		Image img = new Image(original.getDevice(), d, d);
		
		Transform transform = new Transform(original.getDevice());
        transform.translate(w/2 - ox, h - oy);
        transform.scale(sx / 100, sy / 100);
        transform.rotate(rot);
        transform.translate(ox - w/2, oy - h);
		
		GC gc = new GC(img);
		gc.setTransform(transform);
		gc.drawImage(original, 0, 0);
		gc.dispose();
		return img;
	}

}
