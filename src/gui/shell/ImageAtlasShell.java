package gui.shell;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.graphics.Rectangle;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.wb.swt.SWTResourceManager;

import data.ImageAtlas;
import project.Project;

import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Spinner;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.events.PaintListener;
import org.eclipse.swt.events.PaintEvent;

public class ImageAtlasShell extends FileShell<ImageAtlas> {

	private Label label;
	private Spinner spnWidth;
	private Spinner spnHeight;
	private Composite labelHolder;
	
	/**
	 * @wbp.parser.constructor
	 */
	public ImageAtlasShell(Shell parent) {
		this(parent, "");
	}
	
	public ImageAtlasShell(Shell parent, String folder) {
		super(parent, folder);

		list.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent arg0) {
				int i = list.getSelectionIndex();
				if (i >= 0) {
					label.setText(folder + "/" + list.getItem(i));
					resetImage();
				}
			}
		});
		
		Composite composite_1 = new Composite(sashForm, SWT.NONE);
		composite_1.setLayout(new GridLayout(1, false));
		
		labelHolder = new Composite(composite_1, SWT.NONE);
		labelHolder.setLayoutData(new GridData(SWT.FILL, SWT.FILL, false, true, 1, 1));
		
		label = new Label(labelHolder, SWT.TOP);
		label.addPaintListener(new PaintListener() {
			public void paintControl(PaintEvent e) {
				int w = spnWidth.getSelection();
				int h = spnHeight.getSelection();
				Rectangle bounds = label.getImage().getBounds();
				int nc = bounds.width / w;
				int nl = bounds.height / h;
				for (int i = 1; i < nc; i++) {
					e.gc.drawLine(i * w, 0, i * w, bounds.height);
				}
				for (int i = 1; i < nl; i++) {
					e.gc.drawLine(0, i * h, bounds.height, i * h);
				}
				e.gc.drawRectangle(0, 0, bounds.width - 1, bounds.height - 1);
			}
		});
		label.setAlignment(SWT.LEFT);
		GridData gd_label = new GridData(SWT.LEFT, SWT.TOP, true, true, 1, 1);
		gd_label.widthHint = 10;
		gd_label.heightHint = 47;
		label.setLayoutData(gd_label);
		label.setImage(SWTResourceManager.getImage(rootPath() + result));
		
		Composite composite = new Composite(composite_1, SWT.NONE);
		composite.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1));
		GridLayout gl_composite = new GridLayout(2, false);
		gl_composite.marginHeight = 0;
		gl_composite.marginWidth = 0;
		composite.setLayout(gl_composite);
		
		Label lblWidth = new Label(composite, SWT.NONE);
		lblWidth.setText("Width");
		
		spnWidth = new Spinner(composite, SWT.BORDER);
		spnWidth.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent arg0) {
				label.redraw();
			}
		});
		spnWidth.setMaximum(1024);
		spnWidth.setMinimum(1);
		spnWidth.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1));
		
		Label lblHeight = new Label(composite, SWT.NONE);
		lblHeight.setText("Height");
		
		spnHeight = new Spinner(composite, SWT.BORDER);
		spnHeight.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent arg0) {
				label.redraw();
			}
		});
		spnHeight.setMaximum(1024);
		spnHeight.setMinimum(1);
		spnHeight.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1));
		
		sashForm.setWeights(new int[] {1, 1});
	}
	
	public void open(ImageAtlas initial) {
		super.open(initial);
		int i = indexOf(initial.imagePath);
		list.select(i);
		label.setText(initial.imagePath);
		spnWidth.setSelection(initial.width);
		spnHeight.setSelection(initial.height);
		resetImage();
	}

	@Override
	protected ImageAtlas createResult(ImageAtlas initial) {
		int i = list.getSelectionIndex();
		if (i >= 0) {
			String newValue = folder + "/" + list.getItem(i);
			if (newValue.equals(initial.imagePath) &&
					spnWidth.getSelection() == initial.width && 
					spnHeight.getSelection() == initial.height) {
				return null;
			} else {
				ImageAtlas q = new ImageAtlas();
				q.width = spnWidth.getSelection();
				q.height = spnHeight.getSelection();
				q.imagePath = newValue;
				return q;
			}
		} else {
			return null;
		}
	}

	protected boolean isValidFile(File entry) {
		try {
		    BufferedImage image = ImageIO.read(entry);
		    if (image != null) {
		    	image.flush();
		    } else {
		    	return false;
		    }
		    return true;
		} catch(IOException ex) {
		    return false;
		}
	}
	
	protected void resetImage() {
		String path = Project.current.imagePath() + label.getText();
		Image image = SWTResourceManager.getImage(path);
		label.setImage(image);
		label.setSize(image.getBounds().width, image.getBounds().height);
	}
	
	protected String rootPath() {
		return Project.current.imagePath();
	}

}
