package gui.views.database.subcontent;

import gui.Tooltip;
import gui.Vocab;
import lui.base.event.listener.LControlListener;
import data.subcontent.Transform;
import lui.container.LContainer;
import lui.gson.GDefaultObjectEditor;
import lui.widget.LLabel;
import lui.widget.LSpinner;

import java.util.function.Consumer;

public class TransformEditor extends GDefaultObjectEditor<Transform> {

	public Consumer<Transform> onChange;

    public TransformEditor(LContainer parent) {
		super(parent, false);
	}

	protected void createContent(int style) {
		setGridLayout(4);

		LControlListener<Integer> updateListener = e -> {
           if (onChange != null && e.oldValue != null)
				onChange.accept(getObject());
        };
        addSelectionListener(e -> {
			if (onChange != null && e.data != null)
				onChange.accept((Transform) e.data);
		});
		
		LLabel lblOffsetX = new LLabel(this, Vocab.instance.OFFSETX, Tooltip.instance.OFFSETX);
        LSpinner spnOffsetX = new LSpinner(this);
		spnOffsetX.getCellData().setExpand(true, false);
		spnOffsetX.getCellData().setExpand(true, false);
		spnOffsetX.setMaximum(1024 * 4);
		spnOffsetX.setMinimum(-1024 * 4);
		spnOffsetX.addMenu(lblOffsetX);
		addControl(spnOffsetX, "offsetX");
		spnOffsetX.addModifyListener(updateListener);

		LLabel lblRed = new LLabel(this, Vocab.instance.RED, Tooltip.instance.RED);
		LSpinner spnRed = new LSpinner(this);
		spnRed.getCellData().setExpand(true, false);
		spnRed.setMaximum(10000);
		spnRed.setMinimum(0);
		spnRed.addMenu(lblRed);
		addControl(spnRed, "red");
		spnRed.addModifyListener(updateListener);

		LLabel lblOffsetY = new LLabel(this, Vocab.instance.OFFSETY, Tooltip.instance.OFFSETY);
        LSpinner spnOffsetY = new LSpinner(this);
		spnOffsetY.getCellData().setExpand(true, false);
		spnOffsetY.setMaximum(1024);
		spnOffsetY.setMinimum(-1024);
		spnOffsetY.addMenu(lblOffsetY);
		addControl(spnOffsetY, "offsetY");
		spnOffsetY.addModifyListener(updateListener);

		LLabel lblGreen = new LLabel(this, Vocab.instance.GREEN, Tooltip.instance.GREEN);
		LSpinner spnGreen = new LSpinner(this);
		spnGreen.getCellData().setExpand(true, false);
		spnGreen.setMaximum(10000);
		spnGreen.setMinimum(0);
		spnGreen.addMenu(lblGreen);
		addControl(spnGreen, "green");
		spnGreen.addModifyListener(updateListener);

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
		spnBlue.addModifyListener(updateListener);
		
		LLabel lblScaleX = new LLabel(this, Vocab.instance.SCALEX, Tooltip.instance.SCALEX);
        LSpinner spnScaleX = new LSpinner(this);
		spnScaleX.getCellData().setExpand(true, false);
		spnScaleX.setMaximum(10000);
		spnScaleX.setMinimum(-10000);
		spnScaleX.addMenu(lblScaleX);
		addControl(spnScaleX, "scaleX");
		spnScaleX.addModifyListener(updateListener);

		LLabel lblAlpha = new LLabel(this, Vocab.instance.ALPHA, Tooltip.instance.ALPHA);
		LSpinner spnAlpha = new LSpinner(this);
		spnAlpha.getCellData().setExpand(true, false);
		spnAlpha.setMaximum(10000);
		spnAlpha.setMinimum(0);
		spnAlpha.addMenu(lblAlpha);
		addControl(spnAlpha, "alpha");
		spnAlpha.addModifyListener(updateListener);
		
		LLabel lblScaleY = new LLabel(this, Vocab.instance.SCALEY, Tooltip.instance.SCALEY);
        LSpinner spnScaleY = new LSpinner(this);
		spnScaleY.getCellData().setExpand(true, false);
		spnScaleY.setMaximum(10000);
		spnScaleY.setMinimum(-10000);
		spnScaleY.addMenu(lblScaleY);
		addControl(spnScaleY, "scaleY");
		spnScaleY.addModifyListener(updateListener);

		LLabel lblHue = new LLabel(this, Vocab.instance.HUE, Tooltip.instance.HUE);
		LSpinner spnHue = new LSpinner(this);
		spnHue.getCellData().setExpand(true, false);
		spnHue.setMaximum(360);
		spnHue.setMinimum(-360);
		spnHue.addMenu(lblHue);
		addControl(spnHue, "hue");
		spnHue.addModifyListener(updateListener);
		
		LLabel lblRotation = new LLabel(this, Vocab.instance.ROTATION, Tooltip.instance.ROTATION);
        LSpinner spnRotation = new LSpinner(this);
		spnRotation.getCellData().setExpand(true, false);
		spnRotation.setMaximum(360);
		spnRotation.setMinimum(-360);
		spnRotation.addMenu(lblRotation);
		addControl(spnRotation, "rotation");
		spnRotation.addModifyListener(updateListener);

		LLabel lblSaturation = new LLabel(this, Vocab.instance.SATURATION, Tooltip.instance.SATURATION);
		LSpinner spnSaturation = new LSpinner(this);
		spnSaturation.getCellData().setExpand(true, false);
		spnSaturation.setMaximum(10000);
		spnSaturation.setMinimum(0);
		spnSaturation.addMenu(lblSaturation);
		addControl(spnSaturation, "saturation");
		spnSaturation.addModifyListener(updateListener);
		
		new LLabel(this, 2, 1);
		
		LLabel lblBrightness = new LLabel(this, Vocab.instance.BRIGHTNESS, Tooltip.instance.BRIGHTNESS);
		LSpinner spnBrightness = new LSpinner(this);
		spnBrightness.getCellData().setExpand(true, false);
		spnBrightness.setMaximum(10000);
		spnBrightness.setMinimum(0);
		spnBrightness.addMenu(lblBrightness);
		addControl(spnBrightness, "brightness");
		spnBrightness.addModifyListener(updateListener);
	}

	@Override
	public Class<?> getType() {
		return Transform.class;
	}

}
