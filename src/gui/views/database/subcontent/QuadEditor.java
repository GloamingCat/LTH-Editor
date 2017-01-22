package gui.views.database.subcontent;

import gui.helper.ImageHelper;
import gui.views.QuadButton;

import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Label;
import org.eclipse.wb.swt.SWTResourceManager;

import data.Quad;
import project.Project;
import lwt.editor.LObjectEditor;
import lwt.event.LControlEvent;
import lwt.event.listener.LControlListener;

public class QuadEditor extends LObjectEditor {

	private Label imgGraphic;

	public QuadEditor(Composite parent, int style) {
		super(parent, style);

		setLayout(new GridLayout(1, false));
		
		imgGraphic = new Label(this, SWT.NONE);
		imgGraphic.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true, 1, 1));
		imgGraphic.setAlignment(SWT.CENTER);
		imgGraphic.setImage(SWTResourceManager.getImage("/javax/swing/plaf/basic/icons/image-delayed.png"));
		
		QuadButton btnSelect = new QuadButton(this, SWT.NONE);
		btnSelect.setLayoutData(new GridData(SWT.RIGHT, SWT.CENTER, true, false, 1, 1));
		btnSelect.setFolder(getFolder());
		addControl(btnSelect, "quad");
		btnSelect.setLabel(imgGraphic);
		btnSelect.addModifyListener(new LControlListener<Quad>() {
			@Override
			public void onModify(LControlEvent<Quad> event) {
				resetImage();
			}
		});
	}
	
	protected String getFolder() { return ""; }

	protected void resetImage() {
		Quad quad = (Quad) this.getFieldValue(currentObject, "quad");
		String path = Project.current.imagePath() + quad.imagePath;
		Image original = SWTResourceManager.getImage(path);
		Image image;
		if (quad.imagePath.isEmpty()) {
			image = original;
		} else {
			image = ImageHelper.getImageQuad(original, quad.x, quad.y, quad.width, quad.height);
		}
		if (imgGraphic.getImage() != null && imgGraphic.getImage() != original) {
			imgGraphic.getImage().dispose();
		}
		try {
			imgGraphic.setImage(image);
		} catch(IllegalArgumentException e) {}
	}
	
	public void setObject(Object obj) {
		super.setObject(obj);
		resetImage();
	}

	public Label getLabel() {
		return imgGraphic;
	}
	

}
