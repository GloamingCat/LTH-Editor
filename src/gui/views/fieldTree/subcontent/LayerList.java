package gui.views.fieldTree.subcontent;

import gui.shell.field.LayerDialog;
import lui.base.event.listener.LCollectionListener;
import lui.container.LContainer;
import lui.base.data.LDataList;
import lui.base.data.LPath;
import lui.dialog.LObjectDialog;
import lui.dialog.LWindow;
import lui.dialog.LWindowFactory;
import lui.editor.LListEditor;
import lui.base.event.LEditEvent;
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
			public LObjectDialog<Info> createWindow(LWindow parent) {
				return new LayerDialog(parent, maxHeight);
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
		getCollectionWidget().addCheckListener(event -> {
			if (onCheck != null) {
				Layer l = (Layer) event.data;
				onCheck.accept(l);
			}
		});
		getCollectionWidget().addSelectionListener(event -> {
           if (onSelect != null)
			   onSelect.accept(event.path == null ? -1 : event.path.index);
        });
	}

	public void setField(Field field, int lastLayer) {
		if (field == null) {
			setObject(null);
		} else {
			fieldWidth = field.sizeX;
			fieldHeight = field.sizeY;
			maxHeight = field.prefs.maxHeight;
			if (lastLayer >= 0 && lastLayer < getDataCollection().size())
				getCollectionWidget().select(new LPath(lastLayer));
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
	public void setChecked(Layer c, boolean checked) {
		c.visible = checked;
	}
	@Override
	protected Info getEditableData(LPath path) {
		return getDataCollection().get(path.index).info;
	}
	@Override
	protected void setEditableData(LPath path, Info newData) {
		getDataCollection().get(path.index).info = newData;
	}
	@Override
	protected boolean isChecked(Layer data) {
		return data.visible;
	}
	
	public Layer getLayer() {
		return getCollectionWidget().getSelectedObject();
	}

}
