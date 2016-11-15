package data;

import project.Project;
import lwt.dataestructure.LDataList;

public class SkillDag {

	public String name = "New Skill Tree";
	public LDataList<Node> nodes = new LDataList<>();
	
	public String toString() {
		return name;
	}
	
	public static class Node {
	
		public int skillID;
		public int cost;
		public LDataList<ObjectID.Skill> requirements = new LDataList<>();
	
		public String toString() {
			Object obj = Project.current.skills.getList().get(skillID);
			String skillName = obj.toString();
			String cost = " (cost=" + this.cost + ") ";
			
			String req = "[";
			if (requirements.isEmpty()) {
				req += "]";
			} else {
				for(ObjectID.Skill r : requirements) {
					req += r.id + ",";
				}
				req = req.substring(0, req.length() - 1) + "]";
			}
			return skillName + cost + req;
		}
	}
	
}
