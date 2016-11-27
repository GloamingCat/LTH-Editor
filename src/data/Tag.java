package data;

public class Tag {

	public String name = "newTag";
	public String value = "";
	
	public Tag() {}
	
	public Tag(Tag other) {
		name = other.name;
		value = other.value;
	}
	
	public String toString() {
		return "\"" + name + "\": " + value;
	}
	
	public boolean equals(Object obj) {
		if (obj instanceof Tag) {
			Tag tag = (Tag) obj;
			return name.equals(tag.name) && value.equals(tag.value);
		} else {
			return false;
		}
	}
	
}
