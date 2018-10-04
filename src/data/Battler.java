package data;

import data.subcontent.Bonus;
import lwt.dataestructure.LDataList;

public class Battler extends Data {

	public boolean persistent = false;
	public int level;
	public int battleCharID;
	public int fieldCharID;
	public int attackID;
	public int skillDagID;
	public int money;
	public int exp;
	public String scriptAI = "";
	public String build = "";
	public LDataList<Integer> attributes = new LDataList<>();
	public LDataList<Integer> skills = new LDataList<>();
	public LDataList<Bonus> items = new LDataList<>();
	public LDataList<Bonus> elements = new LDataList<>();

}
