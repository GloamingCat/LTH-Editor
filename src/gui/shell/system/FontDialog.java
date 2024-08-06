package gui.shell.system;

import data.config.UIConfig.BaseFont;
import data.subcontent.FontData;
import gui.Tooltip;
import gui.Vocab;
import lui.base.LPrefs;
import lui.dialog.LWindow;
import lui.gson.GObjectDialog;
import lui.widget.LFileSelector;
import lui.widget.LLabel;
import lui.widget.LSpinner;
import lui.widget.LText;

import project.Project;

public class FontDialog extends GObjectDialog<BaseFont> {

	private LFileSelector selFile;
	private LText txtName;
	private LSpinner spnSize;

	public FontDialog(LWindow parent) {
		super(parent, 300, 300,0, Vocab.instance.FONTSHELL);
	}

	@Override
	protected void createContent(int flags) {
		super.createContent(flags);

		contentEditor.setMargins(LPrefs.FRAMEMARGIN, LPrefs.FRAMEMARGIN);
		contentEditor.setGridLayout(2);

		new LLabel(contentEditor, Vocab.instance.NAME, Tooltip.instance.KEY);
		txtName = new LText(contentEditor);
		txtName.getCellData().setExpand(true, false);

		selFile = new LFileSelector(content, false);
		selFile.addFileRestriction( (f) -> {
			String name = f.getName();
			return name.endsWith(".ttf") || name.endsWith(".otf") || name.endsWith(".woff") || name.endsWith(".woff2");
		} );
		selFile.setFolder(Project.current.fontPath());
		selFile.getCellData().setExpand(true, true);
		selFile.getCellData().setSpread(2, 1);

		new LLabel(contentEditor, Vocab.instance.LENGTH, Tooltip.instance.LENGTH);
		spnSize = new LSpinner(contentEditor);
		spnSize.getCellData().setExpand(true, false);

	}

	@Override
	public void open(BaseFont initial) {
		selFile.setSelectedFile(initial.font.getPath());
		spnSize.setValue(Math.round(initial.font.size * 2));
		txtName.setValue(initial.name);
		super.open(initial);
	}

	@Override
	protected BaseFont createResult(BaseFont initial) {
		String[] font = selFile.getSelectedFile().split("\\.");
		return new BaseFont(txtName.getValue(),
			new FontData(font[0],
				font[1],
				spnSize.getValue() / 2f
			)
		);
	}
	
}
