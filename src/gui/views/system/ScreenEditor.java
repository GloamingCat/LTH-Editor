package gui.views.system;

import data.config.Config;
import gui.Tooltip;
import gui.Vocab;
import gui.widgets.CheckBoxPanel;
import lui.base.LPrefs;
import lui.container.LContainer;
import lui.container.LPanel;
import lui.gson.GDefaultObjectEditor;
import lui.widget.*;

import java.lang.reflect.Type;

public class ScreenEditor extends GDefaultObjectEditor<Config.Screen> {
    public ScreenEditor(LContainer parent, boolean doubleBuffered) {
        super(parent, doubleBuffered);
    }

    @Override
    protected void createContent(int style) {
        setGridLayout(4);

		new LLabel(this, Vocab.instance.NATIVESIZE, Tooltip.instance.NATIVESIZE)
				.getCellData().setRequiredSize(LPrefs.LABELWIDTH, LPrefs.WIDGETHEIGHT);
		LSpinner spnNativeWidth = new LSpinner(this);
		spnNativeWidth.getCellData().setExpand(true, false);
		spnNativeWidth.setMinimum(1);
		addControl(spnNativeWidth, "nativeWidth");

		new LLabel(this, "x");
		LSpinner spnNativeHeight = new LSpinner(this);
		spnNativeHeight.getCellData().setExpand(true, false);
		spnNativeHeight.setMinimum(1);
		addControl(spnNativeHeight, "nativeHeight");

		new LLabel(this, Vocab.instance.SCALEFACTOR, Tooltip.instance.SCALEFACTOR);
		LSpinner spnWidthScale = new LSpinner(this);
		spnWidthScale.getCellData().setExpand(true, false);
		spnWidthScale.setMinimum(1);
		addControl(spnWidthScale, "widthScale");

		new LLabel(this, "x");
		LSpinner spnHeightScale = new LSpinner(this);
		spnHeightScale.getCellData().setExpand(true, false);
		spnHeightScale.setMinimum(1);
		addControl(spnHeightScale, "heightScale");

		new LLabel(this, Vocab.instance.DEFAULTSPEED, Tooltip.instance.CAMERASPEED);
		LSpinner spnSpeed = new LSpinner(this);
		spnSpeed.getCellData().setExpand(true, false);
		spnSpeed.setMinimum(1);
		addControl(spnSpeed, "defaultSpeed");

		new LLabel(this, 2, 1);

		LSeparator scaleType = new LSeparator(this, Vocab.instance.SCALETYPE, Tooltip.instance.SCALETYPE);
		scaleType.getCellData().setExpand(true, false);
		scaleType.getCellData().setSpread(4, 1);

		String[] scaleTypes = new String[] {
			Vocab.instance.NOSCALE,
			Vocab.instance.INTEGERONLY,
			Vocab.instance.KEEPRATIO,
			Vocab.instance.FREESCALE
		};

		new LLabel(this, Vocab.instance.PCSCALETYPE, Tooltip.instance.PCSCALETYPE).getCellData().setTargetSize(LPrefs.LABELWIDTH / 2, -1);
		LCombo cmbScale = new LCombo(this, LCombo.READONLY);
		cmbScale.getCellData().setExpand(true, false);
		cmbScale.getCellData().setSpread(3, 1);
		cmbScale.setItems(scaleTypes);
		addControl(cmbScale, "scaleType");

		new LLabel(this, Vocab.instance.MOBILESCALETYPE, Tooltip.instance.MOBILESCALETYPE).getCellData().setTargetSize(LPrefs.LABELWIDTH / 2, -1);
		LCombo cmbScaleMobile = new LCombo(this, LCombo.READONLY);
		cmbScaleMobile.getCellData().setExpand(true, false);
		cmbScaleMobile.getCellData().setSpread(3, 1);
		cmbScaleMobile.setItems(scaleTypes);
		addControl(cmbScaleMobile, "mobileScaleType");

		new LSeparator(this, true).getCellData().setSpread(4, 1);

		LPanel checkScreen = new CheckBoxPanel(this);
		checkScreen.getCellData().setSpread(4, 1);
		checkScreen.getCellData().setExpand(true, true);

		LCheckBox btnPixelPerfect = new LCheckBox(checkScreen);
		btnPixelPerfect.setText(Vocab.instance.PIXELPERFECT);
		btnPixelPerfect.setHoverText(Tooltip.instance.PIXELPERFECT);
		addControl(btnPixelPerfect, "pixelPerfect");

		LCheckBox btnVSync = new LCheckBox(checkScreen);
		btnVSync.setText(Vocab.instance.VSYNC);
		btnVSync.setHoverText(Tooltip.instance.VSYNC);
		addControl(btnVSync, "vsync");

		new LLabel(this, 4, 1).getCellData().setExpand(false, true);

    }

    @Override
    public Type getType() {
        return Config.Screen.class;
    }
}
