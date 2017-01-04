package gui.views.fieldTree.action;

import gui.views.fieldTree.FieldCanvas;
import gui.views.fieldTree.FieldEditor;
import project.Project;
import lwt.action.LAction;
import lwt.dataestructure.LPath;
import lwt.editor.LState;
import lwt.editor.LTreeEditor;
import data.Field;
import data.Layer;

public class ResizeAction implements LAction {

	private Field oldField;
	private Field newField;
	private LPath path;
	private FieldEditor editor;
	
	public ResizeAction (FieldEditor editor, int newW, int newH) {
		oldField = editor.canvas.field;
		newField = new Field(oldField.id, newW, newH);
		newField.prefs = oldField.prefs;
		newField.layers.clear();
		for(Layer l : oldField.layers) {
			Layer newLayer = new Layer(newW, newH);
			for(int i = 0; i < newW; i++) {
				for(int j = 0; j < newH; j++) {
					if (i < oldField.sizeX && j < oldField.sizeY) {
						newLayer.grid[i][j] = l.grid[i][j];
					} else {
						newLayer.grid[i][j] = -1;
					}
				}
			}
			newField.layers.add(newLayer);
			newLayer.info = l.info;
			newLayer.visible = l.visible;
		}
		this.editor = editor;
		this.path = editor.treeEditor.getCollectionWidget().getSelectedPath();
	}
	
	public void redo() {
		Project.current.fieldTree.setField(path, newField);
		editor.canvas.field = null;
		editor.treeEditor.getCollectionWidget().forceSelection(path);	
	}
	
	public void undo() {
		Project.current.fieldTree.setField(path, oldField);
		editor.canvas.field = null;
		editor.treeEditor.getCollectionWidget().forceSelection(path);
	}
	
}
