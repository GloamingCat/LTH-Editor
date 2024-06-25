package data.subcontent;

import data.Data;
import project.Project;

public class Script extends Data {
	
	public Script() { name = ""; }
	
	public Script(boolean load, boolean interact, boolean collide, boolean exit) {
		name = "";
		onLoad = load;
		onInteract = interact;
		onCollide = collide;
	}

	public Script(String name, boolean load, boolean interact, boolean collide, boolean exit) {
		this(load, interact, collide, exit);
		this.name = name;
	}

	public Script(String name, boolean blockPlayer) {
		this(name, true, false, false, false);
		wait = true;
		block = blockPlayer;
	}

	public boolean global = false;
	public boolean block = true;
	public boolean wait = true;
	
	public boolean onLoad = true;
	public boolean onInteract = false;
	public boolean onCollide = false;
	public boolean onExit = false;

	public String description = "";

	@Override
	public String toString() {
		if (name.isEmpty())
			return "            ";
		String s = "(";
		if (onLoad) {
			s += "Load";
			if (onCollide)
				s += ", Collide";
			if (onInteract)
				s += ", Interact";
			if (onExit)
				s += ", Exit";
		} else if (onCollide) {
			s += "Collide";
			if (onInteract)
				s += ", Interact";
			if (onExit)
				s += ", Exit";
		} else if (onInteract) {
			s += "Interact";
			if (onExit)
				s += ", Exit";
		} else if (onExit)
			s += "Exit";
		if (description.isEmpty()) {
			String name = this.name;
			try {
				int id = Integer.parseInt(name);
				name = Project.current.events.getData().get(id).toString();
			} catch (NumberFormatException ignored) {}
			return name + " " + s + ")";
		} else
			return description + " " + s + ")";
	}
	
}
