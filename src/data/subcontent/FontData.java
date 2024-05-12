package data.subcontent;

public class FontData {
	
	public String path = "";
	public int size = 12;
	public String format = "";
	
	public FontData() {}
	public FontData(String path, int size, String format) {
		this.path = path;
		this.size = size;
		this.format = format;
	}
	
	public String toString() {
		return path.replace("fonts/", "") + " " + size;
	}
	
	public boolean equals(Object obj) {
		if (obj instanceof FontData other) {
            return other.path.equals(path) && other.size == size && other.format.equals(format);
		} else return false;
	}
	
}