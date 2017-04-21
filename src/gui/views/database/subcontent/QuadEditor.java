package gui.views.database.subcontent;

import gui.views.QuadButton;

import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;

import lwt.editor.LObjectEditor;
import lwt.widget.LImage;

public class QuadEditor extends LObjectEditor {

	private LImage imgGraphic;

	public QuadEditor(Composite parent, int style) {
		super(parent, style);

		setLayout(new GridLayout(1, false));
		
		imgGraphic = new LImage(this, SWT.NONE);
		imgGraphic.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true, 1, 1));
		imgGraphic.setHorizontalAlign(SWT.CENTER);
		imgGraphic.setVerticalAlign(SWT.CENTER);
		imgGraphic.setImage("/javax/swing/plaf/basic/icons/image-delayed.png");
		
		QuadButton btnSelect = new QuadButton(this, SWT.NONE);
		btnSelect.setLayoutData(new GridData(SWT.RIGHT, SWT.CENTER, true, false, 1, 1));
		btnSelect.setFolder(getFolder());
		addControl(btnSelect, "quad");
		btnSelect.setImage(imgGraphic);
		
	}
	
	protected String getFolder() { return ""; }

	public LImage getImage() {
		return imgGraphic;
	}

}
