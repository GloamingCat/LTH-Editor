package gui.shell;

import gui.Vocab;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.ScrolledComposite;
import org.eclipse.swt.events.ModifyEvent;
import org.eclipse.swt.events.ModifyListener;
import org.eclipse.swt.events.PaintEvent;
import org.eclipse.swt.events.PaintListener;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.graphics.Rectangle;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.wb.swt.SWTResourceManager;

import data.subcontent.Quad;
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
	private ScrolledComposite scroll;
	
	/**
	 * @wbp.parser.constructor
	 */
	public QuadShell(Shell parent) {
		this(parent, "", true);
	}
	
	public QuadShell(Shell parent, String folder, boolean optional) {
		super(parent, folder, optional);
		
		setMinimumSize(600, 400);
		
		Composite quad = new Composite(sashForm, SWT.NONE);
		quad.setLayout(new GridLayout(1, false));
		
		scroll = new ScrolledComposite(quad, SWT.BORDER | SWT.H_SCROLL | SWT.V_SCROLL);
		scroll.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true, 1, 1));
		
		label = new Label(scroll, SWT.NONE);
		label.setAlignment(SWT.CENTER);
		label.setImage(SWTResourceManager.getImage(rootPath() + result));
		scroll.setContent(label);
		
		Composite spinners = new Composite(quad, SWT.NONE);
		spinners.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1));
		GridLayout gl_spinners = new GridLayout(4, false);
		gl_spinners.marginHeight = 0;
		gl_spinners.marginWidth = 0;
		spinners.setLayout(gl_spinners);
		
		Label lblX = new Label(spinners, SWT.NONE);
		lblX.setText(Vocab.instance.QUADX);
		
		spnX = new Spinner(spinners, SWT.BORDER);
		spnX.setMaximum(1024);
		spnX.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1));
		
		Label lblWidth = new Label(spinners, SWT.NONE);
		lblWidth.setText(Vocab.instance.QUADW);
		
		spnWidth = new Spinner(spinners, SWT.BORDER);
		spnWidth.setMaximum(1024);
		spnWidth.setMinimum(1);
		spnWidth.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1));
		
		Label lblY = new Label(spinners, SWT.NONE);
		lblY.setText(Vocab.instance.QUADY);
		
		spnY = new Spinner(spinners, SWT.BORDER);
		spnY.setMaximum(1024);
		spnY.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1));
		
		Label lblHeight = new Label(spinners, SWT.NONE);
		lblHeight.setText(Vocab.instance.QUADH);
		
		spnHeight = new Spinner(spinners, SWT.BORDER);
		spnHeight.setMaximum(1024);
		spnHeight.setMinimum(1);
		spnHeight.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1));
		
		Button btnFullImage = new Button(quad, SWT.NONE);
		GridLayout gl_composite_2 = new GridLayout(2, false);
		gl_composite_2.marginWidth = 0;
		gl_composite_2.marginHeight = 0;
		btnFullImage.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, false, 1, 1));
		
		label.addPaintListener(new PaintListener() {
			@Override
			public void paintControl(PaintEvent e) {
				int x1 = spnX.getSelection();
				int y1 = spnY.getSelection();
				int x2 = x1 + spnWidth.getSelection() - 1;
				int y2 = y1 + spnHeight.getSelection() - 1;
				e.gc.drawLine(x1, y1, x2, y1);
				e.gc.drawLine(x1, y2, x2, y2);
				e.gc.drawLine(x1, y1, x1, y2);
				e.gc.drawLine(x2, y1, x2, y2);
				System.out.println(label.getBounds());
			}
		});
		
		btnFullImage.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent arg0) {
				Rectangle rect = label.getImage().getBounds();
				spnX.setSelection(0);
				spnY.setSelection(0);
				spnWidth.setSelection(rect.width);
				spnHeight.setSelection(rect.height);
				label.redraw();
			}
		});
		btnFullImage.setText(Vocab.instance.FULLIMAGE);

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
		
		ModifyListener redrawListener = new ModifyListener() {
			@Override
			public void modifyText(ModifyEvent arg0) {
				label.redraw();
			}
		};
		
		spnX.addModifyListener(redrawListener);
		spnY.addModifyListener(redrawListener);
		spnWidth.addModifyListener(redrawListener);
		spnHeight.addModifyListener(redrawListener);
		
		sashForm.setWeights(new int[] {1, 1});
	}
	
	public void open(Quad initial) {
		super.open(initial);
		int i = indexOf(initial.path);
		list.select(i);
		label.setText(initial.path);
		spnX.setSelection(initial.x);
		spnY.setSelection(initial.y);
		spnWidth.setSelection(initial.width);
		spnHeight.setSelection(initial.height);
		resetImage();
	}

	@Override
	protected Quad createResult(Quad initial) {
		String newValue = label.getText();
		if (newValue.equals(initial.path) && spnX.getSelection() == initial.x && 
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
			q.path = newValue;
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
		label.setBounds(image.getBounds());
		scroll.setMinSize(label.getSize());
		label.redraw();
	}
	
	protected String rootPath() {
		return Project.current.imagePath();
	}

}
