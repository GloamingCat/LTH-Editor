package gui.shell.system;

import gui.Tooltip;
import gui.Vocab;
import gui.shell.ObjectEditorDialog;
import gui.views.database.subcontent.TagList;
import gui.widgets.IconButton;
import lui.base.LFlags;
import lui.container.LFrame;
import lui.container.LImage;
import lui.container.LPanel;
import lui.dialog.LWindow;
import lui.widget.LLabel;
import lui.widget.LText;

import data.config.Element;

public class ElementDialog extends ObjectEditorDialog<Element> {
	
	public ElementDialog(LWindow parent) {
		super(parent, Vocab.instance.ELEMENTSHELL);
	}
	
	@Override
	protected void createContent(int style) {
		super.createContent(style);
		
		contentEditor.setGridLayout(2);
		
		new LLabel(contentEditor, Vocab.instance.NAME, Tooltip.instance.NAME);
		
		LText txtName = new LText(contentEditor);
		addControl(txtName, "name");
		
		new LLabel(contentEditor, Vocab.instance.ICON, Tooltip.instance.ICON);
		
		LPanel icon = new LPanel(contentEditor);
		icon.setGridLayout(2);
		icon.getCellData().setAlignment(LFlags.CENTER);
		
		LImage imgIcon = new LImage(icon);
		imgIcon.getCellData().setExpand(true, false);

		IconButton btnIcon = new IconButton(icon, true);
		btnIcon.setImageWidget(imgIcon);
		addControl(btnIcon, "icon");

		LFrame grpTags = new LFrame(contentEditor, (String) Vocab.instance.TAGS);
		grpTags.setFillLayout(true);
		grpTags.setHoverText(Tooltip.instance.TAGS);
		grpTags.getCellData().setSpread(2, 1);
		grpTags.getCellData().setExpand(false, true);
		grpTags.getCellData().setMinimumSize(150, 100);
		TagList lstTroops = new TagList(grpTags);
		addChild(lstTroops, "tags");

		pack();
	}

}
