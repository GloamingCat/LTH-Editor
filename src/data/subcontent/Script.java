package data.subcontent;

import data.Data;

public class Script extends Data {
	
	public Script() { name = ""; }
	
	public boolean global = false;
	public boolean block = true;
	public boolean wait = true;
	
	public boolean onLoad = true;
	public boolean onInteract = false;
	public boolean onCollide = false;
	
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
		return name + " " + s + ")";
	}
	
}
