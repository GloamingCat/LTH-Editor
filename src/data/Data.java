package data;

import lui.base.data.LDataList;
import lui.base.data.LDataTree;
import data.subcontent.Tag;

public class Data implements Cloneable {

	public String name = "Empty";
	public LDataList<Tag> tags = new LDataList<>();
	public String key = "";
	
	@Override
	public String toString() {
		return name;
	}
	
	public void onStart(LDataTree<Object> root, LDataTree<Object> node) {
		if (!key.isEmpty()) {
			root.setKeyID(key, node.id);
		}
	}

    @Override
    public Data clone() {
        try {
            Data clone = (Data) super.clone();
            clone.name = name;
			clone.key = key;
			clone.tags = tags.clone();
			clone.tags.replaceAll(Tag::clone);
            return clone;
        } catch (CloneNotSupportedException e) {
            throw new AssertionError();
        }
    }
}
