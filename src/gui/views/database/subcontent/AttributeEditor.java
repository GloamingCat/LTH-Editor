package gui.views.database.subcontent;

import data.config.Attribute;
import lui.base.LPrefs;
import lui.base.data.LPath;
import project.Project;
import lui.container.LContainer;
import lui.gson.GFormEditor;
import lui.base.data.LDataList;
import lui.widget.LSpinner;

public class AttributeEditor extends GFormEditor<Integer, Integer, LSpinner> {
	
	public AttributeEditor(LContainer parent, int columns) {
		super(parent, 0);
		getCollectionWidget().setLabelWidth(30);
		getCollectionWidget().setColumns(columns);
	}

	@Override
	protected LDataList<Integer> getDataCollection() {
		return (LDataList<Integer>) currentObject;
	}

	@Override
	protected Integer getEditableData(LPath path) {
		return getDataCollection().get(path.index);
	}

	@Override
	protected void setEditableData(LPath path, Integer newData) {
		getDataCollection().set(path.index, newData);
	}

	@Override
	public void onVisible() {
		getCollectionWidget().setControlCount(Project.current.attributes.getList().size());
		super.onVisible();
	}

	@Override
	protected LSpinner createControlWidget(LContainer parent) {
		LSpinner spinner = new LSpinner(parent);
		spinner.setMinimum(Integer.MIN_VALUE);
		spinner.getCellData().setRequiredSize(60, LPrefs.WIDGETHEIGHT);
        spinner.getCellData().setTargetSize(60, LPrefs.WIDGETHEIGHT);
        spinner.getCellData().setExpand(true, false);
		return spinner;
	}

	@Override
	protected String getLabelText(final int i) {
		Attribute att = (Attribute) Project.current.attributes.getData().get(i);
		return att.shortName;
	}

	@Override
	public Class<?> getType() {
		return Integer.class;
	}

}
