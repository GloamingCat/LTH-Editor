package gui.views.database.subcontent;

import data.config.Attribute;
import lui.base.LPrefs;
import lui.base.data.LPath;
import project.Project;
import lui.container.LContainer;
import lui.gson.GFormEditor;
import lui.base.data.LDataList;
import lui.widget.LText;

public class BuildEditor extends GFormEditor<String, String, LText> {

	public BuildEditor(LContainer parent, int columns) {
		super(parent, columns);
		getCollectionWidget().setLabelWidth(30);
		getCollectionWidget().setColumns(1);
	}

	@Override
	protected LDataList<String> getDataCollection() {
		return (LDataList<String>) currentObject;
	}

	@Override
	protected String getEditableData(LPath path) {
		return getDataCollection().get(path.index);
	}

	@Override
	protected void setEditableData(LPath path, String newData) {
		getDataCollection().set(path.index, newData);
	}

	@Override
	public void onVisible() {
		setFormList(Project.current.attributes.getList());
		super.onVisible();
	}

	@Override
	protected LText createControlWidget(LContainer parent) {
		LText widget = new LText(parent);
		widget.getCellData().setRequiredSize(80, LPrefs.WIDGETHEIGHT);
        widget.getCellData().setTargetSize(80, LPrefs.WIDGETHEIGHT);
        widget.getCellData().setExpand(true, false);
		return widget;
	}

	@Override
	protected String getLabelText(final int i) {
		Attribute att = (Attribute) Project.current.attributes.getData().get(i);
		return att.shortName;
	}

	@Override
	public Class<?> getType() {
		return String.class;
	}

}
