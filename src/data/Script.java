package data;

import java.util.ArrayList;

import com.sun.javafx.collections.MappingChange.Map;

public class Script extends Data {
	
	public ArrayList<Command> commands = new ArrayList<>();
	
	public static class Command {
		
		public String name = "";
		public Map<String, Object> param;
		
	}
	
}
