package data;

import data.Item.Attribute;
import data.subcontent.Bonus;
import data.subcontent.Icon;
import data.subcontent.Rule;
import data.subcontent.Transformation;
import lbase.data.LDataList;
import lbase.data.LDataTree;

public class Status extends Data {
	
	// General
	public String script = "";
	public Icon icon = new Icon();
	public int priority = 100;
	public boolean visible = true;
	public boolean cumulative = false;
	
	// Graphics
	public String charAnim = "";
	public LDataList<Transformation> transformations = new LDataList<>();
	
	// Durability
	public int duration = -1; // <= -1 for infinite
	public boolean removeOnKO = true;
	public boolean removeOnDamage = true;
	public boolean battleOnly = true;
	
	// Effects
	public boolean deactivate = false;
	public boolean ko = false;
	public LDataList<Rule> behavior = new LDataList<>();
	
	// Drain
	public String drainAtt = "";
	public int drainValue = 0;
	public boolean percentage = true;
	
	// Effects
	public LDataList<Attribute> attributes = new LDataList<>();
	public LDataList<Bonus> bonuses = new LDataList<>();
	public LDataList<Integer> cancel = new LDataList<>();

	
	// Legacy
	public LDataList<Bonus> elements = new LDataList<>();
	public LDataList<Integer> statusDef = new LDataList<>();
	
	public void onStart(LDataTree<Object> root, LDataTree<Object> node) {
		super.onStart(root, node);
		for (Integer id : statusDef) {
			elements.add(new Bonus(3, 100, id));
		}
		statusDef.clear();
		bonuses.addAll(elements);
		elements.clear();
	}

}
