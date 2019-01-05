package data.subcontent;

public class Bonus {

	public int id = 0;
	public int value = 100;
	
	public Bonus() {}
	
	public Bonus(Bonus copy) {
		id = copy.id;
		value = copy.value;
	}
	
	public boolean equals(Object obj) {
		if (obj instanceof Bonus) {
			Bonus bonus = (Bonus) obj;
			return bonus.id == id && bonus.value == value;
		} else return false;
	}

}
