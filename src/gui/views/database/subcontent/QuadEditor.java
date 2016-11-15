package gui.views.database.subcontent;

import java.util.ArrayList;

import gui.Vocab;
import gui.helper.ImageHelper;
import gui.shell.ImageShell;

import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.graphics.Rectangle;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.wb.swt.SWTResourceManager;

import data.Quad;
import project.Project;
import lwt.action.LAction;
import lwt.dialog.LObjectShell;
import lwt.dialog.LShellFactory;
import lwt.editor.LObjectEditor;
import lwt.event.LControlEvent;
import lwt.event.listener.LControlListener;
import lwt.widget.LSpinner;
import lwt.widget.LStringButton;

public class QuadEditor extends LObjectEditor {

	private Label imgGraphic;
	private LSpinner spnX;
	private LSpinner spnY;
	private LSpinner spnWidth;
	private LSpinner spnHeight;
	
	private ArrayList<LSpinner> spinners;
	
	public QuadEditor(Composite parent, int style) {
		super(parent, style);
		
		spinners = new ArrayList<>();
		setLayout(new GridLayout(3, false));
		
		Label lblQuadX = new Label(this, SWT.NONE);
		lblQuadX.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, false, false, 1, 1));
		lblQuadX.setText(Vocab.instance.QUADX);
		
		spnX = new LSpinner(this, SWT.NONE);
		spnX.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1));
		addSpinner(spnX, "x");
		
		imgGraphic = new Label(this, SWT.NONE);
		imgGraphic.setAlignment(SWT.CENTER);
		GridData gd_imgGraphic = new GridData(SWT.FILL, SWT.FILL, true, true, 1, 5);
		gd_imgGraphic.widthHint = 100;
		imgGraphic.setLayoutData(gd_imgGraphic);
		imgGraphic.setImage(SWTResourceManager.getImage("/javax/swing/plaf/basic/icons/image-delayed.png"));
		
		Label lblQuadY = new Label(this, SWT.NONE);
		lblQuadY.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, false, false, 1, 1));
		lblQuadY.setText(Vocab.instance.QUADY);
		
		spnY = new LSpinner(this, SWT.NONE);
		spnY.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1));
		addSpinner(spnY, "y");
		
		Label lblQuadWidth = new Label(this, SWT.NONE);
		lblQuadWidth.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, false, false, 1, 1));
		lblQuadWidth.setText(Vocab.instance.QUADW);
		
		spnWidth = new LSpinner(this, SWT.NONE);
		spnWidth.setMaximum(1024);
		spnWidth.setMinimum(1);
		spnWidth.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, false, false, 1, 1));
		addSpinner(spnWidth, "width");
		
		Label lblQuadHeight = new Label(this, SWT.NONE);
		lblQuadHeight.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, false, false, 1, 1));
		lblQuadHeight.setText(Vocab.instance.QUADH);
		
		spnHeight = new LSpinner(this, SWT.NONE);
		spnHeight.setMaximum(1024);
		spnHeight.setMinimum(1);
		spnHeight.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, false, false, 1, 1));
		addSpinner(spnHeight, "height");
		
		Composite buttons = new Composite(this, SWT.NONE);
		buttons.setLayout(new GridLayout(2, true));
		buttons.setLayoutData(new GridData(SWT.RIGHT, SWT.TOP, false, false, 2, 2));
		
		Button btnFullImage = new Button(buttons, SWT.NONE);
		btnFullImage.addSelectionListener(fullImageAdapter());
		btnFullImage.setLayoutData(new GridData(SWT.FILL, SWT.TOP, false, false, 1, 1));
		btnFullImage.setText(Vocab.instance.FULLIMAGE);
		
		LStringButton btnSelect = new LStringButton(buttons, SWT.NONE) {
			@Override
			protected String getImagePath() {
				return Project.current.imagePath();
			}
		};
		btnSelect.setLayoutData(new GridData(SWT.FILL, SWT.TOP, false, false, 1, 1));
		btnSelect.setLabel(imgGraphic);
		btnSelect.setShellFactory(new LShellFactory<String>() {
			@Override
			public LObjectShell<String> createShell(Shell parent) {
				return new ImageShell(parent, getFolder());
			}
		});
	}
	
	protected String getFolder() { return ""; }
	
	private int[] currentValues() {
		int[] values = new int[4];
		for(int i = 0; i < 4; i++) {
			values[i] = (Integer) spinners.get(i).getValue();
		}
		return values;
	}
	
	private int[] fullImageValues() {
		Quad quad = (Quad) currentObject;
		if (quad.imagePath.isEmpty()) {
			return new int[] { 0, 0, 1, 1 };
		}
		String path = Project.current.imagePath() + quad.imagePath;
		Image image = SWTResourceManager.getImage(path);
		Rectangle r = image.getBounds();
		int[] values = new int[] { 0, 0, r.width, r.height };
		return values;
	}
	
	private boolean areEqual(int[] a, int[] b) {
		for(int i = 0; i < a.length; i++) {
			if (a[i] != b[i]) {
				return false;
			}
		}
		return true;
	}
	
	private SelectionAdapter fullImageAdapter() {
		return new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent arg0) {
				if (currentObject == null)
					return;
				final int[] oldValues = currentValues();
				final int[] newValues = fullImageValues();
				if (areEqual(oldValues, newValues))
					return;
				LAction action = new LAction() {
					@Override
					public void undo() {
						setSpinnerValues(oldValues);
					}
					@Override
					public void redo() {
						setSpinnerValues(newValues);
					}
				};
				getActionStack().newAction(action);
				action.redo();
			}
		};
	}
	
	private void setSpinnerValues(int[] values) {
		for(int i = 0; i < 4; i++) {
			LSpinner spinner = spinners.get(i);
			spinner.setValue(values[i]);
		}
		Quad quad = (Quad) currentObject;
		quad.x = values[0];
		quad.y = values[1];
		quad.width = values[2];
		quad.height = values[3];
		resetImage();
	}
	
	private void addSpinner(LSpinner spinner, String key) {
		addControl(spinner, key);
		spinner.addModifyListener(new LControlListener() {
			@Override
			public void onModify(LControlEvent event) {
				if (!event.newValue.equals(event.oldValue))
					resetImage();
			}
		});
		spinners.add(spinner);
	}
	
	protected void resetImage() {
		Quad quad = (Quad) currentObject;
		if (quad.imagePath.isEmpty())
			return;
		String path = Project.current.imagePath() + quad.imagePath;
		Image original = SWTResourceManager.getImage(path);
		Image image = ImageHelper.getImageQuad(original, quad.x, quad.y, quad.width, quad.height);
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
		} else {
			Object value = getFieldValue(obj, "quad");
			super.setObject(value);
			resetImage();
		}
	}
	

}
