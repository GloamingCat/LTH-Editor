package gui.helper;

import java.util.ArrayList;

import org.eclipse.swt.graphics.Point;

public abstract class BaseIsoField extends FieldMath {

	public BaseIsoField(int rows) {
		super(rows);
	}
	
	protected ArrayList<Point> createNeighborShift() {
		ArrayList<Point> p = fullNeighborShift();
		removePoints(p);
		return p;
	}

	@Override
	protected ArrayList<Point> createVertexShift() {
		ArrayList<Point> p = fullVertexShift();
		removePoints(p);
		return p;
	}
	
	protected abstract void removePoints(ArrayList<Point> p);
	
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
		return height * (conf.tileH + conf.tileS) / 2 + (conf.tileH - conf.tileS) / 2;
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
	
}
