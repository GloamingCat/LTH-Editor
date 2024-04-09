package gui.views.database.subcontent;

import data.config.Attribute;
import project.Project;
import lwt.container.LContainer;
import lwt.gson.GGridForm;
import lbase.data.LDataList;
import lwt.widget.LControlWidget;
import lwt.widget.LText;

public class BuildEditor extends GGridForm<String> {

	public BuildEditor(LContainer parent, int columns) {
		super(parent, columns);
		//addHeader().setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, false, 1, 1));
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
