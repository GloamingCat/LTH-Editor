package gui.views.fieldTree.action;

import gui.views.fieldTree.FieldEditor;
import gui.views.fieldTree.FieldTreeEditor;
import project.Project;
import lwt.action.LAction;
import lwt.dataestructure.LDataList;
import lwt.dataestructure.LPath;
import data.field.CharTile;
import data.field.Field;
import data.field.Layer;

public class ResizeAction implements LAction {

	private int oldW;
	private int oldH;
	private int newW;
	private int newH;
	private int x0 = 0;
	private int y0 = 0;
	
	private Field.Layers oldLayers;
	private Field.Layers newLayers = new Field.Layers();
	
	private LPath path;
	
	public ResizeAction (int newW, int newH, int alignX, int alignY) {
		this.newW = newW;
		this.newH = newH;
		this.path = FieldTreeEditor.instance.fieldTree.getSelectedPath();
		Field field = FieldEditor.instance.canvas.field;
		oldW = field.sizeX;
		oldH = field.sizeY;
		oldLayers = field.layers;
		// X anchor
		if (alignX == 1) {
			x0 = (oldW - newW) / 2;
		} else if (alignX == 2) {
			x0 = oldW - newW;
		}
		// Y anchor
		if (alignY == 1) {
			y0 = (oldH - newH) / 2;
		} else if (alignY == 2) {
			y0 = oldH - newH;
		}
		// Create layers
		resize(oldLayers.terrain, newLayers.terrain);
		resize(oldLayers.obstacle, newLayers.obstacle);
		resize(oldLayers.region, newLayers.region);
	}

	private void resize(LDataList<Layer> layers, LDataList<Layer> newLayers) {
		for(Layer l : layers) {
			Layer newLayer = new Layer(newW, newH);
			for(int i = 0; i < newW; i++) {
				for(int j = 0; j < newH; j++) {
					int x = x0 + i, y = y0 + j;
					if (x < oldW && y < oldH && x >= 0 && y >= 0) {
						newLayer.grid[i][j] = l.grid[x][y];
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
		FieldTreeEditor.instance.fieldTree.forceSelection(null);
		Field field = Project.current.fieldTree.loadField(path);
		field.sizeX = newW;
		field.sizeY = newH;
		field.layers = newLayers;
		for (CharTile t : field.characters) {
			t.x -= x0;
			t.y -= y0;
		}
		FieldTreeEditor.instance.fieldTree.forceSelection(path);
	}
	
	public void undo() {
		FieldTreeEditor.instance.fieldTree.forceSelection(null);
		Field field = Project.current.fieldTree.loadField(path);
		field.sizeX = oldW;
		field.sizeY = oldH;
		field.layers = oldLayers;
		for (CharTile t : field.characters) {
			t.x += x0;
			t.y += y0;
		}
		FieldTreeEditor.instance.fieldTree.forceSelection(path);
	}
	
}
