package gui.helper;

import java.util.ArrayList;
import java.util.Iterator;

import data.subcontent.Point;

public abstract class BaseIsoField extends FieldMath {
	
	
	public BaseIsoField(int rows) {
		super(rows, 315);
	}
	
	@Override
	public Point pixelSize(int sizeX, int sizeY) {
		return new Point((sizeX + sizeY - 1) * (conf.tileW + conf.tileB) / 2 + (conf.tileW - conf.tileB) / 2,
						 (sizeX + sizeY - 1) * (conf.tileH + conf.tileS) / 2 + (conf.tileH - conf.tileS) / 2);
	}
	
	@Override
	public Point pixelCenter(int sizeX, int sizeY, int height) {
		Point s = pixelSize(sizeX, sizeY);
		return new Point((s.x - conf.tileW) / 2 , - height * conf.pixelsPerHeight / 2);
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
	public int lineWidth(int sizeX, int sizeY) {
		int tileCount = Math.max(sizeX, sizeY);
		int imgW = (FieldHelper.config.grid.tileW + FieldHelper.config.grid.tileB) * tileCount;
		return imgW;
	}

	@Override
	public Point pixel2Tile(float x, float y, float d) {
		float newH = d / conf.pixelsPerHeight;
		float sxy = x * 2 / (conf.tileW + conf.tileB);			// sum
		float dyx = (y + newH) * 2 / (conf.tileH + conf.tileS); // dif
		int newX = Math.round((sxy - dyx) / 2);
		int newY = Math.round((sxy + dyx) / 2);
		return new Point(newX, newY);
	}

	@Override
	public Point tile2Pixel(float x, float y, float h) {
		float newHeight = h * conf.pixelsPerHeight;
		int newX = Math.round((x + y) * (conf.tileW + conf.tileB) / 2);
		int newY = Math.round((y - x) * (conf.tileH + conf.tileS) / 2 - newHeight);
		return new Point(newX, newY);
	}
	
	// -------------------------------------------------------------------------------------
	// Field Canvas
	// -------------------------------------------------------------------------------------
	
	public Iterator<ArrayList<Point>> lineIterator(int sizeX, int sizeY) {
		return new Iterator<ArrayList<Point>>() {
			int k = sizeX - 1;
			int l = 1;
			@Override
			public ArrayList<Point> next() {
				if (k >= 0) {
					ArrayList<Point> list = new ArrayList<>();
					for(int i = k, j = 0; i < sizeX && j < sizeY; i++, j++) {
						list.add(new Point(i, j));
					}
					k--;
					return list;
				} else {
					ArrayList<Point> list = new ArrayList<>();
					for(int i = 0, j = l; i < sizeX && j < sizeY; i++, j++) {
						list.add(new Point(i, j));
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
