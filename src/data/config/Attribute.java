package data.config;

import data.Data;

public class Attribute extends Data {

	public String shortName = "ATTR";
	public String script = "";
	public int visibility = 1; // 1 => primary, 2 => secondary, 0 => not visible

	@Override
	public boolean equals(Object obj) {
		if (obj instanceof Attribute other) {
            return other.visibility == visibility && other.key.equals(key) &&
					other.shortName.equals(shortName) && other.script.equals(script);
		} else return false;
	}
	
}
