package gui.views.system;

import data.config.UIConfig.BaseColor;
import data.config.UIConfig.BaseFont;
import gui.Tooltip;
import gui.Vocab;
import gui.shell.system.BaseColorDialog;
import gui.shell.system.FontDialog;
import gui.widgets.NodeForm;
import gui.widgets.SimpleEditableList;
import lui.container.LContainer;
import lui.container.LFlexPanel;
import lui.container.LFrame;
import lui.dialog.LObjectDialog;
import lui.dialog.LWindow;
import lui.dialog.LWindowFactory;
import lui.editor.LObjectEditor;
import project.Project;

public class UIEditor extends LObjectEditor<Object> {

	NodeForm formColors;
	NodeForm formFonts;

	public UIEditor(LContainer parent) {
		super(parent, true);
	}

	@Override
	protected void createContent(int style) {
		setFillLayout(true);

		createMenuInterface();

		LFlexPanel div = new LFlexPanel(this);
		LFlexPanel colors = new LFlexPanel(div);
		LFlexPanel fonts = new LFlexPanel(div);

		// Color

		LFrame grpBaseColors = new LFrame(colors, Vocab.instance.BASECOLORS, Tooltip.instance.BASECOLORS);
		grpBaseColors.setFillLayout(false);
		BaseColorList lstColors = new BaseColorList(grpBaseColors);
		addChild(lstColors, "baseColors");

		LFrame grpColors = new LFrame(colors, Vocab.instance.COLORS, Tooltip.instance.COLORS);
		grpColors.setFillLayout(false);
		formColors = new NodeForm(grpColors, true);
		formColors.setControlWidth(100);
		formColors.getCollectionWidget().setLabelWidth(100);
		formColors.getCollectionWidget().setInsertNewEnabled(true);
		formColors.getCollectionWidget().setDeleteEnabled(true);
		formColors.getCollectionWidget().setMoveEnabled(true);
		addChild(formColors, "colorMap");

		// Fonts

		LFrame grpBaseFonts = new LFrame(fonts, Vocab.instance.BASEFONTS, Tooltip.instance.BASEFONTS);
		grpBaseFonts.setFillLayout(false);
		BaseFontList lstFonts = new BaseFontList(grpBaseFonts);
		addChild(lstFonts, "baseFonts");

		LFrame grpFonts = new LFrame(fonts, Vocab.instance.FONTS, Tooltip.instance.FONTS);
		grpFonts.setFillLayout(false);
		formFonts = new NodeForm(grpFonts, true);
		formFonts.setControlWidth(100);
		formFonts.getCollectionWidget().setLabelWidth(100);
		formFonts.getCollectionWidget().setInsertNewEnabled(true);
		formFonts.getCollectionWidget().setDeleteEnabled(true);
		formFonts.getCollectionWidget().setMoveEnabled(true);
		addChild(formFonts, "fontMap");

		colors.setWeights(1, 3);
		fonts.setWeights(1, 3);
		div.setWeights(1, 1);
	}
	
	public void onVisible() {
		super.onVisible();
		formColors.setComboList(Project.current.uiConfig.getData().baseColors);
		formFonts.setComboList(Project.current.uiConfig.getData().baseFonts);
		setObject(Project.current.uiConfig.getData());
	}

	@Override
	public Object duplicateData(Object obj) {
		return null;
	}

	@Override
	public String encodeData(Object obj) {
		return null;
	}

	@Override
	public Object decodeData(String str) {
		return null;
	}

	@Override
	public boolean canDecode(String str) {
		return false;
	}

	private static class BaseColorList extends SimpleEditableList<BaseColor> {

		public BaseColorList(LContainer parent) {
			super(parent);
			type = BaseColor.class;
			setIncludeID(false);
			setShellFactory(new LWindowFactory<>() {
				@Override
				public LObjectDialog<BaseColor> createWindow(LWindow parent) {
					return new BaseColorDialog(parent);
				}
			});
		}

	}

	private static class BaseFontList extends SimpleEditableList<BaseFont> {

		public BaseFontList(LContainer parent) {
			super(parent);
			type = BaseFont.class;
			setIncludeID(false);
			setShellFactory(new LWindowFactory<>() {
				@Override
				public LObjectDialog<BaseFont> createWindow(LWindow parent) {
					return new FontDialog(parent);
				}
			});
		}

	}

}
