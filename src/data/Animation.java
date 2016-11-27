package data;

public class Animation {
	
	// General
	public String name = "";
	public String imagePath = "";
	public int rows = 8;
	public int cols = 6;
	public int duration = 30;
	
	// Transform
	public Transform transform = new Transform();
	
	//Audio
	public Audio audio = new Audio();

	@Override
	public String toString() {
		return name;
	}
	
}
