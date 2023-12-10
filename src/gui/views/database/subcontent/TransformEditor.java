package gui.views.database.subcontent;

import gui.Vocab;

import data.subcontent.Transform;
import gson.editor.GDefaultObjectEditor;
import lwt.container.LContainer;
import lwt.container.LImage;
import lwt.event.LControlEvent;
import lwt.event.listener.LControlListener;
import lwt.graphics.LPainter;
import lwt.widget.LLabel;
import lwt.widget.LSpinner;

public class TransformEditor extends GDefaultObjectEditor<Transform> {
	
	public LImage image = null;
	public Transform secondaryTransform = null;
	private LSpinner spnOffsetX;
	private LSpinner spnOffsetY;
	private LSpinner spnScaleY;
	private LSpinner spnScaleX;
	private LSpinner spnRotation;

	public TransformEditor(LContainer parent) {
		super(parent, 4, false, false);

		LControlListener<Integer> updateColor = new LControlListener<Integer>() {
			@Override
			public void onModify(LControlEvent<Integer> event) {
				if (event.oldValue == null) return;
				onChangeColor((Transform) getObject());
			}
		};
		LControlListener<Integer> updateOffset = new LControlListener<Integer>() {
			@Override
			public void onModify(LControlEvent<Integer> event) {
				if (event.oldValue == null) return;
				onChangeOffset((Transform) getObject());
			}
		};
		LControlListener<Integer> updateScale = new LControlListener<Integer>() {
			@Override
			public void onModify(LControlEvent<Integer> event) {
				if (event.oldValue == null) return;
				onChangeScale((Transform) getObject());
			}
		};
		
		new LLabel(this, Vocab.instance.OFFSETX);
		
		spnOffsetX = new LSpinner(this);
		spnOffsetX.setMaximum(1024 * 4);
		spnOffsetX.setMinimum(-1024 * 4);
		addControl(spnOffsetX, "offsetX");
		spnOffsetX.addModifyListener(updateOffset);
		
		new LLabel(this, Vocab.instance.RED);
		
		LSpinner spnRed = new LSpinner(this);
		spnRed.setMaximum(10000);
		spnRed.setMinimum(0);
		addControl(spnRed, "red");
		spnRed.addModifyListener(updateColor);
		
		new LLabel(this, Vocab.instance.OFFSETY);
		
		spnOffsetY = new LSpinner(this);
		spnOffsetY.setMaximum(1024);
		spnOffsetY.setMinimum(-1024);
		addControl(spnOffsetY, "offsetY");
		spnOffsetY.addModifyListener(updateOffset);
		
		new LLabel(this, Vocab.instance.GREEN);
		
		LSpinner spnGreen = new LSpinner(this);
		spnGreen.setMaximum(10000);
		spnGreen.setMinimum(0);
		addControl(spnGreen, "green");
		spnGreen.addModifyListener(updateColor);
		
		new LLabel(this, Vocab.instance.OFFSETDEPTH);
		
		LSpinner spnOffsetDepth = new LSpinner(this);
		spnOffsetDepth.setMinimum(-1024);
		spnOffsetDepth.setMaximum(1024);
		addControl(spnOffsetDepth, "offsetDepth");
		
		new LLabel(this, Vocab.instance.BLUE);
		
		LSpinner spnBlue = new LSpinner(this);
		spnBlue.setMaximum(10000);
		spnBlue.setMinimum(0);
		addControl(spnBlue, "blue");
		spnBlue.addModifyListener(updateColor);
		
		new LLabel(this, Vocab.instance.SCALEX);
		
		spnScaleX = new LSpinner(this);
		spnScaleX.setMaximum(10000);
		spnScaleX.setMinimum(-10000);
		addControl(spnScaleX, "scaleX");
		spnScaleX.addModifyListener(updateScale);
		
		new LLabel(this, Vocab.instance.ALPHA);
		
		LSpinner spnAlpha = new LSpinner(this);
		spnAlpha.setMaximum(10000);
		spnAlpha.setMinimum(0);
		addControl(spnAlpha, "alpha");
		spnAlpha.addModifyListener(updateColor);
		
		new LLabel(this, Vocab.instance.SCALEY);
		
		spnScaleY = new LSpinner(this);
		spnScaleY.setMaximum(10000);
		spnScaleY.setMinimum(-10000);
		addControl(spnScaleY, "scaleY");
		spnScaleY.addModifyListener(updateScale);
		
		new LLabel(this, Vocab.instance.HUE);
		
		LSpinner spnHue = new LSpinner(this);
		spnHue.setMaximum(360);
		spnHue.setMinimum(-360);
		addControl(spnHue, "hue");
		spnHue.addModifyListener(updateColor);
		
		new LLabel(this, Vocab.instance.ROTATION);
		
		spnRotation = new LSpinner(this);
		spnRotation.setMaximum(360);
		spnRotation.setMinimum(-360);
		addControl(spnRotation, "rotation");
		
		new LLabel(this, Vocab.instance.SATURATION);
		
		LSpinner spnSaturation = new LSpinner(this);
		spnSaturation.setMaximum(10000);
		spnSaturation.setMinimum(0);
		addControl(spnSaturation, "saturation");
		spnSaturation.addModifyListener(updateColor);
		
		new LLabel(this, 2, 1);
		
		new LLabel(this, Vocab.instance.BRIGHTNESS);
		
		LSpinner spnBrightness = new LSpinner(this);
		spnBrightness.setMaximum(10000);
		spnBrightness.setMinimum(0);
		addControl(spnBrightness, "brightness");
		spnBrightness.addModifyListener(updateColor);
	}
	
	public void setImage(LImage img) {
		img.addPainter(new LPainter() {
			@Override
			public void paint() {
				Object obj = getObject();
				if (obj == null)
					return;
				Transform t = (Transform) obj;
				float sw = t.scaleX / 100f;
				float sh = t.scaleY / 100f;
				int x = Math.round(t.offsetX * sw) + (int) img.ox;
				int y = Math.round(t.offsetY * sh) + (int) img.oy;
				x -= 1;
				drawLine(x - 2, y, x + 2, y);
				drawLine(x, y - 2, x, y + 2);
			}
		});
		image = img;
	}
	
	public void setObject(Object obj) {
		if (obj != null && image != null) {
			Transform t = (Transform) obj;
			Transform t2 = secondaryTransform;
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
		if (image != null && getObject() != null) {
			Transform t2 = secondaryTransform;
			if (t2 != null)
				t = new Transform().combine(t).combine(t2);
			t.setColorTransform(image);
			image.setRect(image.getRect());
			image.refreshImage();
		}
	}
	
	public void onChangeScale(Transform t) {
		if (image != null) {
			Transform t2 = secondaryTransform;
			if (t2 != null)
				t = new Transform().combine(t).combine(t2);
			image.setScale(t.scaleX / 100f, t.scaleY / 100f);
			image.redraw();
		}
	}

	public void updateColorTransform(LImage img) {
		Transform t1 = (Transform) getObject();
		if (t1 != null) {
			Transform t2 = secondaryTransform;
			if (t2 != null) {
				t1.setColorTransform(img, t2);
			} else {
				t1.setColorTransform(img);
			}
		}
	}

	@Override
	public Class<?> getType() {
		return Transform.class;
	}

}
