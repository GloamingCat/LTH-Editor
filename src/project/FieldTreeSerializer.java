package project;

import lwt.dataestructure.LDataTree;
import lwt.dataestructure.LPath;
import data.Field;
import data.FieldTree;
import data.subcontent.Node;

public class FieldTreeSerializer extends TreeMultiSerializer<Field, FieldTree> {

	public FieldTreeSerializer(String folder) {
		super(folder + "fields/", "fieldTree", FieldTree.class, Field.class);
	}
	
	public Field loadField(LPath path) {
		LDataTree<Node> node = data.root.getNode(path);
		return loadData(node.data);
	}

	public void setField(LPath path, Field newField) {
		LDataTree<Node> node = data.root.getNode(path);
		newField.id = node.data.id;
		node.data.name = newField.prefs.name;
		loadedData.put(node.data, newField);
	}

	@Override
	public Field newData(Node node) {
		Field field = new Field(node.id, 15, 15);
		node.name = field.prefs.name;
		return field;
	}

	@Override
	public Field duplicateData(Node node, Field original) {
		Field newField = gson.fromJson(gson.toJson(original), Field.class);
		newField.id = node.id;
		node.name = node.name;
		node.name = newField.prefs.name;
		return newField;
	}

	@Override
	public LDataTree<Node> getRoot() {
		return data.root;
	}

}
