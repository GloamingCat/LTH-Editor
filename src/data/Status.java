package data;

import data.subcontent.Bonus;
import data.subcontent.Icon;
import data.subcontent.Script;
import data.subcontent.Transform;
import lwt.dataestructure.LDataList;

public class Status extends Data {
	
	// General
	public String script = "";
	public Icon icon = new Icon();
	public int priority = 100;
	public boolean visible = true;
	public boolean cumulative = false;
	public boolean deactivate = false;
	public boolean ko = false;
	public Script behavior = new Script();
	
	// Graphics
	public String charAnim = "";
	public Transform transform = new Transform();
	public boolean override = false;
	
	// Durability
	public int duration = -1; // <= -1 for infinite
	public boolean removeOnKO = true;
	public boolean removeOnDamage = true;
	public boolean battleOnly = true;
	
	// Drain
	public String drainAtt = "";
	public int frequency = 0;
	public int drainValue = 0;
	public boolean percentage = true;
	
	// Other
	public LDataList<Bonus> attributes = new LDataList<>();
	public LDataList<Bonus> elements = new LDataList<>();
	public LDataList<Bonus> cancel = new LDataList<>();
	
}
