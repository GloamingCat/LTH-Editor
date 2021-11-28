package gui.shell;

import gui.Vocab;
import gui.widgets.FileSelector;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

import lwt.dialog.LObjectShell;
import lwt.event.LSelectionEvent;
import lwt.event.listener.LSelectionListener;
import lwt.widget.LImage;
import lwt.widget.LLabel;

import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.SashForm;
import org.eclipse.swt.custom.ScrolledComposite;
import org.eclipse.swt.events.ModifyEvent;
import org.eclipse.swt.events.ModifyListener;
import org.eclipse.swt.events.PaintEvent;
import org.eclipse.swt.events.PaintListener;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.graphics.Rectangle;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.wb.swt.SWTResourceManager;

import data.subcontent.Quad;
import project.Project;

import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Spinner;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.widgets.Button;

public class QuadShell extends LObjectShell<Quad> {

	private FileSelector selFile;
	private LImage imgQuad;
	private Spinner spnX;
	private Spinner spnY;
	private Spinner spnWidth;
	private Spinner spnHeight;
	private ScrolledComposite scroll;
	
	public QuadShell(Shell parent, int optional) {
		super(parent);
		setMinimumSize(600, 400);
		content.setLayout(new FillLayout());
		
		SashForm form = new SashForm(content, SWT.HORIZONTAL);
		selFile = new FileSelector(form, optional) {
			@Override
			protected boolean isValidFile(File f) {
				return isImage(f);
			}
		};
		selFile.setFolder(Project.current.imagePath());
		
		Composite quad = new Composite(form, SWT.NONE);
		quad.setLayout(new GridLayout(1, false));
		
		scroll = new ScrolledComposite(quad, SWT.BORDER | SWT.H_SCROLL | SWT.V_SCROLL);
		scroll.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true, 1, 1));
		
		imgQuad = new LImage(scroll);
		imgQuad.setHorizontalAlign(SWT.CENTER);
		imgQuad.setVerticalAlign(SWT.CENTER);
		scroll.setContent(imgQuad);
		
		Composite spinners = new Composite(quad, SWT.NONE);
		spinners.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1));
		GridLayout gl_spinners = new GridLayout(4, false);
		gl_spinners.marginHeight = 0;
		gl_spinners.marginWidth = 0;
		spinners.setLayout(gl_spinners);
		
		new LLabel(spinners, Vocab.instance.QUADX);
		
		spnX = new Spinner(spinners, SWT.BORDER);
		spnX.setMaximum(4095);
		spnX.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1));
		
		new LLabel(spinners, Vocab.instance.QUADW);
		
		spnWidth = new Spinner(spinners, SWT.BORDER);
		spnWidth.setMaximum(4096);
		spnWidth.setMinimum(1);
		spnWidth.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1));
		
		new LLabel(spinners, Vocab.instance.QUADY);
		
		spnY = new Spinner(spinners, SWT.BORDER);
		spnY.setMaximum(4095);
		spnY.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1));
		
		new LLabel(spinners, Vocab.instance.QUADH);
		
		spnHeight = new Spinner(spinners, SWT.BORDER);
		spnHeight.setMaximum(4096);
		spnHeight.setMinimum(1);
		spnHeight.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1));
		
		Button btnFullImage = new Button(quad, SWT.NONE);
		GridLayout gl_composite_2 = new GridLayout(2, false);
		gl_composite_2.marginWidth = 0;
		gl_composite_2.marginHeight = 0;
		btnFullImage.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, false, 1, 1));
		
		imgQuad.addPaintListener(new PaintListener() {
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
			}
		});
		
		btnFullImage.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent arg0) {
				Rectangle rect = imgQuad.getImage().getBounds();
				spnX.setSelection(0);
				spnY.setSelection(0);
				spnWidth.setSelection(rect.width);
				spnHeight.setSelection(rect.height);
				imgQuad.redraw();
			}
		});
		btnFullImage.setText(Vocab.instance.FULLIMAGE);

		selFile.addSelectionListener(new LSelectionListener() {
			@Override
			public void onSelect(LSelectionEvent event) {
				resetImage();
			}
		});
		
		ModifyListener redrawListener = new ModifyListener() {
			@Override
			public void modifyText(ModifyEvent arg0) {
				imgQuad.redraw();
			}
		};
		
		spnX.addModifyListener(redrawListener);
		spnY.addModifyListener(redrawListener);
		spnWidth.addModifyListener(redrawListener);
		spnHeight.addModifyListener(redrawListener);
		
		form.setWeights(new int[] {1, 1});
		
		layout(false, true);
	}
	
	public void open(Quad initial) {
		super.open(initial);
		selFile.setSelectedFile(initial.path);
		spnX.setSelection(initial.x);
		spnY.setSelection(initial.y);
		spnWidth.setSelection(initial.width);
		spnHeight.setSelection(initial.height);
		resetImage();
	}

	@Override
	protected Quad createResult(Quad initial) {
		Quad q = new Quad();
		Rectangle rect = imgQuad.getImage().getBounds();
		q.x = Math.min(spnX.getSelection(), rect.width);
		q.y = Math.min(spnY.getSelection(), rect.height);
		q.width = Math.min(spnWidth.getSelection(), rect.width);
		q.height = Math.min(spnHeight.getSelection(), rect.height);
		q.path = selFile.getSelectedFile();
		return q;
	}

	protected boolean isImage(File entry) {
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
		String path = selFile.getRootFolder() + selFile.getSelectedFile();
		Image image = SWTResourceManager.getImage(path);
		imgQuad.setImage(image);
		imgQuad.setBounds(image.getBounds());
		scroll.setMinSize(imgQuad.getSize());
		imgQuad.redraw();
	}

}
