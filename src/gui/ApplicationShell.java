package gui;

import gui.views.database.DatabaseEditor;
import gui.views.fieldTree.FieldTreeEditor;
import gui.views.system.SystemEditor;

import project.Project;
import lwt.LDefaultApplicationShell;
import lwt.dataserialization.LFileManager;
import lwt.dataserialization.LSerializer;

public class ApplicationShell extends LDefaultApplicationShell {
	
	/**
	 * Launch the application.
	 * @param args
	 */
	public static void main(String args[]) {
		try {
			String folder = args.length > 0 ? args[0] : null;
			if (folder != null)
				System.out.println(folder);
			ApplicationShell shell = new ApplicationShell(folder);
			shell.run();
		} catch (Exception e) {
			LFileManager.log(e);
			e.printStackTrace();
		}
	}

	/**
	 * Create the shell.
	 * @param display
	 */
	public ApplicationShell(String folder) {
		super(1200, 700, "LTH Editor", "/img/icon.png");
		
		defaultView = new FieldTreeEditor(this);
		addView(defaultView, Vocab.instance.FIELDEDITOR, "F2");
		addView(new DatabaseEditor(this), Vocab.instance.DATABASEEDITOR, "F3");
		addView(new SystemEditor(this), Vocab.instance.SYSTEMEDITOR, "F4");
		
		loadDefault(folder);
	}
	
	@Override
	protected LSerializer createProject(String path) {
		int i = path.lastIndexOf(".");
		if (i >= 0)
			path = path.substring(0, i);
		return new Project(path);
	}

}
