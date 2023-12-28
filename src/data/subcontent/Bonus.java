package data.subcontent;

import gui.Vocab;
import project.Project;

public class Bonus extends Property {

	// 0 => immunity
	// 1 => attack property
	// 2 => damage buff
	// 3 => status immunity
	public int type = 0;
	
	public Bonus() {}
	public Bonus(int type, int value, int id) {
		this.type = type;
		this.id = id;
		this.value = value;
	}
	
	public String toString() {
		if (type <= 2) {
			Object element = Project.current.elements.getList().get(id);
			if (element == null) element = "NULL " + id;
			String t = type == 0 ? Vocab.instance.ELEMENTDEF : 
				type == 1 ? Vocab.instance.ELEMENTATK :
				type == 2 ? Vocab.instance.ELEMENTBUFF : "";
			return element.toString() + ": " + value + "% (" + t + ")";
		} else {
			Object status = Project.current.status.getData().get(id);
			if (status == null) status = "NULL " + id;
			return status.toString() + ": " + value + "% (" + Vocab.instance.STATUSDEF + ")";
		}
	}
	
}
