package data;

import data.Battler.Drop;
import data.config.Config;
import data.subcontent.Unit;
import project.Project;
import lwt.dataestructure.LDataList;

public class Troop extends Data {

	public String ai = "";
	public boolean persistent = false;
	public int money = 100;
	public LDataList<Drop> items = new LDataList<>();
	public LDataList<Unit> members = new LDataList<>();
	
	public int[] getSize() {
		Config.Troop conf = Project.current.config.getData().troop;
		int x = conf.width;
		int y = conf.height;
		for (Unit u : members) {
			if (u.list == 0) {
				x = Math.max(x, u.x);
				y = Math.max(y, u.y);
			}
		}
		return new int[] {x, y};
	}
	
}
