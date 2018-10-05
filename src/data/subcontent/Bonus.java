package data.subcontent;

public class Bonus {

	public int id = 0;
	public int value = 100;
	
	public Bonus() {}
	
	public Bonus(Bonus copy) {
		id = copy.id;
		value = copy.value;
	}

}
