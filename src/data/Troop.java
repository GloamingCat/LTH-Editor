package data;

import org.eclipse.swt.graphics.Point;

import data.Battler.Drop;
import data.config.Config;
import data.subcontent.Unit;
import project.Project;
import lwt.dataestructure.LDataList;

public class Troop extends Data {

	public int scriptID = -1;
	public boolean persistent = false;
	public int money = 100;
	public LDataList<Drop> items = new LDataList<>();
	
	public LDataList<Unit> current = new LDataList<>();
	public LDataList<Unit> backup = new LDataList<>();
	
	public Point getSize() {
		Config.Troop conf = Project.current.config.getData().troop;
		int x = conf.width;
		int y = conf.height;
		for (Unit u : current) {
			x = Math.max(x, u.x);
			y = Math.max(y, u.y);
		}
		return new Point(x, y);
	}
	
}
