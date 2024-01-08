package gui.shell;

import java.io.File;
import java.util.ArrayList;

import lwt.container.LSashPanel;
import lwt.dialog.LObjectShell;
import lwt.dialog.LShell;
import lwt.widget.LFlatList;

public abstract class FileShell<T> extends LObjectShell<T> {

	protected String folder;
	protected LSashPanel sashForm;
	protected LFlatList list;
	protected boolean optional;
	
	public static final int OPTIONAL = 0x01;
	
	/**
	 * @wbp.parser.constructor
	 */
	public FileShell(LShell parent, String title, int style) {
		super(parent, title, style);
	}
	
	@Override
	protected void createContent(int style) {
		super.createContent(style);
		this.optional = (style & OPTIONAL) > 0;
		content.setFillLayout(true);
		sashForm = new LSashPanel(content, true);
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
		ArrayList<String> list = new ArrayList<String>();
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
		for (File entry : f.listFiles()) {
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
