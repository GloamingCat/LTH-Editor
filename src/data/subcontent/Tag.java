package data.subcontent;

public class Tag implements Cloneable {

	public String key = "key";
	public String value = "value";
	
	public Tag() {}
	
	public Tag(Tag other) {
		key = other.key;
		value = other.value;
	}
	
	public Tag(String key, String value) {
		this.key = key;
		this.value = value;
	}
	
	public String toString() {
		return "\"" + key + "\": " + value.replace("\n", "").replace("\r", "");
	}
	
	public boolean equals(Object obj) {
		if (obj instanceof Tag tag) {
            return key.equals(tag.key) && value.equals(tag.value);
		} else {
			return false;
		}
	}

    @Override
    public Tag clone() {
        try {
            Tag clone = (Tag) super.clone();
			clone.key = key;
            clone.value = value;
            return clone;
        } catch (CloneNotSupportedException e) {
            throw new AssertionError();
        }
    }
}
