package data;

import lwt.dataestructure.LDataList;
import data.subcontent.Tag;

public class Data {

	public String name = "Empty";
	public LDataList<Tag> tags = new LDataList<>();
	public String key = "";
	
	@Override
	public String toString() {
		return name;
	}
	
}
