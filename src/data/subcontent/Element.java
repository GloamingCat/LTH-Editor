package data.subcontent;

import gui.Vocab;
import project.Project;

public class Element extends Bonus {

	public int type = 0; // 0 => immunity, 1 => attack property, 2 => damage buff
	
	public String toString() {
		Object element = Project.current.elements.getList().get(id);
		if (element == null) element = "NULL";
		String t = type == 0 ? Vocab.instance.ELEMENTDEF : 
			type == 1 ? Vocab.instance.ELEMENTATK :
			type == 2 ? Vocab.instance.ELEMENTBUFF : "";
		return element.toString() + ": " + value + "% (" + t + ")";
	}
	
}
