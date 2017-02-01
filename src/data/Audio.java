package data;

public class Audio {

	public String path = "";
	public int volume = 100;
	public int pitch = 100;
	public int speed = 100;
	
	public Audio() {}
	public Audio(String path, int volume, int pitch, int speed) {
		this.path = path;
		this.volume = volume;
		this.pitch = pitch;
		this.speed = speed;
	}
	
	public String toString() {
		if (path.isEmpty()) {
			return "";
		}
		return path.replace("sfx/", "") + 
				" " + volume + " " + pitch + " " + speed;
	}
	
}
