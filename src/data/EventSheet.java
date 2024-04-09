package data;

import data.subcontent.Tag;
import lbase.data.LDataList;

public class EventSheet extends Data {
	
	public LDataList<Event> events = new LDataList<>();
	public String description = "";
	
	public static class Event extends Data {
		
		public String condition = "";
		
		public Event() {
			name = "print('Hello World')";
		}
		
		public String toString() {
			String str = name;
			for (Tag tag : tags)
				str += ", " + tag.value;
			if (!condition.isEmpty())
				str += " if " + condition;
			return str;
		}
		
	}
	
}
