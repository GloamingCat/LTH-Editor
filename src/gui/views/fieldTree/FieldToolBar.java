package gui.views.fieldTree;

import gui.Vocab;
import gui.shell.field.ResizeShell;
import gui.views.fieldTree.action.ResizeAction;

import java.util.function.Consumer;

import lwt.LFlags;
import lwt.container.LContainer;
import lwt.container.LToolBar;
import lwt.dialog.LObjectShell;
import lwt.dialog.LShell;
import lwt.dialog.LShellFactory;
import lwt.graphics.LRect;

public class FieldToolBar extends LToolBar {

	// External
	public static FieldToolBar instance;

	public FieldToolBar(LContainer parent) {
		super(parent);
		instance = this;
		setCurrentSize(440, 0);

		Consumer<Integer> selectTool = new Consumer<Integer>() {
			@Override
			public void accept(Integer t) {
				onSelectTool(t);
			}
		};

		addItem(selectTool, 0, "/img/pencil.png", true);
		addItem(selectTool, 1, "/img/bucket.png", false);
		addItem(selectTool, 2, "/img/eraser.png", false);

		addSeparator();

		Consumer<Integer> selectEditor = new Consumer<Integer>() {
			@Override
			public void accept(Integer t) {
				onSelectEditor(t);
			}
		};

		addItem(selectEditor, 0, "/img/terrain.png", true);
		addItem(selectEditor, 1, "/img/object.png", false);
		addItem(selectEditor, 2, "/img/region.png", false);
		addItem(selectEditor, 3, "/img/character.png", false);
		addItem(selectEditor, 4, "/img/party.png", false);

		addSeparator();

		Consumer<Boolean> showGrid = new Consumer<Boolean>() {
			@Override
			public void accept(Boolean v) {
				onShowGrid(v);
			}
		};

		addCheckItem(showGrid, Vocab.instance.SHOWGRID, true);

		addSeparator();
		
		Consumer<Object> resize = new Consumer<Object>() {
			@Override
			public void accept(Object v) {
				onResize();
			}
		};

		addButtonItem(resize, null, Vocab.instance.RESIZE);

		pack();
	}

	public void onSelectEditor(int i) {
		FieldSideEditor.instance.selectEditor(i);
	}

	public void onSelectTool(int i) {
		FieldEditor.instance.canvas.setTool(i);
	}

	public void onShowGrid(boolean i) {
		FieldEditor.instance.canvas.setShowGrid(i);
	}

	public void onResize() {
		LRect size = new LRect(LFlags.LEFT, LFlags.TOP, FieldSideEditor.instance.field.sizeX,
				FieldSideEditor.instance.field.sizeY);
		LShellFactory<LRect> factory = new LShellFactory<LRect>() {
			@Override
			public LObjectShell<LRect> createShell(LShell parent) {
				return new ResizeShell(parent);
			}
		};
		size = factory.openShell(getShell(), size);
		if (size != null) {
			resizeField(size);
		}
	}

	private void resizeField(LRect size) {
		ResizeAction action = new ResizeAction(size.width, size.height, size.x, size.y);
		FieldEditor.instance.canvas.getActionStack().newAction(action);
		action.redo();
	}

}
