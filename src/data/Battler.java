package data;

import lwt.dataestructure.LDataList;

public class Battler {

	public String name = "New Battler";
	public boolean persistent = false;
	public int typeID;
	public int characterID;
	public Script scriptAI = new Script();
	public int attackID;
	public int skillDagID;
	public int money;
	public int exp;
	public Script build = new Script();
	public LDataList<Integer> attributes = new LDataList<>();
	public LDataList<Bonus> items = new LDataList<>();
	public LDataList<Bonus> elements = new LDataList<>();
	public LDataList<Tag> tags = new LDataList<>();
	
	public String toString() {
		return name;
	}
	
}
