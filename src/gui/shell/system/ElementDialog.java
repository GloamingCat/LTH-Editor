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
		setRequiredSize(300, 300);
		setSize(300, 300);
	}
	
	@Override
	protected void createContent(int style) {
		super.createContent(style);
		
		contentEditor.setGridLayout(2);
		
		new LLabel(contentEditor, Vocab.instance.NAME, Tooltip.instance.NAME);
		LText txtName = new LText(contentEditor);
		txtName.getCellData().setExpand(true, false);
		addControl(txtName, "name");

		LLabel lblIcon = new LLabel(contentEditor, Vocab.instance.ICON, Tooltip.instance.ICON);
		LPanel compositeIcon = new LPanel(contentEditor);
		compositeIcon.setGridLayout(2);
		compositeIcon.getCellData().setExpand(true, false);
		LImage imgIcon = new LImage(compositeIcon);
		imgIcon.getCellData().setExpand(true, true);
		imgIcon.getCellData().setRequiredSize(0, 48);
		imgIcon.setAlignment(LFlags.LEFT | LFlags.TOP);

		IconButton btnGraphics = new IconButton(compositeIcon, true);
		btnGraphics.setImageWidget(imgIcon);
		btnGraphics.addMenu(lblIcon);
		btnGraphics.addMenu(imgIcon);
		addControl(btnGraphics, "icon");

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
