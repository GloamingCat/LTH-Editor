package gui.shell;

import org.eclipse.swt.widgets.Shell;

import lwt.dialog.LObjectShell;
import lwt.editor.LControlView;
import lwt.editor.LEditor;
import lwt.editor.LObjectEditor;
import lwt.widget.LControl;

import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.layout.GridData;

import com.google.gson.Gson;
import com.google.gson.JsonElement;

public class ObjectShell<T> extends LObjectShell<T> {
	
	public LObjectEditor contentEditor;
	private static Gson gson = new Gson();

	public ObjectShell(Shell parent) {
		super(parent);
		content.setLayout(new FillLayout());
		content.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true, 1, 1));
		contentEditor = new LObjectEditor(content, SWT.NONE);
		contentEditor.createActionStack();
	}
	
	@SuppressWarnings("unchecked")
	public void open(T initial) {
		super.open(initial);
		T copy = (T) gson.fromJson(gson.toJson(initial), initial.getClass());
		contentEditor.setObject(copy);
	}

	@SuppressWarnings("unchecked")
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
	
	protected void addControl(LControl<?> control, String attName) {
		contentEditor.addControl(control, attName);
	}
	
	protected void addControl(LControlView<?> view, String attName) {
		contentEditor.addControl(view, attName);
	}
	
}
