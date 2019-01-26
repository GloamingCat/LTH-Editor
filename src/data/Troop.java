package data;

import org.eclipse.swt.graphics.Point;

import data.Battler.Drop;
import data.config.Config;
import data.subcontent.Script;
import data.subcontent.Unit;
import project.Project;
import lwt.dataestructure.LDataList;

public class Troop extends Data {

	public Script ai = new Script();
	public boolean persistent = false;
	public int money = 100;
	public LDataList<Drop> items = new LDataList<>();
	public LDataList<Unit> members = new LDataList<>();
	
	public Point getSize() {
		Config.Troop conf = Project.current.config.getData().troop;
		int x = conf.width;
		int y = conf.height;
		for (Unit u : members) {
			if (!u.backup) {
				x = Math.max(x, u.x);
				y = Math.max(y, u.y);
			}
		}
		return new Point(x, y);
	}
	
}
