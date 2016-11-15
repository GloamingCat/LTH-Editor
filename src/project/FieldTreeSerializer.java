package project;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Stack;

import lwt.dataestructure.LDataTree;
import lwt.dataserialization.LObjectSerializer;
import data.Field;
import data.FieldNode;
import data.FieldTree;

public class FieldTreeSerializer extends TreeSerializer<FieldNode> {
	
	public FieldTreeSerializer(String path) {
		super(path, FieldTree.class);
	}
	
	public Field newField(LDataTree<Object> parent, int width, int height) {
		Stack<LDataTree<FieldNode>> nodeStack = new Stack<>();
		ArrayList<Integer> usedIDs = new ArrayList<>();
		nodeStack.push(root);
		while(nodeStack.isEmpty() == false) {
			LDataTree<FieldNode> node = nodeStack.pop();
			usedIDs.add(node.data.id);
			for(LDataTree<FieldNode> child : node.children) {
				nodeStack.push(child);
			}
		}
		Collections.sort(usedIDs);
		int chosenID = -1;
		for(Integer id : usedIDs) {
			if (chosenID == id) {
				chosenID++;
			} else {
				break;
			}
		}
		LObjectSerializer serializer = new ObjectSerializer(Project.current.fieldPath() + chosenID, Field.class);		
		Field field = new Field(chosenID, width, height);
		FieldNode newNode = new FieldNode(field.id, field.name);
		serializer.setData(newNode);
		serializers.put(newNode, serializer);
		return field;
	}

	@Override
	protected LObjectSerializer createNodeSerializer(FieldNode node) {
		ObjectSerializer s = new ObjectSerializer(Project.current.fieldPath() + node.id, Field.class);
		s.load();
		return s;
	}

}
