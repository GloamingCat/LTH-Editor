package gui.shell.system;

import gui.Tooltip;
import gui.Vocab;
import gui.shell.ObjectShell;
import gui.views.database.subcontent.TagList;
import gui.widgets.IconButton;
import lwt.LFlags;
import lwt.container.LFrame;
import lwt.container.LImage;
import lwt.container.LPanel;
import lwt.dialog.LShell;
import lwt.widget.LLabel;
import lwt.widget.LText;

import data.config.Element;

public class ElementShell extends ObjectShell<Element> {
	
	public ElementShell(LShell parent) {
		super(parent);
		
		contentEditor.setGridLayout(2, false);
		
		new LLabel(contentEditor, Vocab.instance.NAME, Tooltip.instance.NAME);
		
		LText txtName = new LText(contentEditor);
		addControl(txtName, "name");
		
		new LLabel(contentEditor, Vocab.instance.ICON, Tooltip.instance.ICON);
		
		LPanel icon = new LPanel(contentEditor, 2, false);
		icon.setAlignment(LFlags.CENTER);
		
		LImage imgIcon = new LImage(icon);
		imgIcon.setExpand(true, false);

		IconButton btnIcon = new IconButton(icon, true);
		btnIcon.setImageWidget(imgIcon);
		addControl(btnIcon, "icon");
		
		LFrame grpTags = new LFrame(contentEditor, Vocab.instance.TAGS, true, true);
		grpTags.setHoverText(Tooltip.instance.TAGS);
		grpTags.setSpread(2, 1);
		grpTags.setExpand(false, true);
		grpTags.setMinimumWidth(150);
		grpTags.setMinimumHeight(100);
		TagList lstTroops = new TagList(grpTags);
		addChild(lstTroops, "tags");

		pack();
	}

}
