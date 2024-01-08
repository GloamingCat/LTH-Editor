package gui.shell;

import lwt.dialog.LObjectShell;
import lwt.dialog.LShell;
import lwt.widget.LFileSelector;
import project.Project;

public class LuaShell extends LObjectShell<String> {
	
	private LFileSelector selFile;
	
	public static final int OPTIONAL = 0x01;
	
	/**
	 * @wbp.parser.constructor
	 */
	public LuaShell(LShell parent, String title, int style) {
		super(parent, title, style);
	}
	
	@Override
	protected void createContent(int style) {
		content.setFillLayout(true);
		selFile = new LFileSelector(content, (style & OPTIONAL) > 0);
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
