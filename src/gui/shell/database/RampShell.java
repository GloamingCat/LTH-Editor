package gui.shell.database;

import gui.Vocab;
import gui.helper.ImageHelper;
import gui.helper.RampHelper;

import org.eclipse.swt.widgets.Shell;

import data.Ramp.PointSet;
import lwt.dialog.LObjectShell;
import lwt.event.LControlEvent;
import lwt.event.listener.LControlListener;
import lwt.widget.LCombo;

import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.graphics.Point;

public class RampShell extends LObjectShell<PointSet> {
	
	private LCombo combo;
	private Label label;
	private PointSet points;

	public RampShell(Shell parent) {
		super(parent);
		GridData gridData = (GridData) content.getLayoutData();
		gridData.verticalAlignment = SWT.FILL;
		gridData.grabExcessVerticalSpace = true;
		setMinimumSize(new Point(200, 200));
		content.setLayout(new GridLayout(1, false));
		
		combo = new LCombo(content, SWT.NONE);
		combo.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1));
		combo.addModifyListener(new LControlListener<Integer>() {
			@Override
			public void onModify(LControlEvent<Integer> event) {
				int i = combo.getSelectionIndex();
				if (i < 8) {
					points = RampHelper.getDefault2(i);
				} else if (i < 16) {
					points = RampHelper.getDefault3(i - 8);
				} else {
					points = RampHelper.getDefault4(i - 16);
				}
				label.setImage(ImageHelper.rampImage(points));
			}
		});
		label = new Label(content, SWT.NONE);
		label.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true, 1, 1));
		
		String[] items = new String[24];
		for(int i = 0; i < 8; i++) {
			items[i] = Vocab.instance.RAMP + " 0-" + i;
		}
		for(int i = 8; i < 16; i++) {
			items[i] = Vocab.instance.RAMP + " 1-" + (i - 8);
		}
		for(int i = 16; i < 24; i++) {
			items[i] = Vocab.instance.RAMP + " 2-" + (i - 16);
		}
		
		combo.setIncludeID(false);
		combo.setOptional(false);
		combo.setItems(items);
		combo.setValue(0);
		
		points = RampHelper.getDefault2(0);
		label.setImage(ImageHelper.rampImage(points));
		
		pack();
	}

	@Override
	protected PointSet createResult(PointSet initial) {
		if (initial.equals(points)) {
			return null;
		} else {
			return points;
		}
	}
	
}
