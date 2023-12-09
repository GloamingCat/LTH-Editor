package gui.shell;

import gui.widgets.FileSelector;

import lwt.dialog.LObjectShell;
import lwt.dialog.LShell;

import project.Project;

public class LuaShell extends LObjectShell<String> {
	
	private FileSelector selFile;
	
	/**
	 * @wbp.parser.constructor
	 */
	public LuaShell(LShell parent) {
		this(parent, true);
	}
	
	public LuaShell(LShell parent, boolean optional) {
		super(parent);
		content.setFillLayout(true);
		selFile = new FileSelector(content, optional);
		selFile.addFileRestriction( (f) -> { return f.getName().endsWith(".lua"); } );
		selFile.setFolder(Project.current.scriptPath());
	}
	
	public void open(String initial) {
		super.open(initial);
		selFile.setSelectedFile(initial);
	}
	
	@Override
	protected String createResult(String initial) {
		String path = selFile.getSelectedFile();
		if (initial.equals(path))
			return null;
		return path;
	}
}
