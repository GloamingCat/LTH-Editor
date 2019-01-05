package data;

import lwt.dataestructure.LDataList;
import data.subcontent.Constant;

public class Script extends Data {
	
	public boolean global = false;
	public String commands = "";
	public LDataList<Constant> constants = new LDataList<>();
	
}
