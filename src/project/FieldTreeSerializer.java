package project;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Stack;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import lwt.dataestructure.LDataTree;
import lwt.dataestructure.LPath;
import lwt.dataserialization.LObjectSerializer;
import data.Field;
import data.Layer;
import data.FieldTree;
import data.Node;

public class FieldTreeSerializer extends LObjectSerializer<FieldTree> {
	
	private static Gson gson = new GsonBuilder().setPrettyPrinting().create();
	private HashMap<Integer, Field> loadedFields = new HashMap<>();
	private FieldNodeSerializer nodeSerializer = new FieldNodeSerializer();
	
	public FieldTreeSerializer(String path) {
		super(path + ".json", FieldTree.class);
	}

	@Override
	public boolean save() {
		if (!super.save())
			return false;
		return save(data.root);
	}
	
	protected boolean save(LDataTree<Node> treeNode) {
		for(LDataTree<Node> child : treeNode.children) {
			Field field = loadedFields.get(child.data.id);
			if (field != null) {
				if (!nodeSerializer.saveField(field))
					return false;
			}
			if (!save(child)) {
				return false;
			}
		}
		return true;
	}
	
	public Field loadField(LPath path) {
		LDataTree<Node> node = data.root.getNode(path);
		return loadField(node.data);
	}
	
	public Field loadField(Node node) {
		Field field = loadedFields.get(node.id);
		if (field == null) {
			field = nodeSerializer.loadField(node.id);
			loadedFields.put(node.id, field);
		}
		return field;
	}
	
	public Node newNode() {
		int chosenID = findID();
		Field field = new Field(chosenID, 15, 15);
		loadedFields.put(chosenID, field);
		Node node = new Node();
		node.id = chosenID;
		node.name = field.prefs.name;
		Layer l = new Layer(15, 15);
		field.layers.add(l);
		return node;
	}
	
	private int findID() {
		Stack<LDataTree<Node>> nodeStack = new Stack<>();
		ArrayList<Integer> usedIDs = new ArrayList<>();
		nodeStack.push(data.root);
		while(nodeStack.isEmpty() == false) {
			LDataTree<Node> node = nodeStack.pop();
			for(LDataTree<Node> child : node.children) {
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
	
	public Node duplicateNode(Node original) {
		Field field = Project.current.fieldTree.loadField(original);
		int chosenID = findID();

		Node node = new Node();
		node.id = chosenID;
		node.name = field.prefs.name;
		
		Field newField = gson.fromJson(gson.toJson(field), Field.class);
		newField.id = node.id;
		node.name = node.name;
		loadedFields.put(node.id, newField);
		return node;
	}

	@Override
	protected byte[] toByteArray(FieldTree obj) {
		return gson.toJson(data).getBytes();
	}

	@Override
	protected FieldTree fromByteArray(byte[] bytes) {
		return gson.fromJson(new String(bytes), FieldTree.class);
	}

}
