package data.subcontent;

public class FontData {
	
	public String file;
	public String format;
	public float size = 12;

	public FontData() { file = "Roboto"; format = "ttf"; }
	public FontData(String path, String format, float size) {
		this.file = path;
		this.format = format;
		this.size = size;
	}
	
	public String toString() {
		return file.replace("fonts/", "") + " " + size;
	}

	public String getPath() {
		return file + "." + format;
	}
	
	public boolean equals(Object obj) {
		if (obj instanceof FontData other) {
            return other.file.equals(file) && other.size == size && other.format.equals(format);
		} else return false;
	}
	
}