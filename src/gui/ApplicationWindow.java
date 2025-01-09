package gui;

import gui.views.database.DatabaseEditor;
import gui.views.fieldTree.FieldTreeEditor;
import gui.views.system.SystemEditor;
import lui.LovelyTheme;
import lui.base.serialization.LFileManager;
import lui.base.serialization.LSerializer;
import project.Project;
import lui.LApplicationWindow;

public class ApplicationWindow extends LApplicationWindow {

	public static ApplicationWindow current;

	public static void main(String[] args) {
		try {
			ApplicationWindow.setTheme(new LovelyTheme());
			current = new ApplicationWindow();
			current.run(args);
		} catch (Exception e) {
			LFileManager.log(e);
			e.printStackTrace();
		}
	}

	public ApplicationWindow() {
		super(800, 700, "img/icon.png");
		setCurrentSize(1200, 800);
		setContinuousLayout(false);
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
		if (path == null)
			return null;
		int i = path.lastIndexOf(".");
		if (i >= 0)
			path = path.substring(0, i);
		return new Project(path);
	}

}
