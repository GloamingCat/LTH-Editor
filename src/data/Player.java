package data;

public class Player {
	
	public boolean pixelMovement = false;
	public int walkSpeed = 120;
	public int dashSpeed = 300;
	public boolean stopOnCollision = true;
	public Script script = new Script("character/Player.lua", "");
	public Position startPos = new Position();
	
}