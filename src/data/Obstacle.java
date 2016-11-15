package data;

import lwt.dataestructure.LDataList;

public class Obstacle {

	// General
	public String name = "";
	public int colliderHeight = 1;
	public int rampID = -1;
	
	// Quad
	public Quad quad = new Quad();
	
	// Space Transformation
	public Transform transform = new Transform();
	
	// Passability
	public LDataList<Boolean> neighbors = new LDataList<>(8);
	
	public LDataList<Tag> tags = new LDataList<>();
	
	public String toString() {
		return name;
	}
	
}
