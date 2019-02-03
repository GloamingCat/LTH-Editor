package data.field;

import data.subcontent.Position;

public class Transition {

	public Transition() { }

	public Position destination = new Position();
	public Position tl = new Position();
	public Position br = new Position();
	public int fade = 30;
	
	public String toString() {
		return destination.toString();
	}
	
	public Transition clone() {
		Transition t = new Transition();
		t.destination = destination.clone();
		t.tl = tl.clone();
		t.br = br.clone();
		return t;
	}
	
}
