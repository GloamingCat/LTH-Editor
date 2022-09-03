package data.subcontent;

public class Tag {

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
		if (obj instanceof Tag) {
			Tag tag = (Tag) obj;
			return key.equals(tag.key) && value.equals(tag.value);
		} else {
			return false;
		}
	}
	
}
