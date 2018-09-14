package data.config;

import org.eclipse.swt.graphics.RGB;

import lwt.dataestructure.LDataList;

public class Region {

	public String name = "New Region";
	
	public RGB rgb = new RGB(255, 255, 255);
	
	public LDataList<Integer> battlers = new LDataList<>();
	
	public String toString() {
		return name;
	}
	
}
