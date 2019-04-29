package gui.shell.system;

import gui.Vocab;
import gui.shell.ObjectShell;
import gui.views.database.subcontent.TagList;
import gui.widgets.IconButton;

import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Group;
import org.eclipse.swt.widgets.Shell;

import lwt.widget.LImage;
import lwt.widget.LText;

import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.layout.FillLayout;

import data.config.Element;

public class ElementShell extends ObjectShell<Element> {
	
	public ElementShell(Shell parent) {
		super(parent);
		
		contentEditor.setLayout(new GridLayout(2, false));
		
		Label lblName = new Label(contentEditor, SWT.NONE);
		lblName.setText(Vocab.instance.NAME);
		
		LText txtName = new LText(contentEditor, SWT.NONE);
		txtName.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1));
		addControl(txtName, "name");
		
		Label lblIcon = new Label(contentEditor, SWT.NONE);
		lblIcon.setText(Vocab.instance.ICON);
		
		Composite icon = new Composite(contentEditor, SWT.NONE);
		icon.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, false, false, 1, 1));
		GridLayout gl_icon = new GridLayout(2, false);
		gl_icon.marginHeight = 0;
		gl_icon.marginWidth = 0;
		icon.setLayout(gl_icon);
		
		LImage imgIcon = new LImage(icon, SWT.NONE);
		imgIcon.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, false, 1, 1));

		IconButton btnIcon = new IconButton(icon, 1);
		btnIcon.setImage(imgIcon);
		addControl(btnIcon, "icon");
		
		Group grpTags = new Group(contentEditor, SWT.NONE);
		grpTags.setLayout(new FillLayout(SWT.HORIZONTAL));
		GridData gd_grpTags = new GridData(SWT.FILL, SWT.FILL, false, true, 2, 1);
		gd_grpTags.widthHint = 155;
		gd_grpTags.heightHint = 98;
		grpTags.setLayoutData(gd_grpTags);
		grpTags.setText(Vocab.instance.TAGS);
		
		TagList lstTroops = new TagList(grpTags, SWT.NONE);
		addChild(lstTroops, "tags");

		pack();
	}

}
