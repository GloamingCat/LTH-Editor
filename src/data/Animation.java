package data;

public class Animation {
	
	public String name = "";
	public String imagePath = "";
	public int rows = 8;
	public int cols = 6;
	public int frameDuration = 7;
	
	public Transform transform = new Transform();
	
	// Sound
	public String soundPath = "";
	public int volume = 100;
	public int pitch = 100;

	public String toString() {
		return name;
	}
	
}
