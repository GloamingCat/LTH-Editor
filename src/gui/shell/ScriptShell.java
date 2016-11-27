package gui.shell;

import gui.Vocab;

import java.io.File;
import java.util.ArrayList;

import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.StyledText;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.widgets.Group;
import org.eclipse.swt.widgets.List;
import org.eclipse.swt.widgets.Shell;

import project.Project;
import lwt.dialog.LObjectShell;

import org.eclipse.swt.layout.GridData;

import data.Script;

public class ScriptShell extends LObjectShell<Script> {

	private String folder;
	private List list;
	private StyledText txtParam;
	
	public ScriptShell(Shell parent, String folder) {
		super(parent);
		
		this.folder = folder;
		GridData gridData = (GridData) content.getLayoutData();
		gridData.verticalAlignment = SWT.FILL;
		gridData.grabExcessVerticalSpace = true;
		content.setLayout(new FillLayout());
		list = new List(content, SWT.BORDER | SWT.V_SCROLL);
		list.setItems(getItems(folder + "/"));
		
		Group grpParam = new Group(content, SWT.BORDER);
		grpParam.setText(Vocab.instance.PARAM);
		grpParam.setLayout(new FillLayout());
		txtParam = new StyledText(grpParam, SWT.BORDER);
	}
	
	public void open(Script initial) {
		super.open(initial);
		txtParam.setText(initial.param);
		int i = indexOf(initial.path);
		list.select(i);
	}
	
	private int indexOf(String path) {
		int i = 0;
		for(String s : list.getItems()) {
			if (path.equals(folder + "/" + s)) {
				return i;
			}
			i++;
		}
		return -1;
	}

	@Override
	protected Script createResult(Script initial) {
		int i = list.getSelectionIndex();
		if (i >= 0) {
			String newValue = folder + "/" + list.getItem(i);
			if (newValue.equals(initial.path) && txtParam.getText().equals(initial.param)) {
				return null;
			} else {
				Script s = new Script();
				s.path = newValue;
				s.param = txtParam.getText();
				return s;
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
	
	private void readFiles(String folder, ArrayList<String> items, String path) {
		File f = new File(Project.current.scriptPath() + folder + "/" + path);
		if (!f.exists())
			return;
		for (File entry : f.listFiles()) {
			if (entry.isDirectory()) {
				readFiles(folder, items, path + entry.getName() + "/");
			} else {
				if (entry.getName().endsWith(".lua")) {
					items.add(path + entry.getName());
				}
			}
		}
	}

}
