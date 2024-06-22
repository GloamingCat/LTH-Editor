package gui.shell.system;

import gui.Tooltip;
import gui.Vocab;
import gui.widgets.IconButtonPanel;
import lui.gson.GObjectDialog;
import gui.views.database.subcontent.TagList;
import lui.container.LFrame;
import lui.dialog.LWindow;
import lui.widget.LLabel;
import lui.widget.LText;

import data.config.Element;

public class ElementDialog extends GObjectDialog<Element> {
	
	public ElementDialog(LWindow parent) {
		super(parent, 300, 300, Vocab.instance.ELEMENTSHELL);
	}
	
	@Override
	protected void createContent(int style) {
		super.createContent(style);
		
		contentEditor.setGridLayout(2);
		
		new LLabel(contentEditor, Vocab.instance.NAME, Tooltip.instance.DISPLAYNAME);
		LText txtName = new LText(contentEditor);
		txtName.getCellData().setExpand(true, false);
		addControl(txtName, "name");

		LLabel lblIcon = new LLabel(contentEditor, Vocab.instance.ICON, Tooltip.instance.ICON);
		new IconButtonPanel(contentEditor, lblIcon, contentEditor, "icon");

		LFrame grpTags = new LFrame(contentEditor, Vocab.instance.TAGS);
		grpTags.setFillLayout(true);
		grpTags.setHoverText(Tooltip.instance.TAGS);
		grpTags.getCellData().setSpread(2, 1);
		grpTags.getCellData().setExpand(true, true);
		grpTags.getCellData().setRequiredSize(150, 100);
		TagList lstTroops = new TagList(grpTags);
		addChild(lstTroops, "tags");
	}

}
