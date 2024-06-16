package gui.views.system;

import data.config.Config;
import gui.Tooltip;
import gui.Vocab;
import gui.widgets.ImageButton;
import lui.base.LFlags;
import lui.container.LContainer;
import lui.container.LFrame;
import lui.gson.GDefaultObjectEditor;
import lui.widget.LCombo;
import lui.widget.LLabel;
import lui.widget.LSpinner;
import lui.widget.LText;

import java.lang.reflect.Type;

public class MainEditor extends GDefaultObjectEditor<Config> {
    public MainEditor(LContainer parent, boolean doubleBuffered) {
        super(parent, doubleBuffered);
    }

    @Override
    protected void createContent(int style) {
        setGridLayout(2);
		setEqualCells(true, false);

		// Name

		LFrame grpIdentity = new LFrame(this, Vocab.instance.IDENTITY);
		grpIdentity.setGridLayout(6);
		grpIdentity.setHoverText(Tooltip.instance.IDENTITY);
		grpIdentity.getCellData().setExpand(true, false);

		new LLabel(grpIdentity, Vocab.instance.PROJECTNAME, Tooltip.instance.PROJECTNAME);
		LText txtName = new LText(grpIdentity);
		txtName.getCellData().setSpread(5, 1);
		txtName.getCellData().setExpand(true, false);
		addControl(txtName, "name");

		// Cover

		new LLabel(grpIdentity, Vocab.instance.COVER, Tooltip.instance.COVER);
		LText txtCover = new LText(grpIdentity, true);
		txtCover.getCellData().setExpand(true, false);
		ImageButton btnCover = new ImageButton(grpIdentity, true);
		btnCover.setNameWidget(txtCover);
		addControl(btnCover, "coverID");

		// Logo

		new LLabel(grpIdentity, Vocab.instance.LOGO, Tooltip.instance.LOGO);
		LText txtLogo = new LText(grpIdentity, true);
		txtLogo.getCellData().setExpand(true, false);
		ImageButton btnLogo = new ImageButton(grpIdentity, true);
		btnLogo.setNameWidget(txtLogo);
		addControl(btnLogo, "logoID");

		// Platform

		LFrame grpExecution = new LFrame(this, Vocab.instance.EXECUTION);
		grpExecution.setGridLayout(4);
		grpExecution.setHoverText(Tooltip.instance.EXECUTION);
		grpExecution.getCellData().setExpand(true, false);

		String[] platforms = new String[] {
				Vocab.instance.DESKTOP,
				Vocab.instance.MOBILE,
				Vocab.instance.BROWSER,
				Vocab.instance.MOBILEBROWSER
			};
		new LLabel(grpExecution, Vocab.instance.PLATFORM, Tooltip.instance.PLATFORM);
		LCombo cmbPlatform = new LCombo(grpExecution, LCombo.READONLY);
		cmbPlatform.getCellData().setSpread(3, 1);
		cmbPlatform.getCellData().setExpand(true, false);
		cmbPlatform.setItems(platforms);
		addControl(cmbPlatform, "platform");
		// TODO: export button

		// FPS

		LLabel lblFPS = new LLabel(grpExecution, Vocab.instance.FPSLIMIT, Tooltip.instance.FPSLIMIT);
		lblFPS.getCellData().setAlignment(LFlags.CENTER);

		LSpinner spnFpsMin = new LSpinner(grpExecution);
		spnFpsMin.getCellData().setExpand(true, false);
		spnFpsMin.setMinimum(1);
		addControl(spnFpsMin, "fpsMin");

		new LLabel(grpExecution, " ~ ");

		LSpinner spnFpsMax = new LSpinner(grpExecution);
		spnFpsMax.getCellData().setExpand(true, false);
		spnFpsMax.setMinimum(1);
		addControl(spnFpsMax, "fpsMax");
    }

    @Override
    public Type getType() {
        return Config.class;
    }
}
