package project;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import data.GUI;
import lwt.dataestructure.LDataTree;
import lwt.dataestructure.LPath;

public class GameGUISerializer extends ObjectSerializer {

	private static Gson gson = new GsonBuilder().setPrettyPrinting().create();
	
	private static class Encapsulator {
		public LPath last = new LPath(0);
		public LDataTree<GUI> root = new LDataTree<>();
	}
	
	public GameGUISerializer(String path) {
		super(path, Encapsulator.class);
	}
	
	public LDataTree<GUI> getTree() {
		Encapsulator e = (Encapsulator) getData();
		return e.root;
	}

	public GUI newGUI() {
		return gson.fromJson("{}", GUI.class);
	}

	public GUI duplicateGUI(GUI original) {
		GUI copy = gson.fromJson(gson.toJson(original), GUI.class);
		copy.prefs.name += "(copy)";
		return copy;
	}
	
	public LPath getLast() {
		Encapsulator e = (Encapsulator) getData();
		return e.last;
	}

	public void setLast(LPath path) {
		Encapsulator e = (Encapsulator) getData();
		e.last = path;
	}

}
