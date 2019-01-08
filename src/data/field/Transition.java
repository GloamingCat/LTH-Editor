package data.field;

import data.subcontent.Position;

public class Transition {

	public Transition() { }

	public int side = 0; // North, east, south, west
	public Position destination = new Position();
	public int max = -1;
	public int min = -1;
	public int fade = 60;
	
	public String toString() {
		String name = "North";
		if (side == 1)
			name = "East";
		else if (side == 2)
			name = "South";
		else if (side == 3)
			name = "West";
		return name + " => " + destination.toString();
	}
	
	public Transition clone() {
		Transition t = new Transition();
		t.side = side;
		t.destination = destination.clone();
		t.max = max;
		t.min = min;
		t.fade = fade;
		return t;
	}
	
}
