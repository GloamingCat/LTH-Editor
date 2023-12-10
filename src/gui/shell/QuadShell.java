package gui.shell;

import gui.Vocab;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

import lwt.LFlags;
import lwt.container.LImage;
import lwt.container.LPanel;
import lwt.container.LSashPanel;
import lwt.container.LScrollPanel;
import lwt.dialog.LObjectShell;
import lwt.dialog.LShell;
import lwt.event.LControlEvent;
import lwt.event.LSelectionEvent;
import lwt.event.listener.LControlListener;
import lwt.event.listener.LSelectionListener;
import lwt.graphics.LPainter;
import lwt.graphics.LPoint;
import lwt.widget.LFileSelector;
import lwt.widget.LActionButton;
import lwt.widget.LLabel;
import lwt.widget.LSpinner;

import data.subcontent.Quad;
import project.Project;

public class QuadShell extends LObjectShell<Quad> {

	private LFileSelector selFile;
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
		selFile = new LFileSelector(form, optional);
		selFile.addFileRestriction( (f) -> { return isImage(f); } );
		selFile.setFolder(Project.current.imagePath());

		LPanel quad = new LPanel(form, 1);

		scroll = new LScrollPanel(quad);
		scroll.setExpand(true, true);

		imgQuad = new LImage(scroll);
		imgQuad.setAlignment(LFlags.TOP & LFlags.LEFT);

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
				LPoint size = imgQuad.getImageSize();
				spnX.setValue(0);
				spnY.setValue(0);
				spnWidth.setValue(size.x);
				spnHeight.setValue(size.y);
				imgQuad.redraw();
			}
		});

		imgQuad.addPainter(new LPainter() {
			@Override
			public void paint() {
				int x = spnX.getValue();
				int y = spnY.getValue();
				int w = spnWidth.getValue();
				int h = spnHeight.getValue();
				drawRect(x, y, w, h);
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

		form.setWeights(1, 1);

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
		if (imgQuad.hasImage()) {
			LPoint size = imgQuad.getImageSize();
			q.x = Math.min(spnX.getValue(), size.x);
			q.y = Math.min(spnY.getValue(), size.y);
			q.width = Math.min(spnWidth.getValue(), size.x);
			q.height = Math.min(spnHeight.getValue(), size.y);
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
		scroll.refreshSize(imgQuad.getCurrentSize());
		imgQuad.redraw();
	}

}
