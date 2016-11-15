package data;

import lwt.dataestructure.LDataList;

public class Tileset {

	public String name = "";
	public LDataList<Integer> terrainIDs;
	public LDataList<Integer> obstacleIDs;
	public LDataList<Integer> characterIDs;
	
	public Tileset() {
		terrainIDs = new LDataList<Integer>();
		terrainIDs.add(0);
		obstacleIDs = new LDataList<Integer>();
		obstacleIDs.add(0);
		characterIDs = new LDataList<Integer>();
		characterIDs.add(0);
	}
	
}
