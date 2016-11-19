package gui.shell;

import java.io.File;

import java.util.ArrayList;

import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.widgets.List;
import org.eclipse.swt.widgets.Shell;

import project.Project;
import lwt.dialog.LObjectShell;

import org.eclipse.swt.layout.GridData;

public class AudioShell extends LObjectShell<String> {

	private String folder;
	private List list;
	
	public AudioShell(Shell parent, String folder) {
		super(parent);
		this.folder = folder;
		GridData gridData = (GridData) content.getLayoutData();
		gridData.verticalAlignment = SWT.FILL;
		gridData.grabExcessVerticalSpace = true;
		content.setLayout(new FillLayout());
		list = new List(content, SWT.BORDER | SWT.V_SCROLL);
		list.setItems(getItems(folder + "/"));
	}

	@Override
	protected String createResult(String initial) {
		int i = list.getSelectionIndex();
		if (i >= 0) {
			String newValue = folder + "/" + list.getItem(i);
			if (newValue.equals(initial)) {
				return null;
			} else {
				return newValue;
			}
		} else {
			return null;
		}
	}
	
	private String[] getItems(String folder) {
		ArrayList<String> list = new ArrayList<String>();
		readFiles(folder, list, "");
		String[] array = new String[list.size()];
		for(int i = 0; i < array.length; i++) {
			array[i] = list.get(i);
		}
		return array;
	}
	
	private boolean isAudioFile(String name) {
		return name.endsWith(".ogg") || name.endsWith(".mp3") || name.endsWith(".mid");
	}
	
	private void readFiles(String folder, ArrayList<String> items, String path) {
		File f = new File(Project.current.audioPath() + folder + "/" + path);
		if (!f.exists())
			return;
		for (File entry : f.listFiles()) {
			if (entry.isDirectory()) {
				readFiles(folder, items, path + entry.getName() + "/");
			} else {
				if (isAudioFile(entry.getName())) {	
					items.add(path + entry.getName());
				}
			}
		}
	}
	
	protected void checkSubclass() { }

}
