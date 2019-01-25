package project;

import gson.project.GMultiSerializer;
import lwt.dataestructure.LPath;
import data.field.Field;
import data.field.FieldNode;
import data.field.FieldTree;

public class FieldTreeSerializer extends 
	GMultiSerializer<FieldNode, Field, FieldTree> {
	
	public FieldTreeSerializer(String folder) {
		super(folder, "tree", FieldTree.class, Field.class);
	}
	
	// Load

	@Override
	public String toFileName(FieldNode node, Field data) {
		return data.id + "";
	}
	
	public boolean load() {
		if (super.load()) {
			data.restoreParents();
			return true;
		}
		return false;
	}
	
	public Field loadField(FieldNode node) {
		Field field = loadedData.get(node);
		if (field == null) {
			int id = data.getFieldID(node);
			nodeSerializer.setPath(folder + id + ".json");
			if (nodeSerializer.load()) {
				field = nodeSerializer.getData();
			} else {
				field = new Field(id, 10, 10);
			}
			loadedData.put(node, field);
		}
		return field;
	}

	public Field loadField(LPath path) {
		return loadField(data.getNode(path).data);
	}
	
	// New
	
	public FieldNode newNode() {
		FieldNode node = new FieldNode();
		node.name = "New Dialog Folder";
		int id = data.findID();
		System.out.println(id);
		loadedData.put(node, newField(id, node));
		return node;
	}
	
	public Field newField(int id, FieldNode node) {
		Field field = new Field(id, 10, 10);
		node.name = field.prefs.name;
		return field;
	}
	
	// Duplicate
	
	public FieldNode duplicateNode(FieldNode node) {
		FieldNode copy = node.clone();
		copy.name = node.name;
		int id = data.findID();
		Field data = loadField(node);
		loadedData.put(copy, duplicateField(id, data));
		return copy;
	}

	public Field duplicateField(int id, Field original) {
		String json = gson.toJson(original, Field.class);
		Field newField = gson.fromJson(json, Field.class);
		newField.id = id;
		return newField;
	}

}
