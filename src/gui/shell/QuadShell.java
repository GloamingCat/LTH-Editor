package gui.shell;

import gui.Tooltip;
import gui.Vocab;
import lui.base.LFlags;
import lui.base.event.listener.LControlListener;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

import lui.container.LImage;
import lui.container.LPanel;
import lui.container.LFlexPanel;
import lui.container.LScrollPanel;
import lui.dialog.LObjectWindow;
import lui.dialog.LWindow;
import lui.graphics.LPainter;
import lui.base.data.LPoint;
import lui.widget.LFileSelector;
import lui.widget.LActionButton;
import lui.widget.LLabel;
import lui.widget.LSpinner;

import data.subcontent.Quad;
import project.Project;

public class QuadShell extends LObjectWindow<Quad> {

	private LFileSelector selFile;
	private LImage imgQuad;
	private LSpinner spnX;
	private LSpinner spnY;
	private LSpinner spnWidth;
	private LSpinner spnHeight;
	private LScrollPanel scroll;
	
	public static final int OPTIONAL = 0x01;
	
	/**
	 * @wbp.parser.constructor
	 */
	public QuadShell(LWindow parent, int style) {
		super(parent, style, Vocab.instance.QUADSHELL);
		setMinimumSize(600, 400);
	}
	
	@Override
	protected void createContent(int style) {
		super.createContent(style);

		LFlexPanel form = new LFlexPanel(content, true);
		selFile = new LFileSelector(form, (style & OPTIONAL) > 0);
		selFile.addFileRestriction(this::isImage);
		selFile.setFolder(Project.current.imagePath());

		LPanel quad = new LPanel(form);
		quad.setGridLayout(1);

		scroll = new LScrollPanel(quad);
		scroll.getCellData().setExpand(true, true);

		imgQuad = new LImage(scroll);
		imgQuad.getCellData().setAlignment(LFlags.TOP | LFlags.LEFT);

		LPanel spinners = new LPanel(quad);
		spinners.setGridLayout(4);
		spinners.getCellData().setExpand(true, false);
		spinners.getCellData().setAlignment(LFlags.CENTER);

		new LLabel(spinners, Vocab.instance.QUADX, Tooltip.instance.QUADX);

		spnX = new LSpinner(spinners);
		spnX.setMaximum(4095);

		new LLabel(spinners, Vocab.instance.QUADW, Tooltip.instance.QUADW);

		spnWidth = new LSpinner(spinners);
		spnWidth.setMaximum(4096);
		spnWidth.setMinimum(1);

		new LLabel(spinners, Vocab.instance.QUADY, Tooltip.instance.QUADY);

		spnY = new LSpinner(spinners);
		spnY.setMaximum(4095);

		new LLabel(spinners, Vocab.instance.QUADH, Tooltip.instance.QUADH);

		spnHeight = new LSpinner(spinners);
		spnHeight.setMaximum(4096);
		spnHeight.setMinimum(1);

		LActionButton btnFullImage = new LActionButton(quad, Vocab.instance.FULLIMAGE);
		btnFullImage.addModifyListener(e -> {
            LPoint size = imgQuad.getImageSize();
            spnX.setValue(0);
            spnY.setValue(0);
            spnWidth.setValue(size.x);
            spnHeight.setValue(size.y);
            imgQuad.redraw();
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

		selFile.addSelectionListener(event -> resetImage());

		LControlListener<Integer> redrawListener = event -> imgQuad.redraw();

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
		scroll.setContentSize(imgQuad.getCurrentSize());
		imgQuad.redraw();
	}

}
