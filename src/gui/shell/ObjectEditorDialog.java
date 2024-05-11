package gui.shell;

import lui.container.LControlView;
import lui.dialog.LObjectDialog;
import lui.dialog.LWindow;
import lui.editor.LEditor;
import lui.gson.GDefaultObjectEditor;
import lui.widget.LControlWidget;

import java.lang.reflect.Type;

import com.google.gson.Gson;
import com.google.gson.JsonElement;

public class ObjectEditorDialog<T> extends LObjectDialog<T> {
	
	public GDefaultObjectEditor<T> contentEditor;
	private static final Gson gson = new Gson();

	public ObjectEditorDialog(LWindow parent, int style, String title) {
		super(parent, style, title);
		content.getCellData().setExpand(true, true);
		content.setFillLayout(true);
	}

	public ObjectEditorDialog(LWindow parent, String title) {
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
			@Override
			protected void createContent(int style) {}
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
