package data.subcontent;

import data.Data;

public class Rule extends Data {

	public String condition = "";

	@Override
	public String toString() {
		if (name.isEmpty())
			return "Wait";
		else if (condition.isEmpty())
			return name + " " + tags.toString();
		else
			return name + " if " + condition + " " + tags.toString();
	}
	
}
