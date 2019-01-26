package data.field;

import data.subcontent.Position;

public class Transition {

	public Transition() { }

	public Position destination = new Position();
	public Position start = new Position();
	public Position end = new Position();
	public int fade = 30;
	
	public String toString() {
		return destination.toString();
	}
	
	public Transition clone() {
		Transition t = new Transition();
		t.destination = destination.clone();
		t.start = start.clone();
		t.end = end.clone();
		return t;
	}
	
}
