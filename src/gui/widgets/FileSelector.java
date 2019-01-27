package gui.widgets;

import java.io.File;

import lwt.dataestructure.LDataTree;
import lwt.dataestructure.LPath;
import lwt.widget.LNodeSelector;

import org.eclipse.swt.widgets.Composite;

public abstract class FileSelector extends LNodeSelector<String> {

	protected LDataTree<String> root;
	protected String folder;
	
	/**
	 * Create the composite.
	 * @param parent
	 * @param style
	 */
	public FileSelector(Composite parent, int style) {
		super(parent, style);
	}
	
	public void setFolder(String folder) {
		this.folder = folder;
		root = new LDataTree<String>(folder.substring(0, folder.length() - 1));
		setFiles(root, rootPath() + folder);
		setCollection(root);
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

	public String getSelectedFile() {
		LPath path = getSelectedPath();
		if (path == null)
			return null;
		LDataTree<String> node = root.getNode(path);
		if (node == null || node.id == -1)
			return null;
		String file = node.data;
		node = node.parent;
		while (node != null) {
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
		for (int i = 1; i < names.length; i++) {
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

	protected abstract boolean isValidFile(File entry);
	protected abstract String rootPath();

}
