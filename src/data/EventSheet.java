package data;

import data.subcontent.Tag;
import lui.base.data.LDataList;

public class EventSheet extends Data {
	
	public LDataList<Command> events = new LDataList<>();
	public String description = "";
	public boolean skippable = false;

	public static class Command extends Data {

		public String condition = "";
		public boolean unskippable = false;

		public Command() {
			name = "print('Hello World')";
		}

		@Override
		public String toString() {
			StringBuilder str = new StringBuilder(name.replace("\n", "; "));
			for (Tag tag : tags)
				str.append(", ").append(tag.value);
			if (!condition.isEmpty())
				str.append(" if ").append(condition);
			return str.toString();
		}
		
	}
	
}
