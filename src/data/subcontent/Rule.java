package data.subcontent;

import data.Data;

public class Rule extends Data {

	public String condition = "";
	
	public String toString() {
		if (name.isEmpty())
			return "Wait";
		else if (condition.isEmpty())
			return name;
		else
			return name + " if " + condition;
	}
	
}
