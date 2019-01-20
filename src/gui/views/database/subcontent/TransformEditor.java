package gui.views.database.subcontent;

import gui.Vocab;
import gui.helper.ImageHelper;

import org.eclipse.swt.SWT;
import org.eclipse.swt.events.PaintEvent;
import org.eclipse.swt.events.PaintListener;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Label;

import data.subcontent.Transform;
import lwt.editor.LObjectEditor;
import lwt.event.LControlEvent;
import lwt.event.listener.LControlListener;
import lwt.widget.LImage;
import lwt.widget.LSpinner;

public class TransformEditor extends LObjectEditor {
	
	public LImage image = null;
	private LSpinner spnOffsetX;
	private LSpinner spnOffsetY;
	private LSpinner spnScaleY;
	private LSpinner spnScaleX;
	private LSpinner spnRotation;

	public TransformEditor(Composite parent, int style) {
		super(parent, style);

		setLayout(new GridLayout(4, false));
		
		LControlListener<Integer> updateColor = new LControlListener<Integer>() {
			@Override
			public void onModify(LControlEvent<Integer> event) {
				if (event.oldValue == null) return;
				onChangeColor((Transform) currentObject);
			}
		};
		LControlListener<Integer> updateOffset = new LControlListener<Integer>() {
			@Override
			public void onModify(LControlEvent<Integer> event) {
				if (event.oldValue == null) return;
				onChangeOffset((Transform) currentObject);
			}
		};
		LControlListener<Integer> updateScale = new LControlListener<Integer>() {
			@Override
			public void onModify(LControlEvent<Integer> event) {
				if (event.oldValue == null) return;
				onChangeScale((Transform) currentObject);
			}
		};
		
		Label lblOffsetX = new Label(this, SWT.NONE);
		lblOffsetX.setText(Vocab.instance.OFFSETX);
		
		spnOffsetX = new LSpinner(this, SWT.NONE);
		spnOffsetX.setMaximum(1024);
		spnOffsetX.setMinimum(-1024);
		spnOffsetX.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1));
		addControl(spnOffsetX, "offsetX");
		spnOffsetX.addModifyListener(updateOffset);
		
		Label lblRed = new Label(this, SWT.NONE);
		lblRed.setText(Vocab.instance.RED);
		
		LSpinner spnRed = new LSpinner(this, SWT.NONE);
		spnRed.setMaximum(10000);
		spnRed.setMinimum(0);
		spnRed.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1));
		addControl(spnRed, "red");
		spnRed.addModifyListener(updateColor);
		
		Label lblOffsetY = new Label(this, SWT.NONE);
		lblOffsetY.setText(Vocab.instance.OFFSETY);
		
		spnOffsetY = new LSpinner(this, SWT.NONE);
		spnOffsetY.setMaximum(1024);
		spnOffsetY.setMinimum(-1024);
		spnOffsetY.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1));
		addControl(spnOffsetY, "offsetY");
		spnOffsetY.addModifyListener(updateOffset);
		
		Label lblGreen = new Label(this, SWT.NONE);
		lblGreen.setText(Vocab.instance.GREEN);
		
		LSpinner spnGreen = new LSpinner(this, SWT.NONE);
		spnGreen.setMaximum(10000);
		spnGreen.setMinimum(0);
		spnGreen.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1));
		addControl(spnGreen, "green");
		spnGreen.addModifyListener(updateColor);
		
		Label lblOffsetDepth = new Label(this, SWT.NONE);
		lblOffsetDepth.setText(Vocab.instance.OFFSETDEPTH);
		
		LSpinner spnOffsetDepth = new LSpinner(this, SWT.NONE);
		spnOffsetDepth.setMinimum(-1024);
		spnOffsetDepth.setMaximum(1024);
		spnOffsetDepth.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1));
		addControl(spnOffsetDepth, "offsetDepth");
		
		Label lblBlue = new Label(this, SWT.NONE);
		lblBlue.setText(Vocab.instance.BLUE);
		
		LSpinner spnBlue = new LSpinner(this, SWT.NONE);
		spnBlue.setMaximum(10000);
		spnBlue.setMinimum(0);
		spnBlue.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1));
		addControl(spnBlue, "blue");
		spnBlue.addModifyListener(updateColor);
		
		Label lblScaleX = new Label(this, SWT.NONE);
		lblScaleX.setText(Vocab.instance.SCALEX);
		
		spnScaleX = new LSpinner(this, SWT.NONE);
		spnScaleX.setMaximum(10000);
		spnScaleX.setMinimum(-10000);
		spnScaleX.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1));
		addControl(spnScaleX, "scaleX");
		spnScaleX.addModifyListener(updateScale);
		
		Label lblAlpha = new Label(this, SWT.NONE);
		lblAlpha.setText(Vocab.instance.ALPHA);
		
		LSpinner spnAlpha = new LSpinner(this, SWT.NONE);
		spnAlpha.setMaximum(10000);
		spnAlpha.setMinimum(0);
		spnAlpha.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1));
		addControl(spnAlpha, "alpha");
		spnAlpha.addModifyListener(updateColor);
		
		Label lblScaleY = new Label(this, SWT.NONE);
		lblScaleY.setText(Vocab.instance.SCALEY);
		
		spnScaleY = new LSpinner(this, SWT.NONE);
		spnScaleY.setMaximum(10000);
		spnScaleY.setMinimum(-10000);
		spnScaleY.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1));
		addControl(spnScaleY, "scaleY");
		spnScaleY.addModifyListener(updateScale);
		
		Label lblHue = new Label(this, SWT.NONE);
		lblHue.setText(Vocab.instance.HUE);
		
		LSpinner spnHue = new LSpinner(this, SWT.NONE);
		spnHue.setMaximum(360);
		spnHue.setMinimum(-360);
		spnHue.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1));
		addControl(spnHue, "hue");
		spnHue.addModifyListener(updateColor);
		
		Label lblRotation = new Label(this, SWT.NONE);
		lblRotation.setText(Vocab.instance.ROTATION);
		
		spnRotation = new LSpinner(this, SWT.NONE);
		spnRotation.setMaximum(360);
		spnRotation.setMinimum(-360);
		spnRotation.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1));
		addControl(spnRotation, "rotation");
		
		Label lblSaturation = new Label(this, SWT.NONE);
		lblSaturation.setText(Vocab.instance.SATURATION);
		
		LSpinner spnSaturation = new LSpinner(this, SWT.NONE);
		spnSaturation.setMaximum(10000);
		spnSaturation.setMinimum(0);
		spnSaturation.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1));
		addControl(spnSaturation, "saturation");
		spnSaturation.addModifyListener(updateColor);
		
		Label placeholder = new Label(this, SWT.NONE);
		placeholder.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 2, 1));
		
		Label lblBrightness = new Label(this, SWT.NONE);
		lblBrightness.setText(Vocab.instance.BRIGHTNESS);
		
		LSpinner spnBrightness = new LSpinner(this, SWT.NONE);
		spnBrightness.setMaximum(10000);
		spnBrightness.setMinimum(0);
		spnBrightness.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1));
		addControl(spnBrightness, "brightness");
		spnBrightness.addModifyListener(updateColor);
	}
	
	public void setImage(LImage img) {
		img.addPaintListener(new PaintListener() {
			@Override
			public void paintControl(PaintEvent e) {
				if (currentObject == null)
					return;
				Transform t = (Transform) currentObject;
				float sw = t.scaleX / 100f;
				float sh = t.scaleY / 100f;
				int x = Math.round(t.offsetX * sw) + e.x;
				int y = Math.round(t.offsetY * sh) + e.y;
				x -= 1;
				e.gc.drawLine(x - 2, y, x + 2, y);
				e.gc.drawLine(x, y - 2, x, y + 2);
			}
		});
		image = img;
	}
	
	public void setObject(Object obj) {
		if (obj != null && image != null) {
			Transform t = (Transform) obj;
			image.setRGBA(t.red, t.green, t.blue, t.alpha);
			image.setHSV(t.hue, t.saturation, t.brightness);
			image.setScale(t.scaleX / 100f, t.scaleY / 100f);
		}
		super.setObject(obj);
	}
	
	public void onChangeOffset(Transform t) {
		if (image != null)
			image.redraw();
	}
	
	public void onChangeColor(Transform t) {
		if (image != null && currentObject != null) {
			ImageHelper.setColorTransform(image, t);
			image.setImage(image.getOriginalImage(), image.getRectangle());
			image.redraw();
		}
	}
	
	public void onChangeScale(Transform t) {
		if (image != null) {
			image.setScale(t.scaleX / 100f, t.scaleY / 100f);
			image.redraw();
		}
	}

}
