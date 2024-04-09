package gui.views.fieldTree.subcontent;

import gui.shell.field.LayerShell;
import gui.views.fieldTree.FieldEditor;
import gui.views.fieldTree.FieldTreeEditor;
import lbase.event.listener.LCollectionListener;
import lwt.container.LContainer;
import lbase.data.LDataList;
import lbase.data.LPath;
import lwt.dialog.LObjectWindow;
import lwt.dialog.LWindow;
import lwt.dialog.LWindowFactory;
import lwt.editor.LListEditor;
import lbase.event.LEditEvent;
import lbase.event.LInsertEvent;
import lbase.event.LMoveEvent;
import data.field.Field;
import data.field.Layer;
import data.field.Layer.Info;
import gson.GGlobals;

import java.util.function.Consumer;

public class LayerList extends LListEditor<Layer, Layer.Info> {

	public Consumer<Info> onEdit;
	public Consumer<Integer> onSelect;
	public Consumer<Layer> onCheck;

	private int fieldWidth, fieldHeight, maxHeight;

	/**
	 * @wbp.parser.constructor
	 * @wbp.eval.method.parameter parent new lwt.dialog.LShell()
	 * @wbp.eval.method.parameter type 0
	 */
	public LayerList(LContainer parent) {
		super(parent, true);
		setShellFactory(new LWindowFactory<>() {
			@Override
			public LObjectWindow<Info> createWindow(LWindow parent) {
				return new LayerShell(parent, maxHeight);
			}
		});
		getCollectionWidget().setEditEnabled(true);
		getCollectionWidget().setInsertNewEnabled(true);
		getCollectionWidget().setDuplicateEnabled(true);
		getCollectionWidget().setDeleteEnabled(true);
		getCollectionWidget().setDragEnabled(true);
		getCollectionWidget().setCopyEnabled(true);
		getCollectionWidget().setPasteEnabled(true);
		getCollectionWidget().addEditListener(new LCollectionListener<>() {
			public void onEdit(LEditEvent<Info> e) {
				if (onEdit != null)
					onEdit.accept(e.newData);
			}
		});
		getCollectionWidget().addSelectionListener(event -> {
            Layer l = (Layer) event.data;
            if (event.check) {
                l.visible = !l.visible;
				if (onCheck != null)
					onCheck.accept(l);
            } else {
				if (onSelect != null)
					onSelect.accept(event.path == null ? -1 : event.path.index);
            }
        });
		LCollectionListener<Layer> listener = new LCollectionListener<>() {
			public void onInsert(LInsertEvent<Layer> event) {
				getCollectionWidget().setChecked(new LPath(event.index), 
					event.node.data.visible);
			}
			public void onMove(LMoveEvent<Layer> event) {
				int index = event.destIndex == -1 ? getDataCollection().size() - 1 : event.destIndex;
				getCollectionWidget().setChecked(new LPath(index), 
						event.sourceNode.data.visible);
			}
		};
		getCollectionWidget().addInsertListener(listener);
		getCollectionWidget().addMoveListener(listener);
	}

	public void setField(Field field, int lastLayer) {
		if (field == null) {
			setObject(null);
		} else {
			fieldWidth = field.sizeX;
			fieldHeight = field.sizeY;
			maxHeight = field.prefs.maxHeight;
			if (lastLayer >= 0)
				getCollectionWidget().select(new LPath(lastLayer));
			for (LPath path = new LPath(0); path.index < getDataCollection().size(); path.index++)
				getCollectionWidget().setChecked(path, getDataCollection().get(path.index).visible);
		}
	}
	
	@Override
	protected LDataList<Layer> getDataCollection() {
		return (LDataList<Layer>) currentObject;
	}
	@Override
	protected Layer createNewElement() {
		return new Layer(fieldWidth, fieldHeight);
	}
	@Override
	protected Layer duplicateElement(Layer original) {
		return new Layer(original);
	}
	@Override
	protected String encodeElement(Layer data) {
		return GGlobals.gson.toJson(data);
	}
	@Override
	protected Layer decodeElement(String str) {
		return GGlobals.gson.fromJson(str, Layer.class);
	}
	@Override
	public boolean canDecode(String str) {
		return true;
	}
	
	@Override
	protected Info getEditableData(LPath path) {
		return getDataCollection().get(path.index).info;
	}
	@Override
	protected void setEditableData(LPath path, Info newData) {
		getDataCollection().get(path.index).info = newData;
	}
	
	public Layer getLayer() {
		return getCollectionWidget().getSelectedObject();
	}

}
