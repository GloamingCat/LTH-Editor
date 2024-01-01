package data.subcontent;

public class Property {

	public int id = 0;
	public int value = 100;
	
	public Property() {}
	
	public Property(Property copy) {
		id = copy.id;
		value = copy.value;
	}
	
	public boolean equals(Object obj) {
		if (obj instanceof Property) {
			Property prop = (Property) obj;
			return prop.id == id && prop.value == value;
		} else return false;
	}

}
