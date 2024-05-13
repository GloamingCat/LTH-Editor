package gui.shell;

import java.io.File;
import java.util.ArrayList;

import lui.container.LFlexPanel;
import lui.dialog.LObjectDialog;
import lui.dialog.LWindow;
import lui.widget.LFlatList;

public abstract class FileListDialog<T> extends LObjectDialog<T> {

	protected String folder;
	protected LFlexPanel sashForm;
	protected LFlatList list;
	protected boolean optional;
	
	public static final int OPTIONAL = 0x01;
	
	/**
	 * @wbp.parser.constructor
	 */
	public FileListDialog(LWindow parent, String title, int style) {
		super(parent, style, title);
	}
	
	@Override
	protected void createContent(int style) {
		super.createContent(style);
		this.optional = (style & OPTIONAL) > 0;
		content.setFillLayout(true);
		sashForm = new LFlexPanel(content, true);
		list = new LFlatList(sashForm, optional);
	}
	
	public void setFolder(String folder) {
		this.folder = folder;
		list.setItems(getItems(folder + "/", optional));
	}
	
	protected boolean nullSelected() {
		return optional && list.getValue() == -1;
	}
	
	protected String[] getItems(String folder, boolean optional) {
		ArrayList<String> list = new ArrayList<>();
		if (optional) {
			list.add("");
		}
		readFiles(folder, list, "");
		String[] array = new String[list.size()];
		for(int i = 0; i < array.length; i++) {
			array[i] = list.get(i);
		}
		return array;
	}
	
	protected void readFiles(String folder, ArrayList<String> items, String path) {
		File f = new File(rootPath() + folder + "/" + path);
		if (!f.exists())
			return;
		File[] entries = f.listFiles();
		if (entries == null)
			return;
		for (File entry : entries) {
			if (entry.isDirectory()) {
				readFiles(folder, items, path + entry.getName() + "/");
			} else {
				if (isValidFile(entry))
					items.add(path + entry.getName());
			}
		}
	}
	
	protected String getSelectedPath() {
		return folder + list.getSelectedText();
	}
	
	protected abstract boolean isValidFile(File entry);
	protected abstract String rootPath();

}
