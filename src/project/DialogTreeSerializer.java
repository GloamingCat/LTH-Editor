package project;

import lwt.dataestructure.LDataTree;
import data.Dialog;
import data.DialogTree;
import data.Node;

public class DialogTreeSerializer extends TreeMultiSerializer<Dialog, DialogTree> {
	
	public DialogTreeSerializer(String path) {
		super(path + "dialogs/", "dialogTree", DialogTree.class, Dialog.class);
	}

	@Override
	public Dialog newData(Node node) {
		Dialog dialog = new Dialog();
		dialog.id = dialog.id;
		node.name = dialog.name;
		return dialog;
	}
	
	@Override
	public Dialog duplicateData(Node node, Dialog original) {
		Dialog copy = gson.fromJson(gson.toJson(original), Dialog.class);
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
