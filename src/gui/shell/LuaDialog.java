package gui.shell;

import lui.dialog.LObjectDialog;
import lui.dialog.LWindow;
import lui.widget.LFileSelector;
import project.Project;

public class LuaDialog extends LObjectDialog<String> {
	
	private LFileSelector selFile;
	
	public static final int OPTIONAL = 0x01;
	
	/**
	 * @wbp.parser.constructor
	 */
	public LuaDialog(LWindow parent, String title, int style) {
		super(parent, style, title);
		pack();
	}
	
	@Override
	protected void createContent(int style) {
		super.createContent(style);
		content.setFillLayout(true);
		selFile = new LFileSelector(content, (style & OPTIONAL) > 0);
		selFile.addFileRestriction( f -> f.getName().endsWith(".lua") );
		selFile.setFolder(Project.current.scriptPath());
	}

	@Override
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
