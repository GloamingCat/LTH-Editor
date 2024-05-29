package gui.views.fieldTree;

import data.field.CharTile;
import data.field.Field;
import gui.Vocab;
import gui.shell.field.ResizeDialog;
import gui.views.fieldTree.action.ResizeAction;

import java.util.function.Consumer;

import gui.views.fieldTree.action.TransformAction;
import lui.container.LContainer;
import lui.container.LToolBar;
import lui.dialog.LObjectDialog;
import lui.dialog.LWindow;
import lui.dialog.LWindowFactory;
import lui.graphics.LRect;

public class FieldToolBar extends LToolBar {

	public Consumer<Integer> onSelectTool;
	public Consumer<Integer> onSelectEditor;
	public Consumer<Boolean> onShowGrid;

	public Consumer<LRect> onResize;

	private Field field;

	public FieldToolBar(LContainer parent) {
		super(parent);

		Consumer<Integer> selectTool = i -> {
			if (onSelectTool != null)
				onSelectTool.accept(i);
		};

		addItem(selectTool, FieldCanvas.PENCIL, "img/pencil.png", true);
		addItem(selectTool, FieldCanvas.BUCKET, "img/bucket.png", false);
		addItem(selectTool, FieldCanvas.ERASER, "img/eraser.png", false);

		addSeparator();

		Consumer<Integer> selectEditor = i -> {
			if (onSelectEditor != null)
				onSelectEditor.accept(i);
		};

		addItem(selectEditor, FieldSideEditor.TERRAIN, "img/terrain.png", true);
		addItem(selectEditor, FieldSideEditor.OBSTACLE, "img/object.png", false);
		addItem(selectEditor, FieldSideEditor.REGION, "img/region.png", false);
		addItem(selectEditor, FieldSideEditor.CHAR, "img/character.png", false);
		addItem(selectEditor, FieldSideEditor.PARTY, "img/party.png", false);

		addSeparator();

		Consumer<Boolean> showGrid = v -> {
			if (onShowGrid != null)
				onShowGrid.accept(v);
		};

		addCheckItem(showGrid, Vocab.instance.SHOWGRID, true);

		addSeparator();

		Consumer<Boolean> resizeField = v -> {
			if (field != null)
				resizeField();
		};

		addButtonItem(resizeField, null, Vocab.instance.RESIZE);

		Consumer<Integer> transformField = v -> {
			if (field != null) {
				TransformAction action = new TransformAction(this, v);
				getActionStack().newAction(action);
				transformField(v);
			}
		};

		addListItem(transformField, new Integer[] {0, 1, 2, 3, 4}, Vocab.instance.TRANSFORMFIELD,
				new String[] { Vocab.instance.TRANSPOSE, Vocab.instance.INVERTX, Vocab.instance.INVERTY,
				 Vocab.instance.ROTATE90, Vocab.instance.ROTATE270 }
		);
	}

	public void resizeField() {
		LRect size = new LRect(0, 0, field.sizeX,
				field.sizeY);
		LWindowFactory<LRect> factory = new LWindowFactory<>() {
			@Override
			public LObjectDialog<LRect> createWindow(LWindow parent) {
				return new ResizeDialog(parent);
			}
		};
		size = factory.openWindow(getWindow(), size);
		if (size != null) {
			LRect oldSize = new LRect(-size.x, -size.y, field.sizeX, field.sizeY);
			Field.Layers layers = field.resizeLayers(size.width, size.height, size.x, size.y);
			ResizeAction action = new ResizeAction(this, oldSize, field.layers, size, layers);
			getActionStack().newAction(action);
			resizeField(size, layers);
		}
	}

	public void resizeField(LRect newSize, Field.Layers newLayers) {
		field.sizeX = newSize.width;
		field.sizeY = newSize.height;
		field.layers = newLayers;
		for (CharTile t : field.characters) {
			t.x -= newSize.x;
			t.y -= newSize.y;
		}
		if (onResize != null)
			onResize.accept(newSize);
	}

	public void transformField(int op) {
		field.layers = field.transformLayers(op);
		if (op == 0 || op >= 3) {
			int sizeX = field.sizeX;
			field.sizeX = field.sizeY;
			field.sizeY = sizeX;
		}
		for (CharTile c : field.characters) {
			if (op == 0 || op >= 3) {
				int t = c.x;
				c.x = c.y;
				c.y = t;
			}
			if (op == 1 || op == 3) {
				c.x = field.sizeX - c.x + 1;
			} else if (op == 2 || op == 4) {
				c.y = field.sizeY - c.y + 1;
			}
		}
		if (onResize != null)
			onResize.accept(null);
	}

	public void setField(Field field) {
		this.field = field;
	}

}
