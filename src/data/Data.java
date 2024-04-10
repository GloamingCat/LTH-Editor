package data;

import lui.base.data.LDataList;
import lui.base.data.LDataTree;
import data.subcontent.Tag;

public class Data {

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
	
}
