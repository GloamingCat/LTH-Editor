package gui.shell;

import lwt.container.LControlView;
import lwt.dialog.LObjectShell;
import lwt.dialog.LShell;
import lwt.editor.LEditor;
import lwt.widget.LControlWidget;

import java.lang.reflect.Type;

import com.google.gson.Gson;
import com.google.gson.JsonElement;

import gson.editor.GDefaultObjectEditor;

public class ObjectShell<T> extends LObjectShell<T> {
	
	public GDefaultObjectEditor<T> contentEditor;
	private static Gson gson = new Gson();

	public ObjectShell(LShell parent, int width, int height) {
		this(parent);
		setMinimumSize(width, height);
	}
	
	public ObjectShell(LShell parent) {
		super(parent);
		content.setExpand(true, true);
		contentEditor = new GDefaultObjectEditor<T>(content, false) {
			@Override
			public Type getType() {
				return null;
			}
		};
		contentEditor.createActionStack();
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
		return (T) contentEditor.getObject();
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
