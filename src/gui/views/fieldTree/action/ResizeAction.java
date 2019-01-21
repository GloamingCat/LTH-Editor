package gui.views.fieldTree.action;

import gui.views.fieldTree.FieldEditor;
import gui.views.fieldTree.FieldTreeEditor;
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
	
	public ResizeAction (int newW, int newH) {
		Field field = FieldEditor.instance.canvas.field;
		oldW = field.sizeX;
		oldH = field.sizeY;
		oldLayers = field.layers;
		this.newW = newW;
		this.newH = newH;
		oldLayers = field.layers;
		resize(oldLayers.terrain, newLayers.terrain);
		resize(oldLayers.obstacle, newLayers.obstacle);
		resize(oldLayers.region, newLayers.region);
		this.path = FieldTreeEditor.instance.fieldTree.getSelectedPath();
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
		FieldEditor.instance.canvas.field = null;
		FieldTreeEditor.instance.fieldTree.forceSelection(path);	
	}
	
	public void undo() {
		Field field = Project.current.fieldTree.loadField(path);
		field.sizeX = oldW;
		field.sizeY = oldH;
		field.layers = oldLayers;
		FieldEditor.instance.canvas.field = null;
		FieldTreeEditor.instance.fieldTree.forceSelection(path);
	}
	
}
