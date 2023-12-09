package gui;

import gui.views.database.DatabaseEditor;
import gui.views.fieldTree.FieldTreeEditor;
import gui.views.system.SystemEditor;

import project.Project;
import lwt.LDefaultApplicationShell;
import lwt.dataserialization.LFileManager;
import lwt.dataserialization.LSerializer;

public class ApplicationShell extends LDefaultApplicationShell {
	
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

	public ApplicationShell(String folder) {
		super(1200, 700, "LTH Editor", "/img/icon.png");
		setMinimumSize(800, 600);
		
		FieldTreeEditor fieldTreeEditor = new FieldTreeEditor(this);
		DatabaseEditor databaseEditor = new DatabaseEditor(this);
		SystemEditor systemEditor = new SystemEditor(this);
		addView(fieldTreeEditor, Vocab.instance.FIELDEDITOR, "F2");
		addView(databaseEditor, Vocab.instance.DATABASEEDITOR, "F3");
		addView(systemEditor, Vocab.instance.SYSTEMEDITOR, "F4");
		
		defaultView = fieldTreeEditor;
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
