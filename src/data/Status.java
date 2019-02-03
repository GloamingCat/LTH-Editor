package data;

import data.Item.Attribute;
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
	
	// Graphics
	public String charAnim = "";
	public Transform transform = new Transform();
	public boolean override = false;
	
	// Durability
	public int duration = -1; // <= -1 for infinite
	public boolean removeOnKO = true;
	public boolean removeOnDamage = true;
	public boolean battleOnly = true;
	
	// Effects
	public boolean deactivate = false;
	public boolean ko = false;
	public Script behavior = new Script();
	
	// Drain
	public String drainAtt = "";
	public int frequency = 0;
	public int drainValue = 0;
	public boolean percentage = true;
	
	// Other
	public LDataList<Attribute> attributes = new LDataList<>();
	public LDataList<Bonus> elementAtk = new LDataList<>();
	public LDataList<Bonus> elementDef = new LDataList<>();
	public LDataList<Integer> statusDef = new LDataList<>();
	public LDataList<Integer> cancel = new LDataList<>();
	
}
