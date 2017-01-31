package data;

public class Bonus {

	public Bonus(Bonus copy) {
		id = copy.id;
		value = copy.value;
	}
	public Bonus() {}
	public int id = 0;
	public int value = 100;

}
