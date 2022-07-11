package gui.views.database.subcontent;

import gui.Vocab;

import org.eclipse.swt.SWT;
import org.eclipse.swt.events.PaintEvent;
import org.eclipse.swt.events.PaintListener;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;

import data.subcontent.Transform;
import lwt.editor.LObjectEditor;
import lwt.event.LControlEvent;
import lwt.event.listener.LControlListener;
import lwt.widget.LImage;
import lwt.widget.LLabel;
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
		
		new LLabel(this, Vocab.instance.OFFSETX);
		
		spnOffsetX = new LSpinner(this, SWT.NONE);
		spnOffsetX.setMaximum(1024);
		spnOffsetX.setMinimum(-1024);
		spnOffsetX.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1));
		addControl(spnOffsetX, "offsetX");
		spnOffsetX.addModifyListener(updateOffset);
		
		new LLabel(this, Vocab.instance.RED);
		
		LSpinner spnRed = new LSpinner(this, SWT.NONE);
		spnRed.setMaximum(10000);
		spnRed.setMinimum(0);
		spnRed.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1));
		addControl(spnRed, "red");
		spnRed.addModifyListener(updateColor);
		
		new LLabel(this, Vocab.instance.OFFSETY);
		
		spnOffsetY = new LSpinner(this, SWT.NONE);
		spnOffsetY.setMaximum(1024);
		spnOffsetY.setMinimum(-1024);
		spnOffsetY.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1));
		addControl(spnOffsetY, "offsetY");
		spnOffsetY.addModifyListener(updateOffset);
		
		new LLabel(this, Vocab.instance.GREEN);
		
		LSpinner spnGreen = new LSpinner(this, SWT.NONE);
		spnGreen.setMaximum(10000);
		spnGreen.setMinimum(0);
		spnGreen.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1));
		addControl(spnGreen, "green");
		spnGreen.addModifyListener(updateColor);
		
		new LLabel(this, Vocab.instance.OFFSETDEPTH);
		
		LSpinner spnOffsetDepth = new LSpinner(this, SWT.NONE);
		spnOffsetDepth.setMinimum(-1024);
		spnOffsetDepth.setMaximum(1024);
		spnOffsetDepth.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1));
		addControl(spnOffsetDepth, "offsetDepth");
		
		new LLabel(this, Vocab.instance.BLUE);
		
		LSpinner spnBlue = new LSpinner(this, SWT.NONE);
		spnBlue.setMaximum(10000);
		spnBlue.setMinimum(0);
		spnBlue.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1));
		addControl(spnBlue, "blue");
		spnBlue.addModifyListener(updateColor);
		
		new LLabel(this, Vocab.instance.SCALEX);
		
		spnScaleX = new LSpinner(this, SWT.NONE);
		spnScaleX.setMaximum(10000);
		spnScaleX.setMinimum(-10000);
		spnScaleX.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1));
		addControl(spnScaleX, "scaleX");
		spnScaleX.addModifyListener(updateScale);
		
		new LLabel(this, Vocab.instance.ALPHA);
		
		LSpinner spnAlpha = new LSpinner(this, SWT.NONE);
		spnAlpha.setMaximum(10000);
		spnAlpha.setMinimum(0);
		spnAlpha.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1));
		addControl(spnAlpha, "alpha");
		spnAlpha.addModifyListener(updateColor);
		
		new LLabel(this, Vocab.instance.SCALEY);
		
		spnScaleY = new LSpinner(this, SWT.NONE);
		spnScaleY.setMaximum(10000);
		spnScaleY.setMinimum(-10000);
		spnScaleY.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1));
		addControl(spnScaleY, "scaleY");
		spnScaleY.addModifyListener(updateScale);
		
		new LLabel(this, Vocab.instance.HUE);
		
		LSpinner spnHue = new LSpinner(this, SWT.NONE);
		spnHue.setMaximum(360);
		spnHue.setMinimum(-360);
		spnHue.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1));
		addControl(spnHue, "hue");
		spnHue.addModifyListener(updateColor);
		
		new LLabel(this, Vocab.instance.ROTATION);
		
		spnRotation = new LSpinner(this, SWT.NONE);
		spnRotation.setMaximum(360);
		spnRotation.setMinimum(-360);
		spnRotation.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1));
		addControl(spnRotation, "rotation");
		
		new LLabel(this, Vocab.instance.SATURATION);
		
		LSpinner spnSaturation = new LSpinner(this, SWT.NONE);
		spnSaturation.setMaximum(10000);
		spnSaturation.setMinimum(0);
		spnSaturation.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1));
		addControl(spnSaturation, "saturation");
		spnSaturation.addModifyListener(updateColor);
		
		new LLabel(this, "").setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 2, 1));
		
		new LLabel(this, Vocab.instance.BRIGHTNESS);
		
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
			Transform t2 = secondaryTransform();
			if (t2 != null)
				t = new Transform().combine(t).combine(t2);
			image.setRGBA(t.red / 255f, t.green / 255f, t.blue / 255f, t.alpha / 255f);
			image.setHSV(t.hue, t.saturation / 100f, t.brightness / 100f);
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
			Transform t2 = secondaryTransform();
			if (t2 != null)
				t = new Transform().combine(t).combine(t2);
			t.setColorTransform(image);
			image.setImage(image.getOriginalImage(), image.getRectangle());
			image.redraw();
		}
	}
	
	public void onChangeScale(Transform t) {
		if (image != null) {
			Transform t2 = secondaryTransform();
			if (t2 != null)
				t = new Transform().combine(t).combine(t2);
			image.setScale(t.scaleX / 100f, t.scaleY / 100f);
			image.redraw();
		}
	}

	public void updateColorTransform(LImage img) {
		Transform t1 = (Transform) currentObject;
		if (t1 != null) {
			Transform t2 = secondaryTransform();
			if (t2 != null) {
				t1.setColorTransform(img, t2);
			} else {
				t1.setColorTransform(img);
			}
		}
	}
	
	public Transform secondaryTransform() { return null; }

}
