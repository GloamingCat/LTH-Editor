package data.config;

import data.Data;
import lwt.LColor;
import lwt.dataestructure.LDataList;

public class Region extends Data {
	
	public LColor color = new LColor();
	public LDataList<Integer> troops = new LDataList<>();

}
