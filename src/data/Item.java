package data;

import lwt.dataestructure.LDataList;

public class Item {

	public String name = "";
	public String description = "";
	public String icon = "";
	
	public int skillID = 0;
	public int statusID = 0;
	
	public int price = 10;
	public int weight = 10;
	
	public boolean canSell = true;
	public boolean canConsume = true;
	public boolean canUse = true;
	public boolean canEquip = false;
	
	public LDataList<Integer> attributes = new LDataList<>();
	public LDataList<Tag> tags = new LDataList<>();
	
	public String toString() {
		return name;
	}

}
