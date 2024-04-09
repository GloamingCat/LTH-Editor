package gui.helper;

import java.util.ArrayList;
import java.util.Iterator;

import lbase.data.LPoint;

public abstract class BaseIsoField extends FieldMath {
	
	
	public BaseIsoField(int rows) {
		super(rows, 315);
	}
	
	@Override
	public LPoint pixelSize(int sizeX, int sizeY) {
		return new LPoint((sizeX + sizeY - 1) * (conf.tileW + conf.tileB) / 2 + (conf.tileW - conf.tileB) / 2,
						 (sizeX + sizeY - 1) * (conf.tileH + conf.tileS) / 2 + (conf.tileH - conf.tileS) / 2);
	}
	
	@Override
	public LPoint pixelCenter(int sizeX, int sizeY, int height) {
		LPoint s = pixelSize(sizeX, sizeY);
		return new LPoint((s.x - conf.tileW) / 2 , - height * conf.pixelsPerHeight / 2);
	}
	
	@Override
	public int pixelDisplacement(int height) {
		return (height + 1) * (conf.tileH + conf.tileS) / 2;
	}
	
	@Override
	public int fieldDepth(int sizeX, int sizeY) {
		return sizeX + sizeY - 1;
	}
	
	@Override
	public LPoint depthLimits(int sizeX, int sizeY, int height) {
		int dpy = conf.depthPerY / 2;
		int pph = conf.pixelsPerHeight;
		int dph = conf.depthPerHeight;
		return new LPoint(
				(int)-Math.ceil(sizeY * dpy + pph + dph * (height - 1)),
				(int) Math.ceil(sizeX * dpy + pph * 2 + dph * (height + 1)));
	}
	
	@Override
	public int lineWidth(int sizeX, int sizeY) {
		int tileCount = Math.max(sizeX, sizeY);
		int imgW = (FieldHelper.config.grid.tileW + FieldHelper.config.grid.tileB) * tileCount;
		return imgW;
	}

	@Override
	public LPoint pixel2Tile(float x, float y, float d) {
		float newH = d / conf.pixelsPerHeight;
		float sxy = x * 2 / (conf.tileW + conf.tileB);			// sum
		float dyx = (y + newH) * 2 / (conf.tileH + conf.tileS); // dif
		int newX = Math.round((sxy - dyx) / 2);
		int newY = Math.round((sxy + dyx) / 2);
		return new LPoint(newX, newY);
	}

	@Override
	public LPoint tile2Pixel(float x, float y, float h) {
		float newHeight = h * conf.pixelsPerHeight;
		int newX = Math.round((x + y) * (conf.tileW + conf.tileB) / 2);
		int newY = Math.round((y - x) * (conf.tileH + conf.tileS) / 2 - newHeight);
		return new LPoint(newX, newY);
	}
	
	// -------------------------------------------------------------------------------------
	// Field Canvas
	// -------------------------------------------------------------------------------------
	
	public Iterator<ArrayList<LPoint>> lineIterator(int sizeX, int sizeY) {
		return new Iterator<ArrayList<LPoint>>() {
			int k = sizeX - 1;
			int l = 1;
			@Override
			public ArrayList<LPoint> next() {
				if (k >= 0) {
					ArrayList<LPoint> list = new ArrayList<>();
					for(int i = k, j = 0; i < sizeX && j < sizeY; i++, j++) {
						list.add(new LPoint(i, j));
					}
					k--;
					return list;
				} else {
					ArrayList<LPoint> list = new ArrayList<>();
					for(int i = 0, j = l; i < sizeX && j < sizeY; i++, j++) {
						list.add(new LPoint(i, j));
					}
					l++;
					return list;
				}
			}
			@Override
			public boolean hasNext() {
				return k >= 0 || l < sizeY;
			}
		};
	}
	
}
