package data;

import data.subcontent.Tag;
import lwt.dataestructure.LDataList;
import project.Project;

public class Job extends Data {

	public LDataList<String> build = new LDataList<>();
	public String expCurve = "(100 + math.round(lvl / 5)) * lvl";
	public LDataList<Node> skills = new LDataList<>();
	
	public static class Node {
	
		public int id;
		public int level;
		public LDataList<Integer> requirements = new LDataList<>();
		public LDataList<Tag> tags = new LDataList<>();
	
		public String toString() {
			Object obj = Project.current.skills.getTree().get(id);
			String skillName = obj.toString();
			String lvl = " (level " + level + ")";
			
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
