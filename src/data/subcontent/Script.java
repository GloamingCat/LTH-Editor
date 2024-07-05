package data.subcontent;

import data.Data;
import project.Project;

public class Script extends Data {
	
	public Script() { name = ""; }
	
	public Script(boolean load, boolean interact, boolean collide, boolean exit, boolean destroy) {
		name = "";
		onLoad = load;
		onInteract = interact;
		onCollide = collide;
		onExit = exit;
		onDestroy = destroy;
	}

	public Script(String name, boolean load, boolean interact, boolean collide, boolean exit, boolean destroy) {
		this(load, interact, collide, exit, destroy);
		this.name = name;
	}

	// Load/Exit scripts
	public Script(String name, boolean blockPlayer, boolean load) {
		this(name, load, false, false, !load, false);
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
	public boolean onDestroy = false;

	public String description = "";

	protected final static String[] triggerNames = new String[] { "Load", "Collide", "Interact", "Exit", "Destroy" };

	@Override
	public String toString() {
		if (name.isEmpty())
			return "            ";
		String name = this.name;
		if (description.isEmpty()) {
			try {
				int id = Integer.parseInt(name);
				name = Project.current.events.getData().get(id).toString();
			} catch (NumberFormatException | NullPointerException ignored) {}
		} else
			name = description;
		StringBuilder s = new StringBuilder(name);
		s.append(" (");
		boolean[] triggers = new boolean[] { onLoad, onCollide, onInteract, onExit, onDestroy };
		boolean first = true;
		for (int i = 0; i < triggers.length; i++) {
			if (triggers[i]) {
				s.append(first ? triggerNames[i] : ", " + triggerNames[i]);
				first = false;
			}
		}
		return s.append(")").toString();
	}
	
}
