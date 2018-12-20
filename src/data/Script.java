package data;

import java.util.ArrayList;

import com.google.gson.JsonElement;

public class Script extends Data {
	
	public ArrayList<Command> commands = new ArrayList<>();
	public boolean global = false;
	
	public static class Command {
		
		public String name = "";
		public JsonElement param;
		
	}
	
}
