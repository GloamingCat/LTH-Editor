package data;

import lwt.dataestructure.LDataList;

public class Animation extends Data {
	
	// General
	public Quad quad = new Quad();
	public int rows = 1;
	public int cols = 1;
	public String pattern = "";
	public LDataList<Tag> tags = new LDataList<>();
	
	// Transform
	public Transform transform = new Transform();
	
	// Animation
	public String duration = "";
	public int loop = 0;
	public String animation = "";
	public LDataList<Audio> audio = new LDataList<>();
	
}
