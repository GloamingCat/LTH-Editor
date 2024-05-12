package data.config;

import data.Data;

public class EquipType extends Data {
			
	public int count = 1;
	// 0 => free
	// 1 => at least one equipped
	// 2 => all equipped
	// 3 => cannot change
	// 4 => invisible
	public int state = 0;

	@Override
	public boolean equals(Object obj) {
		if (obj instanceof EquipType et)
			return et.count == count && et.state == state && super.equals(obj);
		return false;
	}
	
}
