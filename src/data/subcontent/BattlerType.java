package data.subcontent;

import gui.helper.TilePainter;

import org.eclipse.swt.graphics.Image;

import lwt.datainterface.LGraphical;

public class BattlerType implements LGraphical {

	public String name = "New Battler Type";
	public String code = "A";
	
	public String toString() {
		return code + ": " + name;
	}

	@Override
	public Image toImage() {
		return TilePainter.getBattlerTypeTile(this);
	}
	
}
