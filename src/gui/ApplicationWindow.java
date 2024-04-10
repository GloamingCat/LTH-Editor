package gui;

import gui.views.database.DatabaseEditor;
import gui.views.fieldTree.FieldTreeEditor;
import gui.views.system.SystemEditor;
import lui.base.serialization.LFileManager;
import lui.base.serialization.LSerializer;
import project.Project;
import lui.LApplicationWindow;

public class ApplicationWindow extends LApplicationWindow {

	public static void main(String[] args) {
		try {
			ApplicationWindow shell = new ApplicationWindow(args);
			shell.run();
		} catch (Exception e) {
			LFileManager.log(e);
			e.printStackTrace();
		}
	}

	public ApplicationWindow(String... args) {
		super(1200, 700, "/img/icon.png", args);
		setMinimumSize(800, 600);
	}

	@Override
	protected void createViews() {
		FieldTreeEditor fieldTreeEditor = new FieldTreeEditor(stack);
		DatabaseEditor databaseEditor = new DatabaseEditor(stack);
		SystemEditor systemEditor = new SystemEditor(stack);
		addView(fieldTreeEditor, Vocab.instance.FIELDEDITOR, "F2");
		addView(databaseEditor, Vocab.instance.DATABASEEDITOR, "F3");
		addView(systemEditor, Vocab.instance.SYSTEMEDITOR, "F4");

		defaultView = fieldTreeEditor;
	}

	@Override
	public String getApplicationName() {
		return "LTH Editor";
	}

	@Override
	public String getProjectExtension() {
		return "json";
	}

	@Override
	public LSerializer createProject(String path) {
		int i = path.lastIndexOf(".");
		if (i >= 0)
			path = path.substring(0, i);
		return new Project(path);
	}

}
