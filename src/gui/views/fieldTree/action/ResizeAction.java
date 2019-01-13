package gui.views.fieldTree.action;

import gui.views.fieldTree.FieldEditor;
import project.Project;
import lwt.action.LAction;
import lwt.dataestructure.LDataList;
import lwt.dataestructure.LPath;
import data.field.Field;
import data.field.Layer;

public class ResizeAction implements LAction {

	private int oldW;
	private int oldH;
	private int newW;
	private int newH;
	
	private Field.Layers oldLayers;
	private Field.Layers newLayers = new Field.Layers();
	
	private LPath path;
	private FieldEditor editor;
	
	public ResizeAction (FieldEditor editor, int newW, int newH) {
		oldW = editor.canvas.field.sizeX;
		oldH = editor.canvas.field.sizeY;
		oldLayers = editor.canvas.field.layers;
		this.newW = newW;
		this.newH = newH;
		oldLayers = editor.canvas.field.layers;
		resize(oldLayers.terrain, newLayers.terrain);
		resize(oldLayers.obstacle, newLayers.obstacle);
		resize(oldLayers.region, newLayers.region);
		resize(oldLayers.party, newLayers.party);
		this.editor = editor;
		this.path = editor.treeEditor.getCollectionWidget().getSelectedPath();
	}
	
	private void resize(LDataList<Layer> layers, LDataList<Layer> newLayers) {
		for(Layer l : layers) {
			Layer newLayer = new Layer(newW, newH);
			for(int i = 0; i < newW; i++) {
				for(int j = 0; j < newH; j++) {
					if (i < oldW && j < oldH) {
						newLayer.grid[i][j] = l.grid[i][j];
					} else {
						newLayer.grid[i][j] = -1;
					}
				}
			}
			newLayers.add(newLayer);
			newLayer.info = l.info;
			newLayer.visible = l.visible;
		}
	}
	
	public void redo() {
		Field field = Project.current.fieldTree.loadField(path);
		field.sizeX = newW;
		field.sizeY = newH;
		field.layers = newLayers;
		editor.canvas.field = null;
		editor.treeEditor.getCollectionWidget().forceSelection(path);	
	}
	
	public void undo() {
		Field field = Project.current.fieldTree.loadField(path);
		field.sizeX = oldW;
		field.sizeY = oldH;
		field.layers = oldLayers;
		editor.canvas.field = null;
		editor.treeEditor.getCollectionWidget().forceSelection(path);
	}
	
}
