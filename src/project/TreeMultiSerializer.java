package project;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Stack;

import lwt.dataestructure.LDataTree;
import data.Node;

public abstract class TreeMultiSerializer<DataT, CollectionT> 
		extends GMultiSerializer<Node, DataT, CollectionT> {
	
	public TreeMultiSerializer(String folder, String fileName,
			Class<CollectionT> collectionType, Class<DataT> dataType) {
		super(folder, fileName, collectionType, dataType);
	}

	@Override
	public String toFileName(Node node) {
		return "" + node.id;
	}
	
	public abstract DataT newData(Node node);
	public abstract DataT duplicateData(Node node, DataT original);
	public abstract LDataTree<Node> getRoot();
	
	public boolean load() {
		if (super.load()) {
			getRoot().restoreParents();
			return true;
		}
		return false;
	}
	
	public Node newNode() {
		Node node = new Node();
		node.name = "New Dialog Folder";
		node.id = findID();
		loadedData.put(node, newData(node));
		return node;
	}
	
	public Node duplicateNode(Node node) {
		Node copy = new Node();
		copy.name = node.name;
		copy.id = findID();
		DataT data = loadData(node);
		loadedData.put(copy, duplicateData(copy, data));
		return copy;
	}
	
	public int findID() {
		Stack<LDataTree<Node>> nodeStack = new Stack<>();
		ArrayList<Integer> usedIDs = new ArrayList<>();
		nodeStack.push(getRoot());
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
	
	
}
