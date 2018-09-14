package data;

import data.subcontent.Quad;
import data.subcontent.Tag;
import lwt.dataestructure.LDataList;

public class Item extends Data {

	public String description = "";
	public Quad icon = new Quad();
	
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

}
