package gui.views.database.subcontent;

import data.config.Attribute;
import lui.base.LPrefs;
import project.Project;
import lui.container.LContainer;
import lui.gson.GGridForm;
import lui.base.data.LDataList;
import lui.widget.LControlWidget;
import lui.widget.LText;

public class BuildEditor extends GGridForm<String> {

	public BuildEditor(LContainer parent, int columns) {
		super(parent, columns);
		labelWidth = 20;
		controlWidth = 80;
	}
	
	protected String getDefaultValue() {
		return "0";
	}
	
	protected LDataList<Object> getList() {
		return Project.current.attributes.getList();
	}
	
	protected LControlWidget<String> createControl(final int i, final Object obj) {
		return new LText(content);
	}
	
	protected String getLabelText(final int i, final Object obj) {
		Attribute att = (Attribute) obj;
		return att.shortName;
	}

	@Override
	public Class<?> getType() {
		return String.class;
	}

}
