package data.config;

import data.Data;
import data.subcontent.Icon;

public class Element extends Data {

	public Icon icon = new Icon();

	@Override
	public boolean equals(Object obj) {
		if (obj == this) {
			return true;
		} else if (obj instanceof Element other) {
            return other.name.equals(name) &&
					other.tags.equals(tags) &&
					other.icon.equals(icon);
		} else {
			return false;
		}
	}
}
