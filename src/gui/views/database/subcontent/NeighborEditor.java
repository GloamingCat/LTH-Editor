package gui.views.database.subcontent;

import gui.Vocab;

import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Group;
import org.eclipse.swt.widgets.Label;
import org.eclipse.wb.swt.SWTResourceManager;

import lwt.editor.LObjectEditor;

import org.eclipse.swt.events.MouseAdapter;
import org.eclipse.swt.events.MouseEvent;
import org.eclipse.swt.events.SelectionEvent;

public class NeighborEditor extends LObjectEditor {

	private boolean[] neighbors = new boolean[8];
	private Label[] labels;
	
	public NeighborEditor(Composite parent, int style) {
		super(parent, style);
		
		setLayout(new FillLayout());
		
		Group group = new Group(this, SWT.NONE);
		group.setText(Vocab.instance.NEIGHBORS);
		group.setLayout(new GridLayout(2, false));
		
		Button btnNone = new Button(group, SWT.NONE);
		btnNone.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1));
		btnNone.setText(Vocab.instance.NONE);
		btnNone.addSelectionListener(allAction(false));
		
		Button btnAll = new Button(group, SWT.NONE);
		btnAll.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1));
		btnAll.setText(Vocab.instance.ALL);
		btnAll.addSelectionListener(allAction(true));
		
		Composite composite = new Composite(group, SWT.NONE);
		composite.setLayout(new GridLayout(3, true));
		composite.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true, 2, 1));
		
		Label arrow135 = new Label(composite, SWT.NONE);
		arrow135.setAlignment(SWT.CENTER);
		arrow135.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true, 1, 1));
		arrow135.setImage(SWTResourceManager.getImage(NeighborEditor.class, "/img/arrow_135.png"));
		
		Label arrow90 = new Label(composite, SWT.NONE);
		arrow90.setAlignment(SWT.CENTER);
		arrow90.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true, 1, 1));
		arrow90.setImage(SWTResourceManager.getImage(NeighborEditor.class, "/img/arrow_90.png"));
		
		Label arrow45 = new Label(composite, SWT.NONE);
		arrow45.setAlignment(SWT.CENTER);
		arrow45.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true, 1, 1));
		arrow45.setImage(SWTResourceManager.getImage(NeighborEditor.class, "/img/arrow_45.png"));
		
		Label arrow180 = new Label(composite, SWT.NONE);
		arrow180.setAlignment(SWT.CENTER);
		arrow180.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true, 1, 1));
		arrow180.setImage(SWTResourceManager.getImage(NeighborEditor.class, "/img/arrow_180.png"));
		
		new Label(composite, SWT.NONE);
		
		Label arrow0 = new Label(composite, SWT.NONE);
		arrow0.setAlignment(SWT.CENTER);
		arrow0.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true, 1, 1));
		arrow0.setImage(SWTResourceManager.getImage(NeighborEditor.class, "/img/arrow_0.png"));
		
		Label arrow225 = new Label(composite, SWT.NONE);
		arrow225.setAlignment(SWT.CENTER);
		arrow225.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true, 1, 1));
		arrow225.setImage(SWTResourceManager.getImage(NeighborEditor.class, "/img/arrow_225.png"));
		
		Label arrow270 = new Label(composite, SWT.NONE);
		arrow270.setAlignment(SWT.CENTER);
		arrow270.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true, 1, 1));
		arrow270.setImage(SWTResourceManager.getImage(NeighborEditor.class, "/img/arrow_270.png"));
		
		Label arrow315 = new Label(composite, SWT.NONE);
		arrow315.setAlignment(SWT.CENTER);
		arrow315.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true, 1, 1));
		arrow315.setImage(SWTResourceManager.getImage(NeighborEditor.class, "/img/arrow_315.png"));
		
		labels = new Label [] {arrow0, arrow45, arrow90, arrow135, arrow180, arrow225, arrow270, arrow315};
		
		for(int i = 0; i < 8; i++) {
			setLabelListener(i);
		}

	}
	
	private void setLabelListener(int i) {
		labels[i].addMouseListener(new MouseAdapter() {
			@Override
			public void mouseDown(MouseEvent arg0) {
				boolean value = !neighbors[i];
				setLabelValue(i, value);
				neighbors[i] = value;
			}
		});
	}
	
	private SelectionAdapter allAction(final boolean newValue) {
		return new SelectionAdapter() {
			public void widgetSelected(SelectionEvent event) {
				for(int i = 0; i < 8; i++) {
					neighbors[i] = newValue;
				}
				updateLabels();
			}
		};
	}
	
	public void setObject(Object obj) {
		super.setObject(obj);
		if (obj != null) {
			boolean[] values = (boolean[]) obj;
			for(int i = 0; i < 8; i++) {
				neighbors[i] = values[i];
			}
			setLabelsValues(neighbors);
		} else {
			neighbors = null;
			disableLabels();
		}
	}
	
	public boolean[] getValues() {
		return neighbors;
	}

	// ==========================================================
	// Label values
	// ==========================================================
	
	private void updateLabels() {
		setLabelsValues(neighbors);
	}
	
	private void disableLabels() {
		for(int i = 0; i < 8; i++) {
			setLabelValue(i, false);
			labels[i].setEnabled(false);
		}
	}
	
	private void setLabelsValues(boolean[] values) {
		for(int i = 0; i < 8; i++) {
			setLabelValue(i, values[i]);
			labels[i].setEnabled(true);
		}
	}

	private void setLabelValue(int i, Boolean value) {
		Image image = null;
		if (value) {
			image = SWTResourceManager.getImage(NeighborEditor.class, "/img/arrow_" + i * 45 + ".png");
		} else {
			image = SWTResourceManager.getImage(NeighborEditor.class, "/img/falsearrow_" + i * 45 + ".png");
		}
		labels[i].setImage(image);
	}

}
