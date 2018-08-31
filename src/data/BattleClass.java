package data;

import lwt.dataestructure.LDataList;
import project.Project;

public class BattleClass extends Data {

	public LDataList<Node> skills = new LDataList<>();
	
	public static class Node {
	
		public int skillID;
		public int minLevel;
		public LDataList<Integer> requirements = new LDataList<>();
	
		public String toString() {
			Object obj = Project.current.skills.getTree().get(skillID);
			String skillName = obj.toString();
			String lvl = " (level=" + this.minLevel + ")";
			
			String req = "[";
			if (requirements.isEmpty()) {
				req += "]";
			} else {
				for(Integer r : requirements) {
					req += r + ",";
				}
				req = req.substring(0, req.length() - 1) + "]";
			}
			return skillName + lvl + req;
		}
	}
	
}
