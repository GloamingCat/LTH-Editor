package gui.shell;

import lwt.container.LControlView;
import lwt.dialog.LObjectWindow;
import lwt.dialog.LWindow;
import lwt.editor.LEditor;
import lwt.gson.GDefaultObjectEditor;
import lwt.widget.LControlWidget;

import java.lang.reflect.Type;

import com.google.gson.Gson;
import com.google.gson.JsonElement;

public class ObjectShell<T> extends LObjectWindow<T> {
	
	public GDefaultObjectEditor<T> contentEditor;
	private static final Gson gson = new Gson();

	public ObjectShell(LWindow parent, int style, String title) {
		super(parent, style, title);
		content.getCellData().setExpand(true, true);
	}

	public ObjectShell(LWindow parent, String title) {
		this(parent, 0, title);
	}
	
	@Override
	protected void createContent(int style) {
		super.createContent(style);
		contentEditor = new GDefaultObjectEditor<>(content, false) {
			@Override
			public Type getType() {
				return null;
			}
		};
		contentEditor.createMenuInterface();
	}
	
	@SuppressWarnings("unchecked")
	public void open(T initial) {
		super.open(initial);
		T copy = (T) gson.fromJson(gson.toJson(initial), initial.getClass());
		contentEditor.setObject(copy);
	}

	@Override
	protected T createResult(T initial) {
		JsonElement c = gson.toJsonTree(contentEditor.getObject());
		JsonElement i = gson.toJsonTree(initial);
		if (i.equals(c))
			return null;
		return contentEditor.getObject();
	}
	
	public void addChild(LEditor editor) {
		contentEditor.addChild(editor);
	}
	
	public void addChild(LEditor editor, String key) {
		contentEditor.addChild(editor, key);
	}
	
	public void removeChild(LEditor editor) {
		contentEditor.removeChild(editor);
	}
	
	protected void addControl(LControlWidget<?> control, String attName) {
		contentEditor.addControl(control, attName);
	}
	
	protected void addControl(LControlView<?> view, String attName) {
		contentEditor.addControl(view, attName);
	}
	
	protected void removeControl(LControlWidget<?> control) {
		contentEditor.removeControl(control);
	}
	
	protected void removeControl(LControlView<?> view) {
		contentEditor.removeControl(view);
	}
	
}
