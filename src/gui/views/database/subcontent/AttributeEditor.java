package gui.views.database.subcontent;

import data.config.Attribute;
import project.Project;
import lui.container.LContainer;
import lui.gson.GGridForm;
import lui.base.data.LDataList;
import lui.widget.LControlWidget;
import lui.widget.LSpinner;

public class AttributeEditor extends GGridForm<Integer> {
	
	public AttributeEditor(LContainer parent, int columns) {
		super(parent, columns);
		labelWidth = 20;
		controlWidth = 60;
	}

	protected Integer getDefaultValue() {
		return 0;
	}
	
	protected LDataList<Object> getList() {
		return Project.current.attributes.getList();
	}
	
	protected LControlWidget<Integer> createControl(final int i, final Object obj) {
		LSpinner spinner = new LSpinner(content);
		spinner.setMinimum(Integer.MIN_VALUE);
		return spinner;
	}
	
	protected String getLabelText(final int i, final Object obj) {
		Attribute att = (Attribute) obj;
		return att.shortName;
	}

	@Override
	public Class<?> getType() {
		return Integer.class;
	}

}
