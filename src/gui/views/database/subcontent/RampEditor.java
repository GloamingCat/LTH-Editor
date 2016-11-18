package gui.views.database.subcontent;

import gui.Vocab;
import gui.helper.FieldHelper;
import gui.helper.TilePainter;
import lwt.editor.LObjectEditor;
import lwt.event.LControlEvent;
import lwt.event.listener.LControlListener;
import lwt.widget.LSpinner;

import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.GC;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Group;
import org.eclipse.swt.widgets.Label;

import data.Ramp;

public class RampEditor extends LObjectEditor {

	private Label label;
	
	public RampEditor(Composite parent, int style) {
		super(parent, style);
		
		setLayout(new FillLayout());
		
		Group grpLines = new Group(this, SWT.NONE);
		grpLines.setLayout(new GridLayout(4, false));
		grpLines.setText(Vocab.instance.LINES);
		
		Label lblBottomX = new Label(grpLines, SWT.NONE);
		lblBottomX.setText(Vocab.instance.BOTTOM + "1 X");
		
		LSpinner spnBX1 = new LSpinner(grpLines, SWT.NONE);
		spnBX1.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1));
		
		Label lblBottomX_1 = new Label(grpLines, SWT.NONE);
		lblBottomX_1.setText(Vocab.instance.BOTTOM + "2 X");
		
		LSpinner spnBX2 = new LSpinner(grpLines, SWT.NONE);
		spnBX2.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1));
		
		Label lblBottomY = new Label(grpLines, SWT.NONE);
		lblBottomY.setText(Vocab.instance.BOTTOM + "1 Y");
		
		LSpinner spnBY1 = new LSpinner(grpLines, SWT.NONE);
		spnBY1.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, false, false, 1, 1));
		
		Label lblBottomY_1 = new Label(grpLines, SWT.NONE);
		lblBottomY_1.setText(Vocab.instance.BOTTOM + "2 Y");
		
		LSpinner spnBY2 = new LSpinner(grpLines, SWT.NONE);
		spnBY2.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1));
		
		Label lblTopX = new Label(grpLines, SWT.NONE);
		lblTopX.setText(Vocab.instance.TOP + "1 X");
		
		LSpinner spnTX1 = new LSpinner(grpLines, SWT.NONE);
		spnTX1.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, false, false, 1, 1));
		
		Label lblTopX_1 = new Label(grpLines, SWT.NONE);
		lblTopX_1.setText(Vocab.instance.TOP + "2 X");
		
		LSpinner spnTX2 = new LSpinner(grpLines, SWT.NONE);
		spnTX2.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, false, false, 1, 1));
		
		Label lblTopY = new Label(grpLines, SWT.NONE);
		lblTopY.setText(Vocab.instance.TOP + "1 Y");
		
		LSpinner spnTY1 = new LSpinner(grpLines, SWT.NONE);
		spnTY1.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, false, false, 1, 1));
		
		Label lblTopY_1 = new Label(grpLines, SWT.NONE);
		lblTopY_1.setText(Vocab.instance.TOP + "2 Y");
		
		LSpinner spnTY2 = new LSpinner(grpLines, SWT.NONE);
		spnTY2.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, false, false, 1, 1));
		
		label = new Label(grpLines, SWT.NONE);
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
		}
	}
	
	public void setObject(Object obj) {
		super.setObject(obj);
		resetImage();
	}
	
	private void resetImage() {
		if (currentObject == null)
			label.setImage(null);
		else {
			Image image = rampImage();
			if (label.getImage() != null) {
				label.getImage().dispose();
			}
			label.setImage(image);
		}
	}
	
	public Image rampImage() {
		Ramp ramp = (Ramp) currentObject;
		Image img = new Image(getDisplay(), (FieldHelper.config.tileW + 4) * 2, 
				(FieldHelper.config.tileH + FieldHelper.config.pixelsPerHeight + 4) * 2);
		GC gc = new GC(img);
		TilePainter.setScale(2);
		TilePainter.paintRamp(gc, FieldHelper.config.tileW / 2 + 2, 
				FieldHelper.config.tileH / 2 + 2, ramp);
		gc.dispose();
		return img;
	}
	
}