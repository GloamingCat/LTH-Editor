package data.subcontent;

import com.google.gson.JsonObject;

public class Constant {

	public String name = "New Constant";
	public int type = 0;
	public JsonObject value;
	
	public String toString() {
		return name + "=" + value.toString();
	}
	
}
