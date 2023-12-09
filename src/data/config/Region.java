package data.config;

import data.Data;
import lwt.dataestructure.LDataList;
import lwt.graphics.LColor;

public class Region extends Data {
	
	public LColor color = new LColor();
	public LDataList<Integer> troops = new LDataList<>();

}
