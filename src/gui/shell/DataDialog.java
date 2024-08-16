package gui.shell;

import data.Data;
import gui.Tooltip;
import gui.Vocab;
import gui.views.database.subcontent.TagList;
import lui.base.LPrefs;
import lui.container.LFrame;
import lui.dialog.LWindow;
import lui.gson.GObjectDialog;
import lui.widget.LLabel;
import lui.widget.LText;

public class DataDialog extends GObjectDialog<Data> {

	public static final int TAGS = 1;
	public static final int NAME = 2;
	public static final int KEY = 4;

	private TagList tagList;
	private LFrame grpTags;

    public DataDialog(LWindow parent, String title, int flags) {
		super(parent, flags, title);
	}

	public DataDialog(LWindow parent, String title, String tagTile, int flags) {
		super(parent, flags | TAGS, title);
		setTagTitle(tagTile);
	}
	
	@Override
	protected void createContent(int style) {
		super.createContent(style);
		contentEditor.setGridLayout(2);

		if ((style & KEY) > 0) {
			new LLabel(contentEditor, Vocab.instance.KEY, Tooltip.instance.KEY);
			LText txtKey = new LText(contentEditor);
			txtKey.getCellData().setExpand(true, false);
			addControl(txtKey, "key");
		}

		if ((style & NAME) > 0) {
			new LLabel(contentEditor, Vocab.instance.NAME, Tooltip.instance.DISPLAYNAME);
			LText txtName = new LText(contentEditor);
			txtName.getCellData().setExpand(true, false);
			addControl(txtName, "name");
		}

		if ((style & TAGS) > 0) {
			grpTags = new LFrame(contentEditor, Vocab.instance.TAGS);
			grpTags.setFillLayout(true);
			grpTags.getCellData().setExpand(true, false);
			grpTags.getCellData().setSpread(2, 1);
			grpTags.getCellData().setRequiredSize(LPrefs.LABELWIDTH, LPrefs.LISTHEIGHT);
			grpTags.getCellData().setTargetSize(LPrefs.LABELWIDTH * 2, LPrefs.LISTHEIGHT * 2);
			tagList = new TagList(grpTags);
			addChild(tagList, "tags");
		}
	}

	public void setTagTitle(String tagTile) {
		tagList.setTitle(tagTile);
		grpTags.setTitle(tagTile);
	}

}
