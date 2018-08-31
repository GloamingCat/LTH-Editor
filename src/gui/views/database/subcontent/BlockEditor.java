package gui.views.database.subcontent;

import gui.helper.ImageHelper;
import gui.views.ImageButton;

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

public class BlockEditor extends LObjectEditor {

	private Label imgGraphic;

	public BlockEditor(Composite parent, int style) {
		super(parent, style);

		setLayout(new GridLayout(1, false));
		
		imgGraphic = new Label(this, SWT.NONE);
		imgGraphic.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true, 1, 1));
		imgGraphic.setAlignment(SWT.CENTER);
		imgGraphic.setImage(SWTResourceManager.getImage("/javax/swing/plaf/basic/icons/image-delayed.png"));
		
		ImageButton btnSelect = new ImageButton(this, SWT.NONE);
		btnSelect.setLayoutData(new GridData(SWT.RIGHT, SWT.CENTER, true, false, 1, 1));
		btnSelect.setFolder(getFolder());
		addControl(btnSelect, "imagePath");
		btnSelect.setLabel(imgGraphic);
		btnSelect.addModifyListener(new LControlListener<String>() {
			@Override
			public void onModify(LControlEvent<String> event) {
				resetImage();
			}
		});
	}
	
	protected String getFolder() { return ""; }

	protected void resetImage() {
		Quad quad = (Quad) currentObject;
		String path = Project.current.imagePath() + quad.path;
		Image original = SWTResourceManager.getImage(path);
		Image image;
		if (quad.path.isEmpty()) {
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
		if (obj == null) {
			super.setObject(null);
			resetImage();
		} else {
			Object value = getFieldValue(obj, "quad");
			super.setObject(value);
			resetImage();
		}
	}

	public Label getLabel() {
		return imgGraphic;
	}
	

}
