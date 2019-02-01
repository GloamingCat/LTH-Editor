package gui.shell;

import gui.widgets.FileSelector;

import java.io.File;

import lwt.dialog.LObjectShell;

import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.layout.FillLayout;

import project.Project;

public class LuaShell extends LObjectShell<String> {
	
	private FileSelector selFile;
	
	/**
	 * @wbp.parser.constructor
	 */
	public LuaShell(Shell parent) {
		this(parent, 1);
	}
	
	public LuaShell(Shell parent, int optional) {
		super(parent);
		content.setLayout(new FillLayout(SWT.HORIZONTAL));
		selFile = new FileSelector(content, optional) {
			@Override
			protected boolean isValidFile(File f) {
				return f.getName().endsWith(".lua");
			}
		};
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
