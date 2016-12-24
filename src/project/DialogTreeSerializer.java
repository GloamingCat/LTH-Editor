package project;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Stack;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import data.DialogNode;
import lwt.dataestructure.LDataTree;
import lwt.dataestructure.LPath;

public class DialogTreeSerializer extends ObjectSerializer {

	private static Gson gson = new GsonBuilder().setPrettyPrinting().create();
	
	private static class Encapsulator {
		public LPath last = new LPath(0);
		public LDataTree<DialogNode> root = new LDataTree<>();
	}
	
	public DialogTreeSerializer(String path) {
		super(path, Encapsulator.class);
	}
	
	public LDataTree<DialogNode> getTree() {
		Encapsulator e = (Encapsulator) getData();
		return e.root;
	}

	private int findID() {
		Stack<LDataTree<DialogNode>> nodeStack = new Stack<>();
		ArrayList<Integer> usedIDs = new ArrayList<>();
		nodeStack.push(getTree());
		while(nodeStack.isEmpty() == false) {
			LDataTree<DialogNode> node = nodeStack.pop();
			for(LDataTree<DialogNode> child : node.children) {
				usedIDs.add(child.data.id);
				nodeStack.push(child);
			}
		}
		Collections.sort(usedIDs);
		int chosenID = 0;
		for(Integer id : usedIDs) {
			if (chosenID == id) {
				chosenID++;
			} else {
				break;
			}
		}
		return chosenID;
	}
	
	public DialogNode newNode() {
		DialogNode node = new DialogNode();
		node.name = "New Dialog Folder";
		node.id = findID();
		return node;
	}

	public DialogNode duplicateNode(DialogNode original) {
		DialogNode copy = gson.fromJson(gson.toJson(original), DialogNode.class);
		copy.name += "(copy)";
		copy.id = findID();
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
