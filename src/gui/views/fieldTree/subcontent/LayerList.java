package gui.views.fieldTree.subcontent;

import gui.shell.field.LayerShell;
import gui.views.fieldTree.FieldEditor;
import gui.views.fieldTree.FieldSideEditor;
import lwt.dataestructure.LDataList;
import lwt.dataestructure.LPath;
import lwt.dialog.LObjectShell;
import lwt.dialog.LShellFactory;
import lwt.editor.LListEditor;
import lwt.event.LEditEvent;
import lwt.event.LSelectionEvent;
import lwt.event.listener.LCollectionListener;
import lwt.event.listener.LSelectionListener;

import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Shell;

import project.Project;
import data.field.Field;
import data.field.Layer;
import data.field.Layer.Info;

public abstract class LayerList extends LListEditor<Layer, Layer.Info> {
	
	private FieldSideEditor editor;
	private int type;
	
	/**
	 * Create the composite.
	 * @param parent
	 * @param style
	 */
	public LayerList(Composite parent, int type) {
		super(parent, 0);
		this.type = type;
		setShellFactory(new LShellFactory<Info>() {
			@Override
			public LObjectShell<Info> createShell(Shell parent) {
				return new LayerShell(parent);
			}
		});
		getCollectionWidget().addEditListener(new LCollectionListener<Info>() {
			public void onEdit(LEditEvent<Info> e) {
				FieldEditor.instance.canvas.updateAllTileImages();
			}
		});
		getCollectionWidget().addSelectionListener(new LSelectionListener() {
			@Override
			public void onSelect(LSelectionEvent event) {
				Layer l = (Layer) event.data;
				editor.selectLayer(l);
				if (editor.field != null && event.path != null) {
					Project.current.fieldTree.getData().
			 			setLastLayer(editor.field.id, event.path.index, type);
				}
			}
		});
		getCollectionWidget().setEditEnabled(true);
		getCollectionWidget().setInsertNewEnabled(true);
		getCollectionWidget().setDuplicateEnabled(true);
		getCollectionWidget().setDeleteEnabled(true);
		getCollectionWidget().setDragEnabled(true);
	}
	
	public void setEditor(FieldSideEditor parent) {
		this.editor = parent;
	}
	
	public void setObject(Object obj) {
		Field field = (Field) obj;
		if (editor.field == null) {
			setDataCollection(null);
		} else {
			super.setObject(getLayerList(field));
			int layer = Project.current.fieldTree.getData().getLastLayer(editor.field.id, type);
			getCollectionWidget().select(new LPath(layer));
		}
	}
	
	@Override
	protected LDataList<Layer> getDataCollection() {
		return editor.field == null ? null : getLayerList(editor.field);
	}
	@Override
	protected Layer createNewData() {
		return new Layer(editor.field.sizeX, editor.field.sizeY);
	}
	@Override
	protected Layer duplicateData(Layer original) {
		return new Layer(original);
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
