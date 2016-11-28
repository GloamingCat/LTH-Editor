package gui.views.database.subcontent;

import gui.Vocab;
import gui.helper.ImageHelper;
import lwt.editor.LObjectEditor;
import lwt.event.LControlEvent;
import lwt.event.listener.LControlListener;
import lwt.widget.LSpinner;

import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Label;

import data.Ramp;
import data.Ramp.PointSet;

public class RampEditor extends LObjectEditor {

	private Label label;
	
	public RampEditor(Composite parent, int style) {
		super(parent, style);
		
		GridLayout gridLayout = new GridLayout(4, false);
		gridLayout.marginWidth = 0;
		gridLayout.marginHeight = 0;
		setLayout(gridLayout);
		
		Label lblBottomX = new Label(this, SWT.NONE);
		lblBottomX.setText(Vocab.instance.BOTTOM + "1 X");
		
		LSpinner spnBX1 = new LSpinner(this, SWT.NONE);
		spnBX1.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1));
		
		Label lblBottomX_1 = new Label(this, SWT.NONE);
		lblBottomX_1.setText(Vocab.instance.BOTTOM + "2 X");
		
		LSpinner spnBX2 = new LSpinner(this, SWT.NONE);
		spnBX2.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1));
		
		Label lblBottomY = new Label(this, SWT.NONE);
		lblBottomY.setText(Vocab.instance.BOTTOM + "1 Y");
		
		LSpinner spnBY1 = new LSpinner(this, SWT.NONE);
		spnBY1.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, false, false, 1, 1));
		
		Label lblBottomY_1 = new Label(this, SWT.NONE);
		lblBottomY_1.setText(Vocab.instance.BOTTOM + "2 Y");
		
		LSpinner spnBY2 = new LSpinner(this, SWT.NONE);
		spnBY2.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1));
		
		Label lblTopX = new Label(this, SWT.NONE);
		lblTopX.setText(Vocab.instance.TOP + "1 X");
		
		LSpinner spnTX1 = new LSpinner(this, SWT.NONE);
		spnTX1.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, false, false, 1, 1));
		
		Label lblTopX_1 = new Label(this, SWT.NONE);
		lblTopX_1.setText(Vocab.instance.TOP + "2 X");
		
		LSpinner spnTX2 = new LSpinner(this, SWT.NONE);
		spnTX2.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, false, false, 1, 1));
		
		Label lblTopY = new Label(this, SWT.NONE);
		lblTopY.setText(Vocab.instance.TOP + "1 Y");
		
		LSpinner spnTY1 = new LSpinner(this, SWT.NONE);
		spnTY1.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, false, false, 1, 1));
		
		Label lblTopY_1 = new Label(this, SWT.NONE);
		lblTopY_1.setText(Vocab.instance.TOP + "2 Y");
		
		LSpinner spnTY2 = new LSpinner(this, SWT.NONE);
		spnTY2.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, false, false, 1, 1));
		
		label = new Label(this, SWT.NONE);
		label.setLayoutData(new GridData(SWT.FILL, SWT.FILL, false, true, 4, 1));
		label.setAlignment(SWT.CENTER);
		
		addAllSpinners(
				new LSpinner[] {spnBX1, spnBX2, spnBY1, spnBY2, spnTX1, spnTX2, spnTY1, spnTY2},
				new String[] {"b1x", "b2x", "b1y", "b2y", "t1x", "t2x", "t1y", "t2y"}
		);
		
	}
	
	private void addAllSpinners(LSpinner[] spinners, String[] names) {
		for(int i = 0; i < 8; i++) {
			addControl(spinners[i], names[i]);
			spinners[i].addModifyListener(new LControlListener() {
				@Override
				public void onModify(LControlEvent event) {
					resetImage();
				}
			});
			spinners[i].setMinimum(-100);
			spinners[i].setMaximum(100);
		}
	}
	
	public void setObject(Object obj) {
		Ramp ramp = (Ramp) obj;
		super.setObject(ramp.points);
		resetImage();
	}
	
	public void resetImage() {
		if (currentObject == null)
			label.setImage(null);
		else {
			PointSet points = (PointSet) currentObject;
			Image image = ImageHelper.rampImage(points);
			if (label.getImage() != null) {
				label.getImage().dispose();
			}
			label.setImage(image);
		}
	}

}