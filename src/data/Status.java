package data;

import lwt.dataestructure.LDataList;

public class Status {

	// General
	public String name = "";
	public String icon = "";
	public Script scriptAI = new Script();
	public int duration = -1; // <= 0 for infinite
	public boolean removeOnDamage;
	
	// Drain
	public int hpDrain;
	public int mpDrain;
	public int frequence;
	public boolean percentage;
	
	// Other
	public LDataList<Bonus> attributes = new LDataList<>();
	public LDataList<Bonus> elements = new LDataList<>();
	public LDataList<Tag> tags = new LDataList<>();
	
	public String toString() {
		return name;
	}
	
}
