package data.subcontent;

import com.google.gson.JsonObject;

public class Constant {

	public String name = "New Constant";
	public int type = 0;
	public JsonObject value;
	
	public String toString() {
		return name + "=" + value.toString();
	}
	
	public boolean equals(Object obj) {
		if (obj instanceof Constant) {
			Constant other = (Constant) obj;
			return other.name.equals(name) && other.type == type && other.value.equals(value);
		} else return false;
	}
	
}
