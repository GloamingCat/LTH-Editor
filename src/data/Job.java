package data;

import lwt.dataestructure.LDataList;
import project.Project;

public class Job extends Data {

	public LDataList<String> build = new LDataList<>();
	public String expCurve = "(100 + math.round(lvl / 5)) * lvl";
	public LDataList<Skill> skills = new LDataList<>();
	
	public static class Skill {
	
		public int id;
		public int level;
	
		public String toString() {
			Object obj = Project.current.skills.getTree().get(id);
			String skillName = obj.toString();
			String lvl = " (lvl " + level + ")";
			return skillName + lvl;
		}
		
	}
	
}
