package gui.shell.field;

import gui.Tooltip;
import gui.Vocab;
import gui.helper.FieldHelper;
import gui.views.fieldTree.*;
import lui.base.LFlags;
import lui.container.LPanel;
import lui.container.LScrollPanel;
import lui.dialog.LObjectDialog;
import lui.dialog.LWindow;
import lui.graphics.LPainter;
import lui.widget.LLabel;
import lui.widget.LSpinner;
import project.Project;
import data.field.Field;
import data.field.Transition.Portal;

public class PortalDialog extends LObjectDialog<Portal> {

	protected FieldCanvas canvas;
	protected LSpinner spnH;
	protected LScrollPanel scrolledComposite;
	protected LLabel lblPos;
	protected boolean[][][] selectedTiles;
	protected int fieldID;

	/**
	 * @wbp.parser.constructor
	 */
	public PortalDialog(LWindow parent, int fieldID) {
		super(parent, 640, 480, Vocab.instance.PORTALSHELL);
		this.fieldID = fieldID;
	}

	@Override
	protected void createContent(int style) {
		super.createContent(style);
		content.setGridLayout(2);

		scrolledComposite = new LScrollPanel(content);
		scrolledComposite.getCellData().setExpand(true, true);

		canvas = new PortalCanvas(scrolledComposite);

		LPanel bottom = new LPanel(content);
		bottom.setGridLayout(3);
		bottom.getCellData().setExpand(true, false);

		new LLabel(bottom, LFlags.CENTER, Vocab.instance.HEIGHT, Tooltip.instance.POSITIONH);
		spnH = new LSpinner(bottom);
		spnH.getCellData().setTargetSize(80, -1);
		spnH.setMinimum(1);
		spnH.setValue(1);
		spnH.addModifyListener(event -> {
            canvas.setHeight(event.newValue - 1);
            canvas.redraw();
        });

		lblPos = new LLabel(bottom, "(-99, -99, -99)");
		lblPos.getCellData().setExpand(true, false);
		lblPos.getCellData().setAlignment(LFlags.RIGHT);

		canvas.onTileEnter = t -> {
			lblPos.setText("(" + (t.dx + 1) + "," + (t.dy + 1) + "," + (t.height + 1) + ")");
			bottom.refreshLayout();
		};

	}

	public void open(Portal initial) {
		super.open(initial);
		if (!initial.isEmpty()) {
			spnH.setValue(initial.getLast().height);
		} else {
			spnH.setValue(1);
		}
		Field field = Project.current.fieldTree.loadField(fieldID);
		int maxHeight = field.layers.maxHeight();
		selectedTiles = initial.getMask(field.sizeX, field.sizeY, maxHeight);
		spnH.setMaximum(maxHeight);
		canvas.setField(field);
		canvas.setHeight(spnH.getValue() - 1);
	}

	@Override
	protected Portal createResult(Portal initial) {
		return new Portal(selectedTiles);
	}

	private class PortalCanvas extends FieldCanvasOpenGL {
		public PortalCanvas(LScrollPanel parent) {
			super(parent);
		}

		@Override
		public void onTileClick() {
			selectedTiles[currentTile.height][currentTile.dx][currentTile.dy] = !selectedTiles[currentTile.height][currentTile.dx][currentTile.dy];
			canvas.redraw();
		}

		@Override
		public void drawSelection(LPainter painter) {
			if (selectedTiles == null)
				return;
			painter.setPaintColor(selectionColor);
			for (int h = 0; h < selectedTiles.length; h++)
				for (int x = 0; x < selectedTiles[h].length; x++)
					for (int y = 0; y < selectedTiles[h][x].length; y++)
						if (selectedTiles[h][x][y])
							drawCursor(painter, FieldHelper.math.tile2Pixel(x, y, h));
		}
	}

}
