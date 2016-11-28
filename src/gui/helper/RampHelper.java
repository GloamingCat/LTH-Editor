package gui.helper;

import org.eclipse.swt.graphics.Point;

import data.Ramp.PointSet;

public class RampHelper {

	public static PointSet getDefault2(int i) {
		Point[] v = FieldHelper.math.vertexShift;
		int n = v.length;
		PointSet set = new PointSet();
		set.b1x = v[i % n].x;
		set.b1y = v[i % n].y;
		set.b2x = v[(i + 3) % n].x;
		set.b2y = v[(i + 3) % n].y;
		set.t1x = v[(i + 1) % n].x;
		set.t1y = v[(i + 1) % n].y;
		set.t2x = v[(i + 2) % n].x;
		set.t2y = v[(i + 2) % n].y;
		return set;
	}
	
	public static PointSet getDefault3(int i) {
		Point[] v = FieldHelper.math.vertexShift;
		int n = v.length;
		PointSet set = new PointSet();
		set.b1x = v[i % n].x;
		set.b1y = v[i % n].y;
		set.b2x = v[(i + 2) % n].x;
		set.b2y = v[(i + 2) % n].y;
		set.t1x = v[(i + 5) % n].x;
		set.t1y = v[(i + 5) % n].y;
		set.t2x = v[(i + 3) % n].x;
		set.t2y = v[(i + 3) % n].y;
		return set;
	}
	
	public static PointSet getDefault4(int i) {
		Point[] v = FieldHelper.math.vertexShift;
		int n = v.length;
		PointSet set = new PointSet();
		set.t1x = v[i % n].x;
		set.t1y = v[i % n].y;
		set.t2x = v[(i + 3) % n].x;
		set.t2y = v[(i + 3) % n].y;
		set.b1x = v[(i + 1) % n].x;
		set.b1y = v[(i + 1) % n].y;
		set.b2x = v[(i + 2) % n].x;
		set.b2y = v[(i + 2) % n].y;
		return set;
	}
	
}
