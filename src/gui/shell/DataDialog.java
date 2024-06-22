package gui.shell;

import data.Data;
import gui.Tooltip;
import gui.Vocab;
import gui.views.database.subcontent.TagList;
import lui.base.LFlags;
import lui.base.LPrefs;
import lui.container.LFrame;
import lui.dialog.LWindow;
import lui.gson.GObjectDialog;
import lui.widget.LLabel;
import lui.widget.LText;

public class DataDialog extends GObjectDialog<Data> {

	public static final int EDITTAGS = 1;

    public DataDialog(LWindow parent, String title, int flags) {
		super(parent, title);
	}
	
	@Override
	protected void createContent(int style) {
		super.createContent(style);
		contentEditor.setGridLayout(2);
		
		new LLabel(contentEditor, Vocab.instance.KEY, Tooltip.instance.KEY);
        LText txtKey = new LText(contentEditor);
		txtKey.getCellData().setExpand(true, false);
		addControl(txtKey, "key");
		
		new LLabel(contentEditor, LFlags.TOP, Vocab.instance.NAME, Tooltip.instance.DISPLAYNAME);
        LText txtName = new LText(contentEditor);
		txtName.getCellData().setExpand(true, false);
		addControl(txtName, "name");

		if ((style & EDITTAGS) > 0) {
			LFrame grpTags = new LFrame(contentEditor, Vocab.instance.TAGS);
			grpTags.setFillLayout(true);
			grpTags.getCellData().setExpand(true, false);
			grpTags.getCellData().setSpread(1, 2);
			grpTags.getCellData().setRequiredSize(LPrefs.LABELWIDTH, LPrefs.LISTHEIGHT);
			grpTags.getCellData().setTargetSize(LPrefs.LABELWIDTH * 2, LPrefs.LISTHEIGHT * 2);
			TagList lstTags = new TagList(grpTags);
			addChild(lstTags, "tags");
		}
	}
	
}
