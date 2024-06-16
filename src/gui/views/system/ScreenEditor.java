package gui.views.system;

import data.config.Config;
import gui.Tooltip;
import gui.Vocab;
import gui.widgets.CheckBoxPanel;
import lui.base.LPrefs;
import lui.container.LContainer;
import lui.container.LFrame;
import lui.container.LPanel;
import lui.gson.GDefaultObjectEditor;
import lui.widget.LCheckBox;
import lui.widget.LCombo;
import lui.widget.LLabel;
import lui.widget.LSpinner;

import java.lang.reflect.Type;

public class ScreenEditor extends GDefaultObjectEditor<Config.Screen> {
    public ScreenEditor(LContainer parent, boolean doubleBuffered) {
        super(parent, doubleBuffered);
    }

    @Override
    protected void createContent(int style) {
        setGridLayout(3);

		new LLabel(this, Vocab.instance.NATIVESIZE, Tooltip.instance.NATIVESIZE);

		LPanel nativeSize = new LPanel(this);
		nativeSize.setGridLayout(3);
		nativeSize.getCellData().setExpand(true, false);
		nativeSize.getCellData().setSpread(2, 1);

		LSpinner spnNativeWidth = new LSpinner(nativeSize);
		spnNativeWidth.getCellData().setExpand(true, false);
		spnNativeWidth.setMinimum(1);
		addControl(spnNativeWidth, "nativeWidth");

		new LLabel(nativeSize, "x");
		LSpinner spnNativeHeight = new LSpinner(nativeSize);
		spnNativeHeight.getCellData().setExpand(true, false);
		spnNativeHeight.setMinimum(1);
		addControl(spnNativeHeight, "nativeHeight");

		new LLabel(this, Vocab.instance.SCALEFACTOR, Tooltip.instance.SCALEFACTOR);

		LPanel scaleFactor = new LPanel(this);
		scaleFactor.setGridLayout(3);
		scaleFactor.getCellData().setExpand(true, false);
		scaleFactor.getCellData().setSpread(2, 1);

		LSpinner spnWidthScale = new LSpinner(scaleFactor);
		spnWidthScale.getCellData().setExpand(true, false);
		spnWidthScale.setMinimum(1);
		addControl(spnWidthScale, "widthScale");

		new LLabel(scaleFactor, "x");

		LSpinner spnHeightScale = new LSpinner(scaleFactor);
		spnHeightScale.getCellData().setExpand(true, false);
		spnHeightScale.setMinimum(1);
		addControl(spnHeightScale, "heightScale");

		LFrame scaleType = new LFrame(this, Vocab.instance.SCALETYPE);
		scaleType.setHoverText(Tooltip.instance.SCALETYPE);
		scaleType.setGridLayout(2);
		scaleType.getCellData().setExpand(true, true);
		scaleType.getCellData().setSpread(3, 1);

		String[] scaleTypes = new String[] {
			Vocab.instance.NOSCALE,
			Vocab.instance.INTEGERONLY,
			Vocab.instance.KEEPRATIO,
			Vocab.instance.FREESCALE
		};

		new LLabel(scaleType, Vocab.instance.PCSCALETYPE, Tooltip.instance.PCSCALETYPE).getCellData().setTargetSize(LPrefs.LABELWIDTH / 2, -1);
		LCombo cmbScale = new LCombo(scaleType, LCombo.READONLY);
		cmbScale.getCellData().setExpand(true, false);
		cmbScale.setItems(scaleTypes);
		addControl(cmbScale, "scaleType");

		new LLabel(scaleType, Vocab.instance.MOBILESCALETYPE, Tooltip.instance.MOBILESCALETYPE).getCellData().setTargetSize(LPrefs.LABELWIDTH / 2, -1);
		LCombo cmbScaleMobile = new LCombo(scaleType, LCombo.READONLY);
		cmbScaleMobile.getCellData().setExpand(true, false);
		cmbScaleMobile.setItems(scaleTypes);
		addControl(cmbScaleMobile, "mobileScaleType");

		LPanel checkScreen = new CheckBoxPanel(this);
		checkScreen.getCellData().setSpread(3, 1);
		checkScreen.getCellData().setExpand(true, true);

		LCheckBox btnPixelPerfect = new LCheckBox(checkScreen);
		btnPixelPerfect.setText(Vocab.instance.PIXELPERFECT);
		btnPixelPerfect.setHoverText(Tooltip.instance.PIXELPERFECT);
		addControl(btnPixelPerfect, "pixelPerfect");

		LCheckBox btnVSync = new LCheckBox(checkScreen);
		btnVSync.setText(Vocab.instance.VSYNC);
		btnVSync.setHoverText(Tooltip.instance.VSYNC);
		addControl(btnVSync, "vsync");

    }

    @Override
    public Type getType() {
        return Config.Screen.class;
    }
}
