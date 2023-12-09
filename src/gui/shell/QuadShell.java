package gui.shell;

import gui.Vocab;
import gui.widgets.FileSelector;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

import lwt.LFlags;
import lwt.container.LPanel;
import lwt.container.LSashPanel;
import lwt.container.LScrollPanel;
import lwt.dialog.LObjectShell;
import lwt.dialog.LShell;
import lwt.event.LControlEvent;
import lwt.event.LSelectionEvent;
import lwt.event.listener.LControlListener;
import lwt.event.listener.LSelectionListener;
import lwt.widget.LActionButton;
import lwt.widget.LImage;
import lwt.widget.LLabel;
import lwt.widget.LSpinner;

import org.eclipse.swt.events.PaintEvent;
import org.eclipse.swt.events.PaintListener;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.graphics.Rectangle;

import data.subcontent.Quad;
import project.Project;

public class QuadShell extends LObjectShell<Quad> {

	private FileSelector selFile;
	private LImage imgQuad;
	private LSpinner spnX;
	private LSpinner spnY;
	private LSpinner spnWidth;
	private LSpinner spnHeight;
	private LScrollPanel scroll;

	public QuadShell(LShell parent, boolean optional) {
		super(parent);
		setMinimumSize(600, 400);

		LSashPanel form = new LSashPanel(content, true);
		selFile = new FileSelector(form, optional);
		selFile.addFileRestriction( (f) -> { return isImage(f); } );
		selFile.setFolder(Project.current.imagePath());

		LPanel quad = new LPanel(form, 1);

		scroll = new LScrollPanel(quad);
		scroll.setExpand(true, true);

		imgQuad = new LImage(scroll);
		scroll.setContent(imgQuad);

		LPanel spinners = new LPanel(quad, 4, false);
		spinners.setExpand(true, false);
		spinners.setAlignment(LFlags.CENTER);

		new LLabel(spinners, Vocab.instance.QUADX);

		spnX = new LSpinner(spinners);
		spnX.setMaximum(4095);

		new LLabel(spinners, Vocab.instance.QUADW);

		spnWidth = new LSpinner(spinners);
		spnWidth.setMaximum(4096);
		spnWidth.setMinimum(1);

		new LLabel(spinners, Vocab.instance.QUADY);

		spnY = new LSpinner(spinners);
		spnY.setMaximum(4095);

		new LLabel(spinners, Vocab.instance.QUADH);

		spnHeight = new LSpinner(spinners);
		spnHeight.setMaximum(4096);
		spnHeight.setMinimum(1);

		LActionButton btnFullImage = new LActionButton(quad, Vocab.instance.FULLIMAGE);
		btnFullImage.addModifyListener(new LControlListener<Object>() {
			@Override
			public void onModify(LControlEvent<Object> e) {
				Rectangle rect = imgQuad.getImage().getBounds();
				spnX.setValue(0);
				spnY.setValue(0);
				spnWidth.setValue(rect.width);
				spnHeight.setValue(rect.height);
				imgQuad.redraw();
			}
		});

		imgQuad.addPaintListener(new PaintListener() {
			@Override
			public void paintControl(PaintEvent e) {
				int x1 = spnX.getValue();
				int y1 = spnY.getValue();
				int x2 = x1 + spnWidth.getValue() - 1;
				int y2 = y1 + spnHeight.getValue() - 1;
				e.gc.drawLine(x1, y1, x2, y1);
				e.gc.drawLine(x1, y2, x2, y2);
				e.gc.drawLine(x1, y1, x1, y2);
				e.gc.drawLine(x2, y1, x2, y2);
			}
		});

		selFile.addSelectionListener(new LSelectionListener() {
			@Override
			public void onSelect(LSelectionEvent event) {
				resetImage();
			}
		});

		LControlListener<Integer> redrawListener = new LControlListener<Integer>() {
			@Override
			public void onModify(LControlEvent<Integer> event) {
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
		spnX.setValue(initial.x);
		spnY.setValue(initial.y);
		spnWidth.setValue(initial.width);
		spnHeight.setValue(initial.height);
		resetImage();
	}

	@Override
	protected Quad createResult(Quad initial) {
		Quad q = new Quad();
		Image img = imgQuad.getImage();
		if (img != null) {
			Rectangle rect = imgQuad.getImage().getBounds();
			q.x = Math.min(spnX.getValue(), rect.width);
			q.y = Math.min(spnY.getValue(), rect.height);
			q.width = Math.min(spnWidth.getValue(), rect.width);
			q.height = Math.min(spnHeight.getValue(), rect.height);
			q.path = selFile.getSelectedFile();
		}
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
		imgQuad.setImage(path);
		scroll.setMinSize(imgQuad.getSize());
		imgQuad.redraw();
	}

}
