package data.subcontent;

import data.Data;

public class Script extends Data {
	
	public Script() { name = ""; }
	
	public Script(boolean load, boolean interact, boolean collide) {
		name = "";
		onLoad = load;
		onInteract = interact;
		onCollide = collide;
	}
	
	public boolean global = false;
	public boolean block = true;
	public boolean wait = true;
	
	public boolean onLoad = true;
	public boolean onInteract = false;
	public boolean onCollide = false;
	
	public String description = "";
	
	public String toString() {
		String s = "(";
		if (onLoad) {
			s += "Load";
			if (onCollide)
				s += ", Collide";
			if (onInteract)
				s += ", Interact";
		} else if (onCollide) {
			s += "Collide";
			if (onInteract)
				s += ", Interact";
		} else if (onInteract)
			s += "Interact";
		if (description.isEmpty())
			return name + " " + s + ")";
		else
			return description + " " + s + ")";
	}
	
}
