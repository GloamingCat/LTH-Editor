package data.config;

import data.subcontent.Tag;
import lwt.dataestructure.LDataList;

public class Plugin {

	public String path = "";
	public boolean on = true;
	public LDataList<Tag> params = new LDataList<>();
	
	public String toString() {
		return path;
	}
	
}
