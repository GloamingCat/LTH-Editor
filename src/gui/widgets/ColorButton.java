package gui.widgets;

import lwt.LVocab;
import lwt.widget.LControl;

import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.ColorDialog;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.graphics.RGB;
import org.eclipse.wb.swt.SWTResourceManager;

public class ColorButton extends LControl<RGB> {
	
	public String name = "";
	protected Button button;
	protected Label imgColor;
	
	/**
	 * Create the composite.
	 * @param parent
	 * @param style
	 */
	public ColorButton(Composite parent, int style) {
		super(parent, style);
		button = new Button(this, SWT.NONE);
		button.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent arg0) {
				ColorDialog d = new ColorDialog(getShell());
				d.setRGB(currentValue);
				RGB newRGB = d.open();
				if (newRGB != null && !newRGB.equals(currentValue)) {
					newModifyAction(currentValue, newRGB);
					setValue(newRGB);
					imgColor.setBackground(SWTResourceManager.getColor(newRGB));
					imgColor.redraw();
				}
			}
		});
		button.setText(LVocab.instance.SELECT);
	}
	
	public void setLabel(Label lbl) {
		imgColor = lbl;
	}
	
	public void setText(String text) {
		button.setText(text);
	}
	
	@Override
	public void setValue(Object value) {
		if (value != null) {
			button.setEnabled(true);
			currentValue = (RGB) value;
			imgColor.setBackground(SWTResourceManager.getColor(currentValue));
			imgColor.redraw();
		} else {
			button.setEnabled(false);
			currentValue = null;
			imgColor.setBackground(SWTResourceManager.getColor(SWT.COLOR_BLACK));
			imgColor.redraw();
		}
	}

}
