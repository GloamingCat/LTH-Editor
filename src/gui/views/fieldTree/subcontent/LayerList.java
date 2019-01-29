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

import org.eclipse.swt.SWT;
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
		super(parent, SWT.CHECK);
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
				if (event.detail == SWT.CHECK) { 
					l.visible = !l.visible;
					FieldEditor.instance.canvas.updateAllTileImages();
				} else {
					editor.selectLayer(l);
					if (editor.field == null || event.path == null)
						return;
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
	
	public void setField(Field field) {
		if (field == null) {
			setDataCollection(null);
		} else {
			LDataList<Layer> layers = getLayerList(field);
			setDataCollection(layers);
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
