package gui.widgets;

import java.io.File;
import java.util.ArrayList;
import java.util.function.Function;

import lwt.container.LContainer;
import lwt.dataestructure.LDataTree;
import lwt.widget.LNodeSelector;

public class FileSelector extends LNodeSelector<String> {

	protected LDataTree<String> root;
	protected ArrayList<Function<File, Boolean>> fileRestrictions = new ArrayList<>();
	
	/**
	 * Create the composite.
	 * @param parent
	 * @param style
	 */
	public FileSelector(LContainer parent, boolean optional) {
		super(parent, optional);
	}
	
	public void setFolder(String folder) {
		root = new LDataTree<String>(folder);
		setFiles(root, folder);
		setCollection(root);
	}
	
	public String getRootFolder() {
		return root.data;
	}
	
	protected void setFiles(LDataTree<String> tree, String path) {
		File f = new File(path);
		if (!f.exists())
			return;
		for (File entry : f.listFiles()) {
			if (entry.isDirectory()) {
				LDataTree<String> subFolder = new LDataTree<String>(entry.getName(), tree);
				setFiles(subFolder, path + entry.getName() + "/");
			} else if (isValidFile(entry)) {
				LDataTree<String> file = new LDataTree<String>(entry.getName(), tree);
				file.id = 0;
			}
		}
	}
	
	public String getFile(int id) {
		LDataTree<String> node = root.findNode(id);
		if (node == null || node.id == -1)
			return null;
		return node.data;
	}

	public String getSelectedFile() {
		LDataTree<String> node = getSelectedNode();
		if (node == null || node.id == -1)
			return "";
		String file = node.data;
		node = node.parent;
		while (node != root) {
			file = node.data + '/' + file;
			node = node.parent;
		}
		return file;
	}
	
	public void setSelectedFile(String file) {
		LDataTree<String> node = findNode(file);
		setValue(node == null ? null : node.toPath());
	}
	
	public LDataTree<String> findNode(String file) {
		String[] names = file.split("/");
		LDataTree<String> node = root;
		for (int i = 0; i < names.length; i++) {
			LDataTree<String> child = findChild(names[i], node);
			if (child == null)
				return null;
			node = child;
		}
		return node;
	}
	
	private LDataTree<String> findChild(String name, LDataTree<String> parent) {
		for (LDataTree<String> child : parent.children) {
			if (name.equals(child.data)) {
				return child;
			}
		}
		return null;
	}

	private boolean isValidFile(File entry) {
		for (var r : fileRestrictions) {
			if (!r.apply(entry)) {
				return false;
			}
		}
		return true;
	}
	
	public void addFileRestriction(Function<File, Boolean> r) {
		fileRestrictions.add(r);
	}
	
	public void removeFileRestriction(Function<File, Boolean> r) {
		fileRestrictions.remove(r);
	}

}
