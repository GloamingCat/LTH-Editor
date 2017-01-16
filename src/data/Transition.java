package data;

public class Transition {

	public Transition() { }
	
	public Transition(Transition t) { }
	
	public Position origin = new Position();
	public Position destination = new Position();
	public boolean fadeOut = true;
	
	public String toString() {
		return origin.toString() + " => " + destination.toString();
	}
	
}
