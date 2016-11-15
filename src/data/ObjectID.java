package data;

import project.Project;
import lwt.dataestructure.LDataList;

public abstract class ObjectID {

	public int id;
	
	protected abstract LDataList<Object> getArray();
	
	public String toString() {
		String i = String.format("[%03d]", id);
		Object obj = getArray().get(id);
		return i + obj.toString();
	}
	
	public static class Skill extends ObjectID {
		protected LDataList<Object> getArray() {
			return Project.current.skills.getList();
		}
	}
	
}
