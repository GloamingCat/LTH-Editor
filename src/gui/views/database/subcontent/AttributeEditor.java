package gui.views.database.subcontent;

import data.config.Attribute;
import gson.editor.GGridForm;
import project.Project;
import lwt.container.LContainer;
import lwt.dataestructure.LDataList;
import lwt.widget.LControlWidget;
import lwt.widget.LSpinner;

public class AttributeEditor extends GGridForm<Integer> {
	
	public AttributeEditor(LContainer parent, int columns) {
		super(parent, columns);
	}

	protected Integer getDefaultValue() {
		return 0;
	}
	
	protected LDataList<Object> getList() {
		return Project.current.attributes.getList();
	}
	
	protected LControlWidget<Integer> createControl(final int i, final Object obj) {
		return new LSpinner(content);
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
