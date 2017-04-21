package data;

public class Script {

	public String path = "";
	public String param = "";
	
	public Script() {}
	public Script(String path, String param) {
		this.param = param;
		this.path = path;
	}
	
	public String toString() {
		return path + "(" + param + ")";
	}
	
	public Script clone() {
		return new Script(path, param);
	}
	
}
