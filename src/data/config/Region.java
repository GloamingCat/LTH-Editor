package data.config;

import data.Data;
import lui.base.data.LDataList;
import lui.graphics.LColor;

public class Region extends Data {
	
	public LColor color = new LColor();
	public LDataList<Integer> battleFields = new LDataList<>();

	@Override
	public boolean equals(Object obj) {
		if (obj instanceof Region r)
			return r.color.equals(color) && r.battleFields.equals(battleFields) && super.equals(obj);
		return false;
	}

}
