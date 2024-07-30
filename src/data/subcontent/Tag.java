package data.subcontent;

import lui.base.data.LDataList;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

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

	public static HashMap<String, String> toMap(ArrayList<Tag> tags) {
		HashMap<String, String> map = new HashMap<>();
		for (Tag tag : tags) {
			map.put(tag.key, tag.value);
		}
		return map;
	}

	public static LDataList<Tag> toTags(HashMap<String, String> map) {
		LDataList<Tag> tags = new LDataList<>();
		for (Map.Entry<String, String> entry : map.entrySet()) {
			tags.add(new Tag(entry.getKey(), entry.getValue()));
		}
		return tags;
	}

}
