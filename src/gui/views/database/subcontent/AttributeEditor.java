package gui.views.database.subcontent;

import java.util.ArrayList;

import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.ScrolledComposite;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Label;

import data.config.Attribute;
import data.config.Config;
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
		return (LDataList<Integer>) currentObject;
	}
	
	public void setObject(Object obj) {
		super.setObject(obj);
		if (obj != null) {
			list = getList();
			for(int i = 0; i < spinners.size(); i++) {
				if (i < list.size()) {
					spinners.get(i).setValue(list.get(i));
				} else {
					list.add(0);
					spinners.get(i).setValue(0);
				}
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
		content.setLayout(new GridLayout(columns, false));
		Control[] controls = content.getChildren();
		Config config = (Config) Project.current.config.getData();
		ArrayList<Attribute> attributes = config.attributes;
		// Add spinners for exceeding attributes
		for(int i = controls.length / 2; i < attributes.size(); i ++) {
			Label label = new Label(content, SWT.NONE);
			label.setLayoutData(new GridData(SWT.LEFT, SWT.CENTER, false, false, 1, 1));
			createSpinner(i);
		}
		// Remove exceeding spinners
		for (int i = attributes.size() * 2; i < controls.length; i++) {
			controls[i].dispose();
		}
		// Update spinners
		controls = content.getChildren();
		spinners.clear();
		for (int i = 0; i < attributes.size(); i++)	{
			Attribute att = attributes.get(i);
			Label label = (Label) controls[i * 2];
			label.setText(att.shortName);
			LSpinner spinner = (LSpinner) controls[i * 2 + 1];
			spinners.add(spinner);
		}
		scrollComp.setContent(content);
		scrollComp.setMinSize(content.computeSize(SWT.DEFAULT, SWT.DEFAULT));
	}
	
	private LSpinner createSpinner(final int i) {
		LSpinner spinner = new LSpinner(content, SWT.NONE);
		spinner.setActionStack(getActionStack());
		spinner.addModifyListener(new LControlListener<Integer>() {
			@Override
			public void onModify(LControlEvent<Integer> event) {
				if (list != null) {
					list.set(i, (Integer) event.newValue);
				}
			}
		});
		spinner.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1));
		return spinner;
	}

}
