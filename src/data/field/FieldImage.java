package data.field;

import data.GameCharacter.Portrait;

public class FieldImage extends Portrait {

	public boolean foreground = false;
	public boolean glued = true;
	public boolean visible = true;
	
	public boolean equals(Object obj) {
		if (!super.equals(obj))
			return false;
		if (obj instanceof FieldImage other) {
            return other.foreground == foreground &&
					other.glued == glued && other.visible == visible;
		} else return false;
	}
	
}
