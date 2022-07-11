package data.subcontent;

public class Audio {

	public String name = "";
	public int volume = 100;
	public int pitch = 100;
	public int time = 0;
	
	public Audio() {}
	public Audio(String path, int volume, int pitch, int time) {
		this.name = path;
		this.volume = volume;
		this.pitch = pitch;
		this.time = time;
	}
	
	public String toString() {
		if (name.isEmpty()) {
			return "          ";
		}
		return name.replace("sfx/", "") + 
				" " + volume + " " + pitch + " " + time;
	}
	
	public boolean equals(Object other) {
		if (other instanceof Audio) {
			Audio audio = (Audio) other;
			return audio.name.equals(name) && audio.pitch == pitch 
					&& audio.volume == volume && audio.time == time;
		} else
			return false;
	}
	
	public static class Node extends Audio {
		public String key = "soundKey";
		
		public Node() {}
		
		public String toString() {
			return key;// + ": " + super.toString();
		}
		
		public boolean equals(Node node) {
			if (node instanceof Node) {
				Node n = (Node) node;
				return key.equals(n.key) && super.equals(node);
			}
			return false;
		}
	}
	
}
