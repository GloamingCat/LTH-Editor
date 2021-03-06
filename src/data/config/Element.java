package data.config;

import data.Data;
import data.subcontent.Icon;

public class Element extends Data {

	public Icon icon = new Icon();
	
	public boolean equals(Object obj) {
		if (obj == this) {
			return true;
		} else if (obj instanceof Element) {
			Element other = (Element) obj;
			return other.name.equals(name) && 
					other.tags.equals(tags) &&
					other.icon.equals(icon);
		} else {
			return false;
		}
	}
}
