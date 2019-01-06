package gui.shell;

import gui.widgets.ImageButton;
import gui.widgets.QuadButton;
import gui.widgets.LuaButton;

import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Text;

import lwt.dialog.LObjectShell;
import lwt.editor.LControlView;
import lwt.editor.LEditor;
import lwt.editor.LObjectEditor;
import lwt.widget.LControl;
import lwt.widget.LImage;

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
		content.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1));
		contentEditor = new LObjectEditor(content, SWT.NONE);
		contentEditor.createActionStack();
	}
	
	@SuppressWarnings("unchecked")
	public void open(T initial) {
		super.open(initial);
		T copy = (T) gson.fromJson(gson.toJson(initial), initial.getClass());
		System.out.println(initial);
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
	
	protected void addImageButton(ImageButton button, LImage label, Text text, String attName) {
		button.setLabel(label);
		button.setNameText(text);
		addControl(button, attName);
	}
	
	protected void addQuadButton(QuadButton button, LImage image, String folderName, String attName) {
		button.setImage(image);
		button.setFolder(folderName);
		addControl(button, attName);
	}
	
	protected void addScriptButton(LuaButton button, Text pathText, String folderName, String attName) {
		button.setPathText(pathText);
		button.setFolder(folderName);
		addControl(button, attName);
	}
	
}
