package gui.views.fieldTree;

import data.field.CharTile;
import data.field.Field;
import gui.Vocab;
import gui.shell.field.ResizeShell;
import gui.views.fieldTree.action.ResizeAction;

import java.util.function.Consumer;

import lui.container.LContainer;
import lui.container.LToolBar;
import lui.dialog.LObjectWindow;
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

		addItem(selectTool, 0, "/img/pencil.png", true);
		addItem(selectTool, 1, "/img/bucket.png", false);
		addItem(selectTool, 2, "/img/eraser.png", false);

		addSeparator();

		Consumer<Integer> selectEditor = i -> {
			if (onSelectEditor != null)
				onSelectEditor.accept(i);
		};

		addItem(selectEditor, 0, "/img/terrain.png", true);
		addItem(selectEditor, 1, "/img/object.png", false);
		addItem(selectEditor, 2, "/img/region.png", false);
		addItem(selectEditor, 3, "/img/character.png", false);
		addItem(selectEditor, 4, "/img/party.png", false);

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

		pack();
	}

	public void resizeField() {
		LRect size = new LRect(0, 0, field.sizeX,
				field.sizeY);
		LWindowFactory<LRect> factory = new LWindowFactory<>() {
			@Override
			public LObjectWindow<LRect> createWindow(LWindow parent) {
				return new ResizeShell(parent);
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
		//FieldTreeEditor.instance.fieldTree.forceSelection(null);
		field.sizeX = newSize.width;
		field.sizeY = newSize.height;
		field.layers = newLayers;
		for (CharTile t : field.characters) {
			t.x -= newSize.x;
			t.y -= newSize.y;
		}
		if (onResize != null)
			onResize.accept(newSize);
		//FieldTreeEditor.instance.fieldTree.forceSelection(path);
	}

	public void setField(Field field) {
		this.field = field;
	}

}
