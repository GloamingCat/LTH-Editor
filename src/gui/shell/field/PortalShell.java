package gui.shell.field;

import gui.Tooltip;
import gui.Vocab;
import gui.helper.FieldHelper;
import gui.views.fieldTree.*;
import lui.base.LFlags;
import lui.base.event.listener.LControlListener;
import lui.container.LScrollPanel;
import lui.dialog.LObjectWindow;
import lui.dialog.LWindow;
import lui.base.event.LControlEvent;
import lui.graphics.LPainter;
import lui.base.data.LPoint;
import lui.widget.LLabel;
import lui.widget.LSpinner;
import project.Project;
import data.field.Field;
import data.field.Transition.Portal;

public class PortalShell extends LObjectWindow<Portal> {

	protected FieldCanvas canvas;
	protected LSpinner spnH;
	protected LScrollPanel scrolledComposite;
	protected LLabel lblPos;
	protected boolean[][][] selectedTiles;
	protected int fieldID;

	/**
	 * @wbp.parser.constructor
	 */
	public PortalShell(LWindow parent, int fieldID) {
		super(parent, Vocab.instance.PORTALSHELL);
		this.fieldID = fieldID;
		setMinimumSize(640, 480);
	}
	
	@Override
	protected void createContent(int style) {
		super.createContent(style);
		content.setGridLayout(3);

		scrolledComposite = new LScrollPanel(content);
		scrolledComposite.getCellData().setExpand(true, true);
		scrolledComposite.getCellData().setSpread(3, 1);

		canvas = new FieldCanvasOpenGL(scrolledComposite) {
			public void onTileLeftDown() {
				selectedTiles[height][tileX][tileY] = !selectedTiles[height][tileX][tileY];
				canvas.redraw();
			}
			public void onTileRightDown() {}
			public void onTileEnter(int x, int y) {
				lblPos.setText("(" + (x + 1) + "," + (y + 1) + ")");
			}
			public void drawSelection(LPainter painter) {
				for (int h = 0; h < selectedTiles.length; h++)
					for (int x = 0; x < selectedTiles[h].length; x++)
						for (int y = 0; y < selectedTiles[h][x].length; y++) 
							if (selectedTiles[h][x][y]) {
								LPoint point = FieldHelper.math.tile2Pixel(x, y, h);
								drawCursor(painter, point);
							}
			}
		};

		new LLabel(content, LFlags.CENTER, Vocab.instance.HEIGHT, Tooltip.instance.POSITIONH);
		spnH = new LSpinner(content);
		spnH.setMinimum(1);
		spnH.setValue(1);
		spnH.addModifyListener(new LControlListener<Integer>() {
			@Override
			public void onModify(LControlEvent<Integer> event) {
				canvas.setHeight(event.newValue - 1);
				canvas.redraw();
			}
		});
		lblPos = new LLabel(content, LFlags.EXPAND, "(-99, -99)");

		pack();
	}

	public void open(Portal initial) {
		super.open(initial);
		if (initial.size() > 0) {
			spnH.setValue(initial.get(initial.size() - 1).height);
		} else {
			spnH.setValue(1);
		}
		Field field = Project.current.fieldTree.loadField(fieldID);
		int maxHeight = field.layers.maxHeight();
		selectedTiles = initial.getMask(field.sizeX, field.sizeY, maxHeight);
		spnH.setMaximum(maxHeight);
		canvas.setField(field);
		canvas.setHeight(spnH.getValue() - 1);
		pack();
	}

	@Override
	protected Portal createResult(Portal initial) {
		return new Portal(selectedTiles);
	}

}
