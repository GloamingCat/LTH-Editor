package data.field;

import gui.helper.TilePainter;
import lwt.datainterface.LGraphical;

import org.eclipse.swt.graphics.Image;

import data.Script;
import data.field.Tileset.BasicTile;

public class CharTile extends BasicTile implements LGraphical {
	
	public String key = "charKey";
	public boolean persistent = false;
	
	public int x = 0;
	public int y = 0;
	public int h = 0;
	
	public int party = -1;
	public int charID = 11;
	public int battlerID = -1;
	
	public String animation = "Idle";
	public int direction = 315;
	
	public Script startScript = new Script();
	public Script collisionScript = new Script();
	public Script interactScript = new Script();
	
	public CharTile() {}

	@Override
	public Image toImage() {
		return TilePainter.getCharacterTile(id, animation, direction);
	}
	
	public String getKey() {
		return id + "," + animation + "," + direction;
	}
	
	public String toString() {
		return key;
	}
	
}