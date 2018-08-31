package data;

import java.util.ArrayList;

import lwt.dataestructure.LDataList;

public class Animation extends Data {
	
	// General
	public Quad quad = new Quad();
	public int rows = 8;
	public int cols = 6;
	public LDataList<Tag> tags = new LDataList<>();
	
	// Transform
	public Transform transform = new Transform();
	
	// Animation
	public int duration = 0;
	public int loop = 0;
	public String animation = "";
	public ArrayList<Audio> audio;
	
}
