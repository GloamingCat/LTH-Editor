package data;

import java.util.ArrayList;

import com.google.gson.JsonElement;
import com.google.gson.internal.StringMap;

public class Script extends Data {
	
	public ArrayList<Command> commands = new ArrayList<>();
	public boolean global = false;
	
	public static class Command {
		
		public String name = "";
		public StringMap<JsonElement> param;
		
	}
	
}
