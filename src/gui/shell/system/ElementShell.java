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
		icon.setAlignment(LFlags.CENTER);
		
		LImage imgIcon = new LImage(icon);
		imgIcon.setExpand(true, false);

		IconButton btnIcon = new IconButton(icon, true);
		btnIcon.setImageWidget(imgIcon);
		addControl(btnIcon, "icon");
		LFrame frame = new LFrame(contentEditor, (String) Vocab.instance.TAGS);
		frame.setFillLayout(true);
		
		LFrame grpTags = frame;
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
