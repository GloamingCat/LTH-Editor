package gui.views.system;

import data.config.UIConfig.BaseColor;
import gui.Tooltip;
import gui.Vocab;
import gui.widgets.NodeForm;
import gui.widgets.SimpleEditableList;
import lui.base.LPrefs;
import lui.container.LContainer;
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
		setSpacing(LPrefs.GRIDSPACING);

		createMenuInterface();

		// Color

		LFrame grpBaseColors = new LFrame(this, Vocab.instance.BASECOLORS, Tooltip.instance.BASECOLORS);
		grpBaseColors.setFillLayout(false);
		BaseColorList lstColors = new BaseColorList(grpBaseColors);
		addChild(lstColors, "baseColors");

		LFrame grpColors = new LFrame(this, Vocab.instance.COLORS, Tooltip.instance.COLORS);
		grpColors.setFillLayout(false);
		formColors = new NodeForm(grpColors, false);
		formColors.getCollectionWidget().setLabelWidth(120);
		addChild(formColors, "colorMap");

		// Fonts

		LFrame grpBaseFonts = new LFrame(this, Vocab.instance.BASEFONTS, Tooltip.instance.BASEFONTS);
		grpBaseFonts.setFillLayout(false);
		//BaseFontList lstFonts = new BaseFontList(grpBaseFonts);
		//addChild(lstFonts, "baseFonts");

		LFrame grpFonts = new LFrame(this, Vocab.instance.FONTS, Tooltip.instance.FONTS);
		grpFonts.setFillLayout(false);
		//formFonts = new NodeForm(grpFonts, false);
		//formFonts.getCollectionWidget().setLabelWidth(120);
		//addChild(formFonts, "fontMap");

	}
	
	public void onVisible() {
		super.onVisible();
		formColors.setComboList(Project.current.uiConfig.getData().baseColors);
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
					//return new BaseColorDialog(parent, BaseColorDialog.OPTIONAL);
					return null;
				}
			});
		}

	}


}
