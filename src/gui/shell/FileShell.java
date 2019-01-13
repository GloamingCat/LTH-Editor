package gui.shell;

import java.io.File;
import java.util.ArrayList;

import lwt.dialog.LObjectShell;

import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.SashForm;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.widgets.List;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.layout.FillLayout;

public abstract class FileShell<T> extends LObjectShell<T> {

	protected String folder;
	protected SashForm sashForm;
	protected List list;
	protected boolean optional;
	
	public FileShell(Shell parent, int style) {
		this(parent, "", true);
	}
	
	/**
	 * @wbp.parser.constructor
	 */
	public FileShell(Shell parent, String folder, boolean optional) {
		super(parent);
		this.optional = optional;
		this.folder = folder;
		GridData gridData = (GridData) content.getLayoutData();
		gridData.verticalAlignment = SWT.FILL;
		gridData.grabExcessVerticalSpace = true;
		content.setLayout(new FillLayout(SWT.HORIZONTAL));
		
		sashForm = new SashForm(content, SWT.NONE);
		
		list = new List(sashForm, SWT.BORDER | SWT.V_SCROLL);
		list.setItems(getItems(folder + "/", optional));
	}
	
	protected boolean nullSelected() {
		return optional && list.getSelectionIndex() == 0;
	}

	protected int indexOf(String path) {
		int i = 0;
		if (path == null) {
			System.out.println("Null path");
			return -1;
		}
		for(String s : list.getItems()) {
			if (path.equals(folder + "/" + s)) {
				return i;
			}
			i++;
		}
		return -1;
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
	
	protected abstract boolean isValidFile(File entry);
	protected abstract String rootPath();

}
