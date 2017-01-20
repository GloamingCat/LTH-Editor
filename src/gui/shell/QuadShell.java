package gui.shell;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

import lwt.dialog.LObjectDialog;
import lwt.dialog.LObjectShell;
import lwt.dialog.LShellFactory;

import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.graphics.Rectangle;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.wb.swt.SWTResourceManager;

import data.ImageAtlas;
import data.Quad;
import data.ImageAtlas.Entry;
import project.Project;

import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Spinner;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.widgets.Button;

public class QuadShell extends FileShell<Quad> {

	private Label label;
	private Spinner spnX;
	private Spinner spnY;
	private Spinner spnWidth;
	private Spinner spnHeight;
	
	private LObjectDialog<ImageAtlas.Entry> atlasDialog;
	
	/**
	 * @wbp.parser.constructor
	 */
	public QuadShell(Shell parent) {
		this(parent, "");
	}
	
	public QuadShell(Shell parent, String folder) {
		super(parent, folder);

		atlasDialog = new LObjectDialog<>(this);
		atlasDialog.setFactory(new LShellFactory<ImageAtlas.Entry>() {
			@Override
			public LObjectShell<Entry> createShell(Shell parent) {
				return new AtlasEntryShell(parent);
			}
		});
		
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
		
		label = new Label(composite_1, SWT.NONE);
		label.setAlignment(SWT.CENTER);
		label.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true, 1, 1));
		label.setImage(SWTResourceManager.getImage(rootPath() + result));
		
		Composite composite = new Composite(composite_1, SWT.NONE);
		composite.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1));
		GridLayout gl_composite = new GridLayout(4, false);
		gl_composite.marginHeight = 0;
		gl_composite.marginWidth = 0;
		composite.setLayout(gl_composite);
		
		Label lblX = new Label(composite, SWT.NONE);
		lblX.setText("X");
		
		spnX = new Spinner(composite, SWT.BORDER);
		spnX.setMaximum(1024);
		spnX.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1));
		
		Label lblWidth = new Label(composite, SWT.NONE);
		lblWidth.setText("Width");
		
		spnWidth = new Spinner(composite, SWT.BORDER);
		spnWidth.setMaximum(1024);
		spnWidth.setMinimum(1);
		spnWidth.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1));
		
		Label lblY = new Label(composite, SWT.NONE);
		lblY.setText("Y");
		
		spnY = new Spinner(composite, SWT.BORDER);
		spnY.setMaximum(1024);
		spnY.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1));
		
		Label lblHeight = new Label(composite, SWT.NONE);
		lblHeight.setText("Height");
		
		spnHeight = new Spinner(composite, SWT.BORDER);
		spnHeight.setMaximum(1024);
		spnHeight.setMinimum(1);
		spnHeight.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1));
		
		Composite composite_2 = new Composite(composite_1, SWT.NONE);
		GridLayout gl_composite_2 = new GridLayout(2, false);
		gl_composite_2.marginWidth = 0;
		gl_composite_2.marginHeight = 0;
		composite_2.setLayout(gl_composite_2);
		composite_2.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, false, 1, 1));
		
		Button btnFullImage = new Button(composite_2, SWT.NONE);
		btnFullImage.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent arg0) {
				Rectangle rect = label.getImage().getBounds();
				spnX.setSelection(0);
				spnY.setSelection(0);
				spnWidth.setSelection(rect.width);
				spnHeight.setSelection(rect.height);
			}
		});
		btnFullImage.setText("Full image");
		
		Button btnFromAtlas = new Button(composite_2, SWT.NONE);
		btnFromAtlas.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent arg0) {
				ImageAtlas.Entry e = atlasDialog.open(null);
				if (e != null) {
					list.deselectAll();
					label.setText(e.atlas.imagePath);
					spnWidth.setSelection(e.atlas.width);
					spnHeight.setSelection(e.atlas.height);
					spnX.setSelection(e.x * e.atlas.width);
					spnY.setSelection(e.y * e.atlas.height);
					resetImage();
				}
			}
		});
		btnFromAtlas.setText("From Atlas");
		
		sashForm.setWeights(new int[] {1, 1});
	}
	
	public void open(Quad initial) {
		super.open(initial);
		int i = indexOf(initial.imagePath);
		list.select(i);
		label.setText(initial.imagePath);
		spnX.setSelection(initial.x);
		spnY.setSelection(initial.y);
		spnWidth.setSelection(initial.width);
		spnHeight.setSelection(initial.height);
		resetImage();
	}

	@Override
	protected Quad createResult(Quad initial) {
		String newValue = label.getText();
		if (newValue.equals(initial.imagePath) && spnX.getSelection() == initial.x && 
				spnY.getSelection() == initial.y &&
				spnWidth.getSelection() == initial.width && 
				spnHeight.getSelection() == initial.height) {
			return null;
		} else {
			Quad q = new Quad();
			q.x = spnX.getSelection();
			q.y = spnY.getSelection();
			q.width = spnWidth.getSelection();
			q.height = spnHeight.getSelection();
			q.imagePath = newValue;
			return q;
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
		String path = rootPath() + label.getText();
		Image image = SWTResourceManager.getImage(path);
		label.setImage(image);
	}
	
	protected String rootPath() {
		return Project.current.imagePath();
	}

}
