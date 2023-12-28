package gui.views.fieldTree.subcontent;

import gui.shell.field.LayerShell;
import gui.views.fieldTree.FieldEditor;
import gui.views.fieldTree.FieldSideEditor;
import gui.views.fieldTree.FieldTreeEditor;
import lwt.LGlobals;
import lwt.container.LContainer;
import lwt.dataestructure.LDataList;
import lwt.dataestructure.LPath;
import lwt.dialog.LObjectShell;
import lwt.dialog.LShell;
import lwt.dialog.LShellFactory;
import lwt.editor.LListEditor;
import lwt.event.LEditEvent;
import lwt.event.LInsertEvent;
import lwt.event.LMoveEvent;
import lwt.event.LSelectionEvent;
import lwt.event.listener.LCollectionListener;
import lwt.event.listener.LSelectionListener;

import project.Project;
import data.field.Field;
import data.field.Layer;
import data.field.Layer.Info;

public abstract class LayerList extends LListEditor<Layer, Layer.Info> {
	
	private FieldSideEditor editor;
	private int type;
	
	/**
	 * @wbp.parser.constructor
	 * @wbp.eval.method.parameter parent new lwt.dialog.LShell()
	 * @wbp.eval.method.parameter type 0
	 */
	public LayerList(LContainer parent, int type) {
		super(parent, true);
		this.type = type;
		setShellFactory(new LShellFactory<Info>() {
			@Override
			public LObjectShell<Info> createShell(LShell parent) {
				return new LayerShell(parent);
			}
		});
		getCollectionWidget().setEditEnabled(true);
		getCollectionWidget().setInsertNewEnabled(true);
		getCollectionWidget().setDuplicateEnabled(true);
		getCollectionWidget().setDeleteEnabled(true);
		getCollectionWidget().setDragEnabled(true);
		getCollectionWidget().setCopyEnabled(true);
		getCollectionWidget().setPasteEnabled(true);
		getCollectionWidget().addEditListener(new LCollectionListener<Info>() {
			public void onEdit(LEditEvent<Info> e) {
				FieldEditor.instance.canvas.refresh();
			}
		});
		getCollectionWidget().addSelectionListener(new LSelectionListener() {
			@Override
			public void onSelect(LSelectionEvent event) {
				Layer l = (Layer) event.data;
				if (event.check) { 
					l.visible = !l.visible;
					FieldEditor.instance.canvas.refresh();
				} else {
					editor.selectLayer(l);
					if (editor.field == null || event.path == null)
						return;
					Project.current.fieldTree.getData().
						setLastLayer(editor.field.id, event.path.index, type);
				}
			}
		});
		LCollectionListener<Layer> listener = new LCollectionListener<Layer>() {
			public void onInsert(LInsertEvent<Layer> event) {
				getCollectionWidget().setChecked(new LPath(event.index), 
					event.node.data.visible);
			}
			public void onMove(LMoveEvent<Layer> event) {
				int index = event.destIndex == -1 ? getLayerList(editor.field).size() - 1 : event.destIndex;
				getCollectionWidget().setChecked(new LPath(index), 
						event.sourceNode.data.visible);
			}
		};
		getCollectionWidget().addInsertListener(listener);
		getCollectionWidget().addMoveListener(listener);
		if (FieldTreeEditor.instance != null)
			setMenuInterface(FieldTreeEditor.instance.getMenuInterface());
	}
	
	public void setEditor(FieldSideEditor parent) {
		this.editor = parent;
	}
	
	public void setField(Field field) {
		if (field == null) {
			setObject(null);
		} else {
			LDataList<Layer> layers = getLayerList(field);
			setObject(layers);
			int layer = Project.current.fieldTree.getData().getLastLayer(field.id, type);
			if (layer >= 0)
				getCollectionWidget().select(new LPath(layer));
			for (LPath path = new LPath(0); path.index < layers.size(); path.index++)
				getCollectionWidget().setChecked(path, layers.get(path.index).visible);
		}
	}
	
	@Override
	protected LDataList<Layer> getDataCollection() {
		return editor.field == null ? null : getLayerList(editor.field);
	}
	@Override
	protected Layer createNewElement() {
		return new Layer(editor.field.sizeX, editor.field.sizeY);
	}
	@Override
	protected Layer duplicateElement(Layer original) {
		return new Layer(original);
	}
	@Override
	protected String encodeElement(Layer data) {
		return LGlobals.gson.toJson(data);
	}
	@Override
	protected Layer decodeElement(String str) {
		return LGlobals.gson.fromJson(str, Layer.class);
	}
	@Override
	public boolean canDecode(String str) {
		return true;
	}
	
	@Override
	protected Info getEditableData(LPath path) {
		return getLayerList(editor.field).get(path.index).info;
	}
	@Override
	protected void setEditableData(LPath path, Info newData) {
		getLayerList(editor.field).get(path.index).info = newData;
	}
	
	public Layer getLayer() {
		return getCollectionWidget().getSelectedObject();
	}
	
	public abstract LDataList<Layer> getLayerList(Field field);

}
