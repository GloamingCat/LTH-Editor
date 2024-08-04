package gui.widgets;

import com.google.gson.InstanceCreator;
import lui.base.data.LDataList;
import lui.base.data.LPath;
import lui.base.gui.LControl;
import lui.container.LContainer;
import lui.container.LPanel;
import lui.gson.GFormEditor;

import java.lang.reflect.Type;

public class SimpleEditableForm<T, W extends LPanel & LControl<T>>
        extends GFormEditor<T, T, W> {

    public Class<?> type;
    public InstanceCreator<W> widgetFactory;

    public SimpleEditableForm(LContainer parent, int style) {
        super(parent, style);
    }

    @Override
    protected T getEditableData(LPath path) {
        return getDataCollection().get(path.index);
    }
    @Override
    protected void setEditableData(LPath path, T newData) {
        getDataCollection().set(path.index, newData);
    }

    @Override
    protected LDataList<T> getDataCollection() {
        return (LDataList<T>) currentObject;
    }

    @Override
    protected String getLabelText(int i) {
        return getDataCollection().get(i).toString();
    }

    @Override
    protected W createControlWidget(LContainer parent) {
        return widgetFactory.createInstance(type);
    }

    @Override
    public Type getType() {
        return type;
    }

}
