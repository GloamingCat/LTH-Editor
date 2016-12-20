package gui.views.database.subcontent;

import java.util.ArrayList;

import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.ScrolledComposite;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Label;

import data.Attribute;
import data.Config;
import project.Project;
import lwt.dataestructure.LDataList;
import lwt.editor.LObjectEditor;
import lwt.event.LControlEvent;
import lwt.event.listener.LControlListener;
import lwt.widget.LSpinner;

public class AttributeEditor extends LObjectEditor {

	protected LDataList<Integer> values;
	protected ArrayList<LSpinner> spinners;
	protected ScrolledComposite scrollComp;
	protected Composite content;
	protected int columns = 2;
	
	protected LDataList<Integer> list;
	
	public AttributeEditor(Composite parent, int style) {
		super(parent, style);
		
		setLayout(new FillLayout());
		spinners = new ArrayList<>();
		
		scrollComp = new ScrolledComposite(this, SWT.V_SCROLL);
		scrollComp.setExpandVertical(true);
		scrollComp.setExpandHorizontal(true);
		scrollComp.setLayout(new FillLayout());
		
		content = new Composite(scrollComp, SWT.NONE);
		scrollComp.setContent(content);

	}
	
	@SuppressWarnings("unchecked")
	private LDataList<Integer> getList() {
		Object value = getFieldValue(currentObject, "attributes");
		return (LDataList<Integer>) value;
	}
	
	public void setObject(Object obj) {
		super.setObject(obj);
		if (obj != null) {
			LDataList<Integer> list = getList();
			for(int i = 0; i < list.size(); i++) {
				spinners.get(i).setValue(list.get(i));
			}
		} else {
			for(LSpinner spinner : spinners) {
				spinner.setValue(null);
			}
		}
	}
	
	public void setColumns(int i) {
		columns = i;
	}
	
	public void onVisible() {
		spinners.clear();
		content.dispose();
		content = new Composite(scrollComp, SWT.NONE);
		content.setLayout(new GridLayout(columns, false));
		Config config = (Config) Project.current.config.getData();
		ArrayList<Attribute> attributes = config.attributes;
		for(int i = 0; i < attributes.size(); i++) {
			Attribute att = attributes.get(i);
			if (att.script.isEmpty()) {
				String name = att.toString();
				Label label = new Label(content, SWT.NONE);
				label.setText(name);
				label.setLayoutData(new GridData(SWT.LEFT, SWT.CENTER, false, false, 1, 1));
				LSpinner spinner = createSpinner(i);
				spinners.add(spinner);
			}
		}
		scrollComp.setContent(content);
		scrollComp.setMinSize(content.computeSize(SWT.DEFAULT, SWT.DEFAULT));
		scrollComp.layout();
		layout();
	}
	
	private LSpinner createSpinner(final int i) {
		LSpinner spinner = new LSpinner(content, SWT.NONE);
		spinner.setActionStack(getActionStack());
		spinner.addModifyListener(new LControlListener() {
			@Override
			public void onModify(LControlEvent event) {
				if (list != null) {
					list.set(i, (Integer) event.newValue);
				}
			}
		});
		spinner.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1));
		return spinner;
	}

}
