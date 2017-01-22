package project;

import lwt.dataestructure.LDataTree;
import data.Dialogue;
import data.DialogueTree;
import data.Node;

public class DialogueTreeSerializer extends TreeMultiSerializer<Dialogue, DialogueTree> {
	
	public DialogueTreeSerializer(String path) {
		super(path + "dialogues/", "dialogueTree", DialogueTree.class, Dialogue.class);
	}

	@Override
	public Dialogue newData(Node node) {
		Dialogue dialogue = new Dialogue();
		dialogue.id = dialogue.id;
		node.name = dialogue.name;
		return dialogue;
	}
	
	@Override
	public Dialogue duplicateData(Node node, Dialogue original) {
		Dialogue copy = gson.fromJson(gson.toJson(original), Dialogue.class);
		copy.name += " (copy)";
		copy.id = node.id;
		node.name = copy.name;
		return copy;
	}
	
	@Override
	public LDataTree<Node> getRoot() {
		return data.root;
	}

}
