package data;

import data.subcontent.Bonus;
import data.subcontent.Quad;
import data.subcontent.Tag;
import lwt.dataestructure.LDataList;

public class Status extends Data {

	// General
	public Quad icon = new Quad();
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
	
}
