package data.config;

import org.eclipse.swt.graphics.RGB;

import data.Data;
import lwt.dataestructure.LDataList;

public class Region extends Data {
	
	public RGB rgb = new RGB(255, 255, 255);
	public LDataList<Integer> troops = new LDataList<>();

}
