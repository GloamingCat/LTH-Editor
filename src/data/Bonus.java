package data;

import project.Project;

public class Bonus {

	public int id = 0;
	public int value = 100;
	
	public static class Attribute extends Bonus {
		public String toString() {
			Config conf = (Config) Project.current.config.getData();
			return conf.attributes.get(id).toString();
		}
	}
	
	public static class Item extends Bonus {
		public String toString() {
			return Project.current.items.getList().get(id).toString();
		}
	}
	
	public static class Element extends Bonus {
		public String toString() {
			Config conf = (Config) Project.current.config.getData();
			return conf.elements.get(id);
		}
	}
	
}
