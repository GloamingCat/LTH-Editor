package gui.shell;

import java.io.File;

import org.eclipse.swt.widgets.Shell;

import project.Project;

public class ScriptShell extends FileShell<String> {
	
	/**
	 * @wbp.parser.constructor
	 */
	public ScriptShell(Shell parent) {
		this(parent, "", true);
	}
	
	public ScriptShell(Shell parent, String folder, boolean optional) {
		super(parent, folder, optional);
	}
	
	public void open(String initial) {
		super.open(initial);
		int i = indexOf(initial);
		list.select(i);
	}
	
	@Override
	protected String createResult(String initial) {
		int i = list.getSelectionIndex();
		if (i >= 0) {
			String newValue = list.getItem(i);
			if (!newValue.isEmpty())
				newValue = newValue.substring(0, newValue.length() - 4);
			if (newValue.equals(initial)) {
				return null;
			} else {
				return newValue;
			}
		} else {
			return null;
		}
	}

	protected boolean isValidFile(File f) {
		return f.getName().endsWith(".lua");
	}
	
	protected String rootPath() {
		return Project.current.scriptPath();
	}

}
