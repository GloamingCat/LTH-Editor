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

public abstract class QuadEditor extends LObjectEditor {

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
		lblQuadX.setText(Vocab.instance.QUADX);
		
		spnX = new LSpinner(this, SWT.BORDER);
		spnX.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1));
		addSpinner(spnX, "x");
		
		imgGraphic = new Label(this, SWT.NONE);
		imgGraphic.setAlignment(SWT.CENTER);
		GridData gd_imgGraphic = new GridData(SWT.FILL, SWT.FILL, true, true, 1, 5);
		gd_imgGraphic.widthHint = 100;
		imgGraphic.setLayoutData(gd_imgGraphic);
		imgGraphic.setImage(SWTResourceManager.getImage("/javax/swing/plaf/basic/icons/image-delayed.png"));
		
		Label lblQuadY = new Label(this, SWT.NONE);
		lblQuadY.setText(Vocab.instance.QUADY);
		
		spnY = new LSpinner(this, SWT.BORDER);
		spnY.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1));
		addSpinner(spnY, "y");
		
		Label lblQuadWidth = new Label(this, SWT.NONE);
		lblQuadWidth.setText(Vocab.instance.QUADW);
		
		spnWidth = new LSpinner(this, SWT.BORDER);
		spnWidth.setMaximum(1024);
		spnWidth.setMinimum(1);
		spnWidth.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, false, false, 1, 1));
		addSpinner(spnWidth, "width");
		
		Label lblQuadHeight = new Label(this, SWT.NONE);
		lblQuadHeight.setLayoutData(new GridData(SWT.LEFT, SWT.TOP, false, false, 1, 1));
		lblQuadHeight.setText(Vocab.instance.QUADH);
		
		spnHeight = new LSpinner(this, SWT.BORDER);
		spnHeight.setMaximum(1024);
		spnHeight.setMinimum(1);
		spnHeight.setLayoutData(new GridData(SWT.FILL, SWT.TOP, false, false, 1, 1));
		addSpinner(spnHeight, "height");
		
		Button btnFullImage = new Button(this, SWT.NONE);
		btnFullImage.addSelectionListener(fullImageAdapter());
		btnFullImage.setLayoutData(new GridData(SWT.LEFT, SWT.TOP, false, false, 1, 1));
		btnFullImage.setText(Vocab.instance.FULLIMAGE);
		
		LStringButton btnSelect = new LStringButton(this, SWT.NONE) {
			@Override
			protected String getImagePath() {
				return Project.current.imagePath();
			}
		};
		btnSelect.setLayoutData(new GridData(SWT.RIGHT, SWT.TOP, false, false, 1, 1));
		btnSelect.setLabel(imgGraphic);
		btnSelect.setShellFactory(new LShellFactory<String>() {
			@Override
			public LObjectShell<String> createShell(Shell parent) {
				return new ImageShell(parent, getFolder());
			}
		});
	}
	
	protected abstract String getFolder();
	
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
		return new int[] { r.x, r.y, r.width, r.height };
	}
	
	private boolean areEqual(int[] a, int[] b) {
		for(int i = 0; i < a.length; i++) {
			if (a[i] != b[i]) {
				return true;
			}
		}
		return false;
	}
	
	private SelectionAdapter fullImageAdapter() {
		return new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent arg0) {
				final int[] oldValues = currentValues();
				final int[] newValues = fullImageValues();
				if (areEqual(oldValues, newValues))
					return;
				LAction action = new LAction() {
					@Override
					public void undo() {
						for(int i = 0; i < oldValues.length; i++) {
							LSpinner spinner = spinners.get(i);
							LControlEvent event = new LControlEvent(newValues[i], oldValues[i]);
							spinner.notifyListeners(event);
						}
					}
					@Override
					public void redo() {
						for(int i = 0; i < newValues.length; i++) {
							LSpinner spinner = spinners.get(i);
							LControlEvent event = new LControlEvent(oldValues[i], newValues[i]);
							spinner.notifyListeners(event);
						}
					}
				};
				getActionStack().newAction(action);
				action.redo();
			}
		};
	}
	
	private void addSpinner(LSpinner spinner, String key) {
		addControl(spinner, key);
		spinner.addModifyListener(new LControlListener() {
			@Override
			public void onModify(LControlEvent event) {
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
		imgGraphic.setImage(image);
	}
	
	public void setObject(Object obj) {
		super.setObject(obj);
		resetImage();
	}
	

}
