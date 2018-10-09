package gui.views.database.subcontent;

import java.util.ArrayList;

import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.ScrolledComposite;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Label;

import data.config.Attribute;
import data.config.Config;
import project.Project;
import lwt.dataestructure.LDataList;
import lwt.editor.LObjectEditor;
import lwt.event.LControlEvent;
import lwt.event.listener.LControlListener;
import lwt.widget.LText;

public class BuildEditor extends LObjectEditor {

	protected LDataList<String> values;
	protected ArrayList<LText> texts;
	protected ScrolledComposite scrollComp;
	protected Composite content;
	protected int columns = 2;
	
	protected LDataList<String> list;
	
	public BuildEditor(Composite parent, int style) {
		super(parent, style);
		
		setLayout(new FillLayout());
		texts = new ArrayList<>();
		
		scrollComp = new ScrolledComposite(this, SWT.V_SCROLL);
		scrollComp.setExpandVertical(true);
		scrollComp.setExpandHorizontal(true);
		scrollComp.setLayout(new FillLayout());
		
		content = new Composite(scrollComp, SWT.NONE);
		scrollComp.setContent(content);

	}
	
	@SuppressWarnings("unchecked")
	private LDataList<String> getList() {
		Object value = getFieldValue(currentObject, "build");
		return (LDataList<String>) value;
	}
	
	public void setObject(Object obj) {
		super.setObject(obj);
		if (obj != null) {
			list = getList();
			for(int i = 0; i < texts.size(); i++) {
				if (i < list.size()) {
					texts.get(i).setValue(list.get(i));
				} else {
					String value = "0";
					list.add(value);
					texts.get(i).setValue(value);
				}
			}
		} else {
			for(LText text : texts) {
				text.setValue(null);
				text.setEnabled(false);
			}
		}
	}
	
	public void setColumns(int i) {
		columns = i;
	}
	
	public void onVisible() {
		texts.clear();
		content.dispose();
		content = new Composite(scrollComp, SWT.NONE);
		content.setLayout(new GridLayout(columns, false));
		Config config = (Config) Project.current.config.getData();
		ArrayList<Attribute> attributes = config.attributes;
		for(int i = 0; i < attributes.size(); i++) {
			Attribute att = attributes.get(i);
			Label label = new Label(content, SWT.NONE);
			label.setText(att.shortName);
			label.setLayoutData(new GridData(SWT.LEFT, SWT.CENTER, false, false, 1, 1));
			LText text = createText(i);
			texts.add(text);
		}
		scrollComp.setContent(content);
		scrollComp.setMinSize(content.computeSize(SWT.DEFAULT, SWT.DEFAULT));
	}
	
	private LText createText(final int i) {
		LText text = new LText(content, SWT.NONE);
		text.setActionStack(getActionStack());
		text.addModifyListener(new LControlListener<String>() {
			@Override
			public void onModify(LControlEvent<String> event) {
				if (list != null) {
					list.set(i, event.newValue);
				}
			}
		});
		text.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1));
		return text;
	}

}
