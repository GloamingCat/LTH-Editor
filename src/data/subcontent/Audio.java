package data.subcontent;

public class Audio {

	public String path = "";
	public int volume = 100;
	public int pitch = 100;
	public int time = 0;
	
	public Audio() {}
	public Audio(String path, int volume, int pitch, int time) {
		this.path = path;
		this.volume = volume;
		this.pitch = pitch;
		this.time = time;
	}
	
	public String toString() {
		if (path.isEmpty()) {
			return "          ";
		}
		return path.replace("sfx/", "") + 
				" " + volume + " " + pitch + " " + time;
	}
	
}