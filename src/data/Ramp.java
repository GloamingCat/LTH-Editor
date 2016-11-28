package data;

public class Ramp {

	public String name = "New Ramp";
	
	public int height = 2;
	public PointSet points = new PointSet();
	
	public static class PointSet {
		
		public int b1x = 0;
		public int b1y = 0;
		public int b2x = 0;
		public int b2y = 0;
		public int t1x = 0;
		public int t1y = 0;
		public int t2x = 0;
		public int t2y = 0;
		
		public boolean equals(Object o) {
			if (o instanceof PointSet) {
				PointSet p = (PointSet) o;
				return p.b1x == b1x && p.b1y == b1y && p.b2x == b2x && p.b2y == b2y 
						&& p.t1x == t1x && p.t1y == t1y && p.t2x == t2x && p.t2y == t2y;
			} else {
				return false;
			}
		}
	}

	public String toString() {
		return name;
	}
	
}
