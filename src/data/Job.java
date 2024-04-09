package data;

import lbase.data.LDataList;
import project.Project;

public class Job extends Data {

	public String expCurve = "(100 + math.round(lvl / 5)) * lvl";
	public LDataList<String> build = new LDataList<>();
	public int attackID = 0;
	public LDataList<Skill> skills = new LDataList<>();
	public LDataList<Status> statuses = new LDataList<>();
	
	public static class Skill {
	
		public int id;
		public int level;
	
		public String toString() {
			Object obj = Project.current.skills.getTree().get(id);
			String skillName = obj == null ? ("NULL " + id) : obj.toString();
			String lvl = " (lvl " + level + ")";
			return skillName + lvl;
		}
		
	}
	
	public static class Status {
		
		public int id;
		public int level;
	
		public String toString() {
			Object obj = Project.current.status.getTree().get(id);
			String skillName = obj == null ? ("NULL " + id) : obj.toString();
			String lvl = " (lvl " + level + ")";
			return skillName + lvl;
		}
		
	}
	
}
