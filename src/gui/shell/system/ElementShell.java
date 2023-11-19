package gui.shell.system;

import gui.Vocab;
import gui.shell.ObjectShell;
import gui.views.database.subcontent.TagList;
import gui.widgets.IconButton;
import lwt.container.LFrame;
import lwt.container.LPanel;
import lwt.dialog.LShell;
import lwt.widget.LImage;
import lwt.widget.LLabel;
import lwt.widget.LText;

import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;

import data.config.Element;

public class ElementShell extends ObjectShell<Element> {
	
	public ElementShell(LShell parent) {
		super(parent);
		
		contentEditor.setLayout(new GridLayout(2, false));
		
		new LLabel(contentEditor, Vocab.instance.NAME);
		
		LText txtName = new LText(contentEditor);
		addControl(txtName, "name");
		
		new LLabel(contentEditor, Vocab.instance.ICON);
		
		LPanel icon = new LPanel(contentEditor, 2, false);
		icon.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, false, false, 1, 1));
		
		LImage imgIcon = new LImage(icon, SWT.NONE);
		imgIcon.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, false, 1, 1));

		IconButton btnIcon = new IconButton(icon, true);
		btnIcon.setImageWidget(imgIcon);
		addControl(btnIcon, "icon");
		
		LFrame grpTags = new LFrame(contentEditor, Vocab.instance.TAGS, true, true);
		GridData gd_grpTags = new GridData(SWT.FILL, SWT.FILL, false, true, 2, 1);
		gd_grpTags.widthHint = 155;
		gd_grpTags.heightHint = 98;
		grpTags.setLayoutData(gd_grpTags);
		TagList lstTroops = new TagList(grpTags);
		addChild(lstTroops, "tags");

		pack();
	}

}
