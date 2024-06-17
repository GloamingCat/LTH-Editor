package data.subcontent;

public class Property implements Cloneable {

	public int id = 0;
	public int value = 100;
	
	public Property() {}
	
	public Property(Property copy) {
		id = copy.id;
		value = copy.value;
	}

	@Override
	public boolean equals(Object obj) {
		if (obj instanceof Property prop) {
            return prop.id == id && prop.value == value;
		} else return false;
	}

    @Override
    public Property clone() {
        try {
            Property clone = (Property) super.clone();
            clone.id = id;
			clone.value = value;
            return clone;
        } catch (CloneNotSupportedException e) {
            throw new AssertionError();
        }
    }

	@Override
	public String toString() {
		return "{id=" + id + "; value=" + value + "}";
	}
}
