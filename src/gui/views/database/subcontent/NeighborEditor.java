package gui.views.database.subcontent;

import gui.Vocab;

import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Group;

import gson.editor.GDefaultObjectEditor;
import lwt.event.LControlEvent;
import lwt.event.listener.LControlListener;
import lwt.widget.LActionButton;
import lwt.widget.LLabel;
import lwt.widget.LToggleButton;

public class NeighborEditor extends GDefaultObjectEditor<boolean[]> {

	private LToggleButton[] labels;
	
	public NeighborEditor(Composite parent, int style) {
		super(parent, style);
		
		setLayout(new FillLayout());
		
		Group group = new Group(this, SWT.NONE);
		group.setText(Vocab.instance.NEIGHBORS);
		group.setLayout(new GridLayout(2, false));
		
		LActionButton btnNone = new LActionButton(group, Vocab.instance.NONE);
		btnNone.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1));
		btnNone.addModifyListener(allAction(false));
		
		LActionButton btnAll = new LActionButton(group, Vocab.instance.ALL);
		btnAll.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1));
		btnAll.addModifyListener(allAction(true));
		
		Composite composite = new Composite(group, SWT.NONE);
		composite.setLayout(new GridLayout(3, true));
		composite.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true, 2, 1));
		
		LToggleButton arrow135 = new LToggleButton(composite, "/img/arrow_135.png", "/img/falsearrow_135.png");
		
		LToggleButton arrow90 = new LToggleButton(composite, "/img/arrow_90.png", "/img/falsearrow_90.png");
		
		LToggleButton arrow45 = new LToggleButton(composite, "/img/arrow_45.png", "/img/falsearrow_45.png");
		
		LToggleButton arrow180 = new LToggleButton(composite, "/img/arrow_180.png", "/img/falsearrow_180.png");
		
		new LLabel(composite, 1);
		
		LToggleButton arrow0 = new LToggleButton(composite, "/img/arrow_0.png", "/img/falsearrow_0.png");
		
		LToggleButton arrow225 = new LToggleButton(composite, "/img/arrow_225.png", "/img/falsearrow_225.png");
		
		LToggleButton arrow270 = new LToggleButton(composite, "/img/arrow_270.png", "/img/falsearrow_270.png");
		
		LToggleButton arrow315 = new LToggleButton(composite, "/img/arrow_315.png", "/img/falsearrow_315.png");
		
		labels = new LToggleButton [] {arrow0, arrow45, arrow90, arrow135, arrow180, arrow225, arrow270, arrow315};

	}
	
	private LControlListener<Object> allAction(final boolean newValue) {
		return new LControlListener<Object>() {
			@Override
			public void onModify(LControlEvent<Object> event) {
				for(int i = 0; i < 8; i++) {
					labels[i].setValue(newValue);
				}
			}
		};
	}
	
	public void setObject(Object obj) {
		super.setObject(obj);
		if (obj != null) {
			boolean[] values = (boolean[]) obj;
			for(int i = 0; i < 8; i++) {
				labels[i].setValue(values[i]);
			}
		} else {
			for(int i = 0; i < 8; i++) {
				labels[i].setValue(null);
			}
		}
	}
	
	public boolean[] getValues() {
		boolean[] values = new boolean[8];
		for (int i = 0; i < 8; i++) {
			values[i] = labels[i].getValue();
		}
		return values;
	}

}
