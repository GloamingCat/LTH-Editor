package gui.views.database.subcontent;

import gui.Tooltip;
import gui.Vocab;
import lui.base.event.listener.LControlListener;
import data.subcontent.Transform;
import lui.container.LContainer;
import lui.container.LImage;
import lui.graphics.LPainter;
import lui.gson.GDefaultObjectEditor;
import lui.widget.LLabel;
import lui.widget.LSpinner;

public class TransformEditor extends GDefaultObjectEditor<Transform> {
	
	public LImage image = null;
	public Transform secondaryTransform = null;

    public TransformEditor(LContainer parent) {
		super(parent, false);
	}

	protected void createContent(int style) {
		setGridLayout(4);

		LControlListener<Integer> updateColor = event -> {
            if (event.oldValue == null) return;
            onChangeColor(getObject());
        };
		LControlListener<Integer> updateOffset = event -> {
            if (event.oldValue == null) return;
            onChangeOffset(getObject());
        };
		LControlListener<Integer> updateScale = event -> {
            if (event.oldValue == null) return;
            onChangeScale(getObject());
        };
		LControlListener<Integer> updateRotation = event -> {
            if (event.oldValue == null) return;
            onChangeRotation(getObject());
        };
		
		LLabel lblOffsetX = new LLabel(this, Vocab.instance.OFFSETX, Tooltip.instance.OFFSETX);
        LSpinner spnOffsetX = new LSpinner(this);
		spnOffsetX.getCellData().setExpand(true, false);
		spnOffsetX.getCellData().setExpand(true, false);
		spnOffsetX.setMaximum(1024 * 4);
		spnOffsetX.setMinimum(-1024 * 4);
		spnOffsetX.addMenu(lblOffsetX);
		addControl(spnOffsetX, "offsetX");
		spnOffsetX.addModifyListener(updateOffset);

		LLabel lblRed = new LLabel(this, Vocab.instance.RED, Tooltip.instance.RED);
		LSpinner spnRed = new LSpinner(this);
		spnRed.getCellData().setExpand(true, false);
		spnRed.setMaximum(10000);
		spnRed.setMinimum(0);
		spnRed.addMenu(lblRed);
		addControl(spnRed, "red");
		spnRed.addModifyListener(updateColor);

		LLabel lblOffsetY = new LLabel(this, Vocab.instance.OFFSETY, Tooltip.instance.OFFSETY);
        LSpinner spnOffsetY = new LSpinner(this);
		spnOffsetY.getCellData().setExpand(true, false);
		spnOffsetY.setMaximum(1024);
		spnOffsetY.setMinimum(-1024);
		spnOffsetY.addMenu(lblOffsetY);
		addControl(spnOffsetY, "offsetY");
		spnOffsetY.addModifyListener(updateOffset);

		LLabel lblGreen = new LLabel(this, Vocab.instance.GREEN, Tooltip.instance.GREEN);
		LSpinner spnGreen = new LSpinner(this);
		spnGreen.getCellData().setExpand(true, false);
		spnGreen.setMaximum(10000);
		spnGreen.setMinimum(0);
		spnGreen.addMenu(lblGreen);
		addControl(spnGreen, "green");
		spnGreen.addModifyListener(updateColor);

		LLabel lblOffsetDepth = new LLabel(this, Vocab.instance.OFFSETDEPTH, Tooltip.instance.OFFSETDEPTH);
		LSpinner spnOffsetDepth = new LSpinner(this);
		spnOffsetDepth.getCellData().setExpand(true, false);
		spnOffsetDepth.setMinimum(-1024);
		spnOffsetDepth.setMaximum(1024);
		spnOffsetDepth.addMenu(lblOffsetDepth);
		addControl(spnOffsetDepth, "offsetDepth");

		LLabel lblBlue = new LLabel(this, Vocab.instance.BLUE, Tooltip.instance.BLUE);
		LSpinner spnBlue = new LSpinner(this);
		spnBlue.getCellData().setExpand(true, false);
		spnBlue.setMaximum(10000);
		spnBlue.setMinimum(0);
		spnBlue.addMenu(lblBlue);
		addControl(spnBlue, "blue");
		spnBlue.addModifyListener(updateColor);
		
		LLabel lblScaleX = new LLabel(this, Vocab.instance.SCALEX, Tooltip.instance.SCALEX);
        LSpinner spnScaleX = new LSpinner(this);
		spnScaleX.getCellData().setExpand(true, false);
		spnScaleX.setMaximum(10000);
		spnScaleX.setMinimum(-10000);
		spnScaleX.addMenu(lblScaleX);
		addControl(spnScaleX, "scaleX");
		spnScaleX.addModifyListener(updateScale);

		LLabel lblAlpha = new LLabel(this, Vocab.instance.ALPHA, Tooltip.instance.ALPHA);
		LSpinner spnAlpha = new LSpinner(this);
		spnAlpha.getCellData().setExpand(true, false);
		spnAlpha.setMaximum(10000);
		spnAlpha.setMinimum(0);
		spnAlpha.addMenu(lblAlpha);
		addControl(spnAlpha, "alpha");
		spnAlpha.addModifyListener(updateColor);
		
		LLabel lblScaleY = new LLabel(this, Vocab.instance.SCALEY, Tooltip.instance.SCALEY);
        LSpinner spnScaleY = new LSpinner(this);
		spnScaleY.getCellData().setExpand(true, false);
		spnScaleY.setMaximum(10000);
		spnScaleY.setMinimum(-10000);
		spnScaleY.addMenu(lblScaleY);
		addControl(spnScaleY, "scaleY");
		spnScaleY.addModifyListener(updateScale);

		LLabel lblHue = new LLabel(this, Vocab.instance.HUE, Tooltip.instance.HUE);
		LSpinner spnHue = new LSpinner(this);
		spnHue.getCellData().setExpand(true, false);
		spnHue.setMaximum(360);
		spnHue.setMinimum(-360);
		spnHue.addMenu(lblHue);
		addControl(spnHue, "hue");
		spnHue.addModifyListener(updateColor);
		
		LLabel lblRotation = new LLabel(this, Vocab.instance.ROTATION, Tooltip.instance.ROTATION);
        LSpinner spnRotation = new LSpinner(this);
		spnRotation.getCellData().setExpand(true, false);
		spnRotation.setMaximum(360);
		spnRotation.setMinimum(-360);
		spnRotation.addMenu(lblRotation);
		addControl(spnRotation, "rotation");
		spnRotation.addModifyListener(updateRotation);

		LLabel lblSaturation = new LLabel(this, Vocab.instance.SATURATION, Tooltip.instance.SATURATION);
		LSpinner spnSaturation = new LSpinner(this);
		spnSaturation.getCellData().setExpand(true, false);
		spnSaturation.setMaximum(10000);
		spnSaturation.setMinimum(0);
		spnSaturation.addMenu(lblSaturation);
		addControl(spnSaturation, "saturation");
		spnSaturation.addModifyListener(updateColor);
		
		new LLabel(this, 2, 1);
		
		LLabel lblBrightness = new LLabel(this, Vocab.instance.BRIGHTNESS, Tooltip.instance.BRIGHTNESS);
		LSpinner spnBrightness = new LSpinner(this);
		spnBrightness.getCellData().setExpand(true, false);
		spnBrightness.setMaximum(10000);
		spnBrightness.setMinimum(0);
		spnBrightness.addMenu(lblBrightness);
		addControl(spnBrightness, "brightness");
		spnBrightness.addModifyListener(updateColor);
	}
	
	public void setImage(LImage img) {
		img.addPainter(new LPainter() {
			@Override
			public void paint() {
				Transform t = getObject();
				if (t == null)
					return;
				float sw = t.scaleX / 100f;
				float sh = t.scaleY / 100f;
				int x = Math.round(t.offsetX * sw) + (int) img.getImageX();
				int y = Math.round(t.offsetY * sh) + (int) img.getImageY();
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
			image.setOffset(t.offsetX, t.offsetY);
			image.setScale(t.scaleX / 100f, t.scaleY / 100f);
			image.setRotation(t.rotation);
		}
		super.setObject(obj);
	}
	
	public void onChangeOffset(Transform t) {
		if (image != null) {
			int ox = t.offsetX;
			int oy = t.offsetY;
			if (secondaryTransform != null) {
				ox += secondaryTransform.offsetX;
				oy += secondaryTransform.offsetY;
			}
			image.setOffset(ox, oy);
			image.redraw();
		}
	}
	
	public void onChangeRotation(Transform t) {
		if (image != null) {
			int r = t.rotation;
			if (secondaryTransform != null) {
				r += secondaryTransform.rotation;
			}
			image.setRotation(r);
			image.redraw();
		}
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
		Transform t1 = getObject();
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
